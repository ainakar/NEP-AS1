package test07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null; 
		DataInputStream fromClient = null;
		DataOutputStream toClient = null;

		try {
			serverSocket = new ServerSocket(9101);
			System.out.println("Server has Started.");

			while(true) {
				socket = serverSocket.accept();
				System.out.println("Client has connected.");

//				new Thread (new HandleAClient(socket)).start();
				
				fromClient = new DataInputStream(socket.getInputStream());
				toClient = new DataOutputStream(socket.getOutputStream());

				int userScore;
				int cpuScore;
				int userOverall;
				int cpuOverall;
				
				String name = fromClient.readUTF();
//				System.out.println(name);
				
				while(true) {
					int rounds = fromClient.readInt();

					userScore = 0;
					cpuScore = 0;
					userOverall = 0;
					cpuOverall = 0;		
					int results = 0;

					while(rounds > 0) {
						int user = fromClient.readInt();

						int cpu = (int)(Math.random()*100 % 3);

						if (user == 0 && cpu == 0) { //rock option
							results = 0;
							System.out.println("The computer is rock. " + name + " is rock. It is a draw.");
						}
						if (user == 0 && cpu == 1) {
							results = -1;
							cpuScore++;
							System.out.println("The computer is paper. " + name + " is rock. Player lost.");
						}
						if (user == 0 && cpu == 2) {
							results = 1;
							userScore++;
							System.out.println("The computer is scissors. " + name + " is rock. Player won.");
						}
						if (user == 1 && cpu == 1) { //paper option
							results = 0;
							System.out.println("The computer is paper. " + name + " is paper. It is a draw.");
						}
						if (user == 1 && cpu < 1) {
							results = 1;
							userScore++;
							System.out.println("The computer is rock. " + name + " is paper. Player won.");
						}
						if (user == 1 && cpu > 1) {
							results = -1;
							cpuScore++;
							System.out.println("The computer is scissors. " + name + " is paper. Player lost.");
						}
						if (user == 2 && cpu == 2) { //scissors option
							results = 0;
							System.out.println("The computer is scissors. " + name + " is scissors. It is a draw.");
						}
						if (user == 2 && cpu == 0) {
							results = -1;
							cpuScore++;
							System.out.println("The computer is rock. " + name + " is scissors. Player lost.");
						}
						if (user == 2 && cpu == 1) {
							results = 1;
							userScore++;
							System.out.println("The computer is paper. " + name + " is scissors. Player won.");
						}

						toClient.writeInt(results);

						rounds--;
					}
					
					int temp = 0;
					int temp1 = 0;
					
					userOverall = userScore + temp;
					cpuOverall = cpuScore + temp1;

					System.out.println("\r\n" + name + " scored " + userScore + ". CPU scored " + cpuScore + ".\r\n");

					toClient.writeInt(userScore);
					toClient.writeInt(cpuScore);
					
					
					boolean wantContinue = fromClient.readBoolean();
					if(!wantContinue) {
						break;
					}
				}
				
				int finalUser = userOverall;
				int finalComp = cpuOverall;
				
				toClient.writeInt(finalUser);
				toClient.writeInt(finalComp);
								
			}
							
		} catch (IOException e) {
			e.printStackTrace();

		}  finally {

			try {

				if(serverSocket != null) {
					serverSocket.close();
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
