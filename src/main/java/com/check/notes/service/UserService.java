package com.check.notes.service;

import com.check.notes.model.UserDtls;

public interface UserService {

	public UserDtls createUser(UserDtls user);
	public boolean checkEmail(String email);
	public UserDtls getUserByEmail(String email) ;
}
