package com.bookserver.model;

import javax.swing.table.DefaultTableModel;

public class UserModel extends DefaultTableModel{
	public static String[] colnam={"帐号","姓名","性别","学号","学院","密码","邮箱","借书"};
	public static Object[][] cell={};
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;	
    }
	public UserModel(){
		super(UserModel.cell,UserModel.colnam);
	}
}