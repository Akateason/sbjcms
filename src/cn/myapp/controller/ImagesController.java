package cn.myapp.controller;

import cn.cms.model.Images;
import cn.myapp.model.ResultObj;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by apple on 16/7/29.
 */
public class ImagesController extends Controller {

    /**
     *int     imagesId
     *int     contentId
     *string  img
     *int     imagesOrder
     */

    public void adds(){
        ResultObj rb=null;

        //保存img数组
        String imgs=getPara("list");
        String[] imgs_arr=null;
        if(imgs.isEmpty() || imgs.length()<=2){
            rb=new ResultObj("0","图片列表不能为空",null);
        }else{
            String contentId=getPara("contentId");
            if(contentId.isEmpty()){
                rb=new ResultObj("0","内容ID不能为空",null);
            }else{
                JsonParser parser=new JsonParser();
                JsonElement els=parser.parse(imgs);
                if(els.isJsonArray()){
                    JsonArray json_arr=els.getAsJsonArray();
                    Iterator it=json_arr.iterator();
                    while (it.hasNext()){
                        Gson gson=new Gson();
                        JsonElement el=(JsonElement)it.next();
                        listImg lImg=gson.fromJson(el,listImg.class);
                        Images nImg=new Images();
                        nImg.setImg(lImg.getimg());
                        nImg.setImagesOrder(lImg.getimagesOrder());
                        nImg.setContentId(Integer.parseInt(contentId));
                        nImg.daoInsert("images","imagesId");
                        rb=new ResultObj("success");
                    }
                }
            }
        }


        renderJson(rb);
    }

    /**
     *
     * int contentId
     * int imagesOrder
     * string img
     *
     */

    public void add(){
        ResultObj rb=null;
        int contentId=getParaToInt("contentId",-1);
        int imagesOrder=getParaToInt("imagesOrder",99);
        String img=getPara("img");
        if(contentId==-1){
            rb=new ResultObj("0","内容ID不能为空",null);
            renderJson(rb);
            return;
        }
        if(img.equals("")){
            rb=new ResultObj("0","图片不能为空",null);
            renderJson(rb);
            return;
        }
        Images nImg=new Images();
        nImg.setImg(img);
        nImg.setImagesOrder(imagesOrder);
        nImg.setContentId(contentId);
        long imagesId=nImg.daoInsert("images","imagesId");
        if (imagesId > 0) {
            // success
            HashMap<String, Object> map = new HashMap<>();
            map.put("imagesId", imagesId);
            rb=new ResultObj(map);
        } else {
            // fail
            rb=new ResultObj(null);
        }
        renderJson(rb);
    }



    /**
     *
     * int imagesId
     * int imagesOrder
     *
     */

    public void update(){
        ResultObj rb=null;
        String imgId=getPara("imagesId");
        if(imgId.isEmpty()){
            rb=new ResultObj("0","图片ID不能为空",null);
        }else{
            String order=getPara("imagesOrder");
            if(order.isEmpty()){
                rb=new ResultObj("0","排序序号不能为空",null);
            }else{
                Record img=Db.findById("images","imagesId",Integer.parseInt(imgId));
                img.set("imagesOrder",Integer.parseInt(order));
                Db.update("images","imagesId",img);
                rb=new ResultObj("success");
                }
            }


        renderJson(rb);
    }

    public  void del(){
        ResultObj rb=null;
        String imgId=getPara("imagesId");
        if(imgId.isEmpty()){
            rb=new ResultObj("0","图片ID不能为空",null);
        }else{
            getModel(Images.class).deleteById(Integer.parseInt(imgId));
            rb=new ResultObj("success");
        }
        
        renderJson(rb);
    }


    private class listImg{
        private String img;
        private int imagesOrder;

        public String getimg() {
            return img;
        }
        public void setimg(String img) {
            this.img = img;
        }
        public int getimagesOrder() {
            return imagesOrder;
        }
        public void setimagesOrder(int imagesOrder) {
            this.imagesOrder = imagesOrder;
        }

    }


}
