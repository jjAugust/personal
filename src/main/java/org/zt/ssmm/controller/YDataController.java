package org.zt.ssmm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.Cluster;
import org.zt.ssmm.core.Plt1;
import org.zt.ssmm.core.Returntype;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.UserTimes;
import org.zt.ssmm.core.UserTimesPercent;
import org.zt.ssmm.core.UserTips;
import org.zt.ssmm.core.Userdata;
import org.zt.ssmm.service.CommenFunction;
import org.zt.ssmm.service.UserService;
import org.zt.ssmm.util.Common;
import org.zt.ssmm.util.ReturnUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import	java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import	org.apache.http.HttpEntity;
import	org.apache.http.HttpResponse;
import	org.apache.http.client.ResponseHandler;
import	org.apache.http.client.methods.HttpUriRequest;
import	org.apache.http.client.methods.RequestBuilder;
import	org.apache.http.impl.client.CloseableHttpClient;
import	org.apache.http.impl.client.HttpClients;
import	org.apache.http.util.EntityUtils;

@Controller
@RequestMapping("/YDataController")
public class YDataController 
{
	@Autowired
	private UserService us;
	private UserTimes temp;

	@ResponseBody
	@RequestMapping("/SelectTracByUserId")
	public Object SelectTracByUserId(String id, HttpServletRequest req,HttpSession httpSession)
	{
		List<UserTips> u = us.SelectTracByUserId(id);
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(u);
		return text;  
	}

	@SuppressWarnings("resource")
	@ResponseBody
	@RequestMapping("/getclusterdata")
	public  Object getclusterdata(String id, HttpServletRequest req,HttpSession httpSession) throws IOException
	{
		 BufferedReader BUFF;
	List<Cluster> res= new LinkedList<Cluster>();
//		BUFF = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/xiongjunjie/Documents/github/personal/src/main/webapp/images/Afterclusterdata.txt"))));
	BUFF = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Common.runClassPath+"../default/images/Afterclusterdata.txt"))));

	String str = BUFF.readLine();
		while((str = BUFF.readLine()) != null){
			String []cor = str.split(",");
			Cluster temp=new Cluster();
			temp.setUser_id(cor[0]);
			temp.setLatitude(cor[1]);
			temp.setLongtitude(cor[2]);
			res.add(temp);
		}
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(res);
		return text;  
		}

	@ResponseBody
	@RequestMapping("/getdata")			
	public Object getdata(String userid,double lng,double lat, HttpServletRequest req,HttpSession httpSession)
	{
		List<Cluster> u = us.QueryInfoByUserId(userid);
		CommenFunction CommenFunction=new CommenFunction();
		List<Cluster> res= new LinkedList<Cluster>();
		for(Cluster c:u){
			if(CommenFunction.Geo_Distance(lat, lng,  Double.valueOf(c.getLongtitude()),Double.valueOf(c.getLatitude()))<0.8){
				Cluster temp=new Cluster();
				temp.setUser_id(c.getUser_id());
				temp.setLatitude(c.getLongtitude());
				temp.setLongtitude(c.getLatitude());
				res.add(temp);
			}
		}
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
		text.setData(res);
		return text;  
	}
				
				
	
	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	
	
}
