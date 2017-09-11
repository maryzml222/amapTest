package com.example.lib_db.dbutil;

import com.example.lib_db.DBApplication;
import com.example.lib_db.gen.DaoSession;
import com.example.lib_db.gen.RoutesLogDao;
import com.example.lib_db.model.RoutesLog;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/9
 * Description:
 */

public class RoutesLogUtils {
    private static RoutesLogUtils instance;
    private DaoSession mDaoSession;
    private static RoutesLogDao mRoutesLogDao;

    private RoutesLogUtils() {
    }

    public static RoutesLogUtils getInstance() {
        if (instance == null) {
            synchronized (TransLogUtils.class) {
                if (instance == null) {
                    instance = new RoutesLogUtils();
                }
                instance.mDaoSession = DBApplication.getDBApplication().getDaoSession();

                instance.mRoutesLogDao = instance.mDaoSession.getRoutesLogDao();
            }
        }
        return instance;
    }

    /**
     * 插入行程数据
     *
     * @param routesLog
     */
    public void insertRoutesLog(RoutesLog routesLog) {
        mRoutesLogDao.insert(routesLog);
    }

    /**
     * 更新行程数据
     *
     * @param routesLog
     */
    public void updateRoutesLog(RoutesLog routesLog) {
        mRoutesLogDao.update(routesLog);
    }

    /**
     * 根据路由行程角色类型和上下班行程标识, 其他行程标识获取对应路由信息
     *
     * @param routesLogRoleCategory
     * @param routesLogDirectionCategory
     * @return
     */
    public RoutesLog getRoutesLogByRoleAndDirect(String routesLogRoleCategory, String routesLogDirectionCategory, String phoneNumber) {
        QueryBuilder<RoutesLog> qb = mRoutesLogDao.queryBuilder();
        qb.where(RoutesLogDao.Properties.RoutesLogRoleCategory.eq(routesLogRoleCategory));
        qb.where(RoutesLogDao.Properties.RoutesLogDirectionCategory.eq(routesLogDirectionCategory));
        qb.where(RoutesLogDao.Properties.PhoneNumber.eq(phoneNumber));

        List<RoutesLog> routesLogList = qb.list();
        if (routesLogList != null && routesLogList.size() > 0) {
            return routesLogList.get(0);
        }

        return null;
    }





}
