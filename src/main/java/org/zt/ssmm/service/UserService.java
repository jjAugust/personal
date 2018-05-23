package org.zt.ssmm.service;

import java.util.List;

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

public interface UserService 
{
	User getUserById(Integer id);
	List<User> getAllUsers();
	List<User> getAllUsersWithRole();
	List<UserTimesPercent>  SelectZeroPhase(String id);
	List<UserTimesPercent>  SelectPercentByTime(IdTimeInfo idTimeInfo);
	List<Cluster> QueryInfoByUserId(String id);
	Userdata getInfoById(String id);
	List<UserTimes> QuerySeqByUserId(String id);
	int updateUserdata(Userdata userData);
	int deleteUserAndPassword(int record);
	int deleteBlackIp(int record);
	int insertUserAndPassword(User role);
	int selectUser(String name);
	int insertIpinfo(Ip info);
	String querypicinfo(String id);
	String querylastpicinfo();
	int addpicinfo(Uploadpic pic);
	int selectByNameOrPhone(User role);
	User selectByNamePWD(User role);
	List<UserTips> SelectTracByUserId(String id);
	int selectIpOneSecond(Ip info);
	int selectPhoneToday(String phone);
	int insertPhoneToday(String phone);
	int insertBlackIp(Ip info);
	int selectBlackIp(Ip info);
	int insertArticle(Article info);
	List<Spatial> selectArticle(String info);
	List<Uploadpic> selectUsPic(String info);
	List<Plt1> selectplt1(String info);

//	Integer deleteUserAndPassword(Integer valueOf);
}
