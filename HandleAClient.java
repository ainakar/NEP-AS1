package test07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleAClient implements Runnable {
	
	Socket socket;
	DataInputStream fromClient;
	DataOutputStream toClient;
	
	public HandleAClient(Socket socket) {
		this.socket = socket;
		
	}
		
	@Override
	public void run() {
	
	try {	
		fromClient = new DataInputStream(socket.getInputStream());
		toClient = new DataOutputStream(socket.getOutputStream());

		while(true) {

			double length = fromClient.readDouble();
			double breadth = fromClient.readDouble();

			System.out.println("Received Length: " + length);
			System.out.println("Received Breadth: " + breadth);

			double area = length * breadth;
			toClient.writeDouble(area);

			boolean wantContinue = fromClient.readBoolean();
			if(!wantContinue) {
				break;
				
			}
			}
		
			} catch (IOException e) {
				e.printStackTrace();

			}  finally {

				try {

					if(socket != null) {
						socket.close();
					}

					if(socket != null) {
						socket.close();
					}

					if(fromClient != null) {
						fromClient.close();
					}

					if(toClient != null) {
						toClient.close();
					}

				}  catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

}
