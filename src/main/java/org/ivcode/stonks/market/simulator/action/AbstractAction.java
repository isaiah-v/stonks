package org.ivcode.stonks.market.simulator.action;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.ivcode.stonks.utils.TimeUtils;

abstract class AbstractAction implements Action {
	
	private final UUID uuid;
	private final LocalDateTime date;
	private final String note;
	
	public AbstractAction(Date date, String note) {
		this(TimeUtils.toLocalDateTime(date), note);
	}
	
	public AbstractAction(LocalDateTime date, String note) {
		this(UUID.randomUUID(), date, note);
	}
	
	public AbstractAction(UUID uuid, LocalDateTime date, String note) {
		this.uuid = uuid;
		this.date = date;
		this.note = note;
	}
	
	@Override
	public UUID getId() {
		return uuid;
	}
	
	@Override
	public LocalDateTime getDate() {
		return date;
	}
	
	@Override
	public String getNote() {
		return note;
	}
}
