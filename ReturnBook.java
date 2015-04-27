/**
 *����Ա�˵Ĺ黹��ʧ
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
	//�����ָ��Ϊ���µ�JSplitePane����
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	//����JPanel����
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	//����ѡ�����
	JTabbedPane jtp=new JTabbedPane();

	//������ť����
	private JButton[] jbArray=new JButton[]
			{
			new JButton	("��    ʧ"),
			new JButton	("��    ��"),
			new JButton	("����ѧ��")
			};
	private JLabel jl1=new JLabel("���������ѧ��:");
	private JLabel jl2=new JLabel("**��ܰ��ʾ:����ѧ�Ų�ѯѧ��������Ϣ��Ȼ���ڱ���е��Ҫ��ʧ���߹黹���飡**");
	private JTextField jtxt=new JTextField();

	public ReturnBook()
	{
		bm=new borrowMode();//����һ��ģ�Ͷ���
		jt=new JTable(bm);//����Table���󣬲���ģ�ͷ�װ��ȥ
		jspn=new JScrollPane(jt);//��JTable��װ����������
		this.setLayout(new GridLayout(1,1));
		//��������RetrunBook�������²��־�Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		jpb.setLayout(null);
		//����Label�Ĵ�С��λ��
		jl1.setBounds(55,65,100,20);	
		jl2.setBounds(55,15,600,20);
		jl2.setForeground(Color.RED);
		//��Jlabel��ӵ�jpt�����
		jpt.add(jl1);
		jpt.add(jl2);
		//ΪJTextField���ô�С��λ��
		jtxt.setBounds(155,65,200,20);
		//��JTextField��ӵ�jpt
		jpt.add(jtxt);
		//����JBuuton�Ĵ�С��λ��
		jbArray[0].setBounds(55,110,90,20);
		jbArray[1].setBounds(200,110,90,20);
		jbArray[2].setBounds(395,65,90,20);
		//���JButton����������¼�������
		for(int i=0;i<3;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}

		//�������ӵ�ѡ�������
		jtp.addTab("��  ʧ  ��  ��  ͼ  ��", jpt);
		//jtp.addTab("��  ��  ͼ  ��", jpb);
		//��jpt���õ�jsp���ϲ�����
		jsp.setTopComponent(jtp);
		//jpb.add(jspn);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(14);
		this.add(jsp);
		//����jsp�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(180);
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(10,10,800,600);
		this.setVisible(true); 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//�������ѧ����ť
		if(e.getSource()==jbArray[2])
		{
			if(jtxt.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�����ѧ����Ϣ����Ϊ�գ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else
			{
				sql="select * from record where stuNo="+Integer.parseInt(jtxt.getText().trim());
				db=new DataBase();
				db.selectDb(sql);
				try {
					if(!(db.rs.next())){
						JOptionPane.showMessageDialog(this, "û�и�ѧ���Ľ�����Ϣ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				bm=new borrowMode(sql);
				jt.setModel(bm);
			}
		}
		//ѡ��黹ͼ���ʱ��
		else if(e.getSource()==jbArray[1])
		{
			int row=jt.getSelectedRow();
			if(row<0)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�黹����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//�������һ��
			int bookNo=Integer.parseInt((String) jt.getValueAt(row, 0));//�õ�����е����
			int stuNo=Integer.parseInt((String) jt.getValueAt(row, 2));
			int flag=checkTime(bookNo, stuNo);
			if(flag==-1)
			{//����û�н��ɷ����ѧ�����������Ȩ������Ϊ��
				sql="update student set Permitted='��' where stuNo="+Integer.parseInt((String) jt.getValueAt(row, 2));
				db=new DataBase();
				db.updateDb(sql);
				//���±��
				bm=new borrowMode();
				jt.setModel(bm);
			}
			else if(flag==0){return;}
			//û�г���,��������
			//�ӽ��ı���ɾ���ü�¼
			sql="delete from record where BookNo="+Integer.parseInt((String)jt.getValueAt(row, 0));
			db=new DataBase();
			db.updateDb(sql);
			sql="update book set Borrowed='��' where BookNo="+Integer.parseInt((String)jt.getValueAt(row, 0));
			db.updateDb(sql);
			//���±��
			bm=new borrowMode();
			jt.setModel(bm);
		}
		//ѡ���ʧ��ť
		else if(e.getSource()==jbArray[0])
		{
			int row=jt.getSelectedRow();
			if(row<0)
			{
				JOptionPane.showMessageDialog(this, "�����±���ѡ��Ҫ��ʧ���飡", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			loseBook(row);
			bm=new borrowMode();
			jt.setModel(bm);//���±��
		}

	} 
	//����һ����ʧͼ��ķ���
	public void loseBook(int row)
	{
		String bookName="";
		int lbno=0;
		int bookno=Integer.parseInt((String) jt.getValueAt(row, 0));//�õ�����е����
		int stuno=Integer.parseInt((String) jt.getValueAt(row, 2));
		//��ͼ����в�������
		sql="select bookName from book where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next())
			{
				bookName=db.rs.getString(1).trim();//��ȡ����
			}
		} catch (Exception e) {e.printStackTrace();}
		//���ҹ�ʧͼ��������ʧ��
		sql="select MAX(LBNO) from losebook";
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				lbno=db.rs.getInt(1);//��ȡ����ʧ��
				lbno++;//��ʧ�ż�1
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//����ʧ��ͼʾ��ӵ���ʧ����
		sql="insert into losebook(LBNO,StuNo,BookNo,BookName) values("+lbno+","+stuno+","+bookno+",'"+bookName+"')";
		db=new DataBase();
		int i=db.updateDb(sql);
		//����ԤԼ�����Ƿ��и���ļ�¼���������ɾ��
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
		//�����ı����Ƿ��иü�¼���������ɾ��
		sql="select BookNo from record where BookNo="+bookno;
		db=new DataBase();
		db.selectDb(sql);
		try {
			if(db.rs.next()){
				sql="delete from record where BookNo="+bookno;//�ӽ�����н���ʧͼ��ļ�¼ɾ��
				db=new DataBase();
				db.updateDb(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//����ͼ������Ƿ��и���ļ�¼������У���ɾ��
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
		if(i<0){//��ʧʧ����ʾ
			JOptionPane.showMessageDialog(this, "��ʧʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}else{//��ʧ�ɹ���ʾ
			JOptionPane.showMessageDialog(this, "��ʧ�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	//������ʱ���Ƿ��ڵķ���
	public int checkTime(int bookNo,int stuNo)
	{//˵������1���������������飬��-1�����Ѿ����ڵ���û�н��ɷ���ġ�����-2�����ڲ����Ѿ������˷���ġ�
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
				returntime=db.rs.getString(1).trim();//��ȡ�黹��ʱ��
			}
		} catch (Exception e) {e.printStackTrace();}
		String[] strday=returntime.split("\\-");//ʹ��������ʽ���涨ʱ��ĸ�ʽ
		int ryear=Integer.parseInt(strday[0].trim());//��ȡ�黹����
		int rmonth=Integer.parseInt(strday[1].trim());//��ȡ�黹����
		int rday=Integer.parseInt(strday[2].trim());//��ȡ�黹����
		now=new Date();
		//��ȡ���ڵ�����
		int day=(((now.getYear()+1900)-ryear)*365+((now.getMonth()+1)-rmonth)*30+(now.getDate()-rday));
		if(day>0)
		{
			//��������ˣ�����ȷ�϶Ի��򣬲���ʾ���ڵ������ͷ�����
			int d=JOptionPane.showConfirmDialog(this, "�������Ѿ�����"+day+"��Ҫ����"+day*1+"Ԫ���Ƿ����ڽ��ɣ�", "��ʾ", JOptionPane.YES_NO_OPTION);
			if(d==JOptionPane.YES_OPTION)
			{//���ڲ����ɷ���
				JOptionPane.showMessageDialog(this, "���ѳɹ�����"+day*1+"Ԫ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				flag=-2;
			}else
			{//���ڵ�û�н��ɷ���
				sql="select BookName from book where BookNo="+bookNo;
				db.selectDb(sql);
				try {
					if(db.rs.next())
					{
						bookName=db.rs.getString(1).trim();//��ȡ����
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//�����ݲ��뵽���ڱ���
				sql="insert into exceedtime(StuNO,BookNO,BookName,DelayTime) values("+stuNo+","+bookNo+",'"+bookName+"',"+day+")";
				db.updateDb(sql);
				flag=-1;//���ڵ�û�н��ɷ���ı�־
			}
		}
		else{flag=1;}
		db.dbClose();
		return flag;
	}

}