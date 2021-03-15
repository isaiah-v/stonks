package org.ivcode.stonks.repository.ally.model;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ivcode.stonks.repository.ally.model.adapters.ZonedDateTimeAdapter;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Clock {
	
	@XmlAttribute
	private String id;

	@XmlElement
	private Integer elapsedtime;

	@XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
	@XmlElement(name = "date")
	private ZonedDateTime date;

	@XmlElement(name = "status")
	private ClockStatus status;

	@XmlElement(name = "message")
	private String message;

	@XmlElement(name = "unixtime")
	private Double unitTime;

	@XmlElement
	private String error;

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

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public ClockStatus getStatus() {
		return status;
	}

	public void setStatus(ClockStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(Double unitTime) {
		this.unitTime = unitTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
