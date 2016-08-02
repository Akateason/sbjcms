package cn.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
	
	public static List<Tag> getTaglistWithContentID(int contentID) {
		List<Record> list = Db.find("select * from tagRelation where contentId = ? ;", contentID) ;
		ArrayList<Tag> taglist = new ArrayList<>() ;
		for (Record record : list) {
			Tag aTag = (Tag)(new Tag().fetchFromRecord(record) );
			taglist.add(aTag) ;
		}
		return taglist ;
	}
	
}
