import java.lang.NumberFormatException;
import java.io.IOException;
import java.lang.String;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;	
import java.io.PrintStream;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;

public class host {
	public static void main(String srgs[]) {
		int count = 0;
//	        String str;
		//hard code to use https (8080)
		try(ServerSocket serverSocket = new ServerSocket(1001)){
		   System.out.println("Ready to go: " + serverSocket.getLocalPort());
			while (true) {
				try{
				//	String str;
					Socket socket = serverSocket.accept();
					count++;
					
					
			/*		System.out.println("#" + count + " from "
						+ socket.getInetAddress() + ":"
						+ socket.getPort());
			 */
					/*  move to background thread
                    OutputStream outputStream = socket.getOutputStream();
                    try (PrintStream printStream = new PrintStream(outputStream)) {
                        printStream.print("Hello from Raspberry Pi, you are #" + count);
                    }
                    */
                    HostThread myHostThread = new HostThread(socket, count);
                    myHostThread.start();
		
			     
                } catch(IOException ex){
                	System.out.println(ex.toString());
                }
			}
      
        } catch (IOException ex){
        	System.out.println(ex.toString());
        }	   
	}
   
    private static class HostThread extends Thread{
    	private Socket hostThreadSocket;
    	int cnt;

    	HostThread(Socket socket, int c){
    		hostThreadSocket = socket;
    		cnt = c;
    	}
    	@Override
    	public void run() {
    		try
	     {
		
    		   OutputStream outputStream;
		   outputStream = hostThreadSocket.getOutputStream();
		   String str;
		   BufferedReader br = new BufferedReader(new InputStreamReader(hostThreadSocket.getInputStream()));
		   str = br.readLine();
		   PrintStream printStream = new PrintStream(outputStream);
		   printStream.print(str);
	     }
	   
		 catch (IOException ex){
    			Logger.getLogger(host.class.getName()).log(Level.SEVERE, null, ex);
    		}finally{
    			try{
    				hostThreadSocket.close();
    			} catch (IOException ex){
    				Logger.getLogger(host.class.getName()).log(Level.SEVERE, null, ex);
    			}
		}
		   
	}
    }
}

