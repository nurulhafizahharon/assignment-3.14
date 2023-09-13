package com.skillsunion.shoppingcartapi.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceForTest {

	public int verifyPassword(String password) throws Exception{
		
		if(password.length() < 8) {
			throw new Exception("Password must be at least 8 characters");
		}else {
			return password.length();
		}
		
	}
}
