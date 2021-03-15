package org.ivcode.stonks.repository.ally.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSales {
	@XmlAttribute
	private String id;

	@XmlElement
	private Integer elapsedtime;

	@XmlElementWrapper(name = "quotes")
	@XmlElement(name = "quote")
	private List<TimeSalesQuote> quotes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getElapsedtime() {
		return elapsedtime;
	}

	public void setElapsedtime(Integer elapsedtime) {
		this.elapsedtime = elapsedtime;
	}

	public List<TimeSalesQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<TimeSalesQuote> quotes) {
		this.quotes = quotes;
	}
}
