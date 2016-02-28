import java.io.BufferedReader;
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
			PrintStream ps = new PrintStream(scoket.getOutputStream());
			//String output = executeCommand("ls");
			//System.out.println("Client output \n :"+output);
			//ps.println(output);
			ps.println("Welcome..! Please your Enter command...");
			
			while(true){
				// Server command receiver
				String command = br.readLine();
				if (command != null){
					
					System.out.println("Client Command recive :"+command);
					String output = executeCommand(command);
					System.out.println("Client output \n :"+output);
					
					// SERVER sender
					//System.out.print("Enter String\n");
					//BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		            //String s = bufferReader.readLine();
					//ps.println(s);
					
					ps.println(output);
				}
				else{
					break;
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
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

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
