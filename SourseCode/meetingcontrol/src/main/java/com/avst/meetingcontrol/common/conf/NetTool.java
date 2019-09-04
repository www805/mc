package com.avst.meetingcontrol.common.conf;

import java.io.*;
import java.net.InetAddress;

public class NetTool {

	private static void printMessage(final InputStream input) {
		new Thread(new Runnable() {
       	public void run() {
        	Reader reader = new InputStreamReader(input);
			BufferedReader bf = new BufferedReader(reader);
        	String line = null;
        	  try {
					while((line=bf.readLine())!=null) {
						System.out.println(line);
					}
         		} catch (IOException e) {
            		e.printStackTrace();
         	 	}finally {
				  try {
					  if(null!=bf){
						bf.close();
					}
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
				  try {
					  if(null!=reader){
						  reader.close();
					  }
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
			  }
        	}
    	}).start();
	}

	/**
	 * 执行cmd命令
	 * @param cmd
	 * @return
	 */
	public static String executeCMD(String cmd){
		OutputStream os = null;
		InputStream in=null;
		InputStream in2=null;
		Process process=null;
		try {
			long start = System.currentTimeMillis();
			process = Runtime.getRuntime().exec(cmd);
			os=process.getOutputStream();
			in=process.getInputStream();
			in2=process.getErrorStream();

			printMessage(in2);

			int exitvalue=process.waitFor();
			if(exitvalue!=0){
				throw new Exception("exitvalue is not 0, 说明代码有错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			try {
				if(null!=os){
					os.flush();
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if(null!=process){
					process.destroy();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if(null!=in){
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;

	}

	//取得LOCALHOST的IP地址
	public static String getMyIP() {
		InetAddress myIPaddress=null;
		try {
			myIPaddress=InetAddress.getLocalHost();
		}
		catch (Exception e) {}
		return (myIPaddress.getHostAddress());
	}

}  