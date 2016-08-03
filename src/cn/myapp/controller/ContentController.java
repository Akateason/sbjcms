package cn.myapp.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.cms.model.CompleteContent;
import cn.cms.model.Content;
import cn.myapp.model.ResultObj;
import cn.myapp.util.XtDate;

public class ContentController extends Controller {
	
	/**
	 * 内容 增加                                    h              	
	@param	String	title ;
	@param	String 	desc ;
	@param	String	author ;
	@param	int		kind ;
	@param  String	link ;
	@param	String	html ;
	@param  String 	cover ;	
	@param	Date	sendtime ;	tick
	@param	int		displayType ;
	@param	int		isTop ;
	@param	int 	isRecommend ;
	@param	int		isSlide ;
	*
	@return	success 1001 contentId
			fail 	0			
	 * @throws ParseException 	
	 */
	public void add() throws ParseException {
		
		String title = getPara("title", null) ;
		if (title == null) {
			ResultObj resultObj = new ResultObj("1", "标题不能为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String desc = getPara("desc", "") ;
		String author = getPara("author","") ;
		int kind = getParaToInt("kind",0) ;
		if (kind == 0) {
			ResultObj resultObj = new ResultObj("2", "类型不能为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String link = getPara("link", "") ;
		String html = getPara("html", "") ;
		if (link.equals("") && html.equals("") ) {			
			ResultObj resultObj = new ResultObj("3", "链接与内容不能全为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String cover = getPara("cover", null) ;
		if (cover == null) {
			ResultObj resultObj = new ResultObj("4", "没有封面图", null) ;
			renderJson(resultObj) ; 
			return ;
		}
				
		Long sendtime = getParaToLong("sendtime" , null) ; 
		long now = XtDate.getNowTick() ;
		long dateSendtime = sendtime != null ? sendtime : now ;
				
		int displayType = getParaToInt("displayType", 0) ;
		
		int isTop = getParaToInt("isTop", 0) ;
		int isRecommend = getParaToInt("isRecommend", 0) ;
		int isSlide = getParaToInt("isSlide",0) ;		
		
		//
		Content aContent = new Content() ;
		aContent.setTitle(title) ;
		aContent.setDesc(desc) ;
		aContent.setAuthor(author) ;
		aContent.setKind(kind) ;
		aContent.setLink(link) ;
		aContent.setHtml(html) ;
		aContent.setCover(cover) ;
		aContent.setCreatetime(now) ;
		aContent.setUpdatetime(now) ;
		aContent.setSendtime(dateSendtime) ;
		aContent.setDisplayType(displayType) ;
		aContent.setReadNum(0) ;
		aContent.setIsTop(isTop) ;
		aContent.setIsRecommend(isRecommend) ;
		aContent.setIsSlide(isSlide) ;
		
		long newContentId = aContent.daoInsert("content", "contentId") ;
		if (newContentId > 0) {
			// success
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("contentId", newContentId) ;
			renderJson(new ResultObj(map)) ;
		}
		else {
			// fail 
			renderJson(new ResultObj(null)) ;
		}		
	}
	
	/**
	 * 内容 修改          
	 * @param                          h
	 	int contentId	
		String title 	
		String desc		
		String author	
		int kind		
		String link		
		String html		
		String cover	
		String sendtime 
		Date dateSend			
		int displayType 
		int isTop		
		int isRecommend	
		int isSlide				
		@return
		success 1001
		fail 	0
	 * @throws ParseException 
	 * 	
	 */
	public void update() throws ParseException {
		
		int contentId	= getParaToInt("contentId") ;
		String title 	= getPara("title") ;
		String desc		= getPara("desc","") ;
		String author	= getPara("author","") ;
		int kind		= getParaToInt("kind", 0) ;
		String link		= getPara("link", "") ;
		String html		= getPara("html", "") ;
		String cover	= getPara("cover","") ;
		Long sendtime = getParaToLong("sendtime",null) ;
		int displayType = getParaToInt("displayType",0) ;
		int isTop		= getParaToInt("isTop",0) ;
		int isRecommend	= getParaToInt("isRecommend",0) ;
		int isSlide		= getParaToInt("isSlide",0) ;
		
		Record record = Db.findById("content", "contentId", contentId) ;
		record.set("title", title).set("desc", desc).set("author", author)
		.set("kind", kind).set("link", link).set("html", html).set("cover", cover)
		.set("displayType",displayType).set("isTop", isTop)
		.set("isRecommend", isRecommend).set("isSlide", isSlide) ;
		
		if (sendtime != null) {					
			record.set("sendtime", sendtime) ;
		}
		
		long now = XtDate.getNowTick() ;
		record.set("updatetime", now) ;

		boolean bSuccess = Db.update("content", "contentId", record) ;
		
		if (bSuccess) {
			renderJson(new ResultObj("success")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}		
	}
	
	/**
	 * 增加阅读数
	 * @param contentId
	 * @return success 1001 / fail 0
	 */
	public void addRead() {
		long contentId = getParaToLong("contentId") ;		
		Record record = Db.findById("content", "contentId", contentId) ;
		if (record == null) {
			ResultObj resultObj = new ResultObj("1", "无此content", null) ;
			renderJson(resultObj);
			return ;
		}
		record.set("readNum", record.getInt("readNum") + 1) ;
		boolean bSuccess = Db.update("content", "contentId", record) ;
		
		if (bSuccess) {
			renderJson(new ResultObj("success")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}
	}
	
	/**
	 * 内容 删除                                    h                删除关联的标签 , 删除关联的多图
	 * @param contentId
	 * @return success 1001 / fail 0
	 */
	public void del() {		
		int contentId = getParaToInt("contentId",0) ;
		if (contentId == 0) {
			renderJson( new ResultObj("1", "contentId不能为空", null)) ;
			return ;
		}
		
		boolean bSuccess = Db.deleteById("content", "contentId", contentId) ;
		if (bSuccess) {
			renderJson(new ResultObj("从 content表 删除成功 !")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}		
	}
	
	/**
	 * 后台 获取内容列表                           i/h
	 * @param	kind		如果为0, 返回全部分类
	 * @param	sendtime	如果为0, 返回最新的 	tick
	 * @param	size		默认 20
	 * @return	list
	 * @throws ParseException 
	 */
	public void list() throws ParseException {
		Gson gson = new Gson() ;
		int kindId = getParaToInt("kind", 0) ;
		Long sendtime = getParaToLong("sendtime", null) ;		
		long dateSendtime = (sendtime == null) ? XtDate.getNowTick() : sendtime ;
		int size = getParaToInt("size", 20) ;
		
		List<Record> listRecord  = null ;
		if (kindId == 0) {						
			listRecord = Db.find("select * from content where createtime < ? order by createtime desc limit ? ;" , dateSendtime , size) ;																	
		}
		else {
			listRecord = Db.find("select * from content where kind = ? and createtime < ? order by createtime desc limit ? ;" , kindId , dateSendtime , size) ;
		}
		
		List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord) ;			
		String jsonStr = gson.toJson(list_completeContent) ;
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class) ;			
		HashMap<String, Object> map = new HashMap<>() ;
		map.put("list", list) ;
		renderJson(new ResultObj(map)) ;
	}
	
	/**
	 * APP获取内容列表
	 * @param : kind 		当kind==0, 返回"推荐"isRecomment类型
	 * @param : sendtime  	如果为0, 返回最新的 	tick
	 * @param	size		默认 20
	 * 
	 */
	public void alist() {
		Gson gson = new Gson() ;
		int kindId = getParaToInt("kind",-1) ;
		if (kindId == -1) {
			renderJson(new ResultObj("1", "kind必传", null)) ;
			return ;
		}
		
		List<Record> listRecord = null ;
		if (kindId == 0) {
			// 推荐
//			listRecord = Db.find("SELECT * FROM pro.content where isRecommend = 1 ;") ;
			
		}
		else if (kindId > 0) {
			// by kindId
//			listRecord = Db.find("SELECT * FROM pro.content where kind = ? ;", kindId) ;
		}
		
		List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord) ;			
		String jsonStr = gson.toJson(list_completeContent) ;
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class) ;			
		HashMap<String, Object> map = new HashMap<>() ;
		map.put("list", list) ;
		renderJson(new ResultObj(map)) ;
		
	}

	/**
	 * 根据标题获取内容列表
	 * @param   title
	 * @param	size		默认 20
	 * @return  list
	 *
	 */
	public void searchByTitle(){
		Gson gson = new Gson() ;
		String title=getPara("title");
		ResultObj rb=null;
		if(title.isEmpty()){
			rb=new ResultObj("0","标题不能为空",null);
		}else{
			List<Record> listRecord=Db.find("select * from content where title like '%"+title+"%'");
			List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord) ;
			String jsonStr = gson.toJson(list_completeContent) ;
			List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class) ;
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("list", list) ;
			rb=new ResultObj(map);
		}
		renderJson(rb);
	}

	/**
	 * 根据标签获取内容列表
	 * @param   title
	 * @param	size		默认 20
	 * @return  list
	 *
	 */
	public void searchByTag(){
		Gson gson = new Gson() ;
		String tag=getPara("tag");
		ResultObj rb=null;
		if(tag.isEmpty()){
			rb=new ResultObj("0","标题不能为空",null);
		}else{
			List<Record> listRecord=Db.find("select c.* from content as c where contentId in (select contentId from tagRelation as tr left join tag as t on t.tagId=tr.tagId where t.name like '%"+tag+"%')");
			List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord) ;
			String jsonStr = gson.toJson(list_completeContent) ;
			List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class) ;
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("list", list) ;
			rb=new ResultObj(map);
		}
		renderJson(rb);
	}
	
}
