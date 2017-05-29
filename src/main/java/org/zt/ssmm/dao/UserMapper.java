package org.zt.ssmm.dao;

import java.util.List;

import org.zt.ssmm.core.Article;
import org.zt.ssmm.core.Ip;
import org.zt.ssmm.core.Plt1;
import org.zt.ssmm.core.Spatial;
import org.zt.ssmm.core.Uploadpic;
import org.zt.ssmm.core.User;
import org.zt.ssmm.core.UserTimes;
import org.zt.ssmm.core.UserTips;
import org.zt.ssmm.core.Userdata;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);
    int insert2(User role);

    int insertSelective(User record);
  int 	updateUserdata(Userdata userData);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getAllUsers();
    Userdata getUserdatabyid(String Userid);
    
    List<User> getAllUsersWithRole();
    int selectUser(String name);
    int selectPhoneToday(String phoneNum);
    int insertIpinfo(Ip info);
    int insertBlackIp(Ip info);
    int insertPhoneToday(String phoneNum);
    int selectByNameOrPhone(User role);
    User selectByNamePWD(User role);
    int selectIpOneSecond(Ip info);
    int selectBlackIp(Ip info);
    List<UserTips> SelectTracByUserId(String id);
    int insertUploadPic(Uploadpic info);
    List<UserTimes> QuerySeqByUserId(String id);
     int 	insertArticle(Article info);
     List<Spatial> selectArticle(String info) ;
     List<Uploadpic> selectUsPic(String info) ;
      List<Plt1> selectPlt1(String info);
}