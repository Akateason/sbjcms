package cn.myapp.util.apns;

import java.util.ArrayList;
import java.util.List;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class APNSUtil {

	private static String sound = "default";
	private static String certificatePath = "push.p12";
	private static String certificatePassword = ""; // 此处注意导出的证书密码不能为空因为空密码会报错

	/**
	 * 给所有人发送通知 . sendNoteToAll
	 * 
	 * @param alert
	 *            通知文字
	 * @param badge
	 *            更新数字
	 * @param tokens
	 *            DEVICETOKEN数组
	 * @return
	 */
	public boolean sendNoteToAll(String alert, int badge, List<String> tokens) {

		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(alert); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
			payLoad.addSound(sound);// 铃音

			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(
					new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			Device device = new BasicDevice();
			device.setToken(tokens.get(0));
			PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
			notifications.add(notification);
			pushManager.stopConnection();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * sendNoteToSingle 单人发 (测试)
	 * @param alert
	 * @param badge
	 * @param deviceToken
	 * @return
	 */
	public boolean sendNoteToSingle(String alert, int badge, String deviceToken) {
		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(alert); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
			payLoad.addSound(sound);// 铃音

			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(
					new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, true));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			Device device = new BasicDevice();
			device.setToken(deviceToken);
			PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
			notifications.add(notification);
			pushManager.stopConnection();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
