package com.bookclient.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bookclient.control.ConnectServer;
import com.common.Message;
public class BookClientLogin extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	private JLabel background;
	private ImageIcon backpicture;
	private JPanel p;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JLabel id;
	private JLabel pass;
	private JButton loginbutton;
	private JButton registerbutton;
	private JTextField itf;
	private JPasswordField ptf;
	public BookClientLogin(){
		super("欢迎登录重庆大学数字图书馆");
		backpicture=new ImageIcon("./images/LoginBackground.jpg");
		background=new JLabel(backpicture);
		background.setBounds(0, 0, backpicture.getIconWidth(), backpicture.getIconHeight());
		itf=new JTextField(10);
		ptf=new JPasswordField(10);
		loginbutton=new JButton("登录");
		registerbutton=new JButton("注册");
		loginbutton.addActionListener(this);
		registerbutton.addActionListener(this);
		loginbutton.setForeground(Color.orange);
		registerbutton.setForeground(Color.orange);
		id=new JLabel("用户名");
		pass=new JLabel("密码");
		id.setForeground(Color.orange);
		pass.setForeground(Color.orange);
		p1=new JPanel();
		p1.setBounds(100,50,200,120);
		p1.setLayout(new BorderLayout());
		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		p3=new JPanel();
		p3.setLayout(new FlowLayout());
		p4=new JPanel();
		p4.setLayout(new FlowLayout());
		p2.add(id);
		p2.add(itf);
		p3.add(pass);
		p3.add(ptf);
		p4.add(loginbutton);
		p4.add(registerbutton);
		p1.add(p2,BorderLayout.NORTH);
		p1.add(p3,BorderLayout.CENTER);
		p1.add(p4,BorderLayout.SOUTH);
		p=(JPanel)this.getContentPane();
		p.setLayout(null);
		p.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p3.setOpaque(false);
		p4.setOpaque(false);
		p.add(p1);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
		this.setBounds(350,200,backpicture.getIconWidth(),backpicture.getIconHeight());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new BookClientLogin().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==loginbutton){
			if(itf.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "请输入你的用户名！",
						"用户名不存在！",JOptionPane.WARNING_MESSAGE);  
				}
			else if(String.valueOf(ptf.getPassword()).trim().equals("")){
				JOptionPane.showMessageDialog(this, "请输入你的密码！",
						"输入有误！",JOptionPane.WARNING_MESSAGE);  
				}
			else{
				Message mes=new Message();
				mes.setFlag(2);    //标志2表明是登录
				ConnectServer cs=new ConnectServer(mes);
				mes.setUserid(Integer.parseInt(itf.getText().trim()));
				mes.setUserpassword(String.valueOf(ptf.getPassword()).trim());
				cs.sendMessage();
				Message mes1=cs.getMessage();
				if(mes1.isOk()){
					new ClientMain(Integer.parseInt(itf.getText().trim())).setVisible(true);
					this.dispose();
				}else{
					JOptionPane.showMessageDialog(this,
			         "登录成功");
				}
			}
		}
		if(arg0.getSource()==registerbutton){
			new ClientRegister().setVisible(true);
		}
	}

}