package com.example.lib_db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.lib_db.dbutil.UpgradeHelper;
import com.example.lib_db.gen.DaoMaster;
import com.example.lib_db.gen.DaoSession;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/1
 * Description: Database基础Application需要被上层应用Application继承
 */

public class DBApplication extends Application {
    private static DBApplication mDBApplication;
    private DaoSession mDaoSession;
    private SQLiteDatabase mDb;

    @Override
    public void onCreate() {
        mDBApplication = this;
    }

    public static DBApplication getDBApplication() {

        return mDBApplication;
    }

    /*
    * GreenDao相关
    */
    public synchronized DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }

        return mDaoSession;
    }

    private void initDaoSession() {
        // 获取Database帮助对象
        // 自动执行upgrade的逻辑.backup all table → del all table → create all new table → restore data
        // 数据库的名称: STATION_HITCHHIKE_TRANSACTION.db
        UpgradeHelper helper = new UpgradeHelper(this, "STATION_HITCHHIKE_TRANSACTION.db", null);
        // 得到可写的数据库操作对象
        mDb = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(mDb);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
