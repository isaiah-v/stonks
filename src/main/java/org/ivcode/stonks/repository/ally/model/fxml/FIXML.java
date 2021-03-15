package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FIXML", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
@XmlAccessorType(XmlAccessType.FIELD)
public class FIXML {

	@XmlElement(name = "ExecRpt", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private ExecutionReport executionReport;

	@XmlElement(name = "Order", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private NewOrder newOrder;

	public ExecutionReport getExecutionReport() {
		return executionReport;
	}

	public void setExecutionReport(ExecutionReport executionReport) {
		this.executionReport = executionReport;
	}

	public NewOrder getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(NewOrder newOrder) {
		this.newOrder = newOrder;
	}

}
