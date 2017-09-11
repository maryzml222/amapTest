package com.example.lib_db.dbutil;

import com.example.lib_db.DBApplication;
import com.example.lib_db.gen.DaoSession;
import com.example.lib_db.gen.TransLogDao;
import com.example.lib_db.model.TransLog;
import com.example.lib_db.utils.StringUtils;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/1
 * Description:
 */

public class TransLogUtils {

    private static TransLogUtils instance;
    private DaoSession mDaoSession;
    private static TransLogDao mTransLogDao;

    private TransLogUtils() {
    }

    public static TransLogUtils getInstance() {
        if (instance == null) {
            synchronized (TransLogUtils.class) {
                if (instance == null) {
                    instance = new TransLogUtils();
                }
                instance.mDaoSession = DBApplication.getDBApplication().getDaoSession();

                instance.mTransLogDao = instance.mDaoSession.getTransLogDao();
            }
        }
        return instance;
    }

    /**
     * 插入表数据
     *
     * @param transLog
     */
    public void insertTransLog(TransLog transLog) {
        mTransLogDao.insert(transLog);
    }

    /**
     * 更新表数据
     *
     * @param transLog
     */
    public void updateTransLog(TransLog transLog) {
        mTransLogDao.update(transLog);
    }

    /**
     * 清空表数据
     */
    public void clearTransLog() {
        mTransLogDao.deleteAll();
    }

    /**
     * 获取最后一笔路由订单信息
     * @return
     */
    public TransLog getLastTransLog() {
        QueryBuilder<TransLog> qb = mTransLogDao.queryBuilder();

        List<TransLog> transLogList = qb.list();
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
    public TransLog getTransLogByID(String id) {
        if(StringUtils.isNotEmpty(id)) {
            QueryBuilder<TransLog> qb = mTransLogDao.queryBuilder();
            qb.where(TransLogDao.Properties.Id.eq(id));

            List<TransLog> transLogList = qb.list();
            if (transLogList != null && transLogList.size() > 0) {
                return transLogList.get(0);
            }
        }

        return null;
    }

    /**
     * 根据路由订单主键--id删除记录
     *
     * @param id
     */
    public void deleteByID(Long id) {
        QueryBuilder<TransLog> qb = mTransLogDao.queryBuilder();
        DeleteQuery<TransLog> bd = qb.where(TransLogDao.Properties.Id.eq(id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 获取所有路由订单信息
     * @return
     */
    public List<TransLog> getAllTransLog(String phoneNumber) {
        return mTransLogDao.queryBuilder().where(TransLogDao.Properties.PhoneNumber.eq(phoneNumber)).list();
    }
}
