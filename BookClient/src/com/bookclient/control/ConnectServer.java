package com.bookclient.control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTable;

import com.common.Message;
import com.common.MyTable;

public class ConnectServer{
	Message ms=null;
	private Socket s=null;
	public ConnectServer(Message mes){
		this.ms=mes;
		try{
			s=new Socket("10.251.108.4",8888);//输入你的IP地址
		}catch(Exception e){
			e.printStackTrace();
			}
		}

	public void sendMessage(){ //开始连接
			try{
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(ms);
					oos.flush();
			}catch(Exception e){
					e.printStackTrace();
			}
		}

	public Message getMessage(){ //开始监听
		    Message ms1=null;
			try{
					ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
					ms1=(Message)ois.readObject();
			}catch(Exception e){
					e.printStackTrace();
			}
			return ms1;
		}

	public void closeConnect(){ //关闭连接
		try{
			s.close();
		}catch(Exception e){
			e.printStackTrace();
			}
		}
	}