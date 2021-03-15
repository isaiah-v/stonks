package org.ivcode.stonks.repository.ally;

import static org.ivcode.stonks.utils.TimeUtils.*;
import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.ivcode.stonks.repository.ally.model.AccountListResponse;
import org.ivcode.stonks.repository.ally.model.AccountResponse;
import org.ivcode.stonks.repository.ally.model.Clock;
import org.ivcode.stonks.repository.ally.model.HistoryResponse;
import org.ivcode.stonks.repository.ally.model.Interval;
import org.ivcode.stonks.repository.ally.model.NewsArticleResponse;
import org.ivcode.stonks.repository.ally.model.NewsSearchResponse;
import org.ivcode.stonks.repository.ally.model.Order;
import org.ivcode.stonks.repository.ally.model.OrderResponse;
import org.ivcode.stonks.repository.ally.model.Profile;
import org.ivcode.stonks.repository.ally.model.QuoteResponse;
import org.ivcode.stonks.repository.ally.model.Symbol;
import org.ivcode.stonks.repository.ally.model.TimeSales;
import org.ivcode.stonks.repository.ally.model.TopList;
import org.ivcode.stonks.repository.ally.model.TopListExchange;
import org.ivcode.stonks.repository.ally.model.TopListType;
import org.ivcode.stonks.repository.ally.model.fxml.FIXML;
import org.ivcode.stonks.repository.ally.model.fxml.NewOrder;
import org.ivcode.stonks.utils.QueryStringBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllyClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllyClient.class);
	
	private static final String FORMAT = ".xml";
	
	private static final String PROTECTED_RESOURCE_URL = "/member/profile.xml";
	private static final String TIME_SALES = "/market/timesales.xml";
	private static final String TOP_LIST = "/market/toplists/";
	private static final String CLOCK = "/market/clock.xml";
	private static final String EXT_QUOTES = "/market/ext/quotes.xml";
	private static final String NEWS_SEARCH = "/market/news/search.xml";
	private static final String NEWS_ARTICLE = "/market/news/";
	private static final String ACCOUNTS = "/accounts.xml";
	private static final String ACCOUNT_ID = "/accounts/";

	@Autowired
	@Qualifier("ally.oath")
	private OAuthService oathService;

	@Autowired
	@Qualifier("ally.token")
	private Token token;

	@Autowired
	@Qualifier("ally.unmarshaller")
	private Unmarshaller unmarshaller;
	
	@Autowired
	@Qualifier("ally.marshaller")
	private Marshaller marshaller;

	@Value("${ally.baseUrl}")
	private String baseUrl;

	public Profile getProfile() throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + PROTECTED_RESOURCE_URL);
		oathService.signRequest(token, request);
		Response response = request.send();

		return parseXml(response, Profile.class);
	}

	/**
	 * This call will return time and sales quote data based on a symbol passed as a
	 * query parameter
	 * 
	 * @param symbol    a single symbol to search on (symbol parameter is required)
	 * @param interval  the requested interval of data to be returned: 5min, 1min,
	 *                  tick
	 * @param startDate begin date for the range of data between this date and
	 *                  enddate
	 * @param endDate   end date for the range of data requested between start date
	 *                  and this date
	 * @return
	 * @throws IOException
	 */
	public TimeSales getTimeSales(String symbol, Interval interval, LocalDate startDate, LocalDate endDate)
			throws IOException {
		String queryString = new QueryStringBuilder()
				.with("symbols", symbol)
				.with("interval", Interval.getText(interval))
				.with("startdate", format(startDate, DateTimeFormatter.ISO_DATE))
				.with("enddate", format(endDate, DateTimeFormatter.ISO_DATE))
				.build();

		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + TIME_SALES + queryString);
		oathService.signRequest(token, request);
		Response response = request.send();

		return parseXml(response, TimeSales.class);
	}
	
	/**
	 * This call will return a ranked list based on the list type specified.
	 * 
	 * @param type     list types
	 * @param exchange
	 * @return the exchange
	 * @throws IOException
	 */
	public TopList getTopList(TopListType type, TopListExchange exchange) throws IOException {
		String queryString = new QueryStringBuilder()
				.with("exchange", TopListExchange.getCode(exchange))
				.build();
		
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + TOP_LIST + type.getName() + FORMAT + queryString);

		oathService.signRequest(token, request);
		Response response = request.send();

		return parseXml(response, TopList.class);
	}
	
	public Clock getClock() throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + CLOCK);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, Clock.class);
	}
	
	public QuoteResponse getQuote(List<Symbol> symbols) throws IOException {
		String queryString = new QueryStringBuilder()
				.with("symbols", symbols.stream().map(Symbol::getCode).collect(toList()))
				.build();
		
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + EXT_QUOTES + queryString);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, QuoteResponse.class);
	}
	
	public NewsSearchResponse getNewsSearch(List<String> symbols, Integer maxHits, LocalDate startDate, LocalDate endDate) throws IOException {
		String queryString = new QueryStringBuilder()
				.with("symbols", symbols)
				.with("maxhits", maxHits)
				.with("startdate", format(startDate, DateTimeFormatter.ISO_DATE))
				.with("enddate", format(endDate, DateTimeFormatter.ISO_DATE))
				.build();
		
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + NEWS_SEARCH + queryString);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, NewsSearchResponse.class);
	}
	
	public NewsArticleResponse getNewsArticle(String id) throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + NEWS_ARTICLE + id + FORMAT);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, NewsArticleResponse.class);
	}
	
	public AccountListResponse getAccountList() throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + ACCOUNTS);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, AccountListResponse.class);
	}
	
	public AccountResponse getAccount(String accountId) throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + ACCOUNT_ID + accountId + FORMAT);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, AccountResponse.class);
	}
	
	public OrderResponse getOrders(String accountId) throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + "/accounts/" + accountId + "/orders" + FORMAT);
		
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, OrderResponse.class);
	}
	
	public List<FIXML> getOrdersFIXML(String accountId) throws IOException {
		OrderResponse response = getOrders(accountId);
		
		List<FIXML> l = new ArrayList<>();
		for(Order order : response.getOrders()) {
			l.add(parseXml(order.getFixmlmessage(), FIXML.class));
		}
		
		return l;
	}
	
	public void postOrder(String accountId, NewOrder order) throws IOException {
		FIXML fixml = new FIXML();
		fixml.setNewOrder(order);
		
		String xml = toXml(fixml);
		System.out.println(xml);
		
		
		OAuthRequest request = new OAuthRequest(Verb.POST, baseUrl + "/accounts/" + accountId + "/orders" + FORMAT);
		request.addHeader("Content-Type", "application/xml");
		request.addPayload(xml);
		
		oathService.signRequest(token, request);
		
		Response response = request.send();
		parseXml(response, null);
	}
	
	public HistoryResponse getHistory(String accountId) throws IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET, baseUrl + "/accounts/" + accountId + "/history" + FORMAT);
		oathService.signRequest(token, request);
		Response response = request.send();
		
		return parseXml(response, HistoryResponse.class);
	}

	private <T> T parseXml(Response response, Class<T> type) throws IOException {
		if (response.getCode() != 200) {
			LOGGER.error(response.getBody());
			throw new IllegalStateException(response.getMessage());
		}

		String body = response.getBody();
		LOGGER.info(body);

		return parseXml(body, type);
	}
	
	private <T> T parseXml(String data, Class<T> type) throws IOException {
		try (StringReader reader = new StringReader(data)) {
			JAXBElement<T> element = unmarshaller.unmarshal(new StreamSource(reader), type);
			return element.getValue();
		} catch (JAXBException e) {
			throw new IOException(e.getMessage(), e);
		}
	}
	
	private String toXml(Object value) throws IOException {
		try (StringWriter writer = new StringWriter()) {
			marshaller.marshal(value, writer);
			
			return writer.toString();
		} catch (JAXBException e) {
			throw new IOException(e.getMessage(), e);
		}
	}
}
