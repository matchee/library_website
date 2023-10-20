package com.action;

import net.sf.json.JSONObject;
import com.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.UserService;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements ModelDriven {
	private String result;
	private UserService userservice;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user.setName(user.getName());
		this.user.setPassword(user.getPassword());
		this.user = user;
	}

	public UserService getUserservice() {
		return userservice;
	}

	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	public Object getModel() {
		// TODO Auto-generated method stub
		if (user == null)
			user = new User();
		return user;
	}

	public String checkUserName() {
		String warnMsg = "";
		User user = userservice.getByName(this.user.getName());
		if (user == null)
			warnMsg = "该用户名可用";
		else
			warnMsg = "该用户名已经存在";
		JSONObject jo = new JSONObject();
		jo.put("warnMsg", warnMsg);
		result = jo.toString();//向jsp页面传递一个result值
		return SUCCESS;
	}

	public String checkUserEmail() {
		User user = userservice.getByEmail(this.user.getEmail());
		String warnMsg = "";
		if (user == null)
			warnMsg = "该邮箱可用";
		else
			warnMsg = "该邮箱已注册";
		JSONObject jo = new JSONObject();
		jo.put("warnMsg", warnMsg);
		result = jo.toString();
		return SUCCESS;
	}

	public String login() {
		if (userservice.checkUser(user))
			return "success";
		else
			return "input";
	}

	public String add() {
		if (userservice.addUser(user) == true)
			return "success";
		else
			return "input";

	}

}
