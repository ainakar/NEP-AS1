package test07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientApp {
	
	public static void main(String[] args) {
		
		Socket socket = null;
		DataOutputStream toServer = null;
		DataInputStream fromServer = null;
		Scanner scanner = new Scanner(System.in);

		try {
			socket = new Socket( "localhost", 9101); 

			toServer = new DataOutputStream(socket.getOutputStream()); 
			fromServer = new DataInputStream(socket.getInputStream());

//			while(true) {
//				System.out.println("Input Rectangle Length: ");
//				double length = Double.parseDouble(scanner.nextLine()); //capture
//
//				System.out.println("Input Rectangle Breadth: ");
//				double breadth = Double.parseDouble(scanner.nextLine()); //capture
//
//				//			toServer.writeUTF("Hello");
//
//
//				toServer.writeDouble(length); //send
//				toServer.writeDouble(breadth); //send
//
//				double area = fromServer.readDouble(); //read
//				System.out.println("Area: " + area);
//
//				System.out.println("Do you want to continue? y/n");
//				String wantContinue = scanner.nextLine();
//				
//				if(wantContinue.equalsIgnoreCase("y")) {
//					toServer.writeBoolean(true);
//					
//				} else {
//					toServer.writeBoolean(false);
//					break;
//				}
//			}
			
			System.out.print("Please enter your name: ");
			toServer.writeUTF(scanner.nextLine());
			System.out.println();
			
			while(true) {
				System.out.println("How many rounds do you want to play?");
				int rounds = Integer.parseInt(scanner.nextLine());
				toServer.writeInt(rounds);
				System.out.println();

				while(rounds > 0) {
					System.out.print("Enter rock (0), paper (1), scissors (2): ");
					int user = Integer.parseInt(scanner.nextLine());

					toServer.writeInt(user);

					int results = fromServer.readInt();

					if (results > 0) {
						System.out.println("You won!\r\n");
					}
					if (results < 0) {
						System.out.println("You lost.\r\n");
					}
					if (results == 0) {
						System.out.println("It's a draw!\r\n");
					}

					//				System.out.print("Do you want to continue? y/n: ");
					//				String wantContinue = scanner.nextLine();
					//				System.out.println(); //newline
					//				
					//				if(wantContinue.equalsIgnoreCase("y")) {
					//					toServer.writeBoolean(true);
					//				} else {
					//					toServer.writeBoolean(false);
					//					break;
					//				}

					rounds--;
				}

				int userScore = fromServer.readInt();
				int cpuScore = fromServer.readInt();
				System.out.println("You " + userScore + " – " + cpuScore + " CPU\r\n");
								
				System.out.println("Do you want to continue? y/n");
				String wantContinue = scanner.nextLine();
				
				if(wantContinue.equalsIgnoreCase("y")) {
					toServer.writeBoolean(true);
					
				} else {
					toServer.writeBoolean(false);
					break;
				}
				
			}
			
			int cumulativeUser = fromServer.readInt();
			int cumulativeComp = fromServer.readInt();

			System.out.println("\r\nYour overall score is: " + cumulativeUser + ". You lost in total of " + cumulativeComp + " time(s).");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if( socket != null) {
					socket.close();
				}

				if(toServer != null) {
					toServer.close();
				}

				if(fromServer != null) {
					fromServer.close();
				}
				if(scanner != null) {
					scanner.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}

}
