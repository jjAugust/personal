package org.zt.ssmm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zt.ssmm.core.Returntype;
import org.zt.ssmm.core.Uploadpic;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.Userdata;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.Common;
import org.zt.ssmm.util.ReturnUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/PlaceController")
public class GoogleMapsController {
	@Autowired
	private UserService us;
	
	@RequestMapping("/getinfo")
	@ResponseBody  
	public Object showUser1( String lat,String lon,String rad, HttpServletRequest req)
	{
		final String  COMPLEX_JSON_STR = Common.httpsRequest("https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
				+ "location="+lat+","+lon+
	"&radius="+rad+"&rankby=prominence&key=AIzaSyB9QkoSbuV6nmyKcJwaPmDUD_wXIEK8a38","GET",null);
		JSONObject jsonObject = JSON.parseObject(COMPLEX_JSON_STR);
        //JSONObject jsonObject1 = JSONObject.parseObject(COMPLEX_JSON_STR);//因为JSONObject继承了JSON，所以这样也是可以的
//      JSONArray students = jsonObject.getJSONArray("students");
//        String teacherName = jsonObject.getString("teacherName");
//        Integer teacherAge = jsonObject.getInteger("teacherAge");
//        System.out.println(temp.get("types"));

		HashMap<String, Integer> to=new HashMap<String, Integer>();
		JSONArray results = jsonObject.getJSONArray("results");
		String TotalTypes="";
		 int size = results.size();
	        for (int i = 0; i < size; i++){
	            JSONObject temp = results.getJSONObject(i);
	            String t_string=temp.getString("types");
	            String change_string="";
	            for(int tin=0;tin<t_string.length();tin++){
	            	char temp_char=t_string.charAt(tin);
	            	if((String.valueOf(temp_char).toLowerCase().charAt(0)>='a'&&
	            			String.valueOf(temp_char).toLowerCase().charAt(0)<='z')||
	            					temp_char==','||temp_char=='_'){
	            		change_string+=t_string.charAt(tin);
	            	}
	            }
	            String[] a=change_string.split(",");
	            for(String tem:a){
		            if(to.containsKey(tem)){
		            	int t=to.get(tem);
		            	to.put(tem, t+1);
		            }
		            else{
		            	to.put(tem, 1);
		            }
	            }
	            TotalTypes+=temp.getString("types");
	        }
		return to;  
	}
	

	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}
}