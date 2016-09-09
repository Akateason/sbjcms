package cn.myapp.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import cn.cms.model.Kind;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import cn.cms.model.CompleteContent;
import cn.cms.model.Content;
import cn.myapp.model.ResultObj;
import cn.myapp.util.XtDate;

public class ContentController extends Controller {

	private static final int kNumberOfSlides = 5 ;
	private static final int kNumberOfTops 	 = 10 ;
	
	/**
	 * 内容 增加 h
	 * 
	 * @param String
	 *            title ;
	 * @param String
	 *            desc ;
	 * @param String
	 *            author ;
	 * @param int
	 *            kind ;
	 * @param String
	 *            link ;
	 * @param String
	 *            html ;
	 * @param String
	 *            cover ;
	 * @param Date
	 *            sendtime ; tick
	 * @param int
	 *            displayType ;
	 * @param int
	 *            isTop ;
	 * @param int
	 *            isRecommend ;
	 * @param int
	 *            isSlide ;
	 *
	 * @return success 1001 contentId fail 0
	 * @throws ParseException
	 */
	public void add() throws ParseException {

		String title = getPara("title", null);
		if (title == null) {
			ResultObj resultObj = new ResultObj("1", "标题不能为空", null);
			renderJson(resultObj);
			return;
		}

		String desc = getPara("desc", "");
		String author = getPara("author", "");
		int kind = getParaToInt("kind", 0);
		if (kind == 0) {
			ResultObj resultObj = new ResultObj("2", "类型不能为空", null);
			renderJson(resultObj);
			return;
		}

		String link = getPara("link", "");
		String html = getPara("html", "");
		if (link.equals("") && html.equals("")) {
			ResultObj resultObj = new ResultObj("3", "链接与内容不能全为空", null);
			renderJson(resultObj);
			return;
		}

		String cover = getPara("cover", null);
		if (cover == null) {
			ResultObj resultObj = new ResultObj("4", "没有封面图", null);
			renderJson(resultObj);
			return;
		}

		Long sendtime = getParaToLong("sendtime", null);
		long now = XtDate.getNowTick();
		long dateSendtime = sendtime != null ? sendtime : now;

		int displayType = getParaToInt("displayType", 0);

		int isTop = getParaToInt("isTop", 0);
		int isRecommend = getParaToInt("isRecommend", 0);
		int isSlide = getParaToInt("isSlide", 0);

		//
		Content aContent = new Content();
		aContent.setTitle(title);
		aContent.setDesc(desc);
		aContent.setAuthor(author);
		aContent.setKind(kind);
		aContent.setLink(link);
		aContent.setHtml(html);
		aContent.setCover(cover);
		aContent.setCreatetime(now);
		aContent.setUpdatetime(now);
		aContent.setSendtime(dateSendtime);
		aContent.setDisplayType(displayType);
		aContent.setReadNum(0);
		aContent.setIsTop(isTop);
		aContent.setIsRecommend(isRecommend);
		aContent.setIsSlide(isSlide);

		long newContentId = aContent.daoInsert("content", "contentId");
		if (newContentId > 0) {
			// success
			HashMap<String, Object> map = new HashMap<>();
			map.put("contentId", newContentId);
			renderJson(new ResultObj(map));
		} else {
			// fail
			renderJson(new ResultObj(null));
		}
	}

	/**
	 * 内容 修改
	 * 
	 * @param h
	 *            int contentId String title String desc String author int kind
	 *            String link String html String cover String sendtime Date
	 *            dateSend int displayType int isTop int isRecommend int isSlide
	 * @return success 1001 fail 0
	 * @throws ParseException
	 * 
	 */
	public void update() throws ParseException {

		int contentId = getParaToInt("contentId");
		String title = getPara("title");
		String desc = getPara("desc", "");
		String author = getPara("author", "");
		int kind = getParaToInt("kind", 0);
		String link = getPara("link", "");
		String html = getPara("html", "");
		String cover = getPara("cover", "");
		Long sendtime = getParaToLong("sendtime", null);
		int displayType = getParaToInt("displayType", 0);
		int isTop = getParaToInt("isTop", 0);
		int isRecommend = getParaToInt("isRecommend", 0);
		int isSlide = getParaToInt("isSlide", 0);

		Record record = Db.findById("content", "contentId", contentId);
		record.set("title", title).set("desc", desc).set("author", author).set("kind", kind).set("link", link)
				.set("html", html).set("cover", cover).set("displayType", displayType).set("isTop", isTop)
				.set("isRecommend", isRecommend).set("isSlide", isSlide);

		if (sendtime != null) {
			record.set("sendtime", sendtime);
		}

		long now = XtDate.getNowTick();
		record.set("updatetime", now);

		boolean bSuccess = Db.update("content", "contentId", record);

		if (bSuccess) {
			renderJson(new ResultObj("success"));
		} else {
			renderJson(new ResultObj(null));
		}
	}

	/**
	 * 增加阅读数
	 * 
	 * @param contentId
	 * @return success 1001 / fail 0
	 */
	public void addRead() {
		long contentId = getParaToLong("contentId");
		Record record = Db.findById("content", "contentId", contentId);
		if (record == null) {
			ResultObj resultObj = new ResultObj("1", "无此content", null);
			renderJson(resultObj);
			return;
		}
		record.set("readNum", record.getInt("readNum") + 1);
		boolean bSuccess = Db.update("content", "contentId", record);

		if (bSuccess) {
			renderJson(new ResultObj("success"));
		} else {
			renderJson(new ResultObj(null));
		}
	}

	/**
	 * 内容 删除 h 删除关联的标签 , 删除关联的多图
	 * 
	 * @param contentId
	 * @return success 1001 / fail 0
	 */
	public void del() {
		int contentId = getParaToInt("contentId", 0);
		if (contentId == 0) {
			renderJson(new ResultObj("1", "contentId不能为空", null));
			return;
		}

		boolean bSuccess = Db.deleteById("content", "contentId", contentId);
		if (bSuccess) {
			renderJson(new ResultObj("从 content表 删除成功 !"));
		} else {
			renderJson(new ResultObj(null));
		}
	}

	/**
	 * 后台 获取内容列表 i/h
	 * 
	 * @param kind
	 *            如果为0, 返回全部分类
	 * @param page
	 *            默认为1, 
	 * @param size
	 *            默认 20
	 * @return list
	 * @throws ParseException
	 */
	public void list() throws ParseException {
		int kindId = getParaToInt("kind", 0);
		int page = getParaToInt("page",1) ;
		int size = getParaToInt("size", 20) ;

		Page<Record> recordPage = null ;
		if (kindId == 0) {
			recordPage = Db.paginate(page, size, "select *","from content order by sendtime desc") ;			
		} else {
			recordPage = Db.paginate(page, size, "select *", "from content where kind = ? order by sendtime desc",kindId) ;
		}
		
		List<HashMap<String, Object>> list = dealList(recordPage.getList());
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", list) ;
		renderJson(new ResultObj(map)) ;
	}

	/**
	 * APP获取内容列表
	 * 
	 * @param :
	 *            kind 当kind==0, 返回"推荐"isRecomment类型
	 * @param :
	 *            sendtime 如果为0, 返回最新的 tick
	 * @param :
	 *            size 默认 20
	 * @return slide list top
	 */
	public void alist() {
		int kindId = getParaToInt("kind", 0);
		Long sendtime = getParaToLong("sendtime", null);
		long dateSendtime = (sendtime == null || sendtime == 0) ? XtDate.getNowTick() : sendtime;
		int size = getParaToInt("size", 20);

		if (kindId == 0) {
			// 推荐
			// 1 . 幻灯片
			List<Record> slides = Db.find(
					"select * from content where isRecommend = 1 and isSlide = 1 order by sendtime desc limit ? ;",
					kNumberOfSlides);
			List<HashMap<String, Object>> list_slides = dealList(slides);

			// 2 . 置顶
			List<Record> tops = Db
					.find("select * from content where isRecommend = 1 and isTop = 1 order by sendtime desc limit ? ;",kNumberOfTops);
			List<HashMap<String, Object>> list_tops = dealList(tops);

			// 3 . 列表 (不带置顶)
			List<Record> contents = Db.find(
					"select * from content where sendtime < ? and isRecommend = 1 and isTop = 0 order by sendtime desc limit ? ;",
					dateSendtime, size);
			List<HashMap<String, Object>> list_content = dealList(contents);

			HashMap<String, Object> map = new HashMap<>();
			map.put("slide", list_slides);
			map.put("list", list_content);
			map.put("top", list_tops);

			renderJson(new ResultObj(map));
		} else if (kindId > 0) {
			// 类型 分页
			// 1 . 幻灯片
			List<Record> slides = Db.find(
					"select * from content where kind = ? and isSlide = 1 order by sendtime desc limit ? ;", kindId,
					kNumberOfSlides);
			List<HashMap<String, Object>> list_slides = dealList(slides);

			// 2 . 置顶
			List<Record> tops = Db.find("select * from content where kind = ? and isTop = 1 order by sendtime desc limit ? ;", kindId,kNumberOfTops) ;
			List<HashMap<String, Object>> list_top = dealList(tops) ;

			// 3 . 列表 (不带置顶)
			List<Record> contents = Db.find(
					"select * from content where sendtime < ? and kind = ? and isTop = 0 order by sendtime desc limit ? ;" ,
					dateSendtime, kindId, size) ;
			List<HashMap<String, Object>> list_content = dealList(contents);

			HashMap<String, Object> map = new HashMap<>();
			map.put("slide", list_slides);
			map.put("list", list_content);
			map.put("top", list_top);

			renderJson(new ResultObj(map));
		}
	}

	/**
	 * 获取内容详情 i/hP : 内容id .
	 * 
	 * @param contentId
	 * @return
	 */
	public void detail() {
		int contentId = getParaToInt("contentId", 0);
		if (contentId == 0) {
			ResultObj resultObj = new ResultObj("1", "contentId不能为空", null);
			renderJson(resultObj);
			return;
		}

		Record record = Db.findFirst("select * from content where contentId = ? ;", contentId);
		if (record == null) {
			ResultObj resultObj = new ResultObj("2", "contentId不存在", null);
			renderJson(resultObj);
			return;
		}

		CompleteContent completeContent = CompleteContent.getCompleteContentWithRecord(record);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(completeContent);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = gson.fromJson(jsonStr, HashMap.class);
		ResultObj resultObj = new ResultObj(map);
		renderJson(resultObj);
	}

	/**
	 * 根据标题获取内容列表
	 * 
	 * @param title
	 * @param size
	 *            默认 20
	 * @return list
	 *
	 */
	public void searchByTitle() {
		Gson gson = new Gson();
		String title = getPara("keyword");
		ResultObj rb = null;
		if (title.isEmpty()) {
			rb = new ResultObj("0", "标题不能为空", null);
		} else {
			List<Record> listRecord = Db.find("select * from content where title like '%" + title + "%'");
			List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord);
			String jsonStr = gson.toJson(list_completeContent);
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class);
			HashMap<String, Object> map = new HashMap<>();
			map.put("list", list);
			rb = new ResultObj(map);
		}
		renderJson(rb);
	}

	/**
	 * 根据标签获取内容列表
	 * 
	 * @param title
	 * @param size
	 *            默认 20
	 * @return list
	 *
	 */
	public void searchByTag() {
		Gson gson = new Gson();
		String tag = getPara("keyword");
		ResultObj rb = null;
		if (tag.isEmpty()) {
			rb = new ResultObj("0", "标题不能为空", null);
		} else {
			List<Record> listRecord = Db
					.find("select c.* from content as c where contentId in (select contentId from tagRelation as tr left join tag as t on t.tagId=tr.tagId where t.name like '%"
							+ tag + "%')");
			List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord);
			String jsonStr = gson.toJson(list_completeContent);
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class);
			HashMap<String, Object> map = new HashMap<>();
			map.put("list", list);
			rb = new ResultObj(map);
		}
		renderJson(rb);
	}

	/**
	 * 根据类别获取内容列表
	 * 
	 * @param kind
	 * @param size
	 *            默认 20
	 * @return list
	 *
	 */
	public void searchByKind() {
		Gson gson = new Gson();
		String kind = getPara("keyword");
		ResultObj rb = null;
		if (kind.isEmpty()) {
			rb = new ResultObj("0", "标题不能为空", null);
		} else {
			List<Record> listRecord = Db.find("select * from content where kind=?", kind);
			List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(listRecord);
			String jsonStr = gson.toJson(list_completeContent);
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class);
			HashMap<String, Object> map = new HashMap<>();
			map.put("list", list);
			rb = new ResultObj(map);
		}
		renderJson(rb);
	}

	private List<HashMap<String, Object>> dealList(List<Record> recordList) {
		Gson gson = new Gson();
		List<CompleteContent> list_completeContent = CompleteContent.getCompleteListWithRecordList(recordList);
		String jsonStr = gson.toJson(list_completeContent);
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> list = gson.fromJson(jsonStr, List.class);
		return list;
	}

	public void index() {
		List<Kind> list = Kind.allKind();
		setAttr("kinds", list);
		render("contentList.html");
	}

	public void addH() {
		List<Kind> list = Kind.allKind();
		setAttr("kinds", list);
		render("addContent.html");
	}

	public void updateH() {
		Gson gson = new Gson();
		int contentId = getParaToInt("id", 0);
		Record record = Db.findFirst("select * from content where contentId = ? ;", contentId);
		if (record == null) {
			ResultObj resultObj = new ResultObj("2", "contentId不存在", null);
			renderJson(resultObj);
			return;
		}
		setAttr("content", gson.toJson(CompleteContent.getCompleteContentWithRecord(record)));
		List<Kind> list = Kind.allKind();
		setAttr("kinds", list);
		render("updateContent.html");
	}

}
