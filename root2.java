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
{
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
	DefaultTreeModel dmt=new DefaultTreeModel(dmtn[0]);
	JTree jt=new JTree(dmt);
	JScrollPane jsp=new JScrollPane(jt);
	private JSplitPane jslp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
	JPanel jp=new JPanel();
	private JLabel jlroot=new JLabel("欢迎进入中大南方图书管理系统！！");
	private Manager mg;//管理员登录的管理员名
	String mgNo;//管理员id
	CardLayout cl=new CardLayout();//获取卡片布局管理器引用
	public Root(String mgNo)
	{
		this.mgNo=mgNo;//获取管理员的id
		mg=new Manager(mgNo);//创建管理员管理面板
		this.SetManage();//设置管理员权限
		this.init();//初始化jp面板
		this.addTreeActionListener();//为树节点注册事件监听器
		jt.setShowsRootHandles(true);//设置显示根节点的控制图标
		jp.setBounds(200,50,600,500);
		jslp.setLeftComponent(jsp);
		jslp.setRightComponent(jp);
		jslp.setDividerLocation(200);
		jslp.setDividerSize(4);
		jlroot.setFont(new Font("Courier", Font.PLAIN, 50));//设置标签的字体和样式以及大小
		//把标签设置为水平居中和垂直居中
		jlroot.setHorizontalAlignment(JLabel.CENTER);
		jlroot.setVerticalAlignment(JLabel.CENTER);
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				{//显示根结点信息
					cl.show(jp, "root");
				}
				if(nv.value.equals("学生用户管理"))
				{//显示根结点信息
					cl.show(jp, "stu");
				}
				if(nv.value.equals("图书管理"))
				{//显示根结点信息
					cl.show(jp, "bookMg");
				}
				if(nv.value.equals("查询图书"))
				{//显示根结点信息
					cl.show(jp, "searchBook");
				}
				if(nv.value.equals("借阅预约图书"))
				{//显示根结点信息
					cl.show(jp, "borrowBk");
				}
				if(nv.value.equals("归还挂失图书"))
				{//显示根结点信息
					cl.show(jp, "returnBook");
				}
				if(nv.value.equals("交纳罚款"))
				{//显示根结点信息
					cl.show(jp, "exceedTime");
				}
				if(nv.value.equals("管理员管理"))
				{//显示根结点信息
					cl.show(jp, "Manager");
				}
				if(nv.value.equals("退出"))
				{//显示根结点信息
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
		
	}
	public static void main(String[] args) {
		new Root("hjs");
	}
}
class NodeValue
{
	String value;
	public String getValue() {
		return this.value;
	}
	public NodeValue(String value)
	{
		this.value=value;
	}
	public String toString()
	{//重写toString()方法
		return value;
	}
}