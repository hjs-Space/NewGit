package com.DUANGDUANG1;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.db.DataBase;

public class exceedTimeMode extends AbstractTableModel{
	Vector<String> head;
	Vector<Vector> data;
	DataBase db;
	String sql;
	private void Init(String sql) {
		if(sql.equals(""))
		{
			sql="select * from exceedtime";
		}
		data=new Vector();
		head = new Vector<String>();{//��������
			head.add("ѧ��");
			head.add("���");
			head.add("����");
			head.add("��������");
		}
		try {
			db=new DataBase();
			db.selectDb(sql);
			while(db.rs.next()){
					Vector head=new Vector();
					head.add(db.rs.getString(1).trim());
					head.add(db.rs.getString(2).trim());
					head.add(db.rs.getString(3).trim());
					head.add(db.rs.getString(4).trim());
					data.add(head);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(db.rs!=null)db.rs.close();
				if(db.stat!=null)db.stat.close();
				if(db.con!=null)db.con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	//ͨ�����ݵ�sql������õ�����ģ��
	public exceedTimeMode (String sql) {
		this.Init(sql);
	}
	//��һ�����캯�������ڳ�ʼ�����ǵ�����ģ��
	public exceedTimeMode(){
		this.Init("");
	}

	//�õ���������
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.data.size();

	}

	//�õ���������
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.head.size();

	}

	//�õ�ĳ��ĳ�е�����
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector)this.data.get(rowIndex)).get(columnIndex);
	}

	public String getColumnName(int column) {
		return (String)this.head.get(column);
	}
}
