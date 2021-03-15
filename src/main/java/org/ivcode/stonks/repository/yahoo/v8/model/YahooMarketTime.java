package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.Date;
import java.util.List;

public class YahooMarketTime {
	private String id;
	private String name;
	private String status;
	private String yfit_market_id;
	private Date close;
	private String message;
	private Date open;
	private String yfit_market_status;
	private Date time;
	private List<MarketTimeZone> timezone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYfit_market_id() {
		return yfit_market_id;
	}

	public void setYfit_market_id(String yfit_market_id) {
		this.yfit_market_id = yfit_market_id;
	}

	public Date getClose() {
		return close;
	}

	public void setClose(Date close) {
		this.close = close;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getOpen() {
		return open;
	}

	public void setOpen(Date open) {
		this.open = open;
	}

	public String getYfit_market_status() {
		return yfit_market_status;
	}

	public void setYfit_market_status(String yfit_market_status) {
		this.yfit_market_status = yfit_market_status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<MarketTimeZone> getTimezone() {
		return timezone;
	}

	public void setTimezone(List<MarketTimeZone> timezone) {
		this.timezone = timezone;
	}
}
