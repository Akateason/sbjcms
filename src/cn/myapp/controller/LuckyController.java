package cn.myapp.controller;


import java.util.HashMap;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.myapp.model.ResultObj;
import cn.wx.activity.model.LuckyItem;

public class LuckyController extends Controller {
	
	public void match() {
		
		String constellation = getPara("constellation") ;//星座
		String zodiac = getPara("zodiac") ;//生肖
		String blood = getPara("blood") ;//血型
		
		// 丢失数据
		if (constellation == null || zodiac == null || blood == null) {			
			ResultObj resultObj = new ResultObj("2", "缺少参数", null) ;
			renderJson(resultObj);
			return ;
		}
		
		// 数据完整,开始匹配
		LuckyItem lucky = doMatching(constellation, zodiac, blood) ;
		if (lucky == null) {
			// fail to select
			ResultObj resultObj = new ResultObj("3", "查询无结果", null) ;
			renderJson(resultObj);
			return ;
		}
		// success 
		Gson gson = new Gson() ;
		String string = gson.toJson(lucky) ;
		HashMap<String, Object> map = gson.fromJson(string, HashMap.class) ;		
		map.put("display", displayString(lucky.getIdlucky())) ;
		renderJson(new ResultObj(map)) ;		
		return ;
	}
	
	
	private LuckyItem doMatching(String constellation,String zodiac, String blood) {
		String sql = "select * from pro.lucky where constellation = ? and zodiac = ? and blood = ? ;" ;
		Record recordItem = Db.findFirst(sql, constellation,zodiac,blood);
		if (recordItem == null) {
			return null ;
		}
		LuckyItem lucky = new LuckyItem() ;
		lucky = (LuckyItem) lucky.fetchFromRecord(recordItem) ;		
		return lucky ;
	}
	
	private String displayString(int idLucky) {
		String result = "" ;
		if (idLucky == 1) {
			result = "厉害了！你的运势战胜了所有人！" ;
		}
		else if (idLucky == 2) {
			result = "只差一名！你就是2017最强运了！" ;
		}
		else if (idLucky == 3) {
			result = "天了噜！你在所有人里排名第三！" ;
		}
		else if (idLucky >= 4 && idLucky <= 10) {
			result = "你居然挤进了前十！还有比你运气好的人吗？" ;
		}
		else if (idLucky >= 11 && idLucky <= 50) {
			result = "你的排名进入前五十，是当之无愧的欧洲人！" ;
		}
		else if (idLucky >= 51 && idLucky <= 100) {
			result = "你的排名挤进了前100！运势也太好了！" ;
		}
		else if (idLucky >= 101 && idLucky <= 200) {
			result = "新年的运势还不错哦！努力一下一定会有收获！" ;
		}
		else if (idLucky >= 201 && idLucky <= 299) {
			result = "你的运势排在中段！凡事要多注意哦！" ;
		}
		else if (idLucky >= 300 && idLucky <= 399) {
			result = "你的运势排名偏后…还有垫背的，不要灰心！" ;
		}
		else if (idLucky >= 400 && idLucky <= 499) {
			result = "这个排名…今年你还是多积攒点RP吧！" ;
		}
		else if (idLucky >= 500 && idLucky <= 549) {
			result = "非洲人你好，今年请远离抽卡游戏！" ;
		}
		else if (idLucky >= 550 && idLucky <= 566) {
			result = "好可怕，你只差一点就排人倒数十名了…" ;
		}
		else if (idLucky >= 567 && idLucky <= 573) {
			result = "你的运势排在今年倒数十名…心疼你。" ;
		}
		else if (idLucky == 574) {
			result = "算了，别难过，后面还有两个垫背的！" ;
		}
		else if (idLucky == 575) {
			result = "……至少你还不是最惨的啊！" ;
		}
		else if (idLucky == 576) {
			result ="最后一名和第一名，不同角度来看都很厉害！" ;
		}
		
		return result ;
	}
	
	
}
