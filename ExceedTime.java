/**
 * 超期罚款
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
public class ExceedTime extends JPanel implements ActionListener
{
	DataBase db;
	String sql;
	exceedTimeMode etm;
	JTable jt;
	String []str=new String[2];
	private JScrollPane jspn;
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//创建分割面板, 垂直分割表示 Component 沿 y 轴分割。
	private JPanel jp=new JPanel();
	private JLabel []jlArray={
			new JLabel("请 输 入 您 的 学  号"),
			new JLabel("请输入您要交的款数"),
			new JLabel("欢迎进入缴费~^_^* 温馨提示：输入学号点击查询学生欠费，再输入学号，罚款金额，点击缴费按钮，完成缴费！您就可以解除借书受限！")
	};
	private JTextField []jtfArray={
			new JTextField(),
			new JTextField()
	};//创建文本框

	//创建按钮
	private JButton []jbArray={
			new JButton("查询欠费"),
			new JButton("缴        费")
	};
	public ExceedTime(){
		jp.setLayout(null);
		this.setLayout(new GridLayout(1,1));
		//向面板中添加Label、按钮、文本框
		for(int i=0;i<2;i++)
		{
			jp.add(jlArray[i]);
			jp.add(jtfArray[i]);
			jp.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}
		jp.add(jlArray[2]);
		//设置标签的位置以及大小
		jlArray[0].setBounds(50, 70, 120, 20);
		jlArray[1].setBounds(50, 120, 120, 20);
		jlArray[2].setBounds(50, 20, 850, 25);
		//设置字体颜色已经字体大小
		jlArray[2].setForeground(Color.RED);
		Font font =new Font("Defualt", Font.PLAIN, 14);
		jlArray[2].setFont(font);
		//设置文本框的位置以及大小
		jtfArray[0].setBounds(180, 70, 120, 20);
		jtfArray[1].setBounds(180, 120, 120, 20);
		//设置按钮的位置以及大小
		jbArray[0].setBounds(340, 70, 100, 20);
		jbArray[1].setBounds(340, 120, 100, 20);
		//创建表格对象
		etm=new exceedTimeMode();//创建exceedTimeMode模型对象
		jt=new JTable(etm);//创建表格对象，表格的初始化
		jspn=new JScrollPane(jt);///初始化JScrollPane
		jsp.setTopComponent(jp);//将jp添加到分割窗格的上部
		jsp.setBottomComponent(jspn);//将jspn添加到jsp的底部
		jsp.setDividerSize(14);
		jsp.setDividerLocation(160);
		this.add(jsp);
		//设置窗体的大小位置
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<2;i++)
		{
			str[i]=jtfArray[i].getText().trim();//获取文本框输入的内容
		}
		int day=0;

		if(str[0].equals(""))
		{
			JOptionPane.showMessageDialog(this, "请输入学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//判断输入的是否都为数字
		if(str[0].matches("\\D"))
		{
			JOptionPane.showMessageDialog(this, "学号应该为数字！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select DelayTime from exceedtime where stuNo="+Integer.parseInt(str[0]);
		db=new DataBase();
		db.selectDb(sql);
		try {
			int flag=0;
			while(db.rs.next()){
				flag++;
				day+= db.rs.getInt(1);//获取超期的天数
			}
			if(flag==0){
				JOptionPane.showMessageDialog(this, "您借的书没有超期！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		//当点击了查询欠费按钮
		if(e.getSource()==jbArray[0])
		{
			sql="select * from exceedtime where StuNo="+Integer.parseInt(str[0]);
			etm=new exceedTimeMode(sql);
			jt.setModel(etm);
			if(day>0)
			{
				JOptionPane.showMessageDialog(this, "您借的书已经超期"+day+"天，需要缴纳罚款"+day*1+"元！", "提示", JOptionPane.INFORMATION_MESSAGE );
				return;
			}else{//如果没有欠款，提示
				JOptionPane.showMessageDialog(this,"您所借的书没有超期！",
						"提示",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		//当点击缴费按钮时
		else if(e.getSource()==jbArray[1])
		{
			if(str[0].equals(""))
			{//输入的学号为空，弹出提示
				JOptionPane.showMessageDialog(this, "请输入您的学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(str[1].equals(""))
			{//输入的金额为空，弹出提示
				JOptionPane.showMessageDialog(this, "请输入您的缴费金额！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			int a=Integer.parseInt(str[1]);//获取罚款文本框输入的值
			int aa=JOptionPane.showConfirmDialog(this,"是否现在缴费？","提示", JOptionPane.YES_NO_OPTION);
			if(aa==JOptionPane.YES_OPTION)
			{

				if(a<(day*1))
				{
					sql="update exceedtime set DelayTime="+(day-a/1)+" where stuNo="+Integer.parseInt(str[0]);
					db=new DataBase();
					int d=db.updateDb(sql);
					if(d==1){
						JOptionPane.showMessageDialog(this, "您已经成功缴纳"+a+"元，还需缴纳"+(day*1-a)+"元", "提示", JOptionPane.INFORMATION_MESSAGE);
						sql="select * from exceedTime";
						etm=new exceedTimeMode(sql);
						jt.setModel(etm);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "缴纳失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				else{//全部缴费完成
					JOptionPane.showMessageDialog(this, "您已经成功缴纳"+day*1+"元!解除借书受制权限！", "提示", JOptionPane.INFORMATION_MESSAGE);
					for(int i=0;i<2;i++)
					{
						jtfArray[i].setText("");
					}
					//恢复该学生的借书权限
					sql="update student set Permitted='是' where stuNo="+Integer.parseInt(str[0]);
					db=new DataBase();
					db.updateDb(sql);
					//删除超期表中该学生的记录
					sql="delete from exceedTime where stuNo="+Integer.parseInt(str[0]);
					db.updateDb(sql);
					//更新表格
					sql="select * from exceedTime";
					etm=new exceedTimeMode(sql);
					jt.setModel(etm);
				}

			}
		}
		db.dbClose();
	}
}