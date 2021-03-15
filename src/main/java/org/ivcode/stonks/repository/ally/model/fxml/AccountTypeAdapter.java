package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AccountTypeAdapter extends XmlAdapter<Integer, AccountType> {

	@Override
	public AccountType unmarshal(Integer v) throws Exception {
		return AccountType.getAccountType(v);
	}

	@Override
	public Integer marshal(AccountType v) throws Exception {
		return AccountType.getId(v);
	}
}
