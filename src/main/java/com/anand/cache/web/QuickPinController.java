package com.anand.cache.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuickPinController {

	@RequestMapping(method = RequestMethod.GET, value = "/pincode")
	public String getPinLocation(@RequestParam(value = "pin", defaultValue = "000000") String pinCode) {
		return pinCode;
	}
}