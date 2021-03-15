package org.ivcode.stonks.market.live;

import java.time.LocalDateTime;
import java.util.Date;

import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.repository.yahoo.websocket.YahooTick;
import org.ivcode.stonks.utils.TimeUtils;

public class YahooTickerTransformer {
	public static Tick createTick(YahooTick yahooTick) {
		if(yahooTick==null) {
			throw new IllegalArgumentException("input not defined");
		}
		
		Long marketTime = yahooTick.getRegularMarketTime();
		
		LocalDateTime time = null;
		if(marketTime!=null) {
			time = TimeUtils.toLocalDateTime(new Date(marketTime));
		}
		
		return new Tick(yahooTick.getSymbol(), time, yahooTick.getRegularMarketPrice(), yahooTick.getRegularMarketVolume());
	}
}
