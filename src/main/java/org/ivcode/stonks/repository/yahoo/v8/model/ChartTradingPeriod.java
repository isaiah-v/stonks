package org.ivcode.stonks.repository.yahoo.v8.model;

public class ChartTradingPeriod {
	private String timezone;
	private Long end;
	private Long start;
	private Integer gmtoffset;

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Integer getGmtoffset() {
		return gmtoffset;
	}

	public void setGmtoffset(Integer gmtoffset) {
		this.gmtoffset = gmtoffset;
	}

}
