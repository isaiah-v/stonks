package org.ivcode.stonks.repository.ally.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ivcode.stonks.repository.ally.model.adapters.BooleanAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.ISOLocalDateAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.ISOLocalTimeAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.ISOZonedDateTimeAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.NullNa;

@XmlAccessorType(XmlAccessType.FIELD)
public class Quote {

	@XmlElement(name = "adp_100")
	private Double averageDailyPrice100;

	@XmlElement(name = "adp_200")
	private Double averageDailyPrice200;

	@XmlElement(name = "adp_50")
	private Double averageDailyPrice50;

	@XmlElement(name = "adv_21")
	private Double averageDailyVolume21;

	@XmlElement(name = "adv_30")
	private Double averageDailyVolume30;

	@XmlElement(name = "adv_90")
	private Double averageDailyVolume90;

	@XmlElement(name = "ask")
	private Double askPrice;

	@XmlJavaTypeAdapter(ISOLocalTimeAdapter.class)
	@XmlElement(name = "ask_time")
	private LocalTime askTime;

	@XmlElement(name = "asksz")
	private Integer latestAskSize;

	@XmlElement(name = "basis")
	private Integer basis;

	@XmlElement(name = "beta")
	private Double beta;

	@XmlElement(name = "bid")
	private Double bid;

	@XmlJavaTypeAdapter(ISOLocalTimeAdapter.class)
	@XmlElement(name = "bid_time")
	private LocalTime bidTime;

	@XmlElement(name = "bidsz")
	private Integer bidSize;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "bidtick")
	private String bidTick;

	@XmlElement(name = "chg")
	private Double change;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "chg_sign")
	private String changeSign;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "chg_t")
	private String changeText;

	@XmlElement(name = "cl")
	private Double close;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "contract_size")
	private String contractSize;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "cusip")
	private String cusip;

	@XmlJavaTypeAdapter(value = ISOLocalDateAdapter.class)
	@XmlElement(name = "date")
	private LocalDate date;

	@XmlJavaTypeAdapter(value = ISOZonedDateTimeAdapter.class)
	@XmlElement(name = "datetime")
	private ZonedDateTime dateTime;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "days_to_expiration")
	private String daysToExpiration;

	@XmlElement(name = "div")
	private Double dividend;

	@XmlJavaTypeAdapter(value = ISOLocalDateAdapter.class)
	@XmlElement(name = "divexdate")
	private LocalDate exDividendDate;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "divfreq")
	private String dividendFrequency;

	@XmlJavaTypeAdapter(value = ISOLocalDateAdapter.class)
	@XmlElement(name = "divpaydt")
	private LocalDate dividendPayDate;

	@XmlElement(name = "dollar_value")
	private Double dollarValue;

	@XmlElement(name = "eps")
	private Double earningsPerShare;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "exch")
	private String exchangeCode;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "exch_desc")
	private String exchangeDescription;

	@XmlElement(name = "hi")
	private Double high;

	@XmlElement(name = "iad")
	private Double indicatedAnnualDividend;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "idelta")
	private String deltaImpliedVolatility;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "igamma")
	private String gammaImpliedVolatility;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "imp_volatility")
	private String impliedVolatility;

	@XmlElement(name = "incr_vl")
	private Double incrementalVolume;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "irho")
	private String impliedRho;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "issue_desc")
	private String issueDescription;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "itheta")
	private String itheta;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "ivega")
	private String ivega;

	@XmlElement(name = "last")
	private Double last;

	@XmlElement(name = "lo")
	private Double low;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "name")
	private String name;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "op_delivery")
	private String optionDelivery;

	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlElement(name = "op_flag")
	private Boolean optionFlag;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "op_style")
	private String optionStyle;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "op_subclass")
	private String optionSubclass;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "openinterest")
	private String openInterest;

	@XmlElement(name = "opn")
	private Double open;

	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "opt_val")
	private String optionValue;

	@XmlElement(name = "pchg")
	private Double percentageChange;
	
	@XmlJavaTypeAdapter(NullNa.class)
	@XmlElement(name = "pchg_sign")
	private String percentageChangeSign;
	
	@XmlElement(name = "pcls")
	private Double priorDayClose;
	
	@XmlElement(name = "pe")
	private Double priceEarningsRatio;
	
	@XmlElement(name = "phi")
	private Double priorDayHigh;
	
	@XmlElement(name = "plo")
	private Double priorDayLow;
	
	@XmlElement(name = "popn")
	private Double priorDayOpen;
	
	public Double getAverageDailyPrice100() {
		return averageDailyPrice100;
	}

	public void setAverageDailyPrice100(Double averageDailyPrice100) {
		this.averageDailyPrice100 = averageDailyPrice100;
	}

	public Double getAverageDailyPrice200() {
		return averageDailyPrice200;
	}

	public void setAverageDailyPrice200(Double averageDailyPrice200) {
		this.averageDailyPrice200 = averageDailyPrice200;
	}

	public Double getAverageDailyPrice50() {
		return averageDailyPrice50;
	}

	public void setAverageDailyPrice50(Double averageDailyPrice50) {
		this.averageDailyPrice50 = averageDailyPrice50;
	}

	public Double getAverageDailyVolume21() {
		return averageDailyVolume21;
	}

	public void setAverageDailyVolume21(Double averageDailyVolume21) {
		this.averageDailyVolume21 = averageDailyVolume21;
	}

	public Double getAverageDailyVolume30() {
		return averageDailyVolume30;
	}

	public void setAverageDailyVolume30(Double averageDailyVolume30) {
		this.averageDailyVolume30 = averageDailyVolume30;
	}

	public Double getAverageDailyVolume90() {
		return averageDailyVolume90;
	}

	public void setAverageDailyVolume90(Double averageDailyVolume90) {
		this.averageDailyVolume90 = averageDailyVolume90;
	}

	public Double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(Double askPrice) {
		this.askPrice = askPrice;
	}

	public LocalTime getAskTime() {
		return askTime;
	}

	public void setAskTime(LocalTime askTime) {
		this.askTime = askTime;
	}

	public Integer getLatestAskSize() {
		return latestAskSize;
	}

	public void setLatestAskSize(Integer latestAskSize) {
		this.latestAskSize = latestAskSize;
	}

	public Integer getBasis() {
		return basis;
	}

	public void setBasis(Integer basis) {
		this.basis = basis;
	}

	public Double getBeta() {
		return beta;
	}

	public void setBeta(Double beta) {
		this.beta = beta;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public LocalTime getBidTime() {
		return bidTime;
	}

	public void setBidTime(LocalTime bidTime) {
		this.bidTime = bidTime;
	}

	public Integer getBidSize() {
		return bidSize;
	}

	public void setBidSize(Integer bidSize) {
		this.bidSize = bidSize;
	}

	public String getBidTick() {
		return bidTick;
	}

	public void setBidTick(String bidTick) {
		this.bidTick = bidTick;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public String getChangeSign() {
		return changeSign;
	}

	public void setChangeSign(String changeSign) {
		this.changeSign = changeSign;
	}

	public String getChangeText() {
		return changeText;
	}

	public void setChangeText(String changeText) {
		this.changeText = changeText;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public String getContractSize() {
		return contractSize;
	}

	public void setContractSize(String contractSize) {
		this.contractSize = contractSize;
	}

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getDaysToExpiration() {
		return daysToExpiration;
	}

	public void setDaysToExpiration(String daysToExpiration) {
		this.daysToExpiration = daysToExpiration;
	}

	public Double getDividend() {
		return dividend;
	}

	public void setDividend(Double dividend) {
		this.dividend = dividend;
	}

	public LocalDate getExDividendDate() {
		return exDividendDate;
	}

	public void setExDividendDate(LocalDate exDividendDate) {
		this.exDividendDate = exDividendDate;
	}

	public String getDividendFrequency() {
		return dividendFrequency;
	}

	public void setDividendFrequency(String dividendFrequency) {
		this.dividendFrequency = dividendFrequency;
	}

	public LocalDate getDividendPayDate() {
		return dividendPayDate;
	}

	public void setDividendPayDate(LocalDate dividendPayDate) {
		this.dividendPayDate = dividendPayDate;
	}

	public Double getDollarValue() {
		return dollarValue;
	}

	public void setDollarValue(Double dollarValue) {
		this.dollarValue = dollarValue;
	}

	public Double getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(Double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getExchangeDescription() {
		return exchangeDescription;
	}

	public void setExchangeDescription(String exchangeDescription) {
		this.exchangeDescription = exchangeDescription;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getIndicatedAnnualDividend() {
		return indicatedAnnualDividend;
	}

	public void setIndicatedAnnualDividend(Double indicatedAnnualDividend) {
		this.indicatedAnnualDividend = indicatedAnnualDividend;
	}

	public String getDeltaImpliedVolatility() {
		return deltaImpliedVolatility;
	}

	public void setDeltaImpliedVolatility(String deltaImpliedVolatility) {
		this.deltaImpliedVolatility = deltaImpliedVolatility;
	}

	public String getGammaImpliedVolatility() {
		return gammaImpliedVolatility;
	}

	public void setGammaImpliedVolatility(String gammaImpliedVolatility) {
		this.gammaImpliedVolatility = gammaImpliedVolatility;
	}

	public String getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(String impliedVolatility) {
		this.impliedVolatility = impliedVolatility;
	}

	public Double getIncrementalVolume() {
		return incrementalVolume;
	}

	public void setIncrementalVolume(Double incrementalVolume) {
		this.incrementalVolume = incrementalVolume;
	}

	public String getImpliedRho() {
		return impliedRho;
	}

	public void setImpliedRho(String impliedRho) {
		this.impliedRho = impliedRho;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getItheta() {
		return itheta;
	}

	public void setItheta(String itheta) {
		this.itheta = itheta;
	}

	public String getIvega() {
		return ivega;
	}

	public void setIvega(String ivega) {
		this.ivega = ivega;
	}

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptionDelivery() {
		return optionDelivery;
	}

	public void setOptionDelivery(String optionDelivery) {
		this.optionDelivery = optionDelivery;
	}

	public Boolean getOptionFlag() {
		return optionFlag;
	}

	public void setOptionFlag(Boolean optionFlag) {
		this.optionFlag = optionFlag;
	}

	public String getOptionStyle() {
		return optionStyle;
	}

	public void setOptionStyle(String optionStyle) {
		this.optionStyle = optionStyle;
	}

	public String getOptionSubclass() {
		return optionSubclass;
	}

	public void setOptionSubclass(String optionSubclass) {
		this.optionSubclass = optionSubclass;
	}

	public String getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(String openInterest) {
		this.openInterest = openInterest;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

}
