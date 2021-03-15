package org.ivcode.stonks.repository.yahoo.websocket;

public interface YahooTickerListener {
	void onTick(YahooTick tick);
}
