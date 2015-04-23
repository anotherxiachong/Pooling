package com.another.pooling.my;


import cn.bmob.v3.BmobObject;

public class Tel extends BmobObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String tel;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
}
