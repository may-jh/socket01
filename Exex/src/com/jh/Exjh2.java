package com.jh;	// 작성자:민지홍

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Exjh2 extends Frame implements ActionListener{
	static TextArea ta;
	static TextField tf;
	static BufferedWriter bw;
	Button btn1;
	MenuItem mi1,mi2,mi3;
	String title="소켓통신 채팅프로그램";
	String name="사용자B";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
//		System.out.println(obj);
		if(obj==mi1){
			FileDialog dia=new FileDialog(this,"저장",FileDialog.SAVE);
			dia.setVisible(true);
			File file=new File(dia.getDirectory()+dia.getFile());
			try {
				if(!file.exists()){
					file.createNewFile();
				}
				FileOutputStream fos=new FileOutputStream(file);
				fos.write(ta.getText().getBytes());
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(obj==mi2){
			dispose();
		}else if(obj==mi3){
			Dialog dia=new Dialog(this,"정보",true);
			Panel p=new Panel();
			Panel p2=new Panel();
			p2.add(new Label("소켓통신 채팅프로그램-2022.01.14"));
			p.setLayout(new BorderLayout());
			p.add(p2,BorderLayout.CENTER);
			Button btn=new Button("확인");
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dia.dispose();
				}		
			});
			p.add(btn,BorderLayout.SOUTH);
			dia.add(p);
			dia.setBounds(300,200,250,100);
			dia.setVisible(true);
		}else if(obj==tf){
			String msg=tf.getText();
			try {
				bw.write(name+"> "+msg);
				bw.newLine();
				bw.flush();
				tf.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(obj==btn1){
			String msg=tf.getText();
			try {
				bw.write(name+"> "+msg);
				bw.newLine();
				bw.flush();
				tf.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public Exjh2(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		this.setTitle(title+"-"+name);
		MenuBar bar=new MenuBar();
		Menu mn1=new Menu("메뉴");
		Menu mn2=new Menu("도움말");
		Panel p=new Panel();
		Panel p1=new Panel();
		p.setLayout(new BorderLayout());
		p1.setLayout(new BorderLayout());
		mi1=new MenuItem("대화내용 저장");
		mi2=new MenuItem("종료");
		mi3=new MenuItem("정보");
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		
		mn1.add(mi1);
		mn1.addSeparator();
		mn1.add(mi2);
		mn2.add(mi3);
		
		bar.add(mn1);
		bar.add(mn2);
		
		tf=new TextField(60);
		tf.addActionListener(this);
		ta=new TextArea();
		
		p.add(ta,BorderLayout.CENTER);
		p1.add(tf,BorderLayout.WEST);
		
		btn1=new Button("전송");
		btn1.addActionListener(this);
		p1.add(btn1,BorderLayout.EAST);
		
		add(p1,BorderLayout.SOUTH);
		add(p,BorderLayout.CENTER);
		setMenuBar(bar);
		setResizable(false);
		setBounds(100,100,500,600);
		setVisible(true);
	}

	public static void main(String[] args) {
		
		Exjh2 me=new Exjh2();
		Socket sock=null;
		InputStream is=null;
		OutputStream os=null;
		InputStreamReader isr=null;
		OutputStreamWriter osw=null;
		BufferedReader br=null;
		
		try {
			sock=new Socket("127.0.0.1",5000);
			is=sock.getInputStream();
			os=sock.getOutputStream();
			isr=new InputStreamReader(is);
			osw=new OutputStreamWriter(os);
			br=new BufferedReader(isr);
			bw=new BufferedWriter(osw);
			
			while(true){
				String msg2=br.readLine();
				ta.append(msg2+"\n");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				bw.close();
				br.close();
				osw.close();
				isr.close();
				os.close();
				is.close();
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
