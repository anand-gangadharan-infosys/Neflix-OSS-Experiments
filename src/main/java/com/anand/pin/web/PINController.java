package com.anand.pin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anand.pin.domain.PostOfficeLocationRepsitory;
import com.anand.pin.domain.PostalLocation;

@RestController
public class PinController {

	@Autowired
	private PostOfficeLocationRepsitory poRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/persist/postal/search")
	public PostalLocation getPinLocation(@RequestParam(value = "pin", defaultValue = "000000") String pinCode) throws Exception {
		PostalLocation poLocation =poRepo.findByPincode(pinCode);
		if(poLocation == null){
			throw new Exception("Location details missing");
		}
		return poRepo.findByPincode(pinCode);

	}

}
