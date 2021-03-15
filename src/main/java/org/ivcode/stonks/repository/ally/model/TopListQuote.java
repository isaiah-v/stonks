package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TopListQuote {
	@XmlElement(name = "chg")
	private Double change;

	@XmlElement(name = "chg_sign")
	private String changeSign;

	@XmlElement(name = "last")
	private Double last;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "pchg")
	private Double percentageChange;

	@XmlElement(name = "pcls")
	private Double priorDayClose;

	@XmlElement(name = "rank")
	private Integer rank;

	@XmlElement(name = "symbol")
	private String symbol;

	@XmlElement(name = "vl")
	private Integer volume;

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

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(Double percentageChange) {
		this.percentageChange = percentageChange;
	}

	public Double getPriorDayClose() {
		return priorDayClose;
	}

	public void setPriorDayClose(Double priorDayClose) {
		this.priorDayClose = priorDayClose;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
