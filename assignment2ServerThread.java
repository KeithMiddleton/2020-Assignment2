import java.io.*; 
import java.net.*;
import java.util.*;

public class assignment2ServerThread extends Thread{
	protected Socket socket       = null;
	protected PrintWriter out     = null;
	protected BufferedReader in   = null;

	//Constructor
	public assignment2ServerThread(Socket socket){
		super();
		this.socket = socket;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("IOEXception while opening a read/write connection");
		}
	}


	public void run(){
		out.println("Connected");	

		boolean endOfSession = false;
		while(!endOfSession) {
			endOfSession = processCommand();
		}
		try {
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	protected boolean processCommand(String cmd){
		if(cmd.equalsIgnoreCase("DIR")){

			File sharedFolder = new File("/shared");
			File[] files = sharedFolder.listFiles();

			for (File file : files) {
			    if (file.isFile()) {
			        out.println(file.getName() + "\n");
			    }
			}
			return true;
		}
		else{
			out.println("Invalid Command");
			return true;
		}
	}

	protected boolean processCommand(String cmd, String args){
		if(cmd.equalsIgnoreCase("UPLOAD")){
			//Temporary
			out.println("Uploaded.")
			return true;
		}
		else if(cmd.equalsIgnoreCase("DOWNLOAD")){
			File tempDownload = new File("/shared/" + args);
			if(tempDownload.exists()){
				//This is temporary
				out.println("File exists.")
			}
			else{
				out.println("File does not exist.")
			}
			return true;
		}
		else{
			out.println("Invalid Command");
			return true;
		}
	}
}