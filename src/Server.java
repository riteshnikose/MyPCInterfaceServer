import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		Server objServer  = new Server();
		objServer.run();
	}
	public void run() throws Exception{
		
		ServerSocket objServerSocket = new ServerSocket(6000);
		Socket scoket = objServerSocket.accept();
		InputStreamReader IR = new InputStreamReader(scoket.getInputStream());
		BufferedReader br = new BufferedReader(IR);
		
		String message = br.readLine();
		System.out.println("Cilent Connection Message "+message);
		 
		if (message != null){
			String intialOut ="Welcome..! Please Enter command...";
			byte[] outByteArry = intialOut.getBytes();
			DataOutputStream dOut = new DataOutputStream(scoket.getOutputStream());

			dOut.writeInt(outByteArry.length); // write length of the message
			dOut.write(outByteArry);           // write the message
			while(true){
				// Server command receiver
				String command = br.readLine();
				if (command != null){
					System.out.println("Client Command recive :"+command);
					String output = executeCommand(command);
					System.out.println("Client output \n :"+output);
					byte[] outPutMessage = output.getBytes();
					dOut.writeInt(outPutMessage.length); // write length of the message
					dOut.write(outPutMessage);  // write the message
				}
			}
		}
		
	}
	
	
	private String executeCommand(String command) {
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

}
