package com.DUANGDUANG1;
/*
 * ��ģ�͵Ĺ���
 * ��Ƭ���ֺ���Ҫ����Ҫ����ʵ�ֵ��
 * ��һ�����ܾ���ʾ��һ���ֹ��ܽ���
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
{//�������ڵ�����
	DefaultMutableTreeNode []dmtn=
		{
			new DefaultMutableTreeNode(new NodeValue("ͼ�����ϵͳ")),
			new DefaultMutableTreeNode(new NodeValue("ѧ���û�����")),
			new DefaultMutableTreeNode(new NodeValue("ͼ�����")),
			new DefaultMutableTreeNode(new NodeValue("��ѯͼ��")),
			new DefaultMutableTreeNode(new NodeValue("����ԤԼͼ��")),
			new DefaultMutableTreeNode(new NodeValue("�黹��ʧͼ��")),
			new DefaultMutableTreeNode(new NodeValue("���ɷ���")),
			new DefaultMutableTreeNode(new NodeValue("����Ա����")),
			new DefaultMutableTreeNode(new NodeValue("�˳�"))
		};
	//����һ��Ĭ�Ϸ���ģ�ͣ���ָ����ͼ�����ϵͳ��Ϊ���ڵ�
	DefaultTreeModel dmt=new DefaultTreeModel(dmtn[0]);
	//����һ�����������������������ģ��
	JTree jt=new JTree(dmt);
	//����������壬��������װ�����������
	JScrollPane jsp=new JScrollPane(jt);
	//�����ָ���壬������Ϊˮƽ����ķָ�
	private JSplitPane jslp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);
	//����������
	JPanel jp=new JPanel();
	//�������ڵ�ı�ǩ
	private JLabel jlroot=new JLabel("��ӭ�����д��Ϸ�ͼ�����ϵͳ����");
	private Manager mg;//����Ա��¼�Ĺ���Ա��
	String mgNo;//����Աid
	String sql;
	DataBase db;
	CardLayout cl=new CardLayout();//��ȡ��Ƭ���ֹ���������
	//���췽��
	public Root(String mgNo)
	{
		this.mgNo=mgNo;//��ȡ����Ա��id
		mg=new Manager(mgNo);//��������Ա�������
		this.SetManage();//���ù���ԱȨ��
		this.init();//��ʼ��jp���
		this.addTreeActionListener();//Ϊ���ڵ�ע���¼�������
		jt.setShowsRootHandles(true);//������ʾ���ڵ�Ŀ���ͼ��
		jp.setBounds(200,50,600,500);//��������λ�ã���С
		jslp.setLeftComponent(jsp);//���ù������jsp��װ���ָ��������
		jslp.setRightComponent(jp);//�������jp��װ���ָ������ұ�
		jslp.setDividerLocation(200);//���÷ָ����ĳ�ʼλ��        
		jslp.setDividerSize(4);//���÷ָ�����С        
		jlroot.setFont(new Font("Courier", Font.PLAIN, 50));//���ñ�ǩ���������ʽ�Լ���С
		//�ѱ�ǩ����Ϊˮƽ���кʹ�ֱ����
		jlroot.setHorizontalAlignment(JLabel.CENTER);
		jlroot.setVerticalAlignment(JLabel.CENTER);
		//����ģ����ӽڵ�
		for(int i=1;i<9;i++)
		{
			dmt.insertNodeInto(dmtn[i], dmtn[0], i-1);
		}
		this.add(jslp);
		this.setTitle("ͼ�����ϵͳ");
		//���ô����״γ��ֵĴ�С��λ��--�Զ�����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//��������
		int h=400;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//����ȫ��
		this.setVisible(true);//���ô���ɼ�		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�ر�
	}
	//��ʼ�����jp
	public void init()
	{
		jp.setLayout(cl);//���ò��ֹ������Ĳ���Ϊ��Ƭ����
		jp.add(jlroot,"root");//��Ӹ������ʾ��Ϣ
		jp.add(new BookManage(),"bookMg");//���ͼ�����ģ�����
		jp.add(new BorrowBook(),"borrowBk");//��ӽ���ԤԼͼ��ģ�����
		jp.add(new ExceedTime(),"exceedTime");//��ӷ�������
		jp.add(this.mg,"Manager");//��ӹ���Ա����ģ�����
		jp.add(new ReturnBook(),"returnBook");//��ӹ黹��ʧͼ�����
		jp.add(new SearchBook(),"searchBook");//��Ӳ���ͼ��������
		jp.add(new Student(),"stu");//���ѧ������ģ�����
	}
	//����Ϊ���ڵ�ע���¼������ķ���
	public void addTreeActionListener(){
		jt.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				//����Ӧ�û�������еĽڵ����
				//getPath()���شӸ�����˽ڵ��·������·�������һ��Ԫ���Ǵ˽ڵ㡣 
				//getLastPathComponent()�ҵ��������һ��ѡ�еĽڵ�
				DefaultMutableTreeNode dmtn1=(DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				NodeValue nv=(NodeValue) dmtn1.getUserObject();//�����Զ���ڵ���û�����
				if(nv.value.equals("ͼ�����ϵͳ"))
				{//��ʾ�������Ϣ����
					cl.show(jp, "root");
				}
				if(nv.value.equals("ѧ���û�����"))
				{//��ʾѧ���û��������
					cl.show(jp, "stu");
				}
				if(nv.value.equals("ͼ�����"))
				{//��ʾͼ��������
					cl.show(jp, "bookMg");
				}
				if(nv.value.equals("��ѯͼ��"))
				{//��ʾ��ѯͼ�����
					cl.show(jp, "searchBook");
				}
				if(nv.value.equals("����ԤԼͼ��"))
				{//��ʾ����ԤԼͼ�����
					cl.show(jp, "borrowBk");
				}
				if(nv.value.equals("�黹��ʧͼ��"))
				{//��ʾ�黹��ʧͼ�����
					cl.show(jp, "returnBook");
				}
				if(nv.value.equals("���ɷ���"))
				{//��ʾ���ɷ������
					cl.show(jp, "exceedTime");
				}
				if(nv.value.equals("����Ա����"))
				{//��ʾ����Ա�������
					cl.show(jp, "Manager");
				}
				if(nv.value.equals("�˳�"))
				{//��ʾ�˳�
					int i=JOptionPane.showConfirmDialog(Root.this, "�Ƿ��˳�ϵͳ", "��Ϣ����", JOptionPane.YES_NO_OPTION);
					if(i==JOptionPane.YES_OPTION)
					{//�˳�ϵͳ
						System.exit(0);
					}
				}
			}
		});
	}
	public void SetManage(){
		//���ڲ�����ѯ��¼���Ƿ���й�Ա��Ȩ��
		sql="select permitted from manager where mgNo='"+mgNo+"'";
		db=new DataBase();//�������ݿ������
		db.selectDb(sql);//ִ�в�ѯ
		try {
			db.rs.next();//���������
			String str=db.rs.getString(1).trim();//��ù���Ȩ��
			if(str.equals("0"))//�����й���Ȩ��
			{
				JOptionPane.showMessageDialog(Root.this, "�ù���Ա�����й���Ȩ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				//ȥ����Ա����������޸Ĺ���Ա��Ȩ��

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
//����һ���ڵ���
class NodeValue
{
	String value;
	//��ýڵ��ֵ
	public String getValue() {
		return this.value;
	}
	public NodeValue(String value)
	{//������
		this.value=value;
	}
	public String toString()
	{//��дtoString()����
		return value;
	}
}