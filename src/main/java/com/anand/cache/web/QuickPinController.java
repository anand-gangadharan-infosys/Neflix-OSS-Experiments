package com.anand.cache.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.anand.cache.domain.CacheMissException;
import com.anand.cache.domain.EVCachePostOffice;
import com.anand.common.model.PostalLocation;
import com.anand.pin.client.PinClient;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netflix.evcache.EVCacheException;

@RestController
public class QuickPinController {

	@Autowired
	private EVCachePostOffice cacheDataStore;

	@Autowired
	private PinClient pinClient;

	@RequestMapping(method = RequestMethod.GET, value = "/postal/search/{pin}")
	public PostalLocation getPinLocation(@PathVariable(value = "pin") String pinCode)
			throws EVCacheException, JsonParseException, JsonMappingException, IOException {

		try {
			PostalLocation location = cacheDataStore.getPostalLocation(pinCode);
			if (location != null) {
				System.out.println("Cache hit");
			}
			return location;
		} catch (CacheMissException e) {
			return fetchFromDB(pinCode);

		}

	}
	
	private PostalLocation fetchFromDB(String pinCode) {
		System.out.println("Cache miss");
		PostalLocation location = pinClient.getPinCode(pinCode);
		if (location != null) {
			saveToCache(pinCode, location);
		}
		return pinClient.getPinCode(pinCode);
	}
	

	private void saveToCache(String pinCode, PostalLocation location) {
		try {
			cacheDataStore.setKey(pinCode, location, 500);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}