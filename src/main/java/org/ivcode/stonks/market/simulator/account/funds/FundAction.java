package org.ivcode.stonks.market.simulator.account.funds;

import static org.apache.commons.lang3.Validate.*;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FundAction {

	private final double amount;
	private final LocalDate settleDate;

	public FundAction(double amount, LocalDate settleDate) {
		this.amount = notNull(amount);
		this.settleDate = notNull(settleDate);
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getSettleDate() {
		return settleDate;
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
