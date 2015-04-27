/**
 *管理员端的归还挂失
 */
package com.DUANGDUANG1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;
import javax.swing.event.*;
import com.db.DataBase;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class ReturnBook extends JPanel implements ActionListener
{

	DataBase db;
	String sql;
	String str;
	JTable jt;
	JScrollPane jspn;
	borrowMode bm;
	//创建分割方向为上下的JSplitePane对象
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	//创建JPanel对象
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	//创建选项卡窗格
	JTabbedPane jtp=new JTabbedPane();

	//创建按钮数组
	private JButton[] jbArray=new JButton[]
			{
			new JButton	("挂    失"),
			new JButton	("归    还"),
			new JButton	("查找学生")
			};
	private JLabel jl1=new JLabel("请输入你的学号:");
	private JLabel jl2=new JLabel("**温馨提示:输入学号查询学生借阅信息，然后在表格中点击要挂失或者归还的书！**");
	private JTextField jtxt=new JTextField();

	public ReturnBook()
	{
		bm=new borrowMode();//创建一个模型对象
		jt=new JTable(bm);//创建Table对象，并将模型封装进去
		jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
		this.setLayout(new GridLayout(1,1));
		//设置整个RetrunBook界面上下部分均为空布局管理器
		jpt.setLayout(null);
		jpb.setLayout(null);
		//设置Label的大小及位置
		jl1.setBounds(55,65,100,20);	
		jl2.setBounds(55,15,600,20);
		jl2.setForeground(Color.RED);
		//将Jlabel添加到jpt面板上
		jpt.add(jl1);
		jpt.add(jl2);
		//为JTextField设置大小及位置
		jtxt.setBounds(155,65,200,20);
		//把JTextField添加到jpt
		jpt.add(jtxt);
		//设置JBuuton的大小及位置
		jbArray[0].setBounds(55,110,90,20);
		jbArray[1].setBounds(200,110,90,20);
		jbArray[2].setBounds(395,65,90,20);
		//添加JButton并给其添加事件监听器
		for(int i=0;i<3;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}

		//把面板添加到选项卡窗格中
		jtp.addTab("挂  失  归  还  图  书", jpt);
		//jtp.addTab("归  还  图  书", jpb);
		//把jpt设置到jsp的上部窗格
		jsp.setTopComponent(jtp);
		//jpb.add(jspn);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(14);
		this.add(jsp);
		//设置jsp中分割条的初始位置
		jsp.setDividerLocation(180);
		//设置窗体的大小位置及可见性
		this.setBounds(10,10,800,600);
		this.setVisible(true); 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//点击查找学生按钮
		if(e.getSource()==jbArray[2])
		{
			if(jtxt.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "输入的学号信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else
			{
				sql="select * from record where stuNo="+Integer.parseInt(jtxt.getText().trim());
				db=new DataBase();
				db.selectDb(sql);
				try {
					if(!(db.rs.next())){
						JOptionPane.showMessageDialog(this, "没有该学生的借阅信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bm=new borrowMode(sql);
				jt.setModel(bm);
			}
		}
		//选择归还图书的时候
		else if(e.getSource()==jbArray[1])
		{
			int row=jt.getSelectedRow();
			if(row<0)
			{
				JOptionPane.showMessageDialog(this, "请选择要归还的书", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//当点击了一行
			int bookNo=Integer.parseInt((String) jt.getValueAt(row, 0));//得到表格中的书号
			int stuNo=Integer.parseInt((String) jt.getValueAt(row, 2));
			int flag=checkTime(bookNo, stuNo);
			if(flag==-1)
			{//超期没有缴纳罚款的学生，将其借书权限设置为否
				sql="update student set Permitted='否' where stuNo="+Integer.parseInt((String) jt.getValueAt(row, 2));
				db=new DataBase();
				db.updateDb(sql);
				//更新表格
				bm=new borrowMode();
				jt.setModel(bm);
			}
			else if(flag==0){return;}
			//没有超期,正常还书
			//从借阅表中删除该记录
			sql="delete from record where BookNo="+Integer.parseInt((String)jt.getValueAt(row, 0));
			db=new DataBase();
			db.updateDb(sql);
			sql="update book set Borrowed='否' where BookNo="+Integer.parseInt((String)jt.getValueAt(row, 0));
			db.updateDb(sql);
			//更新表格
			bm=new borrowMode();
			jt.setModel(bm);
		}
		//选择挂失按钮
		else if(e.getSource()==jbArray[0])
		{
			int row=jt.getSelectedRow();
			if(row<0)
			{
				JOptionPane.showMessageDialog(this, "请在下表中选择要挂失的书！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			loseBook(row);
			bm=new borrowMode();
			jt.setModel(bm);//更新表格
		}

	} 
	//构造一个挂失图书的方法
	public void loseBook(int row)
	{
		String bookName="";
		int lbno=0;
		int bookno=Integer.parseInt((String) jt.getValueAt(row, 0));//得到表格中的书号
		int stuno=Integer.parseInt((String) jt.getValueAt(row, 2));
		//从图书表中查找书名
		sql="select bookName from book where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next())
			{
				bookName=db.rs.getString(1).trim();//获取书名
			}
		} catch (Exception e) {e.printStackTrace();}
		//查找挂失图书表的最大挂失号
		sql="select MAX(LBNO) from losebook";
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				lbno=db.rs.getInt(1);//获取最大挂失号
				lbno++;//挂失号加1
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将挂失的图示添加到挂失表中
		sql="insert into losebook(LBNO,StuNo,BookNo,BookName) values("+lbno+","+stuno+","+bookno+",'"+bookName+"')";
		db=new DataBase();
		int i=db.updateDb(sql);
		//查找预约表中是否有该书的记录，如果有则删除
		sql="select BookNo from orderreport where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				sql="delete from orderreport wnere BookNo="+bookno;
				db=new DataBase();
				db.updateDb(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//检查借阅表中是否有该记录，如果有则删除
		sql="select BookNo from record where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				sql="delete from record where BookNo="+bookno;//从借书表中将丢失图书的记录删除
				db=new DataBase();
				db.updateDb(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查找图书表中是否有该书的记录，如果有，则删除
		sql="select BookNo from book where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				sql="delete from book where BookNo="+bookno;
				db.updateDb(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.dbClose();
		}
		if(i<0){//挂失失败提示
			JOptionPane.showMessageDialog(this, "挂失失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}else{//挂失成功提示
			JOptionPane.showMessageDialog(this, "挂失成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	//构造检查时间是否超期的方法
	public int checkTime(int bookNo,int stuNo)
	{//说明：“1”代表是正常还书，“-1代表已经超期但是没有缴纳罚款的”，“-2代表超期并且已经缴纳了罚款的”
		int flag=0;
		String returntime = "";
		Date now;
		String bookName = "";
		sql="select returnTime from record where BookNo="+bookNo+" and stuNo="+stuNo;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next())
			{
				returntime=db.rs.getString(1).trim();//获取归还的时间
			}
		} catch (Exception e) {e.printStackTrace();}
		String[] strday=returntime.split("\\-");//使用正则表达式来规定时间的格式
		int ryear=Integer.parseInt(strday[0].trim());//获取归还的年
		int rmonth=Integer.parseInt(strday[1].trim());//获取归还的月
		int rday=Integer.parseInt(strday[2].trim());//获取归还的天
		now=new Date();
		//获取超期的天数
		int day=(((now.getYear()+1900)-ryear)*365+((now.getMonth()+1)-rmonth)*30+(now.getDate()-rday));
		if(day>0)
		{
			//如果超期了，弹出确认对话框，并显示超期的天数和罚款金额
			int d=JOptionPane.showConfirmDialog(this, "您的书已经超期"+day+"需要缴纳"+day*1+"元，是否现在缴纳？", "提示", JOptionPane.YES_NO_OPTION);
			if(d==JOptionPane.YES_OPTION)
			{//超期并缴纳罚款
				JOptionPane.showMessageDialog(this, "您已成功缴纳"+day*1+"元", "提示", JOptionPane.INFORMATION_MESSAGE);
				flag=-2;
			}else
			{//超期但没有缴纳罚款
				sql="select BookName from book where BookNo="+bookNo;
				db.selectDb(sql);
				try {
					if(db.rs.next())
					{
						bookName=db.rs.getString(1).trim();//获取书名
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//将数据插入到超期表中
				sql="insert into exceedtime(StuNO,BookNO,BookName,DelayTime) values("+stuNo+","+bookNo+",'"+bookName+"',"+day+")";
				db.updateDb(sql);
				flag=-1;//超期但没有缴纳罚款的标志
			}
		}
		else{flag=1;}
		db.dbClose();
		return flag;
	}

}