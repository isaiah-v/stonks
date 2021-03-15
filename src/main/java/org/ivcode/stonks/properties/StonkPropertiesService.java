package org.ivcode.stonks.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.ivcode.stonks.properties.model.StonkProperties;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StonkPropertiesService {
	
	@Value("${stonks.config}")
	private File propertiesFile;
	
	@Autowired
	private ObjectMapper mapper;
	
	private StonkProperties properties;
	
	private StonkProperties getProperties() throws IOException {
		if(properties==null) {
			properties = load();
		}
		
		return properties;
	}
	
	public void setTradeMax(Double tradeMax) throws IOException {
		getProperties().setTradeMax(tradeMax);
		save();
	}
	
	public Double getTradeMax() throws IOException {
		return getProperties().getTradeMax();
	}
	
	public List<InvestorDto> getInvestors() throws IOException {
		return getProperties().getInvestorDto();
	}
	
	public void setInvestors(List<InvestorDto> investors) throws IOException {
		getProperties().setInvestorDto(investors);
		save();
	}
	
	private void save() throws IOException {
		try (FileOutputStream out = new FileOutputStream(propertiesFile)) {
			mapper.writeValue(out, properties);
		}
	}
	
	private StonkProperties load() throws IOException {
		if(!propertiesFile.exists()) {
			return new StonkProperties();
		}
		
		return mapper.readValue(propertiesFile, StonkProperties.class);
	}
}
