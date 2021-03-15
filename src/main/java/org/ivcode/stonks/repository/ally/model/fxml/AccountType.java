package org.ivcode.stonks.repository.ally.model.fxml;

/**
 * Type of account associated with an order
 * 
 * @author isaiah
 */
public enum AccountType {

	/** Account is carried on customer Side of Books */
	CUSTOMER(1),
	/** Account is carried on non-Customer Side of books */
	NON_CUSTOMER(2),
	/** House Trader */
	HOUSE(3),
	/** Floor Trader */
	FLOOR(4),
	/** Account is carried on non-customer side of books and is cross margined */
	NON_CUSTOMER_CROSS_MARGINED(6),
	/** Account is house trader and is cross margined */
	HOUSE_CROSS_MARGINED(7),
	/** Joint Backoffice Account (JBO) */
	JOINT_BACKOFFICE_ACCOUNT(8);

	private final int id;

	private AccountType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static AccountType getAccountType(Integer id) {
		if (id == null) {
			return null;
		}

		for (AccountType accountType : AccountType.values()) {
			if (id.equals(accountType.id)) {
				return accountType;
			}
		}

		return null;
	}

	public static Integer getId(AccountType accountType) {
		return accountType == null ? null : accountType.id;
	}
}
