package cn.myapp.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.cms.model.Kind;
import cn.myapp.model.ResultObj;

public class KindController extends Controller {

	/**
	 * 类型 增加                                    h
	 *	@param name
	 *	@return success 	kindid
	 */
	public void add() {		
		String kindName = getPara("name") ;
		int order = 1 ;
		
		long kindExist = Db.queryLong("select count(*) from pro.kind where name = ? ;", kindName) ;
		if (kindExist > 0) {
			ResultObj resultObj = new ResultObj("1", "类型已经存在", null) ;
			renderJson(resultObj) ;			
			return ;
		}
		
		Kind kind = new Kind() ;
		kind.setName(kindName); 
		kind.setOrder(order);
		
		long kindId = kind.daoInsert("kind", "kindId") ;
		HashMap<String, Object> map = new HashMap<>() ;
		ResultObj resultObj = null ;
		
		if (kindId > 0) {
			// success
			map.put("kindId", kindId) ;		
			resultObj = new ResultObj(map) ;
		}
		else {
			// fail
			map.put("kindId", kindId) ;		
			resultObj = new ResultObj(null) ;
		}
		
		renderJson(resultObj) ;
	}
	
	/**
	 * 1 获取类型 列表 .                        i/h
	 * @return kindList
	 */
	public void all() {
		List<Kind> list = Kind.allKind() ;
		HashMap<String, Object> map = new HashMap<>() ;
		Gson gson = new Gson() ;
		String jsonstr = gson.toJson(list) ;
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list2 = gson.fromJson(jsonstr, List.class) ;
		map.put("kindList", list2) ;
		
		ResultObj resultObj = new ResultObj(map) ;				
		renderJson(resultObj) ;
	}
	
	/**
	 * 9 类型修改 改名字, 改顺序
	 * @param	kindId
	 * @param	name
	 * @param	order
	 * @return 	1001 / 0
	 */
	public void update() {
		int kindId = getParaToInt("kindId") ;
		String name = getPara("name") ;
		int order = getParaToInt("order") ;
		
		Record record = Db.findById("kind", "kindId", kindId) ; 
		record.set("kindId", kindId).set("name", name).set("order", order) ;
		boolean bSuccess = Db.update("kind" , "kindId" , record) ;
		
		if (bSuccess) {
			renderJson(new ResultObj("success")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}
	}

	public  void index(){
		List<Record> list = Db.find("select * from kind") ;
		setAttr("list",list);
		render("kindList.html");
	}

	public void addH(){
		String kindId=getPara("kindId");
		if(kindId!=null) {
			List<Record> list=Db.find("select * from kind where kindId=?",kindId);
			if(list.size()>=1){
				setAttr("kind",list.get(0));
			}
		}else {
		}
		render("addkind.html");
	}
}
