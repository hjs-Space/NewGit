/**
 * ���ڷ���
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
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//�����ָ����, ��ֱ�ָ��ʾ Component �� y ��ָ
	private JPanel jp=new JPanel();
	private JLabel []jlArray={
			new JLabel("�� �� �� �� �� ѧ  ��"),
			new JLabel("��������Ҫ���Ŀ���"),
			new JLabel("��ӭ����ɷ�~^_^* ��ܰ��ʾ������ѧ�ŵ����ѯѧ��Ƿ�ѣ�������ѧ�ţ����������ɷѰ�ť����ɽɷѣ����Ϳ��Խ���������ޣ�")
	};
	private JTextField []jtfArray={
			new JTextField(),
			new JTextField()
	};//�����ı���

	//������ť
	private JButton []jbArray={
			new JButton("��ѯǷ��"),
			new JButton("��        ��")
	};
	public ExceedTime(){
		jp.setLayout(null);
		this.setLayout(new GridLayout(1,1));
		//����������Label����ť���ı���
		for(int i=0;i<2;i++)
		{
			jp.add(jlArray[i]);
			jp.add(jtfArray[i]);
			jp.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}
		jp.add(jlArray[2]);
		//���ñ�ǩ��λ���Լ���С
		jlArray[0].setBounds(50, 70, 120, 20);
		jlArray[1].setBounds(50, 120, 120, 20);
		jlArray[2].setBounds(50, 20, 850, 25);
		//����������ɫ�Ѿ������С
		jlArray[2].setForeground(Color.RED);
		Font font =new Font("Defualt", Font.PLAIN, 14);
		jlArray[2].setFont(font);
		//�����ı����λ���Լ���С
		jtfArray[0].setBounds(180, 70, 120, 20);
		jtfArray[1].setBounds(180, 120, 120, 20);
		//���ð�ť��λ���Լ���С
		jbArray[0].setBounds(340, 70, 100, 20);
		jbArray[1].setBounds(340, 120, 100, 20);
		//����������
		etm=new exceedTimeMode();//����exceedTimeModeģ�Ͷ���
		jt=new JTable(etm);//���������󣬱��ĳ�ʼ��
		jspn=new JScrollPane(jt);///��ʼ��JScrollPane
		jsp.setTopComponent(jp);//��jp��ӵ��ָ����ϲ�
		jsp.setBottomComponent(jspn);//��jspn��ӵ�jsp�ĵײ�
		jsp.setDividerSize(14);
		jsp.setDividerLocation(160);
		this.add(jsp);
		//���ô���Ĵ�Сλ��
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<2;i++)
		{
			str[i]=jtfArray[i].getText().trim();//��ȡ�ı������������
		}
		int day=0;

		if(str[0].equals(""))
		{
			JOptionPane.showMessageDialog(this, "������ѧ�ţ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//�ж�������Ƿ�Ϊ����
		if(str[0].matches("\\D"))
		{
			JOptionPane.showMessageDialog(this, "ѧ��Ӧ��Ϊ���֣�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql="select DelayTime from exceedtime where stuNo="+Integer.parseInt(str[0]);
		db=new DataBase();
		db.selectDb(sql);
		try {
			int flag=0;
			while(db.rs.next()){
				flag++;
				day+= db.rs.getInt(1);//��ȡ���ڵ�����
			}
			if(flag==0){
				JOptionPane.showMessageDialog(this, "�������û�г��ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		//������˲�ѯǷ�Ѱ�ť
		if(e.getSource()==jbArray[0])
		{
			sql="select * from exceedtime where StuNo="+Integer.parseInt(str[0]);
			etm=new exceedTimeMode(sql);
			jt.setModel(etm);
			if(day>0)
			{
				JOptionPane.showMessageDialog(this, "��������Ѿ�����"+day+"�죬��Ҫ���ɷ���"+day*1+"Ԫ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE );
				return;
			}else{//���û��Ƿ���ʾ
				JOptionPane.showMessageDialog(this,"���������û�г��ڣ�",
						"��ʾ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		//������ɷѰ�ťʱ
		else if(e.getSource()==jbArray[1])
		{
			if(str[0].equals(""))
			{//�����ѧ��Ϊ�գ�������ʾ
				JOptionPane.showMessageDialog(this, "����������ѧ�ţ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(str[1].equals(""))
			{//����Ľ��Ϊ�գ�������ʾ
				JOptionPane.showMessageDialog(this, "���������Ľɷѽ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			int a=Integer.parseInt(str[1]);//��ȡ�����ı��������ֵ
			int aa=JOptionPane.showConfirmDialog(this,"�Ƿ����ڽɷѣ�","��ʾ", JOptionPane.YES_NO_OPTION);
			if(aa==JOptionPane.YES_OPTION)
			{

				if(a<(day*1))
				{
					sql="update exceedtime set DelayTime="+(day-a/1)+" where stuNo="+Integer.parseInt(str[0]);
					db=new DataBase();
					int d=db.updateDb(sql);
					if(d==1){
						JOptionPane.showMessageDialog(this, "���Ѿ��ɹ�����"+a+"Ԫ���������"+(day*1-a)+"Ԫ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						sql="select * from exceedTime";
						etm=new exceedTimeMode(sql);
						jt.setModel(etm);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "����ʧ�ܣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				else{//ȫ���ɷ����
					JOptionPane.showMessageDialog(this, "���Ѿ��ɹ�����"+day*1+"Ԫ!�����������Ȩ�ޣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					for(int i=0;i<2;i++)
					{
						jtfArray[i].setText("");
					}
					//�ָ���ѧ���Ľ���Ȩ��
					sql="update student set Permitted='��' where stuNo="+Integer.parseInt(str[0]);
					db=new DataBase();
					db.updateDb(sql);
					//ɾ�����ڱ��и�ѧ���ļ�¼
					sql="delete from exceedTime where stuNo="+Integer.parseInt(str[0]);
					db.updateDb(sql);
					//���±��
					sql="select * from exceedTime";
					etm=new exceedTimeMode(sql);
					jt.setModel(etm);
				}

			}
		}
		db.dbClose();
	}
}