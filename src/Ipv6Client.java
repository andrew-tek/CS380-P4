//Andrew Tek
//Project 4
//CS 380
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Ipv6Client {
	static int MINSIZE = 40;
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("codebank.xyz", 38004)) {
			OutputStream out = socket.getOutputStream();
			System.out.println("Connected to server...");
			System.out.println("Source Address: " + socket.getRemoteSocketAddress().toString());
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
			int data = 1;
			for (int i = 0; i < 12; i++) {
				data *= 2;
				int size = MINSIZE + data;
				byte [] packet = new byte [size];
				//Version/Traffic Class
				packet [0] = 0x6 << 4;
				packet[1] = 0;
				//Flow Label
				packet[2] = 0;
				packet[3] = 0;
				//Payload Length
				packet[4] = (byte) ((data >>> 8) & 0xFF);
				packet[5] = (byte) (data & 0xFF);
				//Next Header
				packet[6] = 0x11;
				//Hop limit
				packet[7] = 20;
				//Source Address 8-17 are all 0
				packet[18] = (byte) 0xFF;
				packet[19] = (byte) 0xFF;
				packet[20] = 127;
				packet[21] = 0;
				packet[22] = 0;
				packet[23] = 1;
				//Destination Address 24-33 are all 0
				packet[34] = (byte) 0xFF;
				packet[35] = (byte) 0xFF;
				packet[36] = (byte) 52;
				packet[37] = (byte) 37;
				packet[38] = (byte) 88;
				packet[39] = (byte) 154;
				out.write(packet);
				System.out.println("Packet: " + (i + 1));
				System.out.println("Data Length: " + data);
				for (int l = 0; l < 4; l++) {
					
					System.out.printf("%x",is.read());
				}
				System.out.println();
				
				
			}
			
			
			
		}
		catch (Exception e) {
			System.out.println("Unable to connect to server");
		}
		
	}
	
}

