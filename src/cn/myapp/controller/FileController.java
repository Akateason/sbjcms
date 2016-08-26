package cn.myapp.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.util.UUID;

/**
 * Created by apple on 16/8/22.
 */
public class FileController extends Controller {
    public void upload(){
        UploadFile uf = getFile();
        String filepath=uf.getUploadPath();
        String extname=uf.getFileName().substring(uf.getFileName().lastIndexOf("."));
        String filename=UUID.randomUUID().toString()+extname;
        boolean rennameFlag=uf.getFile().renameTo(new File(filepath+"/"+filename));
        renderText("/"+JFinal.me().getConstants().getBaseUploadPath()+"/"+filename);
    }
}
