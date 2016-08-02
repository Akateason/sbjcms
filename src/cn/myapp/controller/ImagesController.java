package cn.myapp.controller;

import cn.cms.model.Images;
import cn.myapp.model.ResultObj;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jfinal.core.Controller;

import java.util.Iterator;

/**
 * Created by apple on 16/7/29.
 */
public class ImagesController extends Controller {

    /**
     *int     imagesId
     *int     contentId
     *string  img
     *int     order
     */

    public void add(){
        ResultObj rb=null;
        String imgs=getPara("list");
        String[] imgs_arr=null;
        if(imgs.isEmpty() || imgs.length()<=2){
            rb=new ResultObj("0","图片列表不能为空",null);
        }else{
            String contentId=getPara("contentId");
            if(contentId.isEmpty()){
                rb=new ResultObj("0","内容ID不能为空",null);
            }else{
                /*imgs=imgs.substring(1,imgs.length()-1);
                imgs_arr=imgs.split(",");
                for(String img:imgs_arr){
                    Gson gson=new Gson();
                    listImg listImg=gson.fromJson(img,listImg.class);
                    new Images().set("imgUrl",listImg.getimgUrl()).set("order",listImg.getorder()).set("contentId",contentId).save();
                    rb=new ResultObj();
                }*/

                JsonParser parser=new JsonParser();
                JsonElement els=parser.parse(imgs);
                if(els.isJsonArray()){
                    JsonArray json_arr=els.getAsJsonArray();
                    Iterator it=json_arr.iterator();
                    while (it.hasNext()){
                        Gson gson=new Gson();
                        JsonElement el=(JsonElement)it.next();
                        listImg lImg=gson.fromJson(el,listImg.class);
                        new Images().set("img",lImg.getimgUrl()).set("order",lImg.getorder()).set("contentId",contentId).save();
                        rb=new ResultObj("success");
                    }
                }
            }
        }

        renderJson(rb);
    }

    public void update(){
        ResultObj rb=null;
        String imgId=getPara("imagesId");
        if(imgId.isEmpty()){
            rb=new ResultObj("0","图片ID不能为空",null);
        }else{
            String order=getPara("order");
            if(order.isEmpty()){
                rb=new ResultObj("0","排序序号不能为空",null);
            }else{
                Images img=getModel(Images.class).getOneByID(Integer.parseInt(imgId));
                img.set("order",Integer.parseInt(order)).update();
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
            Images img=getModel(Images.class).getOneByID(Integer.parseInt(imgId));
            img.delete();
            rb=new ResultObj("success");
        }
        
        renderJson(rb);
    }

    private class listImg{
        private String imgUrl;
        private int order;

        public String getimgUrl() {
            return imgUrl;
        }
        public void setimgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        public int getorder() {
            return order;
        }
        public void setorder(int order) {
            this.order = order;
        }

    }


}
