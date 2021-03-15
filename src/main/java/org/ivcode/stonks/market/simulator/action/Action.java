package org.ivcode.stonks.market.simulator.action;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Action {
	UUID getId();
	LocalDateTime getDate();
	String getNote();
	void visit(ActionVisitor visitor);
}
