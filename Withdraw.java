//Jingxuan-commit
package cns.sdut.bank.view;
import java.awt.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import javax.swing.*;

import cn.sdut.bank.dao.caozuo;
import cn.sdut.bank.tools.Finddate;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.math.BigDecimal;


public class Withdraw extends JFrame implements ActionListener
{
	static JButton sure, exit;
	static JLabel accnum, mon,pwd;
	static JTextField accnumtext, montext;
	static JPasswordField pwdtext;
	static String leftMon,Mon;
	  
	public Withdraw()
	{
		this.setTitle("取款");
		JPanel jpanel = new JPanel();
		this.setContentPane(jpanel);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 670) / 2;
		int y = (int)(height - 540) / 2;
		this.setBounds(x, y, 670, 540);
		
		//添加标签组件
		GridLayout gird = new GridLayout(6,0);
		JPanel jpanel1 = new JPanel();
		JPanel jpanel2 = new JPanel();
		JPanel jpanel3 = new JPanel();
		JPanel jpanel4 = new JPanel();
		JPanel jpanel5 = new JPanel();
		JPanel jpanel6 = new JPanel();
		
		jpanel.setLayout(gird);
		jpanel.add(jpanel1);
		jpanel.add(jpanel2);
		jpanel.add(jpanel3);
		jpanel.add(jpanel4);
		jpanel.add(jpanel5);
		jpanel.add(jpanel6);
		
		ImageIcon img = new ImageIcon("image/3.jpg");
		JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
		
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		
		accnum = new JLabel("账        号:      ");
		accnumtext = new JTextField(10); 
		
		mon = new JLabel("取款金额:      ");
		montext = new JTextField(10);
		
	    pwd = new JLabel("密        码:      ");
		pwdtext = new JPasswordField(10);
	    
		sure = new JButton("   确认   ");
		JLabel Black_JLabel = new JLabel("         ");
		exit = new JButton("   退出   ");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//确认取款的监听事件
		sure.addActionListener(new actionTest() {
			public void actionPerformed(ActionEvent e)
			   {	  
			   		  String user_accnum = null;
			   		  user_accnum = accnumtext.getText();
			   		  
			   		  String user_mon1 = null;//user_mon1指的是取款金额（字符串格式）
			   		  user_mon1 = montext.getText();
			   		  
			   		  String user_pwd = null;
			   		  user_pwd = new String(pwdtext.getPassword());
		   		 	  caozuo cz = new caozuo();
		   		      Finddate f = new Finddate();
			   		  if(user_accnum.equals("") | user_accnum.length() > 8)//账号weikong 
			   		  {
			   			  new hint(0);
			   		  }
			   		  else if(user_mon1.equals(""))
			   		  {
			   			  new hint(1);
			   		  }
			   		  else if(user_pwd.equals(""))
			   		  {
			   			  new hint(2);
			   		  }
			   		  else if(f.getCount(user_accnum) != 1)
			   		  {
			   			  new hint(3);
			   		  }
			   		  else if(f.getPsw(user_pwd, user_accnum) != 1)
			   		  {
			   			  new hint(4);
			   		  }
			   		  else 
			   		  { 
						   		  int flag = 0;
			   					  for(int i = 0;i <= user_mon1.length() - 1;i++)//判断是否输入的取款金额都是数字
			   					  {
			   						  if((user_mon1.charAt(i) >= '0' && user_mon1.charAt(i) <= '9') || user_mon1.charAt(i) == '.') 
			   						  {
			   							  continue;
			   						  }
			   						  else
			   						  {
			   							  flag = 1;
			   							  break;
			   						  }
			   					  }
			   					  if(flag == 1)//有非数字的输入
			   					  {
			   						  new hint(1);
			   					  }
			   					  else//输入的金额合法
			   					  {   
			   						  String mon = f.getMoney(user_accnum);
			   						  BigDecimal a = new BigDecimal(mon);
			   				    	  BigDecimal b = new BigDecimal(user_mon1);
			   				    	  a = a.subtract(b);
			   				    	  String panduan = String.valueOf(a);
			   				   		  if(panduan.charAt(0) == '-') //panduanyue
			   				   		  {
			   				   			  new hint(5);
			   				   		  }
			   				   		  else
			   				   		  {
			   				   			  cz.takeMoney(user_accnum, user_mon1, user_pwd);
			   							  Mon = user_mon1;
			   							  String allMon = f.getMoney(user_accnum);
			   							  new succ(Mon, allMon);//将提取的金额传入
			   						  }
			   					  }
			   				  }  
			   			  }	  
		});
		
		exit.addActionListener(this);
			

		
		jpanel2.add(accnum);
		jpanel2.add(accnumtext);
		jpanel3.add(mon);
		jpanel3.add(montext);
		jpanel4.add(pwd);
		jpanel4.add(pwdtext);
		jpanel5.add(sure);
		jpanel5.add(Black_JLabel);
		jpanel5.add(exit);
		
		this.getLayeredPane().setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		jpanel.setOpaque(false);
		jpanel1.setOpaque(false);
		jpanel2.setOpaque(false);
		jpanel3.setOpaque(false);
		jpanel4.setOpaque(false);
		jpanel5.setOpaque(false);
		jpanel6.setOpaque(false);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.setVisible(false);
		new Home();
	}

}

class hint
{
	JLabel T;
	JButton ok;
	hint(int flag)
	{	
		JFrame Hint = new JFrame("提示");
		JPanel jpanel = new JPanel();
		Hint.setContentPane(jpanel);
		
		Hint.setLayout(null);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 300) / 2;
		int y = (int)(height - 200) / 2;
		Hint.setBounds(x, y, 300, 200);
		
		//点击确定关闭弹窗
		ok = new JButton("确定");
		ok.addActionListener(new actionTest()
		{
			public void actionPerformed(ActionEvent e)
			{
				Hint.dispose();
			}
		});
		
		if(flag == 0)
		T = new JLabel("请输入合法账号！");
		
		else if(flag == 1)
			T = new JLabel("请输入合法金额！");
		
		else if(flag == 2)
			T = new JLabel("请输入合法密码！");
		
		else if(flag == 3)
			T = new JLabel("您的账号不存在！");
		
		else if(flag == 4)
			T = new JLabel("您的密码错误！");
		
		else if(flag == 5)
			T = new JLabel("您的余额不足！");
		
		//设置标签、按钮在屏幕上的位置及大小
		T.setBounds(100, 10, 200, 100);
		ok.setBounds(110, 100, 60, 30);
		
		Hint.add(T);
		Hint.add(ok);
		Hint.setVisible(true);
	}
}

class succ extends JFrame implements ActionListener
{
	JLabel aa, bb, cc,dd;
	JButton ok;

	
	private ImageJPanel ip;
	
	succ(String Mon, String allMon)
	{
		
		ip = new ImageJPanel();
		
		this.setTitle("提示");
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(this);
		Container c = this.getContentPane();
		
		
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 670) / 2;
		int y = (int)(height - 540) / 2;
		this.setBounds(x, y, 670, 540);
		
		//添加标签组件
		c.setLayout(null);
		
		ok = new JButton("确认");
		ok.addActionListener(this);
		
		
		aa = new JLabel("恭喜您，取款成功！");
		bb = new JLabel("您提取的金额为： " + Mon + "元");
		cc = new JLabel("您的账户余额： " + allMon + "元");//通过数据库函数调出余额
		
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");  
	    String time = sdf.format(date.getTime()); 
		
		dd = new JLabel("操作时间：" + time);
		
		aa.setBounds(250, 100, 200, 100);
		bb.setBounds(250, 150, 200, 100);
		cc.setBounds(250, 200, 200, 100);
		dd.setBounds(250, 250, 300, 100);
		ok.setBounds(280, 350, 100, 50);
		
		
		
		c.add(aa);
		c.add(bb);
		c.add(cc);
		c.add(dd);
		c.add(ok);
		
		ip.setBounds(0, 0, this.getWidth(), this.getHeight());
		c.add(ip);

		this.getLayeredPane().setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.setVisible(false);
	}
}