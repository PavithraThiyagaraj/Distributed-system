import java. io.*;
import java.net.*;

/*
 * Class Name    : MultiCastGroup
 * Function Name : Main
 * Description   : Call the Notifier and Listener Thread
 * Author        : Pavithra T
 * 
 */




public class MultiCastGroup {

 
	  public static void main (String[] args) {
		  
		//Listening in the port number 6000

	    int mcastPort = 6000;
	    
	    //Initializing the multicast address

	    InetAddress mcastAddr = null;

	    try {

	      mcastAddr = InetAddress.getByName("230.0.0.1");

	    } catch (UnknownHostException uhe) {

	      System.out.println("Problems getting the multicast address");

	      uhe.printStackTrace(); System.exit(1);

	    }

	    // start new thread to receive multicasts

	    new Thread(new MultiCastNodeListener(mcastPort, mcastAddr), 

	               "MultiCastNodeListener").start();

	    // start new thread to send multicasts

	 new Thread(new MultiCastNodeNotifier(mcastPort, mcastAddr), 

	               "MultiCastNodeNotifier").start();
	
	  }

	}


