package com.DUANGDUANG1;
import javax.swing.*;
import java.sql.*;
public class DataBase
{
	Connection con=null;//����Connection����
	Statement stat;
	ResultSet rs;
	
	int count;
	public static Login log;
	public DataBase(){
		try{//����MySQL�������࣬���������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/la","root","");
		 	stat=con.createStatement();//����Statement����
		}
		catch(Exception e){//�����Login�ഫ�Ĳ������ԣ�����ʾ����
    	   	JOptionPane.showMessageDialog(log,"�û�����������󣡣���",
    	   	              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		}	
	}	
	public void selectDb(String sql){//����select����
		try{
			rs=stat.executeQuery(sql);
			
		}
		catch(Exception ie){ie.printStackTrace();}
	}	
	public int updateDb(String sql){//����update����
		try{
			sql = new String(sql.getBytes(),"UTF8");//ת��
			count=stat.executeUpdate(sql);
			//System.out.println("�������");

		}
		catch(Exception ie){ie.printStackTrace();}
		return count;		
	}	
	public void dbClose(){//����close����		
		try{rs.close();stat.close();con.close();}
		catch(Exception e){e.printStackTrace();}	
	}
	
}