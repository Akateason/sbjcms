package cn.myapp.controller;

import java.util.HashMap;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

import cn.myapp.util.rsa.*;
import cn.myapp.model.ResultObj;

public class SignController extends Controller {
	
	public void rsa() throws Exception {
		
		String stringWillSign = getPara("string", null) ;
		if (stringWillSign == null) {
			ResultObj resultObj = new ResultObj("1", "string不能为空", null) ;
			renderJson(resultObj) ;
			return;
		}
		
		String privateKey = PropKit.use("RSA.txt").get("privateKey") ;		
        String signstr = RSASignature.sign(stringWillSign,privateKey) ;  
//        System.out.println("签名原串："+stringWillSign) ;  
//        System.out.println("签名串："+signstr) ;  
        HashMap<String, String> mapResult = new HashMap<>() ;
        mapResult.put("sign", signstr) ;
        renderJson(new ResultObj(mapResult)) ;		        
	}
	
}
