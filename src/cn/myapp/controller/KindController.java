package cn.myapp.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.jfinal.core.Controller;

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
			resultObj = new ResultObj("0", "插入错误", null) ;
		}
		
		renderJson(resultObj);
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
	
}
