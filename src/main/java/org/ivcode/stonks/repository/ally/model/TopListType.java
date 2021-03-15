package org.ivcode.stonks.repository.ally.model;

public enum TopListType {
	/** Top losers by dollar amount */
	TOP_LOSERS("toplosers"),
	/** Top percentage losers */
	TOP_PERCENTAGE_LOSERS("toppctlosers"),
	/** Top volume */
	TOP_VOLUME("topvolume"),
	/** Top active */
	TOP_ACTIVE("topactive"),
	/** Top gainers by dollar amount */
	TOP_GAINERS("topgainers"),
	/** Top percentage gainers */
	TOP_PERCENTAGE_GAINERS("toppctgainers");

	private final String name;

	private TopListType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static String getName(TopListType type) {
		return type == null ? null : type.name;
	}
}
