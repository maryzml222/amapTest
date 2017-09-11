package com.example.lib_db.dbutil;

import com.example.lib_db.DBApplication;
import com.example.lib_db.gen.DaoSession;
import com.example.lib_db.gen.TransLogDao;
import com.example.lib_db.gen.UserInfoLogDao;
import com.example.lib_db.model.TransLog;
import com.example.lib_db.model.UserInfoLog;
import com.example.lib_db.utils.StringUtils;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by ${Mary} on 2017/8/16.
 */

public class UserInfoLogUtils {

    private static UserInfoLogUtils instance;
    private DaoSession mDaoSession;
    private static UserInfoLogDao mTransLogDao;

    private UserInfoLogUtils() {
    }

    public static UserInfoLogUtils getInstance() {
        if (instance == null) {
            synchronized (UserInfoLogUtils.class) {
                if (instance == null) {
                    instance = new UserInfoLogUtils();
                }
                instance.mDaoSession = DBApplication.getDBApplication().getDaoSession();

                instance.mTransLogDao = instance.mDaoSession.getUserInfoLogDao();
            }
        }
        return instance;
    }

    /**
     * 插入表数据
     *
     * @param transLog
     */
    public void insertTransLog(UserInfoLog transLog) {
        mTransLogDao.insert(transLog);
    }

    /**
     * 更新表数据
     *
     * @param transLog
     */
    public void updateTransLog(UserInfoLog transLog) {
        mTransLogDao.update(transLog);
    }

    /**
     * 清空表数据
     */
    public void clearTransLog() {
        mTransLogDao.deleteAll();
    }


    /**
     * 根据用户手机号
     *
     * @param phoneNumber id
     * @return UserInfoLog
     */
    public UserInfoLog getUserInfoLogByPhoneNumber(String phoneNumber) {
        if(StringUtils.isNotEmpty(phoneNumber)) {
            QueryBuilder<UserInfoLog> qb = mTransLogDao.queryBuilder();
            qb.where(UserInfoLogDao.Properties.PhoneNumber.eq(phoneNumber));

            List<UserInfoLog> transLogList = qb.list();
            if (transLogList != null && transLogList.size() > 0) {
                return transLogList.get(0);
            }
        }

        return null;
    }

    /**
     * 根据用户手机号--删除记录
     *
     * @param phoneNumber
     */
    public void deleteByPhoneNumber(Long phoneNumber) {
        QueryBuilder<UserInfoLog> qb = mTransLogDao.queryBuilder();
        DeleteQuery<UserInfoLog> bd = qb.where(TransLogDao.Properties.PhoneNumber.eq(phoneNumber)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 获取所有路由订单信息
     * @return
     */
    public List<UserInfoLog> getAllUserInfoLog() {
        return mTransLogDao.queryBuilder().list();
    }
}
