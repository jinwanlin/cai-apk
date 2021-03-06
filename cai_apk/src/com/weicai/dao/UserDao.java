package com.weicai.dao;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.weicai.daoCore.SqliteDAO;
import com.weicai.model.User;
 
/**
 * @author jiuwuerliu@sina.com
 *
 * ����ʾ��
 */
public class UserDao {
    static final String tag="UserDao";
     
    private static SQLiteDatabase db;
    
    public static SQLiteDatabase createDatabase(){    
        SQLiteDatabase db=SQLiteDatabase.create(null);  
        String createdb=
                "CREATE TABLE IF NOT EXISTS t_user("
                +"id         INTEGER PRIMARY KEY,"
                +"name       VARCHAR(512)"
                +");";
        db.execSQL(createdb);       
        return db;
    }
     
    static{
        db=createDatabase();
    }
     
    public static User create(User user){
        SqliteDAO dao=new SqliteDAO(db);
        return dao.insert(user);
    }
     
    public static User find_by_id(int id){
        SqliteDAO dao=new SqliteDAO(db);
        return dao.loadByPrimaryKey(new User(id));
    }
    
    public static List<User> all(){
        SqliteDAO dao=new SqliteDAO(db);
        return dao.loadAll(User.class, null);
    }
    
    public static User first(){
        List<User> list = all();
        if(list.size() > 0){
        	return list.get(0);
        }else{
        	return null;
        }
    }
    
    public static long countAll(){
    	SqliteDAO dao=new SqliteDAO(db);
        return dao.countAll(User.class);
    }
}