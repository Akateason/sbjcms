package cn.myapp.controller;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.util.UUID;

/**
 * Created by apple on 16/8/22.
 */
public class FileController extends Controller {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "DuqjJwtgQ1lOuya_ON6CtpODRJmDgKmriOiGewCX";
    String SECRET_KEY = "gQxIYAWtyPrT_ELUARUQf7dMSSTkc4H1eltKyamD";
    //要上传的空间
    String bucketname = "cmsimg";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname);
    }

    public void upload(){
        UploadFile uf = getFile();
        String filepath=uf.getUploadPath();
        String extname=uf.getFileName().substring(uf.getFileName().lastIndexOf("."));
        String filename=UUID.randomUUID().toString()+extname;
        boolean rennameFlag=uf.getFile().renameTo(new File(filepath+"/"+filename));

        //上传七牛
        try {
            //调用put方法上传
            Response res = uploadManager.put(filepath+"/"+filename, filename, getUpToken());
            //打印返回的信息
            Gson gson=new Gson();
            qiniuRes qiniures=gson.fromJson(res.bodyString(),qiniuRes.class);
            renderText("http://cmsimg.subaojiang.com/"+qiniures.key);
            return ;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
            //TODO 传一个出错图片
            renderText("http://cmsimg.subaojiang.com/5a36b9b8-38bb-434e-bfd4-6d9d51900424.jpg");
        }
    }
    private class qiniuRes{
        private String hash;
        private String key;
        public void setHash(String hash){this.hash=hash;}
        public void setKey(String key){this.key=key;}
        public String getHash(){return this.hash;}
        public String getKey(){return this.key;}
    }
}

