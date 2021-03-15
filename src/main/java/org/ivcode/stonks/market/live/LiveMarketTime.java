package org.ivcode.stonks.market.live;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketStatusListener;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.repository.yahoo.v8.model.Chart;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartCurrentTradingPeriods;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartMeta;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResult;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;
import org.ivcode.stonks.service.yahoo.v8.model.Range;
import org.ivcode.stonks.utils.EpochUtils;
import org.ivcode.stonks.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class LiveMarketTime implements MarketTime, AutoCloseable, Runnable {

	private static final String TEST_SYMBOL = "^DJI";
	private static final LocalTime TIME_REFRESH = LocalTime.parse("01:00");
	
	private final Set<MarketStatusListener> listeners = new HashSet<>();
	
	private final YahooService service;
	
	private ZonedDateTime pre;
	private ZonedDateTime regular;
	private ZonedDateTime post;
	private ZonedDateTime close;
	
	private ZonedDateTime nextUpdate = null;
	
	private MarketStatus status;
	
	private boolean isRunning = false;
	
	@Autowired
	public LiveMarketTime(YahooService service) {
		this.service = service;
	}

	@Override
	public Date getTime() {
		// The Date on this computer's "should" be fairly close to that of the current market
		return new Date();
	}

	@Override
	public MarketStatus getMarketStatus() {
		return status;
	}

	@Override
	public boolean addMarketStatusListener(MarketStatusListener listener) {
		return listeners.add(listener);
	}

	@Override
	public boolean removeMarketStatusListener(MarketStatusListener listener) {
		return listeners.remove(listener);
	}
	
	private void updateMarketStatus(ZonedDateTime now) {
		if(now.isBefore(pre)) {
			setMarketStatus(MarketStatus.CLOSED);
			return;
		}
		
		if(now.isBefore(regular)) {
			setMarketStatus(MarketStatus.PRE_MARKET);
			return;
		}
		
		if(now.isBefore(post)) {
			setMarketStatus(MarketStatus.OPEN);
			return;
		}
		
		if(now.isBefore(close)) {
			setMarketStatus(MarketStatus.POST_MARKET);
			return;
		}
		
		setMarketStatus(MarketStatus.CLOSED);
	}
	
	private void setMarketStatus(MarketStatus newStatus) {
		if(newStatus==null) {
			throw new IllegalArgumentException("status cannot be null");
		}
		
		if(newStatus.equals(this.status)) {
			return;
		}
		this.status = newStatus;
		
		for(MarketStatusListener listener : listeners) {
			try {
				listener.onMarketStatusChange(newStatus);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void run() {
		if(this.isRunning) {
			throw new IllegalStateException("only one instance can run at a time");
		}
		this.isRunning = true;
		
		while(this.isRunning) {
			// if something goes wrong, wait 1 hour before trying again
			long wait = TimeUnit.HOURS.toMillis(1);
			try {
				ZonedDateTime now = TimeUtils.now();
				
				updateDay(now);
				updateMarketStatus(now);
				wait = calcWaitTime(now);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				this.wait(wait);
			} catch (InterruptedException e) {
				// TODO fix this
				e.printStackTrace();
			}
		}
	}
	
	private void updateDay(ZonedDateTime now) throws IOException {
		if(nextUpdate!=null && now.isBefore(nextUpdate)) {
			return;
		}
		
		ChartResponse chartResponse = service.getChart(TEST_SYMBOL, Range.ONE_DAY, Interval.ONE_DAY);
		updateDay(chartResponse);
		
		nextUpdate = now.with(TIME_REFRESH).plusDays(1);
	}
	
	private long calcWaitTime(ZonedDateTime now) {
		long test = -1;
		
		long nowMilli = TimeUtils.toMilli(now);
		
		test = TimeUtils.toMilli(pre) - nowMilli;
		if(test>0) {
			return test;
		}
		
		test = TimeUtils.toMilli(regular) - nowMilli;
		if(test>0) {
			return test;
		}
		
		test = TimeUtils.toMilli(post) - nowMilli;
		if(test>0) {
			return test;
		}
		
		test = TimeUtils.toMilli(close) - nowMilli;
		if(test>0) {
			return test;
		}
		
		test = TimeUtils.toMilli(nextUpdate) - nowMilli;
		if(test>0) {
			return test;
		}
		
		return 0;
	}
	
	private void updateDay(ChartResponse chartResponse) {
		if(chartResponse==null) {
			throw new IllegalStateException();
		}
		
		Chart chart = chartResponse.getChart();
		if(chart==null) {
			throw new IllegalStateException();
		}
		
		List<ChartResult> results = chart.getResult();
		if(results==null) {
			throw new IllegalStateException();
		}
		
		ChartResult result = results.get(0);
		if(result==null) {
			throw new IllegalStateException();
		}
		
		ChartMeta meta = result.getMeta();
		if(meta==null) {
			throw new IllegalStateException();
		}
		
		ChartCurrentTradingPeriods periods = meta.getCurrentTradingPeriod();
		if(periods==null) {
			throw new IllegalArgumentException();
		}
		
		Long preTime = periods.getPre().getStart();
		Long regTime = periods.getRegular().getStart();
		Long postTime = periods.getPost().getStart();
		Long clostTime = periods.getPost().getEnd();
		
		Date preDate = EpochUtils.toDate(preTime);
		Date regDate = EpochUtils.toDate(regTime);
		Date postDate = EpochUtils.toDate(postTime);
		Date clostDate = EpochUtils.toDate(clostTime);
		
		ZonedDateTime pre = TimeUtils.toZonedDateTime(preDate);
		ZonedDateTime reg = TimeUtils.toZonedDateTime(regDate);
		ZonedDateTime post = TimeUtils.toZonedDateTime(postDate);
		ZonedDateTime close = TimeUtils.toZonedDateTime(clostDate);
		
		this.pre = pre;
		this.regular = reg;
		this.post = post;
		this.close = close;
	}
	
	@Override
	public synchronized void close() {
		if(!this.isRunning) {
			return;
		}
		this.isRunning = false;
		this.notifyAll();
	}
}
