package cn.myapp.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
	@param	Date	sendtime ;	yyyy-MM-dd HH:mm:ss
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
			ResultObj resultObj = new ResultObj("0", "标题不能为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String desc = getPara("desc", "") ;
		String author = getPara("author","") ;
		int kind = getParaToInt("kind",0) ;
		if (kind == 0) {
			ResultObj resultObj = new ResultObj("0", "类型不能为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String link = getPara("link", "") ;
		String html = getPara("html", "") ;
		if (link.equals("") && html.equals("") ) {			
			ResultObj resultObj = new ResultObj("0", "链接与内容不能全为空", null) ;
			renderJson(resultObj) ;
			return ;
		}
		
		String cover = getPara("cover", null) ;
		if (cover == null) {
			ResultObj resultObj = new ResultObj("0", "没有封面图", null) ;
			renderJson(resultObj) ; 
			return ;
		}
		
		Date now = XtDate.getNowDate() ;		
		String sendtime = getPara("sendtime",null) ;
		Date dateSendtime = sendtime != null ? XtDate.getDateWithString(sendtime) : now ;
				
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
		String sendtime = getPara("sendtime",null) ;
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
			Date dateSend	= XtDate.getDateWithString(sendtime) ;		
			record.set("sendtime", dateSend) ;
		}
		
		Date now = XtDate.getNowDate() ;
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
			ResultObj resultObj = new ResultObj("0", "无此content", null) ;
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
	
}
