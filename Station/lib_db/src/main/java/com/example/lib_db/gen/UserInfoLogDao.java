package com.example.lib_db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.lib_db.model.UserInfoLog;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO_LOG".
*/
public class UserInfoLogDao extends AbstractDao<UserInfoLog, Long> {

    public static final String TABLENAME = "USER_INFO_LOG";

    /**
     * Properties of entity UserInfoLog.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PhoneNumber = new Property(1, String.class, "phoneNumber", false, "phoneNumber");
        public final static Property Token = new Property(2, String.class, "token", false, "token");
        public final static Property IsLogin = new Property(3, boolean.class, "isLogin", false, "isLogin");
        public final static Property IsDriver = new Property(4, boolean.class, "isDriver", false, "isDriver");
        public final static Property Nickname = new Property(5, String.class, "nickname", false, "nickname");
        public final static Property Sex = new Property(6, String.class, "sex", false, "sex");
        public final static Property Birth = new Property(7, String.class, "birth", false, "birth");
        public final static Property InaugurationUnit = new Property(8, String.class, "inaugurationUnit", false, "inaugurationUnit");
        public final static Property Industry = new Property(9, String.class, "industry", false, "industry");
        public final static Property Position = new Property(10, String.class, "position", false, "position");
        public final static Property Signature = new Property(11, String.class, "signature", false, "signature");
        public final static Property RealName = new Property(12, String.class, "realName", false, "realName");
        public final static Property DriverLicenseNumber = new Property(13, String.class, "driverLicenseNumber", false, "driverLicenseNumber");
        public final static Property LicensePlateNumber = new Property(14, String.class, "licensePlateNumber", false, "licensePlateNumber");
        public final static Property VehicleOwner = new Property(15, String.class, "vehicleOwner", false, "vehicleOwner");
        public final static Property VehicleBrand = new Property(16, String.class, "vehicleBrand", false, "vehicleBrand");
        public final static Property RegisterDate = new Property(17, String.class, "registerDate", false, "registerDate");
        public final static Property HeadUrl = new Property(18, String.class, "headUrl", false, "headUrl");
        public final static Property DrivingLicenseUrl = new Property(19, String.class, "drivingLicenseUrl", false, "drivingLicenseUrl");
        public final static Property DriverLicense = new Property(20, String.class, "driverLicense", false, "driverLicense");
    }


    public UserInfoLogDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoLogDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_LOG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"phoneNumber\" TEXT," + // 1: phoneNumber
                "\"token\" TEXT," + // 2: token
                "\"isLogin\" INTEGER NOT NULL ," + // 3: isLogin
                "\"isDriver\" INTEGER NOT NULL ," + // 4: isDriver
                "\"nickname\" TEXT," + // 5: nickname
                "\"sex\" TEXT," + // 6: sex
                "\"birth\" TEXT," + // 7: birth
                "\"inaugurationUnit\" TEXT," + // 8: inaugurationUnit
                "\"industry\" TEXT," + // 9: industry
                "\"position\" TEXT," + // 10: position
                "\"signature\" TEXT," + // 11: signature
                "\"realName\" TEXT," + // 12: realName
                "\"driverLicenseNumber\" TEXT," + // 13: driverLicenseNumber
                "\"licensePlateNumber\" TEXT," + // 14: licensePlateNumber
                "\"vehicleOwner\" TEXT," + // 15: vehicleOwner
                "\"vehicleBrand\" TEXT," + // 16: vehicleBrand
                "\"registerDate\" TEXT," + // 17: registerDate
                "\"headUrl\" TEXT," + // 18: headUrl
                "\"drivingLicenseUrl\" TEXT," + // 19: drivingLicenseUrl
                "\"driverLicense\" TEXT);"); // 20: driverLicense
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_LOG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoLog entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(3, token);
        }
        stmt.bindLong(4, entity.getIsLogin() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsDriver() ? 1L: 0L);
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(6, nickname);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(8, birth);
        }
 
        String inaugurationUnit = entity.getInaugurationUnit();
        if (inaugurationUnit != null) {
            stmt.bindString(9, inaugurationUnit);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(10, industry);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(11, position);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(12, signature);
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(13, realName);
        }
 
        String driverLicenseNumber = entity.getDriverLicenseNumber();
        if (driverLicenseNumber != null) {
            stmt.bindString(14, driverLicenseNumber);
        }
 
        String licensePlateNumber = entity.getLicensePlateNumber();
        if (licensePlateNumber != null) {
            stmt.bindString(15, licensePlateNumber);
        }
 
        String vehicleOwner = entity.getVehicleOwner();
        if (vehicleOwner != null) {
            stmt.bindString(16, vehicleOwner);
        }
 
        String vehicleBrand = entity.getVehicleBrand();
        if (vehicleBrand != null) {
            stmt.bindString(17, vehicleBrand);
        }
 
        String registerDate = entity.getRegisterDate();
        if (registerDate != null) {
            stmt.bindString(18, registerDate);
        }
 
        String headUrl = entity.getHeadUrl();
        if (headUrl != null) {
            stmt.bindString(19, headUrl);
        }
 
        String drivingLicenseUrl = entity.getDrivingLicenseUrl();
        if (drivingLicenseUrl != null) {
            stmt.bindString(20, drivingLicenseUrl);
        }
 
        String driverLicense = entity.getDriverLicense();
        if (driverLicense != null) {
            stmt.bindString(21, driverLicense);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoLog entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(2, phoneNumber);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(3, token);
        }
        stmt.bindLong(4, entity.getIsLogin() ? 1L: 0L);
        stmt.bindLong(5, entity.getIsDriver() ? 1L: 0L);
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(6, nickname);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(7, sex);
        }
 
        String birth = entity.getBirth();
        if (birth != null) {
            stmt.bindString(8, birth);
        }
 
        String inaugurationUnit = entity.getInaugurationUnit();
        if (inaugurationUnit != null) {
            stmt.bindString(9, inaugurationUnit);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(10, industry);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(11, position);
        }
 
        String signature = entity.getSignature();
        if (signature != null) {
            stmt.bindString(12, signature);
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(13, realName);
        }
 
        String driverLicenseNumber = entity.getDriverLicenseNumber();
        if (driverLicenseNumber != null) {
            stmt.bindString(14, driverLicenseNumber);
        }
 
        String licensePlateNumber = entity.getLicensePlateNumber();
        if (licensePlateNumber != null) {
            stmt.bindString(15, licensePlateNumber);
        }
 
        String vehicleOwner = entity.getVehicleOwner();
        if (vehicleOwner != null) {
            stmt.bindString(16, vehicleOwner);
        }
 
        String vehicleBrand = entity.getVehicleBrand();
        if (vehicleBrand != null) {
            stmt.bindString(17, vehicleBrand);
        }
 
        String registerDate = entity.getRegisterDate();
        if (registerDate != null) {
            stmt.bindString(18, registerDate);
        }
 
        String headUrl = entity.getHeadUrl();
        if (headUrl != null) {
            stmt.bindString(19, headUrl);
        }
 
        String drivingLicenseUrl = entity.getDrivingLicenseUrl();
        if (drivingLicenseUrl != null) {
            stmt.bindString(20, drivingLicenseUrl);
        }
 
        String driverLicense = entity.getDriverLicense();
        if (driverLicense != null) {
            stmt.bindString(21, driverLicense);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfoLog readEntity(Cursor cursor, int offset) {
        UserInfoLog entity = new UserInfoLog( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // phoneNumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // token
            cursor.getShort(offset + 3) != 0, // isLogin
            cursor.getShort(offset + 4) != 0, // isDriver
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nickname
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // sex
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // birth
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // inaugurationUnit
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // industry
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // position
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // signature
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // realName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // driverLicenseNumber
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // licensePlateNumber
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // vehicleOwner
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // vehicleBrand
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // registerDate
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // headUrl
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // drivingLicenseUrl
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // driverLicense
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoLog entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhoneNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setToken(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsLogin(cursor.getShort(offset + 3) != 0);
        entity.setIsDriver(cursor.getShort(offset + 4) != 0);
        entity.setNickname(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSex(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBirth(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setInaugurationUnit(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIndustry(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPosition(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSignature(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRealName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDriverLicenseNumber(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setLicensePlateNumber(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setVehicleOwner(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setVehicleBrand(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setRegisterDate(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setHeadUrl(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setDrivingLicenseUrl(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setDriverLicense(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfoLog entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfoLog entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoLog entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
