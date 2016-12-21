package com.lanxi.easyintegral.test;

import static java.lang.System.out;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lanxi.easyintegral.entity.IntegralSmsTemplate;
import com.lanxi.easyintegral.util.BeanUtil;
import com.lanxi.easyintegral.util.SqlUtilForDB;
public class TestJDBC {

	@Test 
	public void testJdbc() throws Exception{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="select * from "+"INTEGRAL_SMS_TEMPLATE";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		boolean flag=true;
		ResultSetMetaData metaData=rs.getMetaData();
		int max=100;
		int count=metaData.getColumnCount();
		Map<String,Field> fields=BeanUtil.getFieldsNoStatic(IntegralSmsTemplate.class);
		List<Field> list=new ArrayList<>();
		for(Map.Entry<String,Field> each:fields.entrySet()){
			Field field=each.getValue();
			field.setAccessible(true);
			list.add(field);
		}
		int index=1;
		while(rs.next()){
			count=metaData.getColumnCount();
			IntegralSmsTemplate temp=new IntegralSmsTemplate();
			for(int i=1;i<count;i++){
				String value=rs.getString(i);
				list.get(i-1).set(temp, value.trim());
			}
			temp.setId(index<10?"200"+index:"20"+index);
		}
		
	}
	@Test
	public void testVersion(){
		Connection conn=SqlUtilForDB.getConnection();
		out.println(SqlUtilForDB.getDatabaseProductVersion(conn));
	}
	@Test
	public void testSql() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
//		String sql="select * from (select ROW_NUMBER() OVER() AS ROWNUM,t.* from t_POINT_ACCOUNT t) where ROWNUM between 1 and 100";
		String sql="select * from INTEGRAL_GIFT";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		int max=100;
		ResultSetMetaData metaData=rs.getMetaData();
		int count=metaData.getColumnCount();
		for(int i=1;i<count;i++){
			System.out.print(metaData.getColumnName(i));
			System.out.print(" ");
		}
		System.out.println();
		while(rs.next()&&max>0){
			count=metaData.getColumnCount();
			for(int i=1;i<count;i++){
				System.out.print(rs.getString(i));
				System.out.print(" ");
			}
			System.out.println();
			max--;
		}
	}
	@Test
	public void testGetUsers() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="select CUST_NO from(select ROW_NUMBER() OVER() AS ROWNUM,CUST_NO from t_POINT_ACCOUNT)where ROWNUM between 1 and 100";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString(1));
		}
			SqlUtilForDB.closeConnection(conn);
	}
	@Test
	public void testPoint() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="select SUM (POINTS_VAL) from (select POINTS_VAL from t_POINT_ACCOUNT where CUST_NO ='15068610940')";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getInt(1));
		}
	}
	@Test
	public void testPhone() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="select PHONE from t_POINT_CUST_INFO where CUST_NO='15068610940'";
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		ResultSetMetaData metaData=rs.getMetaData();
		if(rs.next()){
			System.out.println(rs.getString(1));
		}
		int count=metaData.getColumnCount();
		for(int i=1;i<count;i++){
			System.out.print(metaData.getColumnName(i));
			System.out.print(" ");
		}
		System.out.println();
		while(rs.next()){
			count = metaData.getColumnCount();
			for(int i=1;i<count;i++){
				System.out.print(rs.getString(i));
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	@Test
	public void testMakeTable() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="create table integral_order (id char(20) not null ,"
				+ "transition_sequence char(20),"
				+ "sms_id char(20) not	null,"
				+ "phone char(11) not	null,"
				+ "user_id char(20) not	null,"
				+ "gift_id char(20) not	null,"
				+ "gift_count int,"
				+ "total_price decimal(10,2),"
				+ "total_value int,"
				+ "work_time char(16),"
				+ "status char(2),"
				+ "res_code	char(4),"
				+ "res_msg char(100),"
				+ "remark char(100),"
				+ "beiy	char(100))";
		Statement statement=conn.createStatement();
		System.out.println(statement.execute(sql)); 
	}
	@Test
	public void testGet(){
		Connection conn=SqlUtilForDB.getConnection();
		out.println(SqlUtilForDB.getDatabaseProductVersion(conn));
		out.println(SqlUtilForDB.getTableNames(conn));
	}
	@Test 
	public void testCount() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="alter table INTEGRAL_ORDER alter res_msg set data type varchar(800) ";
		Statement statement=conn.createStatement();
		statement.execute(sql);
	}
	@Test
	public void excuteSql() throws SQLException{
		Connection conn=SqlUtilForDB.getConnection();
		String sql="update INTEGRAL_GIFT set status='2',soldout_time='20161220' where id='1001'";
		Statement statement=conn.createStatement();
		statement.execute(sql);
	}
}
