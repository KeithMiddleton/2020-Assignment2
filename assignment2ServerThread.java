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

	protected boolean processCommand(){
		String temp = null;
		String fName = null;
		String fContent = null;
		try {
			temp = in.readLine();
		} catch (IOException e) {
			System.err.println("Error reading command from socket.");
			return true;
		}
		if (temp == null) {
			out.println("No command given");
			return true;
		}
	
		StringTokenizer st = new StringTokenizer(temp);
		String command = st.nextToken();
		
		if (st.hasMoreTokens()) {
			String holder = st.nextToken();
			fName = temp.substring(command.length()+1, (command.length() + holder.length() + 1));
			fContent = temp.substring((command.length() + holder.length() + 2),temp.length());
		}

		return processCommand(command, fName, fContent);	
	}


	protected boolean processCommand(String cmd, String fName, String fContent){
		if(cmd.equalsIgnoreCase("DIR")){
			File sharedFolder = new File("/shared");
			File[] files = sharedFolder.listFiles();

			for (File file : files) {
			    if (file.isFile()) {
			        out.println(file.getName() + "\n");
			    }
			}
			out.println("Dir");
			return true;
		}
		else if(cmd.equalsIgnoreCase("UPLOAD")){
			try{	
				File tempUpload = new File("/shared/" + fName);
				FileWriter uploadWriter = new FileWriter(tempUpload);
      			uploadWriter.write(fContent);
      			uploadWriter.close();
      			
      			out.println("Uploaded.");
			}
			catch(IOException e){
				out.println("Cannot Upload.");
			}
			out.println("Upload");
			return true;
		}
		else if(cmd.equalsIgnoreCase("DOWNLOAD")){
			File tempDownload = new File("/shared/" + fName);
			String send = "";

			try{
				
				if(tempDownload.exists()){
				Scanner sc = new Scanner(tempDownload);

				while (sc.hasNextLine())
				{
					send += sc.nextLine();
				}
			
				out.println("Downloaded.");
			}
				else{
					out.println("File does not exist.");
				}

			}
			catch(IOException e){
				out.println("Error in download.");
			}
			out.println("Download");
			return true;
		}
		else{
			out.println("Invalid Command");
			return true;
		}
	}
}