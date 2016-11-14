package cn.myapp.controller;

import com.jfinal.core.Controller;

import cn.cms.model.Device;
import cn.myapp.model.ResultObj;
import cn.myapp.util.apns.APNSUtil;

public class APNSController extends Controller {

	
	/**
	 * add 添加DeviceToken
	 * @param deviceToken
	 */
	public void add() {
		String deviceToken = getPara("deviceToken", null);
		if (deviceToken == null) {
			ResultObj resultObj = new ResultObj("1", "deviceToken不能为空", null);
			renderJson(resultObj);
			return;
		}
		Device device = new Device() ;
		device.setToken(deviceToken) ;
		boolean tokenExisted = Device.hasToken(deviceToken) ;
		if (tokenExisted) {
			renderJson(new ResultObj("1", "token已经存在",null));	
			return ;
		}
		
		long deviceID = device.daoInsert("Device", "deviceId") ;
		if (deviceID > 0) {
			renderJson(new ResultObj("deviceID:"+deviceID));
		}
		else {
			renderJson(new ResultObj(null));	
		}		
	}
	
	
	/**
	 * send2Single 单个 推送通知 测试用 .
	 * @param deviceToken 获取终端设备标识，这个标识需要通过接口发送到服务器端，服务器端推送消息到APNS时需要知道终端的标识，APNS通过注册的终端标识找到终端设备。
	 * @param alert  	  push文字内容
	 * @param badge		  更新的数量
	 * 
	 * @return success
	 * 
	 */	
	public void send2Single() {
		
		String deviceToken = getPara("deviceToken", null);
		if (deviceToken == null) {
			ResultObj resultObj = new ResultObj("1", "deviceToken不能为空", null);
			renderJson(resultObj);
			return;
		}
		
		String alert = getPara("alert", null);  // push文字内容
		if (alert == null) {
			ResultObj resultObj = new ResultObj("1", "alert不能为空", null);
			renderJson(resultObj);
			return;
		}
		
		int badge = getParaToInt("badge", 0) ; // 图标小红圈的数值 
		if (badge == 0) {
			ResultObj resultObj = new ResultObj("1", "badge不能为0", null);
			renderJson(resultObj);
			return;
		}		
		
		APNSUtil util = new APNSUtil() ;
		boolean success = util.sendNoteToSingle(alert, badge, deviceToken) ;
		if (success) {
			renderJson(new ResultObj("发送成功")) ;
		}
		else {
			renderJson(new ResultObj(null)) ;
		}	
	}
	
	
	
	/**
	 * send2All 对所有人发送通知 .
	 * @param alert  	  push文字内容
	 * @param badge		  更新的数量
	 * 
	 * @return success
	 */
	public void send2All() {
		String alert = getPara("alert", null);  // push文字内容
		if (alert == null) {
			ResultObj resultObj = new ResultObj("1", "alert不能为空", null);
			renderJson(resultObj);
			return;
		}
		
		int badge = getParaToInt("badge", 0) ; // 图标小红圈的数值 
		if (badge == 0) {
			ResultObj resultObj = new ResultObj("1", "badge不能为0", null);
			renderJson(resultObj);
			return;
		}	
		
//		APNSUtil util = new APNSUtil() ;
//		boolean success = util.sendNoteToAll(alert, badge, tokens) ;		
//		if (success) {
//			renderJson(new ResultObj("发送成功")) ;
//		}
//		else {
//			renderJson(new ResultObj(null)) ;
//		}
		
	}

}
