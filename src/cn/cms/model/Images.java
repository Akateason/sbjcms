package cn.cms.model;

import cn.myapp.model.DaoObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/29.
 */

@SuppressWarnings("serial")
public class Images extends DaoObject
{	

	private int 	imagesId ;
	private int 	contentId ;
	private String	img ;
	private int 	imagesOrder ;
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
	public int getImagesOrder() {
		return imagesOrder;
	}
	public void setImagesOrder(int imagesOrder) {
		this.imagesOrder = imagesOrder;
	}

	public Images getOneByID(int id) {
		Record record = Db.findById("images", "imagesId", id) ;
		return (Images)new Images().fetchFromRecord(record) ;
    }

	
    public static List<Images> getAllByContentID(int id) {
    	List<Images> listImages = new ArrayList<>() ;
        List<Record> records= Db.find("select * from images where contentId = ? order by imagesOrder",id) ;
    	for (Record record : records) {
    		Images aiImages = (Images)new Images().fetchFromRecord(record) ;
    		listImages.add(aiImages) ;
		}
    	return listImages ;
    }
    
}
