package com.jh;	// ÀÛ¼ºÀÚ:¹ÎÁöÈ«

import java.net.*;
import java.util.*;
import java.io.*;

public class Exjhserver extends Thread{
	static ArrayList<Socket> list=new ArrayList<>();
	Socket sock=null;
	
	public void sayAll(String msg) throws IOException{
		OutputStream os=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw=null;
		for(int i=0; i<list.size(); i++){
			Socket sock=list.get(i);
			os=sock.getOutputStream();
			osw=new OutputStreamWriter(os);
			bw=new BufferedWriter(osw);
			bw.write(msg);
			bw.newLine();
			bw.flush();
		}
	}
	
	@Override
	public void run() {
		InputStream is=null;
		OutputStream os=null;
		InputStreamReader isr=null;
		OutputStreamWriter osw=null;
		BufferedReader br=null;
		BufferedWriter bw=null;
		try {
			is=sock.getInputStream();
			os=sock.getOutputStream();
			isr=new InputStreamReader(is);
			osw=new OutputStreamWriter(os);
			br=new BufferedReader(isr);
			bw=new BufferedWriter(osw);
			while(true){
				String msg=br.readLine();
//				if(msg.equals("exit")){break;}
				sayAll(msg);
			}
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

	public static void main(String[] args) {
		ServerSocket serve=null;
		
		try {
			serve=new ServerSocket(5000);
			while(true){
				Exjhserver thr=new Exjhserver();
				thr.sock=serve.accept();
				thr.start();
				list.add(thr.sock);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				serve.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
