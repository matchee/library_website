package com.bookserver.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.bookserver.view.ServerMain;
import com.common.Message;

public class SocketServer implements Runnable{
	Socket client=null;
	// 连接MySQL数据库
	public static final String DBURL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false" ;
	// MySQL的用户
	public static final String DBUSER = "root";
	// MySQL的密码
	public static final String DBPASS = "root";
	Connection conn = null ;		
	Statement stmt = null ;		
	ResultSet rs = null ;		
	public SocketServer(Socket client){
		this.client=client;
	}
	public void sendMes(Message mes){
		try{
			ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
			oos.writeObject(mes);
			oos.flush();
			client.close();
		}catch(Exception e){
			e.printStackTrace();
			}
	}
	@Override
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			Message mes=(Message)ois.readObject();
			Message mes1=new Message();
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
			stmt = conn.createStatement() ;
			
			if(mes.getFlag()==1){   //users表
				String username=mes.getUsername();
				String usersex=mes.getUsersex();
				String usercollege=mes.getUsercollege();
				int userstudentid=mes.getUserstudentid();
				String userpassword=mes.getUserpassword();
				String usermail=mes.getUsermail();
				String sql = "insert into users(username,usersex,usercollege,userstudentid,userpassword,usermail) values('"+username+"','"+usersex+"','"+usercollege+"',"+userstudentid+",'"+userpassword+"','"+usermail+"');";
				stmt.executeUpdate(sql) ;
				String sql1="select userid from users where usermail='"+usermail+"';";
				rs = stmt.executeQuery(sql1);
				int uid = 0;
				while(rs.next()){
					uid=rs.getInt("userid");
				}
				mes1.setUserid(uid);
				sendMes(mes1);
				//
				String sql2="insert into borrowbooks(userid) values ("+uid+");";
				stmt.executeUpdate(sql2);
			}
			if(mes.getFlag()==2){  //验证登录
				int userid=mes.getUserid();
				String userpassword=mes.getUserpassword();
				String sql ="select * from users where userid="+userid+" and userpassword='"+userpassword+"';";
				rs = stmt.executeQuery(sql) ;
				if(rs.next()){
					mes1.setOk(true);
				}else{
					mes1.setOk(false);
				}
				sendMes(mes1);
			}
			if(mes.getFlag()==3){  //books表
				String sql ="select * from books;";
				rs = stmt.executeQuery(sql) ;
				while(rs.next()){
					int bookid=rs.getInt(1);
					String bookname=rs.getString(2);
					String bookpress=rs.getString(3);
					String pressdate=rs.getString(4);
					String bookauthor=rs.getString(5);
					int bookcount=rs.getInt(6);
					int bookincount=rs.getInt(7);
					mes1.setFlag(0);
					mes1.setBookid(bookid);
					mes1.setBookname(bookname);
					mes1.setBookpress(bookpress);
					mes1.setPressdate(pressdate);
					mes1.setBookauthor(bookauthor);
					mes1.setBookcount(bookcount);
					mes1.setBookincount(bookincount);
					ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
					oos.writeObject(mes1);
					oos.flush();
				}
				Message mes3=new Message();
				mes3.setFlag(-3);
				sendMes(mes3);
			}
			if(mes.getFlag()==4){  //4表示查询书籍
				String str=mes.getShortmes();
				String sql ="select * from books where bookname like '%"+str+"%' or bookname like '"+str+"%' or bookname like '%"+str+"';";
				rs = stmt.executeQuery(sql) ;
				while(rs.next()){
					int bookid=rs.getInt(1);
					String bookname=rs.getString(2);
					String bookpress=rs.getString(3);
					String pressdate=rs.getString(4);
					String bookauthor=rs.getString(5);
					int bookcount=rs.getInt(6);
					int bookincount=rs.getInt(7);
					mes1.setFlag(0);
					mes1.setBookid(bookid);
					mes1.setBookname(bookname);
					mes1.setBookpress(bookpress);
					mes1.setPressdate(pressdate);
					mes1.setBookauthor(bookauthor);
					mes1.setBookcount(bookcount);
					mes1.setBookincount(bookincount);
					ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
					oos.writeObject(mes1);
					oos.flush();
				}
				Message mes3=new Message();
				mes3.setFlag(-3);
				sendMes(mes3);
			}
			if(mes.getFlag()==5){  //5表示查询用户
				int uid=mes.getUserid();
				String sql ="select * from users where userid="+uid+";";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					int userid=rs.getInt(1);
					String username=rs.getString(2);
					String usersex=rs.getString(3);
					int userstudentid=rs.getInt(4);
					String usercollege=rs.getString(5);
					String userpassword=rs.getString(7);
					String usermail=rs.getString(8);
					String userowe=rs.getString(9);
					mes1.setUserid(userid);
					mes1.setUsername(username);
					mes1.setUsersex(usersex);
					mes1.setUserstudentid(userstudentid);
					mes1.setUsercollege(usercollege);
					mes1.setUserpassword(userpassword);
					mes1.setUsermail(usermail);
					mes1.setUserowe(userowe);
					sendMes(mes1);
				}
			}
			if(mes.getFlag()==6){   //6表示查询欠费读者
				int bookid=mes.getBookid();
				int userid=mes.getUserid();
				String bookname = null;
				String bookpress = null;
				String pressdate = null;
				String bookauthor = null;
				String bookstarttime=mes.getBookstarttime();
				int bookincount = 0;
				String sql ="select * from books where bookid="+bookid+";";
				rs = stmt.executeQuery(sql);
				if(rs.next()){
					bookincount=rs.getInt(7); 
					bookname=rs.getString(2);
					bookpress=rs.getString(3);
					pressdate=rs.getString(4);
					bookauthor=rs.getString(5);				
				}
				if(bookincount!=0){
					bookincount--;
					String sql1 ="update books set bookincount="+bookincount+" where bookid="+bookid+";";
					stmt.executeUpdate(sql1);
					String sql2="insert into borrowbooks values ("+userid+","+bookid+",'"
					+bookname+"','"+bookpress+"','"+pressdate+"','"+bookauthor+"','"+bookstarttime+"');";
					stmt.executeUpdate(sql2);
					String owe="欠费";
					String sql3="update users set owe='"+owe+"' where userid="+userid+";";
					stmt.executeUpdate(sql3);
					mes1.setOk(true);
					ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
					oos.writeObject(mes1);
					oos.flush();
				}
				if(bookincount==0){
					mes1.setOk(false);
					ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
					oos.writeObject(mes1);
					oos.flush();
				}
			}
			if(mes.getFlag()==7){    //7表示查询用户借书列表
				int userid=mes.getUserid();
				String sql ="select * from borrowbooks where userid="+userid+";";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					int bookid=rs.getInt(2);
					String bookname=rs.getString(3);
					String bookpress=rs.getString(4);
					String pressdate=rs.getString(5);
					String bookauthor=rs.getString(6);
					String bookstarttime=rs.getString(7);
					mes1.setFlag(0);
					mes1.setBookid(bookid);
					mes1.setBookname(bookname);
					mes1.setBookpress(bookpress);
					mes1.setPressdate(pressdate);
					mes1.setBookauthor(bookauthor);
					mes1.setBookstarttime(bookstarttime);
					ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());	
					oos.writeObject(mes1);
					oos.flush();
				}
				Message mes2=new Message();
				mes2.setFlag(-3);
				sendMes(mes2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
