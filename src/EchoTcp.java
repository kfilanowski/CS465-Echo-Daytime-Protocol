import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This class tests the Echo protocol with user input to a server and a port.
 * Uses a TCP connection.
 * @author Kevin Filanowski
 * @version Oct 1, 2018
 */
public class EchoTcp {
	/** The port number to access, by default it will be 7 (RFC Standard). */
	private int portNumber;
    /** Server to connect to. */
    private String server;
    
    /**
     * Constructor to initialize EchoTcp Object, using both a specified
     * server and an optional argument for port.
     * @param server The server address to connect to.
     * @param portNumber The port number to connect to.
     */
    public EchoTcp(String[] args) {
    	// Mandatory command line argument, the host name.
        if (args.length < 1) {
            System.out.println("Usage: java EchoTcp host_name [Port #]");
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
            // Set the default port to 7 if user has not specified one.
            this.portNumber = 7;
        }
        this.server = server;
    }
    
	/**
	 * The main operator for the class. This will open a socket and
	 * setup a tcp connection to send an echo to a specified server.
	 * The program will also print the response back.
	 * @param args The command line arguments.
	 */
    public void go(String[] args) {
    	// Try with resource, so closable things will auto-close.
        try (
        	/** Socket to open up a connection with the server. */
        	Socket myClient = new Socket(InetAddress.getByName(args[0]),
        					  portNumber);
        	/** PrintWriter to write to the server. */
            PrintWriter writer = new PrintWriter(myClient.getOutputStream());
            /** BufferedReader to read the response from the server. */
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            						myClient.getInputStream()));
        	){
        	
        	// Set a timeout in case we get no response.
        	myClient.setSoTimeout(500);
        	
        	// Read the user input.
            String sentence = readUserInput();
			// Write that user input to the server.
			writer.println(sentence);
            writer.flush();
            
            // Print the response from the server.
            System.out.println(reader.readLine());
            
            // Exception catching.
        } catch (UnknownHostException ex) {
			System.out.println("Unknown host: " + args[0]);
		} catch (SocketException ex) {
			System.out.println("Unable to open socket or bind to the port.");
		} catch (SocketTimeoutException ex) {
             System.out.println("Timeout, took too long");
        } catch (IOException ex) {
			System.out.println("An I/O error occured when creating this "
								+ "socket.");
        } catch (SecurityException ex) {
			System.out.println("The security of the system blocked out "
								+ "connection.");
		} catch (IllegalArgumentException ex) {
			System.out.println("Please specify a port between 0 and 65535, "
								+ "inclusive.");
		}
    }
    
    /**
	 * This function simply requests a line of user input from the command
	 * line and returns the user's response.
	 * @return The input from the command line.
	 */
    public String readUserInput() {
    	System.out.println("Enter a sentence > ");
    	// Initialize Scanner.
        Scanner in = new Scanner(System.in);
        // Read input.
        String str = in.nextLine();
        // Close and return input.
        in.close();
        return str;
    }

    /**
     * Main driver of the program.
     * @param args Command line arguments.
     */
	public static void main(String[] args) {
		// Create EchoTcp object.   
		EchoTcp echo = new EchoTcp(args);
		// Operate.
		echo.go(args);
	}
}
