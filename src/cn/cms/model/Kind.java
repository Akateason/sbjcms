package cn.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import cn.myapp.model.DaoObject;

@SuppressWarnings("serial")
public class Kind extends DaoObject {
	
	private int kindId ;
	private String name ;
	private int order ;
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
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
	public static List<Kind> allKind() {
		List<Record> listRecord = Db.find("select * from kind") ; 						
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
