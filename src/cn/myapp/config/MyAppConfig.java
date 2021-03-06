/**
 * 
 */
package cn.myapp.config;

import cn.myapp.controller.*;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

/**
 * @author teason
 */
public class MyAppConfig extends JFinalConfig {

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configConstant(com.jfinal.config.Constants)
	 */
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setEncoding("utf-8");
		me.setBaseViewPath("/WEB-INF/view");
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configRoute(com.jfinal.config.Routes)
	 */
	@Override
	public void configRoute(Routes me) {	
		me.add("/sign",SignController.class,"/") ;
		me.add("/login",LoginController.class,"/") ;
		me.add("/sms",SMSController.class,"/") ;
		me.add("/apns",APNSController.class,"/") ;
		me.add("/content",ContentController.class,"/") ;
		me.add("/kind",KindController.class,"/") ;
		me.add("/remind",RemindController.class,"/") ;
		me.add("/images",ImagesController.class) ;
		me.add("/tag",TagController.class,"/") ;
		me.add("/file",FileController.class,"/") ;
		me.add("/",ManageController.class) ;
		
		// 微信活动
		me.add("/luck", LuckyController.class,"/") ;
	}
	
	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configPlugin(com.jfinal.config.Plugins)
	 */
	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://127.0.0.1:3306/pro?useUnicode=true&characterEncoding=utf8",
				"root", "") ;
		
		me.add(cp) ;
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp) ;
		me.add(arp) ;

//		arp.addMapping("Images","imagesId",Images.class) ;
	}
	
	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.Interceptors)
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		
	}
	
	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configHandler(com.jfinal.config.Handlers)
	 */
	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx"));
	}
	
	@Override
	public void beforeJFinalStop() {
		// TODO Auto-generated method stub
		super.beforeJFinalStop();
		try {
			Unirest.shutdown() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
