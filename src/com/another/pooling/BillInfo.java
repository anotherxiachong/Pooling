package com.another.pooling;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class BillInfo extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String  username;//
	String deadline;//
	String describe;//
	String contact;
	BmobGeoPoint position;//
	String link;//
	String address;//
	String detailaddress;
	ArrayList<String> tabs;
	int classes;
	String[] followman;
	String[] imgfilename;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeadline() {
		return deadline;
	}
	public String[] getImgfilename() {
		return imgfilename;
	}
	public void setImgfilename(String[] imgfilename) {
		this.imgfilename = imgfilename;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public BmobGeoPoint getPosition() {
		return position;
	}
	public void setPosition(BmobGeoPoint position) {
		this.position = position;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public ArrayList<String> getTabs() {
		return tabs;
	}
	public void setTabs(ArrayList<String> tabs) {
		this.tabs = tabs;
	}
	public int getClasses() {
		return classes;
	}
	public void setClasses(int classes) {
		this.classes = classes;
	}
	public String[] getFollowman() {
		return followman;
	}
	public void setFollowman(String[] followman) {
		this.followman = followman;
	}
	

}
