package cn.cms.model;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class CompleteContent extends Content {
	
	private List<Map> taglist ;
	private List<Images> imagelist ;
	private String kindName ;
	
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public List<Map> getTaglist() {
		return taglist;
	}
	public void setTaglist(List<Map> taglist) {
		this.taglist = taglist;
	}
	public List<Images> getImagelist() {
		return imagelist;
	}
	public void setImagelist(List<Images> imagelist) {
		this.imagelist = imagelist;
	}
	
	public CompleteContent(Content content , List<Map> taglist , List<Images> imagesList, String kindName) {
		// TODO Auto-generated constructor stub
		super(content) ;
		this.setTaglist(taglist);
		this.setImagelist(imagesList);
		this.setKindName(kindName); 
	}
	
	public static CompleteContent getCompleteContentWithRecord(Record record) {
		Content aContent = (Content)new Content().fetchFromRecord(record) ;	
		List<Map> taglist = TagRelation.getTaglistWithContentID(aContent.getContentId()) ;
		List<Images> imageslist = Images.getAllByContentID(aContent.getContentId()) ;				
		String kindName = Kind.getKindNameWithKindID(aContent.getKind()) ;
		CompleteContent completeContent = new CompleteContent(aContent, taglist, imageslist,kindName) ;
		return completeContent ;
	}

	public static List<CompleteContent> getCompleteListWithRecordList(List<Record> recordList) {
		List<CompleteContent> list_completeContent = new ArrayList<>() ;		
		for (Record record : recordList) {				
			CompleteContent completeContent = getCompleteContentWithRecord(record) ;
			list_completeContent.add(completeContent) ;
		}
		return list_completeContent ;
	}
	
}
