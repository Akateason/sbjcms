package cn.cms.model;

import cn.myapp.model.DaoObject;

@SuppressWarnings("serial")
public class Content extends DaoObject {
	
	private int 	contentId ;
	private String	title ;
	private String 	desc ;
	private String	author ;
	private int		kind ;
	private String	link ;
	private String	html ;
	private String 	cover ;
	private long	createtime ;
	private long	updatetime ;
	private long	sendtime ;
	private	int		displayType ;
	private int		readNum ;
	private	int		isTop ;
	private	int 	isRecommend ;
	private	int		isSlide ;
	
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public long getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}
	public long getSendtime() {
		return sendtime;
	}
	public void setSendtime(long sendtime) {
		this.sendtime = sendtime;
	}
	public int getDisplayType() {
		return displayType;
	}
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public int getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(int isRecommend) {
		this.isRecommend = isRecommend;
	}
	public int getIsSlide() {
		return isSlide;
	}
	public void setIsSlide(int isSlide) {
		this.isSlide = isSlide;
	}
	
	public Content() {
		// TODO Auto-generated constructor stub
	}
	
	public Content(Content content) {
		this.setContentId(content.contentId);
		this.setTitle(content.title);
		this.setDesc(content.desc);
		this.setAuthor(content.author);
		this.setKind(content.kind);
		this.setLink(content.link);
		this.setHtml(content.html);
		this.setCover(content.cover);
		this.setCreatetime(content.createtime);
		this.setUpdatetime(content.updatetime);
		this.setSendtime(content.sendtime);
		this.setDisplayType(content.displayType);
		this.setReadNum(content.readNum);
		this.setIsTop(content.isTop);
		this.setIsRecommend(content.isRecommend);
		this.setIsSlide(content.isSlide);			
	}
	
}
