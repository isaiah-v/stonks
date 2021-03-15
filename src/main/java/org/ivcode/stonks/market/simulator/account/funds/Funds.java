package org.ivcode.stonks.market.simulator.account.funds;

import java.time.LocalDate;
import java.util.List;

public interface Funds {
	FundSummary getSummary(LocalDate date);
	double getTotal();
	List<FundAction> getFundActions();
}
