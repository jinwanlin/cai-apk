package com.weicai.dao;

 
/**
 * @author jiuwuerliu@sina.com
 *
 * ����ʾ��
 */
public class ProductDao {
//    static final String tag="ProductDao";
     
//    private static SQLiteDatabase db;
    
//    public static SQLiteDatabase createDatabase(){    
//        SQLiteDatabase db=SQLiteDatabase.create(null);  
//        String createdb=
//                "CREATE TABLE IF NOT EXISTS t_product("
//                +"id         INTEGER PRIMARY KEY,"
//                		
//                +"sn       VARCHAR(10),"
//                +"name       VARCHAR(50),"
//                +"type       VARCHAR(25),"
//                +"amounts       VARCHAR(100),"
//                +"price       DOUBLE,"
//                +"unit       VARCHAR(25),"
//                
//                +"createdAt   DATETIME,"
//                +"updatedAt   DATETIME"
//                +");";
//        db.execSQL(createdb);       
//        return db;
//    }
//     
//    static{
//        db=createDatabase();
//    }
//    
//    public static Product create(Product product){
//        SqliteDAO dao=new SqliteDAO(db);
//        return dao.insert(product);
//    }
//     
//    public static User find_by_id(int id){
//        SqliteDAO dao=new SqliteDAO(db);
//        return dao.loadByPrimaryKey(new User(id));
//    }
//    
//    public static List<Product> all(){
//        SqliteDAO dao=new SqliteDAO(db);
//        return dao.loadAll(Product.class, null);
//    }
//    
//    public static long countAll(){
//    	SqliteDAO dao=new SqliteDAO(db);
//        return dao.countAll(User.class);
//    }
//    
//    public static void update_all(List<Product> products){
//    	for (int i = 0; i < products.size(); i++) {
//			create(products.get(i));
//		}
//    }
}