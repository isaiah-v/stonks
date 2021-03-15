package org.ivcode.stonks.repository.ally.model;

import java.time.LocalTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ivcode.stonks.repository.ally.model.adapters.ClockMarketStatusAdapter;
import org.ivcode.stonks.repository.ally.model.adapters.ISOLocalTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClockStatus {

	@XmlJavaTypeAdapter(value = ClockMarketStatusAdapter.class)
	@XmlElement(name = "current")
	private ClockMarketStatus current;

	@XmlJavaTypeAdapter(value = ClockMarketStatusAdapter.class)
	@XmlElement(name = "next")
	private ClockMarketStatus next;

	@XmlJavaTypeAdapter(value=ISOLocalTimeAdapter.class)
	@XmlElement(name = "change_at")
	private LocalTime changeAt;

	public ClockMarketStatus getCurrent() {
		return current;
	}

	public void setCurrent(ClockMarketStatus current) {
		this.current = current;
	}

	public ClockMarketStatus getNext() {
		return next;
	}

	public void setNext(ClockMarketStatus next) {
		this.next = next;
	}

	public LocalTime getChangeAt() {
		return changeAt;
	}

	public void setChangeAt(LocalTime changeAt) {
		this.changeAt = changeAt;
	}

}
