package cn.cms.model;

import cn.myapp.model.DaoObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public static List<Map> getTaglistWithContentID(int contentID) {
		List<Record> list = Db.find("select * from tagRelation left join tag on tag.tagId = tagRelation.tagId where contentId = ? ;", contentID) ;
		ArrayList<Map> tmplist = new ArrayList() ;
		for (int i = 0; i < list.size(); i++) {
			HashMap<String,Object> tmpMap = new HashMap<>() ;
			Record record = list.get(i) ;
			tmpMap.put("tagId",record.getInt("tagId")) ;
			tmpMap.put("name",record.getStr("name")) ;
			tmpMap.put("relationId",record.getInt("relationId")) ;
			tmplist.add(tmpMap) ;
		}
		return tmplist ;
	}
	
}
