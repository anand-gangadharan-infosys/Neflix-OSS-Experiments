package com.anand.cache.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anand.cache.domain.EVCachePostOffice;
import com.anand.common.model.PostalLocation;
import com.netflix.evcache.EVCacheException;

@RestController
@RequestMapping(path = "/cache/postal")

public class QuickPinController {

	@Autowired
	private EVCachePostOffice dataStore;
	
	@GetMapping(path = "/search")
	public PostalLocation getPinLocation(@RequestParam(value = "pin", defaultValue = "000000") String pinCode) throws EVCacheException {
		return dataStore.getPostalLocation(pinCode);
	}
}