package cn.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
//import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import com.jfinal.core.Controller;

import cn.myapp.model.ResultObj;

public class APNSController extends Controller {

	public void test() {
		String str = System.getProperty("user.dir") + "/sbjcms/apns/push.p12";
		System.out.println(str);
		
		String str1 = APNSController.class.getResource("push.p12").getPath() ;
		System.out.println(str1);
		
//		System.out.println(Class.class.getClass().getResource("/").getPath());
		renderJson("dd");
	}
	
	
	/**
	 * sendAppNote 推送通知
	 * @param deviceToken 获取终端设备标识，这个标识需要通过接口发送到服务器端，服务器端推送消息到APNS时需要知道终端的标识，APNS通过注册的终端标识找到终端设备。
	 * @param alert  	  push文字内容
	 * @param badge		  更新的数量
	 * 
	 * @return success
	 * @throws IOException 
	 */	
	public void sendAppNote() throws IOException {
		
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
		
		String sound = "default";// 铃音
		
		List<String> tokens = new ArrayList<String>();
		tokens.add(deviceToken);
		
		String certificatePath = "/Desktop/zs/push.p12" ; //properties.getProperty("/apns/push.p12") ;  //"./apns/push.p12" ; //"D:/ZshPush.p12";
		String certificatePassword = "";// 此处注意导出的证书密码不能为空因为空密码会报错
		boolean sendCount = true;

		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(alert); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值

			if (!StringUtils.isBlank(sound)) {
				payLoad.addSound(sound);// 铃音
			}
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(
					new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
				notifications.add(notification);
			} else {
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			pushManager.stopConnection() ;

			// success
			ResultObj resultObj = new ResultObj("success") ;
			renderJson(resultObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
