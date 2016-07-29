package cn.myapp.controller;

import java.util.Date;

import com.jfinal.core.Controller;

import cn.myapp.model.ResultObj;

public class ContentController extends Controller {
	
	/**
	 * 
	private int 	contentId ;
	private String	title ;
	private String 	desc ;
	private String	author ;
	private int		kind ;
	private String	link ;
	private String	html ;
	private String 	cover ;
	private Date	createtime ;
	private Date	updatetime ;
	private Date	sendtime ;
	private	int		displayType ;
	private int		readNum ;
	private	int		isTop ;
	private	int 	isRecommend ;
	private	int		isSlide ;
	
	 */
	public void add() {
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
		
		String link = getPara("link", null) ;
		String html = getPara("html", null) ;
		if (link == null && html == null ) {			
			
		}
		
		
	}
	
	
	
	
}
