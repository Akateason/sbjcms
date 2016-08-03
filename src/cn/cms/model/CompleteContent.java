package cn.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class CompleteContent extends Content {
	
	private List<Tag> taglist ;
	private List<Images> imagelist ;
	
	public List<Tag> getTaglist() {
		return taglist;
	}
	public void setTaglist(List<Tag> taglist) {
		this.taglist = taglist;
	}
	public List<Images> getImagelist() {
		return imagelist;
	}
	public void setImagelist(List<Images> imagelist) {
		this.imagelist = imagelist;
	}
	
	public CompleteContent(Content content , List<Tag> taglist , List<Images> imagesList) {
		// TODO Auto-generated constructor stub
		super(content) ;
		this.setTaglist(taglist);
		this.setImagelist(imagesList);
	}

	public static List<CompleteContent> getCompleteListWithRecordList(List<Record> recordList) {
		List<CompleteContent> list_completeContent = new ArrayList<>() ;
		
		for (Record record : recordList) {
			Content aContent = (Content)new Content().fetchFromRecord(record) ;	
			List<Tag> taglist = TagRelation.getTaglistWithContentID(aContent.getContentId()) ;
			List<Images> imageslist = Images.getAllByContentID(aContent.getContentId()) ;				
			CompleteContent completeContent = new CompleteContent(aContent, taglist, imageslist) ;
			list_completeContent.add(completeContent) ;
		}
		return list_completeContent ;
	}
	
}
