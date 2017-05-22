package com.llh.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;

public class Test1 {
	private Mongo mg = null;
    private DB db;
    private DBCollection resource;
    
    @Before
    public void init() {
        try {
           // mg = new Mongo();
            mg = new Mongo("127.0.20.22", 30000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        //获取testdb DB；如果默认没有创建，mongodb会自动创建
        db = mg.getDB("testdb");
        //获取resource DBCollection；如果默认没有创建，mongodb会自动创建
        resource = db.getCollection("resource");
    }
    
    @Test
    public void queryAll() {
        print("查询resource的所有数据：");
        //db游标
        DBCursor cur = resource.find();
        DBObject obj = resource.findOne();
        /*while (cur.hasNext()) {
            print(cur.next());
        }*/
        print(obj);
        Map map = obj.toMap();
        System.out.println(map.size());
        
        
        ///////////
        DBObject user = new BasicDBObject();
        user.put("name", "hoojo");
        user.put("age", 24);
        //resource.save(user)保存，getN()获取影响行数
        //print(resource.save(user).getN());
        
        //扩展字段，随意添加字段，不影响现有数据
        user.put("sex", "男");
        print(resource.save(user).getN());
        
        //添加多条数据，传递Array对象
        print(resource.insert(user, new BasicDBObject("name", "tom")).getN());
        
        //添加List集合
        List<DBObject> list = new ArrayList<DBObject>();
        list.add(user);
        DBObject user2 = new BasicDBObject("name", "lucy");
        user.put("age", 22);
        list.add(user2);
        //添加List集合
        print(resource.insert(list).getN());
        
        //查询下数据，看看是否添加成功
        print("count: " + resource.count());
        
        
        ////////////////////
      //查询id = 4de73f7acd812d61b4626a77
        print("find id = 4de73f7acd812d61b4626a77: " + resource.find(new BasicDBObject("_id", new ObjectId("4de73f7acd812d61b4626a77"))).toArray());
        
        //查询age = 24
        print("find age = 24: " + resource.find(new BasicDBObject("age", 24)).toArray());
        
        //查询age >= 24
        print("find age >= 24: " + resource.find(new BasicDBObject("age", new BasicDBObject("$gte", 24))).toArray());
        print("find age <= 24: " + resource.find(new BasicDBObject("age", new BasicDBObject("$lte", 24))).toArray());
        
        print("查询age!=25：" + resource.find(new BasicDBObject("age", new BasicDBObject("$ne", 25))).toArray());
        print("查询age in 25/26/27：" + resource.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.IN, new int[] { 25, 26, 27 }))).toArray());
        print("查询age not in 25/26/27：" + resource.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.NIN, new int[] { 25, 26, 27 }))).toArray());
        print("查询age exists 排序：" + resource.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.EXISTS, true))).toArray());
        
        print("只查询age属性：" + resource.find(null, new BasicDBObject("age", true)).toArray());
        print("只查属性：" + resource.find(null, new BasicDBObject("age", true), 0, 2).toArray());
        print("只查属性：" + resource.find(null, new BasicDBObject("age", true), 0, 2, Bytes.QUERYOPTION_NOTIMEOUT).toArray());
        
        //只查询一条数据，多条去第一条
        print("findOne: " + resource.findOne());
        print("findOne: " + resource.findOne(new BasicDBObject("age", 26)));
        print("findOne: " + resource.findOne(new BasicDBObject("age", 26), new BasicDBObject("name", true)));
        
        //查询修改、删除
        print("findAndRemove 查询age=25的数据，并且删除: " + resource.findAndRemove(new BasicDBObject("age", 25)));
        
        //查询age=26的数据，并且修改name的值为Abc
        print("findAndModify: " + resource.findAndModify(new BasicDBObject("age", 26), new BasicDBObject("name", "Abc")));
        print("findAndModify: " + resource.findAndModify(
            new BasicDBObject("age", 28), //查询age=28的数据
            new BasicDBObject("name", true), //查询name属性
            new BasicDBObject("age", true), //按照age排序
            false, //是否删除，true表示删除
            new BasicDBObject("name", "Abc"), //修改的值，将name修改成Abc
            true, 
            true));
        
    }
    
    @After
    public void destory() {
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
        resource = null;
        System.gc();
    }
    
    
    
    public void print(Object o) {
        System.out.println(o);
    }
}
