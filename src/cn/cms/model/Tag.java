package cn.cms.model;

import cn.myapp.model.DaoObject;

@SuppressWarnings("serial")
public class Tag extends DaoObject {
	
	private int 	tagId ;
	private String 	name ;
	
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
