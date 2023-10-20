package com.bookserver.model;

import javax.swing.table.DefaultTableModel;

public class OweUserModel  extends DefaultTableModel{
	public static String[] colnam={"帐号","姓名","性别","学号","学院","拖欠书名","拖欠书号"};
	public static Object[][] cell={};
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;	
    }
	public OweUserModel(){
		super(OweUserModel.cell,OweUserModel.colnam);
	}
}
