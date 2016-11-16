package cn.cms.model;

import cn.myapp.model.DaoObject;
import cn.myapp.util.XtDate;

public class User extends DaoObject {
	
	private static final long serialVersionUID = 1L;
	
	
	private int userId ;
	private String phone ;
	private long createTime ;
	private long updateTime ;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * new user with phone . will insert in User TB
	 * @param phone
	 */
	public User(String phone) {
		// TODO Auto-generated constructor stub
		this.phone = phone ;		
		this.createTime = XtDate.getNowTick() ;
		this.updateTime = XtDate.getNowTick() ;
	}
	
	
	
}
