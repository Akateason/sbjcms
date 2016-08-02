package cn.cms.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by apple on 16/7/29.
 */

@SuppressWarnings("serial")
public class Images extends Model<Images>
{	
	public static final Images dao = new Images();

    public Images getOneByID(int id){
        return Images.dao.findById(id);
    }

}
