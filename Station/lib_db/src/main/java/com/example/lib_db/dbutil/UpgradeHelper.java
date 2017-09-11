/*
 * Copyright (c) 2015  DreamLiner Studio
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lib_db.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lib_db.gen.DaoMaster;
import com.example.lib_db.gen.RoutesLogDao;
import com.example.lib_db.gen.TransLogDao;

import org.greenrobot.greendao.database.Database;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/1
 * Description: 升级Database
 */

public class UpgradeHelper extends DaoMaster.DevOpenHelper {

    public static String TAG = UpgradeHelper.class.getSimpleName();

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * Here is where the calls to upgrade are executed
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        /* i represent the version where the user is now and the class named with this number implies that is upgrading from i to
        i++ schema */
        for (int i = oldVersion; i < newVersion; i++) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");

            MigrationHelper.getInstance().migrate(db, TransLogDao.class, RoutesLogDao.class);
        }
    }
}

