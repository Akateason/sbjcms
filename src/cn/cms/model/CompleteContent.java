package cn.cms.model;

import java.util.List;

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

}
