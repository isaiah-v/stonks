package org.ivcode.stonks.market.ally.broker;

import java.io.IOException;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.BrokerService;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.properties.StonkPropertiesService;
import org.ivcode.stonks.repository.ally.AllyClient;
import org.ivcode.stonks.utils.CachedValue;

public class AllyBroker implements BrokerService {

	private final AllyClient client;
	private final String accountId;
	private final StonkPropertiesService propertiesService;
	
	private Double tradeMax;
	
	private CachedValue<AccountSummary, IOException> accountSummary;
	
	public AllyBroker(AllyClient client, String accountId, StonkPropertiesService properties, Double tradeMax) {
		this.client = client;
		this.accountId = accountId;
		this.propertiesService = properties;
		this.tradeMax = tradeMax;
		
		accountSummary = new CachedValue<>(60, this::getAccountSummaryValue);
	}
	
	@Override
	public AccountSummary getAccountSummary() throws IOException {
		return accountSummary.get();
	}
	
	private AccountSummary getAccountSummaryValue() throws IOException {
		return AccountSummaryTransformer.toAccountSummary(client.getAccount(accountId), tradeMax);
	}

	@Override
	public void submitOrder(Order order) throws IOException {
		accountSummary.clear();
		
		client.postOrder(accountId, OrderTransformation.transformOrder(accountId, order));
	}

	@Override
	public void setTadeMax(Double tradeMax) {
		accountSummary.clear();
		
		try {
			propertiesService.setTradeMax(tradeMax);			
			this.tradeMax = tradeMax;
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public Double getTadeMax() {
		return tradeMax;
	} 
}
