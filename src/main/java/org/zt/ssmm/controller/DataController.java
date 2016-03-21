package org.zt.ssmm.controller;

import java.util.ArrayList;
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
import org.zt.ssmm.util.ReturnUtil;


@Controller
@RequestMapping("/dataController")
public class DataController {
	@Autowired
	private UserService us;
	
	@RequestMapping("/getinfo")
	@ResponseBody  
	public Object showUser1( String id, HttpServletRequest req)
	{
		Userdata u = us.getInfoById(id);
		List<Uploadpic> list=us.selectUsPic(id);
		Returntype text=new Returntype();
		ReturnUtil.fix(text,"_KEYS_s01");
//		System.out.println(u);
		text.setData(u);
		text.setPic(list);
		return text;  
	}
	

	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}
}