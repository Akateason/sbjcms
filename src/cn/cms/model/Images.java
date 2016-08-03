package cn.cms.model;

import cn.myapp.model.DaoObject;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by apple on 16/7/29.
 */

@SuppressWarnings("serial")
public class Images extends DaoObject
{	

	private int 	imagesId ;
	private int 	contentId ;
	private String	img ;
	private int 	order ;
    public int getImagesId() {
		return imagesId;
	}
	public void setImagesId(int imagesId) {
		this.imagesId = imagesId;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	public Images getOneByID(int id) {
		Record record = Db.findById("images", "imagesId", id) ;
		return (Images)new Images().fetchFromRecord(record) ;
    }
	
    public static List<Images> getAllByContentID(int id) {
    	List<Images> listImages = new ArrayList<>() ;
        List<Record> records= Db.find("select * from images where contentId = ?",id) ;
    	for (Record record : records) {
    		Images aiImages = (Images)new Images().fetchFromRecord(record) ;
    		listImages.add(aiImages) ;
		}
    	return listImages ;
    }
    
}
