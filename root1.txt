package com.DUANGDUANG1;
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
	private JSplitPane jspn=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
	JPanel jp=new JPanel();
	String mgNo;
	public Root(String mgNo)
	{
		jt.setShowsRootHandles(true);//设置显示根节点的控制图标
		jp.setBounds(200,50,600,500);
		jspn.setLeftComponent(jsp);
		jspn.setRightComponent(jp);
		jspn.setDividerLocation(200);
		jspn.setDividerSize(4);
		for(int i=1;i<9;i++)
		{
			dmt.insertNodeInto(dmtn[i], dmtn[0], i-1);
		}
		this.add(jspn);
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