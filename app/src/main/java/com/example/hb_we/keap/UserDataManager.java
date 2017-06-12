package com.example.hb_we.keap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hb_we on 2017/6/4.
 */

public class UserDataManager {             //用户数据管理类
    //一些宏定义和声明
    private static final String TAG = "UserDataManager";
    private static final String DB_NAME = "user_data";
    private static final String TABLE_NAME = "users";
    private static final String TABLE_INFO = "information";
    public static final String ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_SEX = "user_sex";
    public static final String USER_BIRTHYEAR= "user_birthyear";
    public static final String USER_HEIGHT = "user_height";
    public static final String USER_WEIGHT = "user_weight";
    private static final int DB_VERSION = 4;
    private Context mContext = null;

    //创建用户表
    private static final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " integer primary key autoincrement,"
            + USER_NAME + " varchar not null,"
            + USER_PWD + " varchar not null" + ");";

    private static final String CREATE_INFO_DB = "CREATE TABLE " + TABLE_INFO + " ("
            + ID + " integer primary key autoincrement,"
            + USER_NAME + " varchar not null,"
            + USER_SEX+ " varchar not null,"
            + USER_BIRTHYEAR + " varchar not null,"
            + USER_HEIGHT + " varchar not null,"
            + USER_WEIGHT + " varchar not null"+ ");";

    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    //DataBaseManagementHelper继承自SQLiteOpenHelper
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
            db.execSQL(CREATE_INFO_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO + ";");
            onCreate(db);
        }
    }

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");
    }
    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }
    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }
    //添加新用户，即注册
    public long insertUserData(UserData userData) {
        String userName=userData.getUserName();
        String userPwd=userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
    }

    public long insertUserInfo(UserInfo userInfo) {
        String userName=userInfo.getUserName();
        String userSex = userInfo.getUserSex();
        String userBirthYear = userInfo.getUserBirthYear();
        String userHeight = userInfo.getUserHeight();
        String userWeight = userInfo.getUserWeight();
        ContentValues values = new ContentValues();
        values.put(USER_NAME,userName);
        values.put(USER_SEX, userSex);
        values.put(USER_BIRTHYEAR,userBirthYear);
        values.put(USER_HEIGHT,userHeight);
        values.put(USER_WEIGHT,userWeight);
        return mSQLiteDatabase.insert(TABLE_INFO,null, values);
        //return mSQLiteDatabase.update(TABLE_INFO, values,USER_NAME+"=?",new String[]{userName})
    }
    //修改密码
    public boolean updateUserData(UserData userData) {
        int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME,userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.update(TABLE_NAME, values,USER_NAME+"=?",new String[]{userName}) > 0;
    }
    //修改个人资料
    public long updateUserInfo(UserInfo userInfo) {
        int id = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String userSex = userInfo.getUserSex();
        String userBirthYear = userInfo.getUserBirthYear();
        String userHeight = userInfo.getUserHeight();
        String userWeight = userInfo.getUserWeight();
        ContentValues values = new ContentValues();
//        values.put(USER_NAME,userName);
        values.put(USER_SEX, userSex);
        values.put(USER_BIRTHYEAR,userBirthYear);
        values.put(USER_HEIGHT,userHeight);
        values.put(USER_WEIGHT,userWeight);
        return mSQLiteDatabase.update(TABLE_INFO, values,USER_NAME+"=?",new String[]{userName});
    }

//    public void updateApplicationInfo(ApplicationInfo configInfo) {
//        dbOpenHelper
//                .getWritableDatabase()
//                .execSQL(
//                        "update application set s=?, tt=?, st=?, tc1=?, tc2=?,ru=?,tn=?,m=? where id=?",
//                        new Object[] { configInfo.getS(), configInfo.getTt(),
//                                configInfo.getSt(), configInfo.getTc1(),
//                                configInfo.getTc2(), configInfo.getRu(),
//                                configInfo.getTn(),configInfo.getM(), configInfo.getId() });
//    }

    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }
    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName){
        Log.i(TAG,"findUserByName , userName="+userName);
        int result=0;
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"=?", new String[]{userName}, null, null, null);
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName, null,null,null,null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return result;
    }
    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName,String pwd){
        Log.i(TAG,"findUserByNameAndPwd");
        int result=0;
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"=?"+" and "+USER_PWD+"=?",
                new String[]{userName,pwd}, null, null, null);
//        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName+" and "+USER_PWD+"="+pwd, null, null, null, null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByNameAndPwd , result="+result);
        }
        return result;
    }
    //根据用户名查询用户信息，用于显示用户信息
    public Cursor findInfoByName(String userName){
        openDataBase();
        return mSQLiteDatabase.query(TABLE_INFO,null,USER_NAME+"=?",new String[]{userName},
                null,null,null);
    }
}
