package cn.myapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.cms.model.Tag;
import cn.cms.model.TagRelation;
import cn.myapp.model.ResultObj;

public class TagController extends Controller {
	
	/**
	 * 标签 新增                                    h
	 * @param	name		
	 * @return success 1001
	 */
	public void add() {		
		String tagName = getPara("name","") ;
		
		if (tagName.length() == 0) {
			ResultObj resultObj = new ResultObj("1", "标签不能为空", null) ;
			renderJson(resultObj);			
			return ;
		}
		
		long tagExistCount = Db.queryLong("select count(*) from tag where name = ? ;" , tagName) ;
		if (tagExistCount > 0) {
			ResultObj resultObj = new ResultObj("2", "该标签已经存在", null) ;
			renderJson(resultObj);			
			return ;
		}
		
		Tag aTag = new Tag() ;
		aTag.setName(tagName) ;		
		long newTagId = aTag.daoInsert("tag", "tagId") ;
		HashMap<String, Object> map = new HashMap<>() ;
		map.put("tagId", newTagId) ;
		
		if (newTagId > 0) {
			renderJson(new ResultObj(map)) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}
	}
	
	/**
	 * 标签 模糊搜索  返回列表            h
	 * @param	word 	
	 * @return	success	1001 		tagList
	 * 			fail	0
	 */
	public void search() {
		String word = getPara("word") ;				
		String sql = "select * from tag where name like '%" + word + "%' ;" ;
		List<Record> recordList = Db.find(sql) ;
		ArrayList<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>() ;
		for (Record record : recordList) {
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("tagId", record.getInt("tagId")) ;
			map.put("name", record.getStr("name")) ;
			tagList.add(map) ;
		}
		HashMap<String, Object>rMap = new HashMap<String, Object>() ;
		rMap.put("tagList", tagList) ;
		
		if (tagList.size() > 0) {
			renderJson(new ResultObj(rMap));
		}
		else {
			ResultObj resultObj = new ResultObj("1", "无此标签", rMap) ;
			renderJson(resultObj);
		}
	}
	
	/**
	 * tagRelation .
	 * 标签 关联 绑定                            h                 内容保存的时候
	 * @param	 contentId
	 * 			 tagId
	 * @return	 1001 success		relationId = ?
	 * 			 0 	fail
	 */
	public void addBind() {
		int contentId = getParaToInt("contentId",0) ;
		int tagId = getParaToInt("tagId",0) ;		
		if (contentId == 0) {
			renderJson(new ResultObj("1", "contentId不能为空", null));
			return ;
		}
		if (tagId == 0) {
			renderJson(new ResultObj("2", "tagId不能为空", null));
			return ;
		}
		
		TagRelation relation = new TagRelation() ;
		relation.setContentId(contentId) ;
		relation.setTagId(tagId) ;
		
		long relationId = relation.daoInsert("tagRelation", "relationId") ;
		if (relationId > 0) {
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("relationId", relationId) ;
			renderJson(new ResultObj(map)) ;			
		}
		else {
			renderJson(new ResultObj(null)) ;			
		}
	}
	
	/**
	 * 标签 关联 删除 (不删除标签)       h                 编辑或新增文章时
	 * @param	 contentId
	 * 			 tagId
	 * @return 	success 1001 	删除成功
	 * 			fail 0
	 */
	public void delBind() {
		int contentId = getParaToInt("contentId",0) ;
		int tagId = getParaToInt("tagId",0) ;
		if (contentId == 0) {
			renderJson(new ResultObj("1", "contentId不能为空", null));
			return ;
		}
		if (tagId == 0) {
			renderJson(new ResultObj("2", "tagId不能为空", null));
			return ;
		}
		
		Record record = Db.findFirst("select * from tagRelation where contentId = ? and tagId = ? " , contentId , tagId) ;
		if (record == null) {
			renderJson(new ResultObj("1", "没有relationId, 没有关联记录", null)) ;
			return ;
		}
		boolean bSuccess = Db.delete("tagRelation", "relationId", record) ;
		if (bSuccess) {			
			renderJson(new ResultObj("删除成功")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}
	}
	
	
}
