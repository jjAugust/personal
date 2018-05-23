package org.zt.ssmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.Cluster;
import org.zt.ssmm.core.IdTimeInfo;
import org.zt.ssmm.core.Ip;
import org.zt.ssmm.core.Plt1;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.Uploadpic;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.UserTimes;
import org.zt.ssmm.core.UserTimesPercent;
import org.zt.ssmm.core.UserTips;
import org.zt.ssmm.core.Userdata;
import org.zt.ssmm.dao.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserMapper userMapper;

	@Override
	public int addpicinfo(Uploadpic pic)
	{
		return userMapper.addpicinfo(pic);
	}
	
	@Override
	public String querylastpicinfo()
	{
		return userMapper.querylastpicinfo();
	}
	
	@Override
	public String querypicinfo(String id)
	{
		return userMapper.querypicinfo(id);
	}

	@Override
	public User getUserById(Integer id) 
	{
		return userMapper.selectByPrimaryKey(id);
	}
	@Override
	public   List<Cluster> QueryInfoByUserId(String id)

	{
		return userMapper.QueryInfoByUserId(id);
	}
	
	@Override
	public   int deleteBlackIp(int record)

	{
		return userMapper.deleteBlackIp(record);
	}
	@Override
	public  	List<UserTimesPercent>  SelectPercentByTime(IdTimeInfo idTimeInfo)
	{
		return userMapper.SelectPercentByTime(idTimeInfo);
	}

	@Override
	public List<UserTimes> QuerySeqByUserId(String id)
	{
		return userMapper.QuerySeqByUserId(id);
	}
	
	@Override
	public List<UserTimesPercent>  SelectZeroPhase(String id)
	{
		return userMapper.SelectZeroPhase(id);
	}

	@Override
	public List<User> getAllUsers() 
	{
		return userMapper.getAllUsers();
	}
	
	@Override
	public Userdata getInfoById(String id) 
	{
		return userMapper.getUserdatabyid(id);
	}
	
	
	@Override
	public List<User> getAllUsersWithRole() 
	{
		return userMapper.getAllUsersWithRole();
	}

	@Override
	public int deleteUserAndPassword(int record) 
	{
		return userMapper.deleteByPrimaryKey(record);
	}
	
	@Override
	public int selectByNameOrPhone(User role) 
	{
		return userMapper.selectByNameOrPhone(role);
	}
	
	@Override
	public int insertUserAndPassword(User role) 
	{
		return userMapper.insert2(role);
	}
	
	@Override
	public int selectUser(String role) 
	{
		return userMapper.selectUser(role);
	}
	
	@Override
	public int insertIpinfo(Ip info) 
	{
		return userMapper.insertIpinfo(info);
	}
	
	@Override
	public int insertBlackIp(Ip info) 
	{
		return userMapper.insertBlackIp(info);
	}
	
	@Override
	public int 	insertPhoneToday(String phone)
	{
		return userMapper.insertPhoneToday(phone);
	}

	
	@Override
	public int selectPhoneToday(String phone) 
	{
		return userMapper.selectPhoneToday(phone);
	}
	
	@Override
	public int 	updateUserdata(Userdata userData)
	{
		return userMapper.updateUserdata(userData);
	}

	
	@Override
	public User selectByNamePWD(User info) 
	{
		return userMapper.selectByNamePWD(info);
	}
	
	@Override
	public int selectIpOneSecond(Ip info) 
	{
		return userMapper.selectIpOneSecond(info);
	}
	
	@Override
	public int selectBlackIp(Ip info) 
	{
		return userMapper.selectBlackIp(info);
	}
	
	@Override
	public int 	insertArticle(Article info)
	{
		return userMapper.insertArticle(info);
	}
	@Override
	public List<Spatial> selectArticle(String info) 
	{
		return userMapper.selectArticle(info);
	}
	@Override
	
	public List<Plt1> selectplt1(String info)
	{
		return userMapper.selectPlt1(info);
	}
	@Override
	public List<Uploadpic> selectUsPic(String info) 
	{
		return userMapper.selectUsPic(info);
	}
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	@Override
	public List<UserTips> SelectTracByUserId(String id) {
		return null;
	}



}
