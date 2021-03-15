package org.ivcode.stonks.repository.ally.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ivcode.stonks.repository.ally.model.adapters.ISOLocalDateAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.ISOZonedDateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSalesQuote {

	@XmlElement
	@XmlJavaTypeAdapter(ISOLocalDateAdapter.class)
	private LocalDate date;

	@XmlElement(name = "datetime")
	@XmlJavaTypeAdapter(ISOZonedDateTimeAdapter.class)
	private ZonedDateTime dateTime;

	@XmlElement(name = "hi")
	private Double high;

	@XmlElement(name = "incr_vl")
	private Integer increaseVolume;

	@XmlElement(name = "last")
	private Double last;

	@XmlElement(name = "lo")
	private Double low;

	@XmlElement(name = "opn")
	private Double open;

	@XmlElement(name = "timestamp")
	@XmlJavaTypeAdapter(ISOZonedDateTimeAdapter.class)
	private ZonedDateTime timeStamp;

	@XmlElement(name = "vl")
	private Integer volumn;

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

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Integer getIncreaseVolume() {
		return increaseVolume;
	}

	public void setIncreaseVolume(Integer increaseVolume) {
		this.increaseVolume = increaseVolume;
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

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public ZonedDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(ZonedDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getVolumn() {
		return volumn;
	}

	public void setVolumn(Integer volumn) {
		this.volumn = volumn;
	}

}
