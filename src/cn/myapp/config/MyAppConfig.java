/**
 * 
 */
package cn.myapp.config;

import cn.cms.model.Images;
import cn.myapp.controller.ContentController;
import cn.myapp.controller.ImagesController;
import cn.myapp.controller.KindController;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.mashape.unirest.http.Unirest;

<<<<<<< HEAD
import java.io.IOException;
=======
import cn.myapp.controller.ContentController;
import cn.myapp.controller.KindController;
import cn.myapp.controller.TagController;
>>>>>>> 061fb581ccbf5e280a3bc65ff95fe7d972672681

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
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configRoute(com.jfinal.config.Routes)
	 */
	@Override
	public void configRoute(Routes me) {		
		me.add("/content",ContentController.class) ;
		me.add("/kind",KindController.class) ;
		me.add("/images",ImagesController.class) ;
		me.add("/tag",TagController.class) ;
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

		arp.addMapping("Images","imagesId",Images.class);
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
