import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Class Name    :MultiCastNodeListener 
 * Functions     : checkTime , setOperations , run
 * Description   : defining the above functions
 * 
 */


public class MultiCastListener  implements Runnable{
	
	
	 //creating a TreeSet to store the ip address
	
	private  TreeSet<String> set = new TreeSet<String>();
	  
	 //creating a TreeMap to have ip and time inorder to find who left the group
	  
	private  TreeMap<String, String> tmap =  new TreeMap<String, String>(); 
	
	 //declaring variables to calculate the time   
	  
	  int second, minute, hour;
	  boolean val=false;
	  
	 //Base conditions

	  int mcastPort = 0;

	  InetAddress mcastAddr = null;

	  InetAddress localHost = null;

	  
	  //Constructor to assign the values 
	  
	  public MultiCastListener(int port, InetAddress addr) {

	    mcastPort = port;

	    mcastAddr = addr;
	   
	    //handling the exception  

	    try {

	      localHost = InetAddress.getLocalHost();

	    } catch (UnknownHostException uhe) {

	      System.out.println("Problems identifying local host");

	      uhe.printStackTrace();  System.exit(1);

	    }
	    
	    

	  }
	  
 /*
  * Function Name : checkTime
  * Description   : To find the difference between the current time and the values in the map which has the 
  *                 updated time of the each node
  * 
  */

 
	  
	  
	  public void checkTime(String time1,String ipAddress,DatagramPacket recv) throws UnknownHostException, IOException 
	  {
		 
	  GregorianCalendar date = new GregorianCalendar();
		
		second = date.get(Calendar.SECOND);
	   minute = date.get(Calendar.MINUTE);
	   hour = date.get(Calendar.HOUR);

		
		String time= hour+":"+minute+":"+second ;
		
		 String ip=ipAddress.substring(1,	ipAddress.length());
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1;
		try {
			date1 = format.parse(time);
			Date date2 = format.parse(time1);
			long difference = date1.getTime() - date2.getTime();
			long result=difference/1000;
			if(result>5)
			{
				System.out.println("&&&&&&&&& result : "+result);
				try {
					sendPingRequest(ip);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				val=true;
				setOperations(recv,val);
				deleteKey(ipAddress);
				System.out.println(ipAddress+" left the group");
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 } 

	  
	  
	  
	  
 /*  
  * Function Name : sendPingRequest
  * Description   : To know if the host is reachable or not 
  * 
  */
	  
	  
	  public static boolean sendPingRequest(String ipAddress) 
	           throws UnknownHostException, IOException 
	{ 
		 
	 InetAddress geek = InetAddress.getByName(ipAddress); 
	// System.out.println("Sending Ping Request to " + ipAddress); 
	 
	 if (geek.isReachable(1000)) 
	 {
	// System.out.println(ipAddress+" reachable");
	   return true; }
	 else
	 { System.out.println("Sorry ! We can't reach to this host"+ipAddress +" LEFT THE GROUP");
	
	 return false;}
	} 
	
	  
	  
	  
	  
	  
/*
 * Function Name : setOperations
 * Description   : To store and remove the ip when they join  and  leave from the multicast group,
 * 				   thus gives the number of active members
 * 
 */
	  
	  
 
	  
	  
	  
	  public void setOperations(DatagramPacket recv,boolean val) throws UnknownHostException, IOException
	  {
		  set.add(recv.getAddress().toString());
	         
	        
          
          System.out.println("ip stored in the set are"+set);
  	    
    	  System.out.println("No. of active members : "+set.size());
    	  
    	  Iterator setValue = set.iterator(); 
    	  
        
          while (setValue.hasNext()) { 
        	  
        	
        	  String str=(String) setValue.next();
        	  String ip=str.substring(1,	str.length());
        	  if(val)
        	  {
        		  setValue.remove();
        	  }
      		  if( ! sendPingRequest(ip))
      		  {
      			  setValue.remove();
      	
      		//	System.out.println(set);
      		//	System.out.println(set.size());
      		  }
      		  
      				  
      		  
      	
          } 

	  }
	  
	  
	 public void deleteKey(String packet)
	 {
			
		   	/*Set<String> set = tmap.keySet();
		    
		    for(String values: set){
		      
		      if(values.contains(packet)){
		        tmap.remove(values);
		        System.out.println("DELETE KEYSSSSSSSSSSSSSSSSSSSSS");
		        System.out.println(tmap);
		      }
		    }
*/
		System.out.println("before "+tmap); 
		 
		 Iterator<String> iterator = tmap.keySet().iterator();
		    
		    while(iterator.hasNext()){
		      String certification = iterator.next();
		      if(certification!=null)
		      {
		      if(certification.contains(packet)){
		        iterator.remove();
		      }
		      }
		    }


		System.out.println("after"+tmap);
	 }
	  
	  
/*
 * Function Name : run
 * Description   : setup and data transfer
 */

	  
	  
	  public void run() {
		

	    MulticastSocket mSocket = null;
	   
	    //handling the exception

	    try {
	    
	    	
	      System.out.println("Setting up multicast receiver");
	      
	      //creating a multicast  scoket

	      mSocket = new MulticastSocket(mcastPort);
	      
	      //joining the group

	      mSocket.joinGroup(mcastAddr);

	    } catch(IOException ioe) {

	      System.out.println("Trouble opening multicast port");

	      ioe.printStackTrace();      System.exit(1);

	    }

	    //creating a packet to transmit data
	    
	    DatagramPacket packet;

	    System.out.println("Multicast receiver set up ");
	    
	    //initializing a timer
	    
	    int timer=0;
	    
	    
	    while (true) {
	    	
	    	timer++;

	      try {
	    	  
	    	  //buffer to store the data
	    	  
	    	  byte[] buf1 = new byte[1000];
	 		
	    	  DatagramPacket recv = new DatagramPacket(buf1, buf1.length);
	    	  
	    	  //receiving the packets
	 		  
	    	  mSocket.receive(recv);
	 		 
	 		
	 		 
	 		 String msg1=new String(recv.getData(),
	 		 
	 	     recv.getOffset(),recv.getLength());
	 		         
	 		         
	 		     
	 		         
	 	   System.out.println("Multicast UDP message received from "+recv.getAddress()+"  is  "+msg1);
	 		 

	        byte[] buf = new byte[1000];

	        packet = new DatagramPacket(buf,buf.length);

	      
	        mSocket.receive(packet);
	        
	        //serializing the packet
	       
	        ByteArrayInputStream bistream =  new ByteArrayInputStream(packet.getData());

	        ObjectInputStream ois = new ObjectInputStream(bistream);

	        Integer value = (Integer) ois.readObject();



	        // ignore packets from myself, print the rest

	        if (!(packet.getAddress().equals(localHost))) {

	          System.out.println("Received multicast packet: "+  value.intValue() + " from: " + packet.getAddress());
	         
	          //calling the setOperations method to store the ip 
	          
	          setOperations(recv,false);
	        } 
	        
	        
	        //calculating the current time
	        

	        GregorianCalendar date = new GregorianCalendar();
	    	
	        second = date.get(Calendar.SECOND);
	        
	        minute = date.get(Calendar.MINUTE);
	        
	        hour = date.get(Calendar.HOUR);
	   
	    	
	    	String time= hour+":"+minute+":"+second ;
	    	
	    	//storing the ip and their corresponding time in the map 
	    	
	    	tmap.put(packet.getAddress().toString(), time);
	    	
	    	System.out.println("VALUES IN HASH MAP ARE ");
	    	
	    	//iterating the hashMap 
	    	
	   	for (Map.Entry m:tmap.entrySet()) {
	    		
	            System.out.println("ip" + m.getKey() +  
	            		
	                               " time " + m.getValue()); 
	            
	           
	      } 
	   	
	   	
	   	
	   	
	  

	        //conditional loop to check if the node  still exists in the group foe every 5 seconds
	    	
	        if(timer==5)
	        {
	        	for (Map.Entry m:tmap.entrySet()) {
	        		
	        		//calling checkTime method to know if packets are transmitted at a given interval of time
	        		
		           checkTime(m.getValue().toString(),m.getKey().toString(),recv);
		          
		           
		         //  System.out.println(m.getValue().toString()+"  ***************OK****************  "+m.getKey().toString());
		      } 
	        
	        	timer=0;
	        }
	        
	        //closing the byte input stream

	        ois.close();
	        
	        // closing the byte input stream

	        bistream.close();
	      
	      } 
	      
	      //handling the exception
	      
	      catch(IOException ioe) {

	        System.out.println("Trouble reading multicast message");

	        ioe.printStackTrace();  System.exit(1);

	      } catch (ClassNotFoundException cnfe) {

	        System.out.println("Class missing while reading mcast packet");

	        cnfe.printStackTrace(); System.exit(1);

	      }

	    }
	 
	    

	  }

	}
