package cn.cms.model;

import cn.myapp.model.DaoObject;

@SuppressWarnings("serial")
public class TagRelation extends DaoObject {
	
	private int relationId ;
	private int contentId ;
	private int tagId ;
	
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
}
