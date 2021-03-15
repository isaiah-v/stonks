package org.ivcode.stonks.market.simulator.businessday;

import java.time.LocalDate;
import java.util.Date;

public interface BusinessDays {
	int getBusinessDays(Date from, Date to);

	int getBusinessDays(LocalDate from, LocalDate to);

	/**
	 * Gets the business date for the given business days in the future or past. A
	 * negative number of business days implies that we should search for business
	 * days in the past. Because we may want to find the closet business date, zero
	 * business days, into the past the number of negative business days is computed
	 * by finding {@code (days = -1 * (businessDays +1))}
	 * 
	 * @param date         The start date
	 * @param businessDays The number of business days from the start date. Note
	 *                     that past business days are computed by
	 *                     {@code (days = -1 * (businessDays +1))}
	 * @return The computed business date.
	 */
	LocalDate getBusinessDate(LocalDate date, int businessDays);
}
