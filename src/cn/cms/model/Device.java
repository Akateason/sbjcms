package cn.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.myapp.model.DaoObject;

public class Device extends DaoObject {

	private static final long serialVersionUID = 1L;

	private int 	deviceId ;
	private String 	token ;
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	public static List<String> allDeviceToken() {
		List<Record> listRecord = Db.find("select * from Device") ;
		if (listRecord.size() != 0) {			
			ArrayList<String> tmpList = new ArrayList<>() ;
			for (Record record : listRecord) {
				Device aDevice = (Device)new Device().fetchFromRecord(record) ;				
				tmpList.add(aDevice.getToken()) ;
			}			
			return tmpList ;
		}
		return null ;
	}
	
	
	public static boolean hasToken(String token) {
		long number = Db.queryLong("select count(*) from pro.Device where token = ?",token) ;
		return (number > 0) ;				
	}
	
}
