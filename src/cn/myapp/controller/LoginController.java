package cn.myapp.controller;

import java.util.HashMap;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.cms.model.User;
import cn.myapp.model.ResultObj;

public class LoginController extends Controller {
	
	/**
	 *  用手机号码注册/登陆
	 * @param 	phone  string 
	 * 
	 */
	public void getId() {
		String phone = getPara("phone", null) ;
		if (phone == null) {
			ResultObj resultObj = new ResultObj("1", "phone不能为空", null) ;
			renderJson(resultObj) ;
			return;
		}
		long returnUserID = 0 ;
		User aUser = new User(phone) ;		
		Record rec = Db.findFirst("select * from pro.User where phone = ?" , phone) ;		
		if (rec == null) {
			returnUserID = aUser.daoInsert("User", "userId") ;
		}
		else {
			aUser = (User)aUser.fetchFromRecord(rec) ;
			returnUserID = aUser.getUserId() ;
		}
		System.out.println(returnUserID) ;
		HashMap<String, Long> resultMap = new HashMap<>() ;
		resultMap.put("userId", returnUserID) ;
		renderJson(new ResultObj(resultMap)) ;
	}
	
}
