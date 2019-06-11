package cns.sdut.bank.view;
import java.awt.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cn.sdut.bank.dao.caozuo;
import cn.sdut.bank.tools.Finddate;
import cn.sdut.bank.service.file_io;
import java.awt.event.*;
import java.io.*;

public class Find extends JFrame implements ActionListener
{
	static JButton sure, exit;
	static JLabel accnum, pwd;
	static JTextField accnumtext;
	static JPasswordField pwdtext;
	static String Accnum;
	  
	public Find()
	{
		this.setTitle("查询");
		JPanel jpanel = new JPanel();
		this.setContentPane(jpanel);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 660) / 2;
		int y = (int)(height - 500) / 2;
		this.setBounds(x, y, 660, 500);
		
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
		
	    pwd = new JLabel("密        码:      ");
		pwdtext = new JPasswordField(10);
	    
		sure = new JButton("   确认   ");
		JLabel Black_JLabel = new JLabel("         ");
		exit = new JButton("   退出   ");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		file_io f1 = new file_io();
		
		
		//确认取款的监听事件
		sure.addActionListener(new actionFind() {
			public void actionPerformed(ActionEvent e)
			   {	  	  
					  String user_accnum = null;
			   		  user_accnum = accnumtext.getText();
			   		  
			   		  String user_pwd = null;
			   		  user_pwd = new String(pwdtext.getPassword());
		   			  
			   		  caozuo cz = new caozuo();
			   		  Finddate f = new Finddate();
			   		  
			   		  if(user_accnum.equals(""))//账号输入错误时
			   		  {
			   			  new findHint(0);
			   		  }
			   		  else if(user_pwd.equals("")) 
			   		  {
			   			  new findHint(1);
			   		  }
			   		  else if(f.getCount(user_accnum) != 1)
			   		  {
			   			  new findHint(2);
			   		  }
			   		  else if(f.getPsw(user_pwd, user_accnum) != 1)
			   		  {
			   			  new findHint(3);
			   		  }
			   		  else //密码正确时，弹出信息窗口
			   		  {
			   				  Accnum = user_accnum;
			   				  new FindSucc(Accnum);
			   				  
			   		  }  
			   	   }
			   }
         );
		
		exit.addActionListener(this);
		
			

		
		jpanel3.add(accnum);
		jpanel3.add(accnumtext);
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

class findHint extends JFrame
{
	JLabel T;
	JButton ok;
	findHint(int flag)
	{	
		JFrame findHint = new JFrame("提示");
		JPanel jpanel = new JPanel();
		findHint.setContentPane(jpanel);
		
		findHint.setLayout(null);
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 300) / 2;
		int y = (int)(height - 200) / 2;
		findHint.setBounds(x, y, 300, 200);
		
		
		//点击确定关闭弹窗
		ok = new JButton("确定");
		ok.addActionListener(new actionFind()
		{
			public void actionPerformed(ActionEvent e)
			{
				findHint.dispose();
			}
		});
		
		if(flag == 0)
		T = new JLabel("请输入合法账号！");	
		else if(flag == 1)
			T = new JLabel("请输入合法密码！");
		else if(flag == 2)
			T = new JLabel("您的账号不存在！");
		else if(flag == 3)
			T = new JLabel("您的密码错误！");
		
		//设置标签、按钮在屏幕上的位置及大小
		T.setBounds(100, 10, 200, 100);
		ok.setBounds(110, 100, 60, 30);
		
		findHint.add(T);
		findHint.add(ok);
		findHint.setVisible(true);
	}
	
}

class FindSucc extends JFrame implements ActionListener
{
	JLabel aa, bb, cc, dd, ee;
	JButton ok, ok2;
	// 默认表格模型
	String[][] datas = {};
    String[] titles = { "操作类型", "操作金额（元）" ,"	操作时间","旧密码","新密码" };
	private DefaultTableModel model = new DefaultTableModel(datas, titles);
	private JTable table = new JTable(model);
	JScrollPane jsp = new JScrollPane(table);
    
	
	private ImageJPanel2 ip;
	
	FindSucc(String Accnum)
	{
		
		ip = new ImageJPanel2();
		
		this.setTitle("提示");
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(this);
		Container c = this.getContentPane();
		
		
		//设置窗口在屏幕中居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (int)(width - 800) / 2;
		int y = (int)(height - 600) / 2;
		this.setBounds(x, y, 800, 600);
		
	
		
		//添加标签组件
		c.setLayout(null);
		
		ok = new JButton("确认");
		ok.addActionListener(this);
		
		ok2 = new JButton("查询记录");//按钮，点击一下向表格中加入一条记录

	    ok2.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e)
	         {
	        	 //实验开始
	        	String path = "userinfo\\";
	     		String name = Accnum;
	     		String houzhuiming = ".txt";
	     		File file = new File(path + name + houzhuiming);
	     		try {
	             BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	             String s = null;
	             while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	                 String a[] = s.split(",");
	                 String aa = null, bb = null, cc = null, dd = null, ee = null;
	             	for(int i = 0;i < 6;i++) {
	             		switch(i) {
	             		case 0:{
	             			switch(a[i]) {
	             			case "1":{
	             				aa = "开户";
	             				break;
	             			}
	             			case "2":{
	             				aa = "存款";
	             				break;
	             			}
	             			case "3":{
	             				aa = "取款";
	             				break;
	             			}
	             			case "4":{
	             				aa = "转账";
	             				break;
	             			}
	             			case "6":{
	             				aa = "修改密码";
	             				break;
	             			}
	             			case "7":{
	             				aa = "销户";
	             				break;
	             			}
	             			}
	             			break;
	             		}
	             		case 2:{
	             			bb = a[i];
	             			break;
	             		}
	             		case 3:{
	             			cc = a[i];
	             			break;
	             		}
	             		case 4:{
	             			dd = a[i];
	             			break;
	             		}
	             		case 5:{
	             			ee = a[i];
	             			break;
	             		}
	             		}
	             	}
	             	//向表格中加入一行记录,  addRow加入的是字符串数组，将想要加入的各个字符串变成数组后加入
		            model.addRow(new String[] {aa,bb,cc,dd,ee});
	             }
	             br.close();
	     		}
	     		catch(IOException a) {
		        	a.printStackTrace();
		        }
	        	 //实验结束
	        	
	         }
	     });
		
		
		caozuo cz = new caozuo();
		Finddate f = new Finddate();
		String Name = f.getName(Accnum);
		String openTime = f.getdate(Accnum);
		aa = new JLabel("账        号：" + Accnum);
		bb = new JLabel("姓        名：" + Name);
		cc = new JLabel("账户余额：" + f.getMoney(Accnum) + "元");//通过数据库函数调出余额
		dd = new JLabel("开户日期：" + openTime);
		ee = new JLabel("操作记录："); 

		aa.setBounds(125, 10, 200, 100);
		bb.setBounds(125, 40, 200, 100);
		cc.setBounds(125, 70, 200, 100);
		dd.setBounds(125, 100, 300, 100);
		ee.setBounds(125, 130, 200, 100);
		ok.setBounds(350, 470, 100, 50);
		ok2.setBounds(125,470,100,50);
		jsp.setBounds(125, 200, 650, 250);

		
		//默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(aa);
		c.add(bb);
		c.add(cc);
		c.add(dd);
		c.add(ee);
		c.add(ok);
		c.add(ok2);
		c.add(jsp);
		
		ip.setBounds(0, 0, this.getWidth(), this.getHeight());
		c.add(ip);
		
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		this.setVisible(false);
	}
	
}

class ImageJPanel2 extends JPanel 
{
	private ImageIcon ii;

	public ImageJPanel2() 
	{
		// bk.jpg是指背景图片的名称 
		ii=new ImageIcon("image/5.jpg");
	}

	// 绘制背景图片 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(ii.getImage(), 0, 0, this);
	}
}

class actionFind implements ActionListener
{
    public void actionPerformed(ActionEvent e)
   {
   	  
   }  	 
}