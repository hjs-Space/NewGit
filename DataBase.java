package com.DUANGDUANG1;
import javax.swing.*;
import java.sql.*;
public class DataBase
{
	Connection con=null;//声明Connection引用
	Statement stat;
	ResultSet rs;
	
	int count;
	public static Login log;
	public DataBase(){
		try{//加载MySQL的驱动类，并创建数据库连接
			Class.forName("com.mysql.jdbc.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/la","root","");
		 	stat=con.createStatement();//创建Statement对象
		}
		catch(Exception e){//如果从Login类传的参数不对，则提示出错
    	   	JOptionPane.showMessageDialog(log,"用户名或密码错误！！！",
    	   	              "信息",JOptionPane.INFORMATION_MESSAGE);
		}	
	}	
	public void selectDb(String sql){//声明select方法
		try{
			rs=stat.executeQuery(sql);
			
		}
		catch(Exception ie){ie.printStackTrace();}
	}	
	public int updateDb(String sql){//声明update方法
		try{
			sql = new String(sql.getBytes(),"UTF8");//转码
			count=stat.executeUpdate(sql);
			//System.out.println("出错出错");

		}
		catch(Exception ie){ie.printStackTrace();}
		return count;		
	}	
	public void dbClose(){//声明close方法		
		try{rs.close();stat.close();con.close();}
		catch(Exception e){e.printStackTrace();}	
	}
	
}