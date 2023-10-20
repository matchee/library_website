package com.common;

public class Message implements java.io.Serializable{
	public int row;    //行号
	public int column;  //列号
	public int userid=0;//用户ID
	public String username="";//用户名
	public int userstudentid=0;//用户学号
	public String usersex="";   //用户性别
	public int userclassid=0;//用户班号
	public String usercollege="";//用户所在学院
	public String userpassword="";//用户密码
	public String usermail="";  //用户邮箱
	public String userowe;     //用户是否借书
	public int flag=-1;//标志信息
	public boolean ok=false;//布尔值，是/否
	//public String[] mes;//信息数组
	public String shortmes="";//短消息
	public String booktype="";//书的类别
	public String bookname="";//书的名称
	public int bookid=0;//书号
	public String bookpress="";//出版社
	public String pressdate="";//出版日期
	public String bookauthor="";//作者
	public String bookcontent="";//书的概要内容
	public int bookcount=0;//书的总数
	public int bookincount=0;//书的当前在管总数
	public String bookstarttime="";//书的开始借阅日期
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getUserowe() {
		return userowe;
	}
	public void setUserowe(String userowe) {
		this.userowe = userowe;
	}
	public String getUsermail() {
		return usermail;
	}
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUserstudentid() {
		return userstudentid;
	}
	public void setUserstudentid(int userstudentid) {
		this.userstudentid = userstudentid;
	}
	public String getUsersex() {
		return usersex;
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}
	public int getUserclassid() {
		return userclassid;
	}
	public void setUserclassid(int userclassid) {
		this.userclassid = userclassid;
	}
	public String getUsercollege() {
		return usercollege;
	}
	public void setUsercollege(String usercollege) {
		this.usercollege = usercollege;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	/*
	public String[] getMes() {
		return mes;
	}
	public void setMes(String[] mes) {
		this.mes = mes;
	}
	*/
	public String getShortmes() {
		return shortmes;
	}
	public void setShortmes(String shortmes) {
		this.shortmes = shortmes;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getBookpress() {
		return bookpress;
	}
	public void setBookpress(String bookpress) {
		this.bookpress = bookpress;
	}
	public String getPressdate() {
		return pressdate;
	}
	public void setPressdate(String pressdate) {
		this.pressdate = pressdate;
	}
	public String getBookauthor() {
		return bookauthor;
	}
	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}
	public String getBookcontent() {
		return bookcontent;
	}
	public void setBookcontent(String bookcontent) {
		this.bookcontent = bookcontent;
	}
	public int getBookcount() {
		return bookcount;
	}
	public void setBookcount(int bookcount) {
		this.bookcount = bookcount;
	}
	public int getBookincount() {
		return bookincount;
	}
	public void setBookincount(int bookincount) {
		this.bookincount = bookincount;
	}
	public String getBookstarttime() {
		return bookstarttime;
	}
	public void setBookstarttime(String bookstarttime) {
		this.bookstarttime = bookstarttime;
	}
}
