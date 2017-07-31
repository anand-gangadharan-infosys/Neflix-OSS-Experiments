package com.anand.pin.domain;

import org.springframework.data.repository.CrudRepository;

public interface PostOfficeLocationRepsitory extends CrudRepository<PostalLocation, String>{
	
	 PostalLocation findByPincode(String pincode);

}
