package cn.wx.activity.model;

import cn.myapp.model.DaoObject;

@SuppressWarnings("serial")
public class LuckyItem extends DaoObject {
	
	private int 	idlucky ;		// id == 排名 .
	private String	constellation ;	// 星座
	private String  zodiac ;		// 生肖
	private String  blood ;			// 血型
	private String	starName ; 		// 相同的明星
	
	public int getIdlucky() {
		return idlucky;
	}
	public void setIdlucky(int idlucky) {
		this.idlucky = idlucky;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getZodiac() {
		return zodiac;
	}
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getStarName() {
		return starName;
	}
	public void setStarName(String starName) {
		this.starName = starName;
	}
	
	
	
	
	
}
