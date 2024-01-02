package com.project.service;

import com.project.model.User;

public interface UserService {

	public User saveUser(User user);
	public void removeSessionMessage();
}
