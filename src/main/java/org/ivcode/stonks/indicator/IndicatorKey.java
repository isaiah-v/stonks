package org.ivcode.stonks.indicator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IndicatorKey<T, I extends Indicator<T>> {
	private final Class<I> type;
	private final String name;

	public IndicatorKey(Class<I> type, String name) {
		this.type = type;
		this.name = name;
	}

	public Class<I> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
