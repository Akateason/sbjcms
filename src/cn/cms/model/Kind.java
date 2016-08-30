package cn.cms.model;

import cn.myapp.model.DaoObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Kind extends DaoObject {
	
	private int kindId ;
	private String name ;
	private int kindOrder ;
	public int getKindId() {
		return kindId;
	}
	public void setKindId(int kindId) {
		this.kindId = kindId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKindOrder() {
		return kindOrder;
	}
	public void setKindOrder(int order) {
		this.kindOrder = order;
	}
	
	public static List<Kind> allKind() {
		List<Record> listRecord = Db.find("select * from kind order by kindOrder") ;
		if (listRecord.size() != 0) {			
			ArrayList<Kind> list = new ArrayList<>() ;
			for (Record record : listRecord) {
				Kind aKind = (Kind)new Kind().fetchFromRecord(record) ;				
				list.add(aKind) ;
			}			
			return list ;
		}
		return null ;
	}
	
}
