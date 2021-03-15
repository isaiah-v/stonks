package org.ivcode.stonks.repository.yahoo.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.ivcode.stonks.utils.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@ClientEndpoint
public class YahooWebSocket {
	
	// TODO seperate the threading logic from the client
	
	
	
	private static final String THREAD_NAME = YahooWebSocket.class.getSimpleName();
	private static final boolean THREAD_DAEMON = true;
	
	private static final long WAIT_RECONNECT = TimeUnit.SECONDS.toMillis(30);
	private static final long OFFER_TIMEOUT = 90;
	private static final TimeUnit OFFER_TIMEOUT_UNIT = TimeUnit.SECONDS;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YahooWebSocket.class);

	private final URI uri;
	private final Set<String> subscriptionSymbols = new HashSet<>();
	private final YahooTickerListener listener;
	private final ObjectMapper mapper;
	
	
	private final ThreadFactory threadFactory;
	private Thread processingThread;
	
	private final BlockingDeque<YahooTick> deque = new LinkedBlockingDeque<>();
	
	private Session session;
	private boolean isClosed;
	
	public YahooWebSocket(URI uri, ObjectMapper mapper, YahooTickerListener listener) {
		this.uri = uri;
		this.listener = listener;
		this.mapper = mapper;
		
		threadFactory = new BasicThreadFactory(THREAD_NAME, THREAD_DAEMON);
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		LOGGER.trace("OPEN : WebSocket");
		this.session = session;
		
		stopProcessingThread();
		startProcessingThread();
		
		sendSubscribeMessage(subscriptionSymbols);
	}
	
	@OnClose
	public void onClose() throws IOException {
		LOGGER.trace("CLOSE : WebSocket");
		this.session = null;
		
		stopProcessingThread();
		
		if(this.shouldBeOpen()) {
			forkReconnect();
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			YahooProto.Quote quote = YahooProto.Quote.parseFrom(Base64.getDecoder().decode(message));
			
			//LOGGER.trace("PROTO: {}", message);
			//LOGGER.trace("MESSAGE: \n{}", quote);
			
			YahooTick tick = new YahooTick();
			tick.setSymbol(quote.getSymbol());
			tick.setRegularMarketPrice(quote.getRegularMarketPrice());
			tick.setRegularMarketTime(quote.getRegularMarketTime());
			tick.setExchange(quote.getExchange());
			tick.setRegularMarketChangePercent(quote.getRegularMarketChangePercent());
			tick.setRegularMarketVolume(quote.getRegularMarketVolume());
			tick.setRegularMarketChange(quote.getRegularMarketChange());
			
			deque.offer(tick, OFFER_TIMEOUT, OFFER_TIMEOUT_UNIT);
		} catch (InterruptedException e) {
			Thread.interrupted();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void stopProcessingThread() {
		Thread processingThread = this.processingThread;
		if(processingThread!=null && processingThread.isAlive()) {
			processingThread.interrupt();
			// TODO block until closed
		}
	}
	
	private void startProcessingThread() {
		threadFactory.newThread(this::processTicks).start();
	}
	
	private void processTicks() {
		
		synchronized (threadFactory) {
			if(processingThread!=null) {
				throw new IllegalArgumentException("only one processing thread is allowed");
			}
			processingThread = Thread.currentThread();
		}
		
		try {
			while(!deque.isEmpty() || !this.isClosed) {
				try {
					YahooTick tick = deque.take();
					listener.onTick(tick);
				} catch (RuntimeException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}  catch (InterruptedException e) {
			LOGGER.trace("TICK PROCESSOR ENDED");
		}
		
		synchronized (threadFactory) {
			processingThread = null;
		}
	}
	
	public void setSubscriptionSymbols(Collection<String> symbols) throws IOException {
		Set<String> symbolsAdded = new HashSet<String>(symbols);
		symbolsAdded.removeAll(this.subscriptionSymbols);
		
		if(!symbolsAdded.isEmpty()) {
			sendSubscribeMessage(symbolsAdded);
		}
		
		Set<String> symbolsRemoved = new HashSet<String>(this.subscriptionSymbols);
		symbolsRemoved.removeAll(symbols);
		
		if(!symbolsRemoved.isEmpty()) {
			sendUnsubscribe(symbolsRemoved);
		}
		
		this.subscriptionSymbols.clear();
		this.subscriptionSymbols.addAll(symbols);
		
		if(isDisconncected() && shouldBeOpen()) {
			conncect();
		} else if(!isDisconncected() && !shouldBeOpen()) {
			disconncect();
		}
	}

	public Set<String> getSubscriptionSymbols() {
		return Collections.unmodifiableSet(subscriptionSymbols);
	}

	public boolean isDisconncected() {
		return session==null ? true : !session.isOpen();
	}
	
	public void open() throws IOException {
		this.isClosed = false;
		
		if(isDisconncected() && shouldBeOpen()) {
			conncect();
		}
	}
	
	public void close() throws IOException {
		this.isClosed = true;
		
		if(!isDisconncected() && !shouldBeOpen()) {
			disconncect();
		}
	}
	
	private boolean shouldBeOpen() {
		return !this.isClosed && !this.subscriptionSymbols.isEmpty();
	}
	
	public void conncect() throws IOException {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.setDefaultMaxSessionIdleTimeout(-1);
			container.connectToServer(this, uri);
		} catch(DeploymentException e) {
			throw new IOException(e.getMessage(), e);
		}
	}
	
	public void disconncect() throws IOException {
		if(session!=null) {
			session.close();
			this.session = null;
		}
	}

	private void sendSubscribeMessage(Set<String> symbols) throws IOException {
		if(symbols.isEmpty()) {
			return;
		}
		
		SubscribeMessage message = new SubscribeMessage();
		message.setSubscribe(symbols);
		
		String txtMessage = mapper.writeValueAsString(message);
		sendMessage(txtMessage);
	}

	private void sendUnsubscribe(Set<String> symbols) throws IOException {
		UnsubscribeMessage message = new UnsubscribeMessage();
		message.setUnsubscribe(symbols);
		
		String txtMessage = mapper.writeValueAsString(message);
		sendMessage(txtMessage);
	}
	
	private void sendMessage(String message) throws IOException {
		Session session = this.session;
		
		if(session==null) {
			return;
		}
		
		LOGGER.trace("SEND MESSAGE: {}", message);
		session.getBasicRemote().sendText(message);
	}
	
	private void forkReconnect() {
		threadFactory.newThread(this::reconnect).start();
	}
	
	private void reconnect() {
		while(true) {
			try {
				Thread.sleep(WAIT_RECONNECT);
				if(shouldBeOpen()) {
					conncect();
					return;
				}
			} catch(InterruptedException e) {
				Thread.interrupted();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
