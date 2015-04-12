package com.another.pooling;

import cn.bmob.v3.BmobObject;

public class citys extends BmobObject{
	int ID;
	String CITY;
	int NodeType;
	int ParentID;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public int getNodeType() {
		return NodeType;
	}
	public void setNodeType(int nodeType) {
		NodeType = nodeType;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	
	
}
