import java.io.*;
import java.net.*;
/*
 * Class Name    : MultiCastNodeNoitifier
 * Function Name : run
 * Description   : creating a socket and nofity the  data
 * Author        : Pavithra T
 * 
 */

public class MultiCastNodeNotifier implements Runnable{
	
     //Base Condition
	 
	  private DatagramSocket dgramSocket = null;

	  int mcastPort = 0;

	  InetAddress mcastAddr = null;

	  InetAddress localHost = null;


	  //Constructor to initialize the values
	  

	  MultiCastNodeNotifier (int port, InetAddress addr) {

	    mcastPort = port;

	    mcastAddr = addr;
	    //creating a socket

	    try {

	      dgramSocket = new DatagramSocket();

	    } 
	    
	    //handling exception
	    
	    catch (IOException ioe){

	      System.out.println("problems creating the datagram socket.");

	      ioe.printStackTrace(); 
	      
	      System.exit(1);

	    }

	    try {
	    	
	    	//fetch the local host address

	      localHost = InetAddress.getLocalHost();

	    } catch (UnknownHostException uhe) {

	      System.out.println("Problems identifying local host");

	      uhe.printStackTrace();  System.exit(1);

	    }



	  }



	  public void run() {
	    DatagramPacket packet = null;
	    int count = 0;
	    // send multicast msg once per second 
	    while (true) {
	      // careate the packet to sned.
	      try {
	        // serialize the multicast message
	    	  
	    	  
	    	  String msg="this is pavithra";
	    	  
	    	  DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),   mcastAddr,  mcastPort);
	    	  dgramSocket.send(hi);
	   	  
	       ByteArrayOutputStream bos = new ByteArrayOutputStream();
	       ObjectOutputStream out = new ObjectOutputStream (bos);
	       out.writeObject(new Integer(count++));
	       //out.writeObject(new String(msg));
	       out.flush();
	       out.close();
	       
	        // Create a datagram packet and send it

	        packet = new DatagramPacket(bos.toByteArray(), bos.size(), mcastAddr, mcastPort);

	        // send the packet

	        dgramSocket.send(packet);

	        System.out.println("sending multicast message");

	        Thread.sleep(1000);

	      } catch(InterruptedException ie) {

	        ie.printStackTrace();

	      } catch (IOException ioe) {

	        System.out.println("error sending multicast");

	        ioe.printStackTrace(); System.exit(1);

	      }

	    }

	  }
	}

