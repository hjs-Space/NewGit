package com.DUANGDUANG1;
/*
 * 树模型的构造
 * 卡片布局很重要，主要用于实现点击
 * 哪一个功能就显示那一部分功能界面
 */
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
public class Root extends JFrame
{//创建树节点数组
	DefaultMutableTreeNode []dmtn=
		{
			new DefaultMutableTreeNode(new NodeValue("图书管理系统")),
			new DefaultMutableTreeNode(new NodeValue("学生用户管理")),
			new DefaultMutableTreeNode(new NodeValue("图书管理")),
			new DefaultMutableTreeNode(new NodeValue("查询图书")),
			new DefaultMutableTreeNode(new NodeValue("借阅预约图书")),
			new DefaultMutableTreeNode(new NodeValue("归还挂失图书")),
			new DefaultMutableTreeNode(new NodeValue("交纳罚款")),
			new DefaultMutableTreeNode(new NodeValue("管理员管理")),
			new DefaultMutableTreeNode(new NodeValue("退出"))
		};
	//创建一个默认发树模型，并指定“图书管理系统”为根节点
	DefaultTreeModel dmt=new DefaultTreeModel(dmtn[0]);
	//创建一个树对象，这个对象包含这个树模型
	JTree jt=new JTree(dmt);
	//创建滚动面板，并将树封装到滚动面板中
	JScrollPane jsp=new JScrollPane(jt);
	//创建分割面板，并设置为水平方向的分割
	private JSplitPane jslp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
	//创建面板对象
	JPanel jp=new JPanel();
	//创建根节点的标签
	private JLabel jlroot=new JLabel("欢迎进入中大南方图书管理系统！！");
	private Manager mg;//管理员登录的管理员名
	String mgNo;//管理员id
	String sql;
	DataBase db;
	CardLayout cl=new CardLayout();//获取卡片布局管理器引用
	//构造方法
	public Root(String mgNo)
	{
		this.mgNo=mgNo;//获取管理员的id
		mg=new Manager(mgNo);//创建管理员管理面板
		this.SetManage();//设置管理员权限
		this.init();//初始化jp面板
		this.addTreeActionListener();//为树节点注册事件监听器
		jt.setShowsRootHandles(true);//设置显示根节点的控制图标
		jp.setBounds(200,50,600,500);//设置面板的位置，大小
		jslp.setLeftComponent(jsp);//设置滚动面板jsp封装到分割面板的左边
		jslp.setRightComponent(jp);//设置面板jp封装到分割面板的右边
		jslp.setDividerLocation(200);//设置分隔条的初始位置        
		jslp.setDividerSize(4);//设置分隔条大小        
		jlroot.setFont(new Font("Courier", Font.PLAIN, 50));//设置标签的字体和样式以及大小
		//把标签设置为水平居中和垂直居中
		jlroot.setHorizontalAlignment(JLabel.CENTER);
		jlroot.setVerticalAlignment(JLabel.CENTER);
		//向树模型添加节点
		for(int i=1;i<9;i++)
		{
			dmt.insertNodeInto(dmtn[i], dmtn[0], i-1);
		}
		this.add(jslp);
		this.setTitle("图书管理系统");
		//设置窗体首次出现的大小和位置--自动居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//本窗体宽度
		int h=400;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗体全屏
		this.setVisible(true);//设置窗体可见		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
	}
	//初始化面板jp
	public void init()
	{
		jp.setLayout(cl);//设置布局管理器的布局为卡片布局
		jp.add(jlroot,"root");//添加根结点显示信息
		jp.add(new BookManage(),"bookMg");//添加图书管理模块界面
		jp.add(new BorrowBook(),"borrowBk");//添加借阅预约图书模块界面
		jp.add(new ExceedTime(),"exceedTime");//添加罚款处理界面
		jp.add(this.mg,"Manager");//添加管理员管理模块界面
		jp.add(new ReturnBook(),"returnBook");//添加归还挂失图书界面
		jp.add(new SearchBook(),"searchBook");//添加查找图书管理界面
		jp.add(new Student(),"stu");//添加学生管理模块界面
	}
	//构造为树节点注册事件监听的方法
	public void addTreeActionListener(){
		jt.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				//首先应该获得鼠标点中的节点对象
				//getPath()返回从根到达此节点的路径。该路径中最后一个元素是此节点。 
				//getLastPathComponent()找到树中最后一个选中的节点
				DefaultMutableTreeNode dmtn1=(DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				NodeValue nv=(NodeValue) dmtn1.getUserObject();//返回自定义节点的用户对象。
				if(nv.value.equals("图书管理系统"))
				{//显示根结点信息界面
					cl.show(jp, "root");
				}
				if(nv.value.equals("学生用户管理"))
				{//显示学生用户管理界面
					cl.show(jp, "stu");
				}
				if(nv.value.equals("图书管理"))
				{//显示图书管理界面
					cl.show(jp, "bookMg");
				}
				if(nv.value.equals("查询图书"))
				{//显示查询图书界面
					cl.show(jp, "searchBook");
				}
				if(nv.value.equals("借阅预约图书"))
				{//显示借阅预约图书界面
					cl.show(jp, "borrowBk");
				}
				if(nv.value.equals("归还挂失图书"))
				{//显示归还挂失图书界面
					cl.show(jp, "returnBook");
				}
				if(nv.value.equals("交纳罚款"))
				{//显示交纳罚款界面
					cl.show(jp, "exceedTime");
				}
				if(nv.value.equals("管理员管理"))
				{//显示管理员管理界面
					cl.show(jp, "Manager");
				}
				if(nv.value.equals("退出"))
				{//显示退出
					int i=JOptionPane.showConfirmDialog(Root.this, "是否退出系统", "消息提醒", JOptionPane.YES_NO_OPTION);
					if(i==JOptionPane.YES_OPTION)
					{//退出系统
						System.exit(0);
					}
				}
			}
		});
	}
	public void SetManage(){
		//用于操作查询登录者是否具有官员的权限
		sql="select permitted from manager where mgNo='"+mgNo+"'";
		db=new DataBase();//创建数据库类对象
		db.selectDb(sql);//执行查询
		try {
			db.rs.next();//结果集下移
			String str=db.rs.getString(1).trim();//获得管理权限
			if(str.equals("0"))//不具有管理权限
			{
				JOptionPane.showMessageDialog(Root.this, "该管理员不具有管理权限", "消息", JOptionPane.INFORMATION_MESSAGE);
				//去管理员管理面板中修改管理员的权限

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Root("hjs");
	}
}
//创建一个节点类
class NodeValue
{
	String value;
	//获得节点的值
	public String getValue() {
		return this.value;
	}
	public NodeValue(String value)
	{//构造器
		this.value=value;
	}
	public String toString()
	{//重写toString()方法
		return value;
	}
}