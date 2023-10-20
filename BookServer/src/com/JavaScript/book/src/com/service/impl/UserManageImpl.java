package com.service.impl;

import java.util.List;
import com.domain.User;
import com.service.UserService;
import dao.*;

public class UserManageImpl implements UserService {

	private UserDAO userdao;
	
	public UserDAO getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDAO userdao) {
		this.userdao = userdao;
	}

	public User getByName(String name) {
		return userdao.findByName(name);
	}

	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return userdao.findByEmail(email);
	}

	public boolean checkUser(User user){
		List<User> users= (userdao.findByNameAndPass(user));
		if(users.size()>0)
			return true;
		else
			return false;
	}
	
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		Integer ref=(Integer) userdao.save(user);
		if(ref!=null)
			return true;
		else
			return false;
	}

}
