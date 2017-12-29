package com.example.gaodemapdemo.dbUtil;


import com.example.gaodemapdemo.MyApplication;
import com.example.gaodemapdemo.dbUtil.model.User;
import com.example.gaodemapdemo.greendao.gen.DaoSession;
import com.example.gaodemapdemo.greendao.gen.UserDao;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


public class UserUtils {
    private static UserUtils instance;
    private DaoSession mDaoSession;
    private static UserDao mUseDao;

    private UserUtils() {
    }

    public static UserUtils getInstance() {
        if (instance == null) {
            synchronized (UserUtils.class) {
                if (instance == null) {
                    instance = new UserUtils();
                }
                instance.mDaoSession = MyApplication.getInstance().getDaoSession();

                mUseDao = instance.mDaoSession.getUserDao();
            }
        }
        return instance;
    }

    /**
     * 插入表数据
     *
     * @param transLog
     */
    public Long insertUser(User transLog) {
        return mUseDao.insert(transLog);
    }

    /**
     * 更新表数据
     *
     * @param transLog
     */
    public void updateUser(User transLog) {
        mUseDao.update(transLog);
    }

    /**
     * 清空表数据
     */
    public void clearUser() {
        mUseDao.deleteAll();
    }

    /**
     * 获取最后一笔路由订单信息
     * @return
     */
    public User getLastUser() {
        QueryBuilder<User> qb = mUseDao.queryBuilder();

        List<User> transLogList = qb.list();
        if (transLogList != null && transLogList.size() > 0) {
            return transLogList.get(transLogList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 根据路由订单主键--id查询
     *
     * @param id id
     * @return translog
     */
    public User getUserByID(String id) {
            QueryBuilder<User> qb = mUseDao.queryBuilder();
            qb.where(UserDao.Properties.Id.eq(id));

            List<User> transLogList = qb.list();
            if (transLogList != null && transLogList.size() > 0) {
                return transLogList.get(0);
            }

        return null;
    }

    /**
     * 根据路由订单主键--id删除记录
     *
     * @param id
     */
    public void deleteByID(Long id) {
        QueryBuilder<User> qb = mUseDao.queryBuilder();
        DeleteQuery<User> bd = qb.where(UserDao.Properties.Id.eq(id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 获取所有路由订单信息
     * @return
     */
    public List<User> getAllUser(String phoneNumber) {
        return mUseDao.queryBuilder().where(UserDao.Properties.Name.eq(phoneNumber)).list();
    }
}
