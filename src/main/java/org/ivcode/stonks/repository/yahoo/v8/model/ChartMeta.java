package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class ChartMeta {
	private String currency;
	private String symbol;
	private String exchangeName;
	private String instrumentType;
	private Integer firstTradeDate;
	private Integer regularMarketTime;
	private Integer gmtoffset;
	private String timezone;
	private String exchangeTimezoneName;
	private Double regularMarketPrice;
	private Double chartPreviousClose;
	private Integer priceHint;
	private ChartCurrentTradingPeriods currentTradingPeriod;
	private ChartTradingPeriods tradingPeriods;
	private String dataGranularity;
	private String range;
	private List<String> validRanges;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}

	public Integer getFirstTradeDate() {
		return firstTradeDate;
	}

	public void setFirstTradeDate(Integer firstTradeDate) {
		this.firstTradeDate = firstTradeDate;
	}

	public Integer getRegularMarketTime() {
		return regularMarketTime;
	}

	public void setRegularMarketTime(Integer regularMarketTime) {
		this.regularMarketTime = regularMarketTime;
	}

	public Integer getGmtoffset() {
		return gmtoffset;
	}

	public void setGmtoffset(Integer gmtoffset) {
		this.gmtoffset = gmtoffset;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getExchangeTimezoneName() {
		return exchangeTimezoneName;
	}

	public void setExchangeTimezoneName(String exchangeTimezoneName) {
		this.exchangeTimezoneName = exchangeTimezoneName;
	}

	public Double getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(Double regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public Double getChartPreviousClose() {
		return chartPreviousClose;
	}

	public void setChartPreviousClose(Double chartPreviousClose) {
		this.chartPreviousClose = chartPreviousClose;
	}

	public Integer getPriceHint() {
		return priceHint;
	}

	public void setPriceHint(Integer priceHint) {
		this.priceHint = priceHint;
	}

	public ChartCurrentTradingPeriods getCurrentTradingPeriod() {
		return currentTradingPeriod;
	}

	public void setCurrentTradingPeriod(ChartCurrentTradingPeriods currentTradingPeriod) {
		this.currentTradingPeriod = currentTradingPeriod;
	}

	public ChartTradingPeriods getTradingPeriods() {
		return tradingPeriods;
	}

	public void setTradingPeriods(ChartTradingPeriods tradingPeriods) {
		this.tradingPeriods = tradingPeriods;
	}

	public String getDataGranularity() {
		return dataGranularity;
	}

	public void setDataGranularity(String dataGranularity) {
		this.dataGranularity = dataGranularity;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public List<String> getValidRanges() {
		return validRanges;
	}

	public void setValidRanges(List<String> validRanges) {
		this.validRanges = validRanges;
	}

}
