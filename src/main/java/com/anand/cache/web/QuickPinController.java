package com.anand.cache.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anand.cache.domain.EVCachePostOffice;
import com.anand.common.model.PostalLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netflix.evcache.EVCacheException;

@RestController
public class QuickPinController {

	@Autowired
	private EVCachePostOffice dataStore;
	
	@RequestMapping(method = RequestMethod.GET, value ="/cache")
	public PostalLocation getPinLocation(@RequestParam(value = "pin", defaultValue = "000000") String pinCode)
			throws EVCacheException, JsonParseException, JsonMappingException, IOException {
		return dataStore.getPostalLocation(pinCode);
		
	}
}