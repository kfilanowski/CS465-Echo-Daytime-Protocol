import java.io.*;
import java.net.*;

/**
 * Implements the Daytime Protocol - This class uses UDP to send a packet to
 * the server in which the server will respond with the date and time.
 * @author Kevin Filanowski
 * @version Oct 1, 2018
 **/
public class DaytimeUdp {
	/** The port number to access, by default it will be 13 (RFC Standard). **/
	private int portNumber;
    /** The server to send packets to. */
    private String server;

    public DaytimeUdp(String[] args) {
    	// Mandatory command line argument, the host name.
        if (args.length < 1) {
            System.out.println("Usage: java DaytimeUdp host_name [Port #]");
            System.exit(1);
        }
        // Optional port number argument.
        if (args.length > 1) {
        	try {this.portNumber = Integer.parseInt(args[1]);}
        	catch (NumberFormatException ex) {
        		System.out.println("Invalid port number: " + args[1]
    					+ "\nPlease pick a port between 0 and 65535.");
        		System.exit(1);
        	}
        } else {
            this.portNumber = 13;
        }
        this.server = args[0];
    }
	
	/**
	 * The main operator of the program. This will open a datagram socket and
	 * send UDP packets to a server, in which we will print the response
	 * which will be the date and time.
	 * @param args The command line arguments.
	 */
	public void go(String[] args) {
    	// Try with resource, so closable things will auto-close.
        try (
			/** Open a datagram socket for sending packets **/
			DatagramSocket myClient = new DatagramSocket();
        	){

            // Retrieve the InetAddress of the host.
            InetAddress ia = InetAddress.getByName(args[0]);
              
            // Create an empty Datagram packet to send to the server.
            byte[] sendBits = ("").getBytes();
		    DatagramPacket dpSend = new DatagramPacket(sendBits, 
                                        sendBits.length, ia, portNumber);
            
		    // Create a Datagram packet to retrieve the response.
		    byte[] receiveBits = new byte[32];
		    DatagramPacket dpReceive = new DatagramPacket(receiveBits,
		    							   receiveBits.length);

		    // Set a timeout in case we get no response.
            myClient.setSoTimeout(500);
            // Send and receive the packet.
            myClient.send(dpSend);
            myClient.receive(dpReceive);
            
            // Print the data received.
            System.out.println(new String(dpReceive.getData()));
            
		} catch (UnknownHostException ex) {
			System.out.println("Unknown host: " + args[0]);
		} catch (SocketException ex) {
			System.out.println("Unable to open socket or bind to the port.");
		} catch (SocketTimeoutException ex) {
             System.out.println("Timeout, took too long");
        } catch (IOException ex) {
			System.out.println("An I/O error occured when creating this"
								+ " socket.");
        } catch (SecurityException ex) {
			System.out.println("The security of the system blocked out"
								+ " connection.");
		} catch (IllegalArgumentException ex) {
			System.out.println("Please specify a port between 0 and 65535,"
								+ " inclusive.");
		}
	}

	/**
     * Main driver of the program.
     * @param args Command line arguments.
     */
	public static void main(String[] args) {
		// Create a DaytimeUdp object.
		DaytimeUdp daytime = new DaytimeUdp(args);
		// Operate
		daytime.go(args);
	}
}
