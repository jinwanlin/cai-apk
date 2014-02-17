package com.weicai.daoCore;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
 
/**
 * @author jiuwuerliu@sina.com
 * 
 */
public class SqliteDAO{
    static final String tag="DAO";
     
    /**
     * ���ʵ����ݿ�
     */
    private SQLiteDatabase db;
     
    /**
     * ���ݲ����ͻ����ʽ:
     * 0-����
     * 1-�׳��쳣
     * 2-�滻����
     */
    private int conflictType=2;
     
    public SqliteDAO(SQLiteDatabase db){
        this.db=db;        
    }
  
    public SQLiteDatabase getSQLiteDatabase(){
        return db;
    }
     
    /**
     * ����������ݿ�, �洢����������ֶε����ݿ�Ķ�Ӧ�ֶ�,����NULL�ֶ�.
     * @param entity  ������Ķ���
     * @return ����������ݿ�ɹ��򷵻ظö���,���򷵻�NULL
     */
    public <T> T insert(T entity){
        return insert(entity,false);
    }
     
    /**
     * ����������ݿ�, ���洢����ķǿ��ֶε����ݿ�,�����NULL�ֶν�������.
     * @param entity ������Ķ���
     * @return ����������ݿ�ɹ��򷵻ظö���,���򷵻�NULL
     */
    public <T> T insertSelective(T entity){
        return insert(entity,true);
    }
     
     
    private <T> T insert(T entity,boolean selective){    
        ContentValues values=getContentValues(entity,selective);
		Log.i(tag, "insert id: "+ values.getAsInteger("id")+",  name:"+values.getAsString("name"));
        T exist_obj=this.loadByPrimaryKey(entity);
        if(exist_obj!=null){
            return exist_obj;
        }
          
        long r=0;
        if(conflictType==2){
            r=db.replace(getTableName(entity), null,values);           
        }else{
            r=db.insert(getTableName(entity), null, values);            
        }
         
        if(r>=0){
            return entity;
        }
          
        return null;               
    }
     
    /**
     * ��������ɾ������
     * @param entity ��ɾ���Ķ���, ����ֻ��������.
     * @return
     */
    public <T> int delete(T entity){
        Object[] args=getPrimarySelectionAndArgs(entity);
        return db.delete(getTableName(entity), (String)args[0], (String[])args[1]);    
    }
     
     
    /**
     * �������������ݿ�����һ����¼������
     * @param entity ����ʵ��(�����ʼ�������ֶ�)
     * @return �ɹ��򷵻صĸ����ݿ�ʵ��,ʧ���򷵻�NULL
     */
    public <T> T loadByPrimaryKey(T entity){
        Object[] args=getPrimarySelectionAndArgs(entity);
        Cursor cursor=db.query(getTableName(entity), null,(String)args[0],(String[])args[1], null,null,null);
        try{
            if(cursor.moveToNext()){
                T db_entity=getEntity(cursor,entity);
                return db_entity;
            }else{
                return null;
            }
        }finally{
            cursor.close();
        }
    }
    
    /**
     * �������������ݿ�����һ����¼������
     * @param entity ����ʵ��(�����ʼ�������ֶ�)
     * @return �ɹ��򷵻صĸ����ݿ�ʵ��,ʧ���򷵻�NULL
     */
    public <T> T getByPrimaryKey(T entity, String id){
        Object[] args=getPrimarySelectionAndArgs(entity);
        Cursor cursor=db.query(getTableName(entity), null,(String)args[0],(String[])args[1], null,null,null);
        try{
            if(cursor.moveToNext()){
                T db_entity=getEntity(cursor,entity);
                return db_entity;
            }else{
                return null;
            }
        }finally{
            cursor.close();
        }
    }
     
     
    public <T> List<T> loadAll(Class<T> entity,String orderBy){   
        List<T> entities=new ArrayList<T>();
        Cursor cursor = db.query(getTableName(entity), null,null,null, null,null,orderBy);
        Log.i(tag, "product size: "+ cursor.getCount());
        try{
            if(cursor!=null && cursor.moveToNext()){
            	do{
                    T obj=(T)entity.newInstance();
                    getEntity(cursor,obj);
                    entities.add(obj);
                }while(cursor.moveToNext());
            }
            return entities;
        }catch(Exception e){
            Log.e(tag,""+e, e);
            return entities;
        }finally{
            cursor.close();
        }
    }

    public <T> long countAll(Class<T> entity){
    	long count = 0;
        Cursor c = db.rawQuery("select count(*) from " + getTableName(entity), null);
        if (c.moveToNext()) {
        	count = c.getLong(0);
        }
        return count;
    }
     
    /**
     * �������ݿ�ʵ��,  ���¶���������ֶε����ݿ�Ķ�Ӧ�ֶ�,����NULL�ֶ�.
     * @param entity �����µĶ���(�����������)
     * @return �ɹ����µļ�¼��
     */
    public int updateByPrimaryKey(Object entity){
        return updateByPrimaryKey(entity,false);
    }
     
    /**
     * �������ݿ�ʵ��,  �����¶���ķǿ��ֶε����ݿ�Ķ�Ӧ�ֶ�,�����NULL�ֶν�������.
     * @param entity �����µĶ���(�����������)
     * @return �ɹ����µļ�¼��
     */
    public int updateByPrimaryKeySelective(Object entity){
        return updateByPrimaryKey(entity,true);
    }
     
    private int updateByPrimaryKey(Object entity,boolean selective){
        ContentValues values=getContentValues(entity,selective);
        Object[] args=getPrimarySelectionAndArgs(entity);
         
        int r=db.update(getTableName(entity), values, (String)args[0],(String[])args[1]);
         
        return r;
    }
     
    /**
     * �Ӷ����н����������ֶ�, �Լ������ֶζ�Ӧ��ֵ
     * @param entity
     * @return
     */
    private Object[] getPrimarySelectionAndArgs(Object entity){
        Object[] ret=new Object[2];
        String selection=null;
        List<String> args=new ArrayList<String>();
        try{
            Class<?> entity_class=entity.getClass();         
            Field[] fs=entity_class.getDeclaredFields();
            for(Field f:fs){
                if(isPrimaryKey(f)){               
                    Method get=getGetMethod(entity_class,f);
                    if(get!=null){
                        Object o=get.invoke(entity);                       
                        String value=null;
                        if(o!=null){
                            value=o.toString();
                            if(selection==null){
                                selection=f.getName()+"=?";                            
                            }else{
                                selection+=" AND "+f.getName()+"=?";
                            }
                             
                            args.add(value);
                             
                        }else{
                            throw new RuntimeException("Primary key: "+f.getName()+" must not be null");
                        }
                    }
                }
            }          
            if(selection==null){
                throw new RuntimeException("Primary key not found!");
            }
             
            ret[0]=selection;
            ret[1]=args.toArray(new String[args.size()]);
            return ret;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
      
    /**
     * ������ת��ΪContentValues
     * @param entity
     * @param selective
     * @return
     */
    private ContentValues getContentValues(Object entity,boolean selective){
        ContentValues values=new ContentValues();
        try{
            Class<?> entity_class=entity.getClass();         
            Field[] fs=entity_class.getDeclaredFields();
            for(Field f:fs){
                if(isTransient(f)==false){             
                    Method get=getGetMethod(entity_class,f);
                    if(get!=null){
                        Object o=get.invoke(entity);
                        if(!selective || (selective && o!=null)){
                            String name=f.getName();                       
                            Class<?> type=f.getType();
                            if(type==String.class){
                                values.put(name,(String)o);
                            }else if(type==int.class || type==Integer.class){
                                values.put(name,(Integer)o);
                            }else if(type==float.class || type==Float.class){
                                values.put(name,(Float)o);
                            }else if(type==double.class || type==Double.class){
                                values.put(name,(Double)o);
                            }else if(type==long.class || type==Long.class){
                                values.put(name,(Long)o);
                            }else if(type==Date.class){
                                values.put(name,datetimeToString((Date)o));
                            }else{ 
                                values.put(name,o.toString());                             
                            }
                        }
                    }
                }
            }
            return values;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
     
    /**
     * �����ݿ��¼ת��Ϊ����
     *
     * @param cursor
     * @param entity
     * @return
     */
    private <T> T getEntity(Cursor cursor, T entity){
        try{
            Class<?> entity_class=entity.getClass();
             
            Field[] fs=entity_class.getDeclaredFields();
            for(Field f:fs){
                int index=cursor.getColumnIndex(f.getName());
                if(index>=0){                   
                    Method set=getSetMethod(entity_class,f);
                    if(set!=null){
                        String value=cursor.getString(index);                                          
                        if(cursor.isNull(index)){
                            value=null;
                        }
                        Class<?> type=f.getType();
                        if(type==String.class){
                            set.invoke(entity,value);
                        }else if(type==int.class || type==Integer.class){
                            set.invoke(entity,value==null?(Integer)null:Integer.parseInt(value));
                        }else if(type==float.class || type==Float.class){
                            set.invoke(entity,value==null?(Float)null:Float.parseFloat(value));
                        }else if(type==double.class || type==Double.class){
                            set.invoke(entity,value==null?(Double)null:Double.parseDouble(value));
                        }else if(type==long.class || type==Long.class){
                            set.invoke(entity,value==null?(Long)null:Long.parseLong(value));
                        }else if(type==Date.class){
                            set.invoke(entity,value==null?(Date)null:stringToDateTime(value));
                        }else{ 
                            set.invoke(entity,value);                      
                        }                           
                    }
                }
            }          
            return entity;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
     
    private String datetimeToString(Date d){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(d!=null){
            return sdf.format(d);
        }
        return null;
    }
     
    private Date stringToDateTime(String s){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(s!=null){
            try {
                return sdf.parse(s);
            } catch (ParseException e) {            
                Log.e(tag,"����ʱ�����: "+s,e);
            }
        }
        return null;
    }
     
    private Method getGetMethod(Class<?> entity_class,Field f){
        String fn=f.getName();
        String mn="get"+fn.substring(0,1).toUpperCase()+fn.substring(1);
        try{           
            return entity_class.getDeclaredMethod(mn);
        }catch(NoSuchMethodException e){
            Log.w(tag,"Method: "+mn+" not found.");
             
            return null;
        }
    }
     
    private Method getSetMethod(Class<?> entity_class,Field f){
        String fn=f.getName();
        String mn="set"+fn.substring(0,1).toUpperCase()+fn.substring(1);
        try{           
            return entity_class.getDeclaredMethod(mn,f.getType());
        }catch(NoSuchMethodException e){
            Log.w(tag,"Method: "+mn+" not found.");
             
            return null;
        }
    }
      
    /**
     * ����Ƿ�Ϊ�����ֶ�
     */
    private boolean isPrimaryKey(Field f){      
        Annotation an=f.getAnnotation(Id.class);
        if(an!=null){
            return true;            
        }
        return false;
    }
     
    private boolean isTransient(Field f){       
        Annotation an=f.getAnnotation(Transient.class);
        if(an!=null){
            return true;            
        }
        return false;
    }
     
     
 
    private String getTableName(Object entity){
        Table table=entity.getClass().getAnnotation(Table.class);
        String name= table.name();
        return name;
    }
    
    private String getTableName(Class<?> entity){
        Table table=entity.getAnnotation(Table.class);
        String name= table.name();
        return name;
    }
     
    public int getConflictType() {
        return conflictType;
    }
 
    public void setConflictType(int conflictType) {
        this.conflictType = conflictType;
    }
     
     
}