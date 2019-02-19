package com.infinite.dstask.main;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String staffId;
	private String name;
	private String dept;
	private String designation;
	private Integer salary;
	private String level;
	private ArrayList<StaffFile> staffList = new ArrayList<>();

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public StaffFile(String staffId, String name, String dept, String designation, Integer salary, String level) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.dept = dept;
		this.designation = designation;
		this.salary = salary;
		this.level = level;
	}

	public StaffFile() {
		super();
	}

	@Override
	public String toString() {
		return "StaffFile [staffId=" + staffId + ", name=" + name + ", dept=" + dept + ", designation=" + designation
				+ ", salary=" + salary + ", level=" + level + "]";
	}

	/* Store the List Objects in file */
	public void storeObject(StaffFile staff) {

		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream("/home/harini/Documents/StaffFile.txt", true);
			//objOps = new ObjectOutputStream(ops);
			AppendingObjectOutputStream appendingObjectOutputStream ;
			objOps = new AppendingObjectOutputStream(ops);
			
			objOps.writeObject(staff);
			
			//objOps.flush();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOps != null)
					objOps.close();
			} catch (Exception ex) {

			}
		}

	}

	/* Retrieve the values from the file */
	public void readObjects() throws FileNotFoundException, IOException, ClassNotFoundException {
	ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("/home/harini/Documents/StaffFile.txt"));

	Object obj = null;
    while ((obj = inputStream.readObject()) != null) {
      if (obj instanceof StaffFile) {
        System.out.println(((StaffFile) obj).toString());
       
      }
    }
    inputStream.close();
  }

	/*public static ArrayList<StaffFile> readObjects() throws FileNotFoundException{
		FileInputStream fis = new FileInputStream("/home/harini/Documents/StaffFile.txt");
		ArrayList<StaffFile> objectsList = new ArrayList<StaffFile>();
		boolean cont = true;
		try{
		   ObjectInputStream input	=   new ObjectInputStream(fis);
		  
		   while(cont){
			  
		      Object obj = input.readObject();
		     //System.out.println(obj.toString());
		      if(obj != null)
		         objectsList.add((StaffFile) obj);
		      else
		         cont = false;
		   }
		}catch(Exception e){
		 e.printStackTrace();
		}
		return objectsList;
	}*/
	/*private static List readObjects() throws IOException, ClassNotFoundException {
	    List objects = new ArrayList();
	    InputStream is = null;
	    try {
	      is = new FileInputStream("/home/harini/Documents/StaffFile.txt");
	      ObjectInputStream ois = new ObjectInputStream(is);
	      while (true) {
	        try {
	          Object object = ois.readObject();
	          objects.add(object);
	        } catch (EOFException ex) {
	          break;
	        }
	      }
	    } finally {
	      if (is != null) {
	        is.close();
	      }
	    }
	    return objects;
	  }*/
	/*public void displayObjects() throws IOException{
        
       InputStream fileIs = null;
        ObjectInputStream objIs = null;
        ObjectInputStream inputStream;
		try {
			inputStream = new ObjectInputStream(new FileInputStream("/home/harini/Documents/StaffFile.txt"));
			Object obj = null;

	        while ((obj = inputStream.readObject()) != null) {
	          if (obj instanceof StaffFile) {
	            System.out.println(((StaffFile) obj));
	          }
	        }
	       // inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
      }*/
	/*String fileName = "/Users/pankaj/source.txt";
	File file = new File(fileName);
	FileInputStream fis = new FileInputStream(file);
	InputStreamReader isr = new InputStreamReader(fis);
	BufferedReader br = new BufferedReader(isr);

	String line;
	while((line = br.readLine()) != null){
	     //process the line
	     System.out.println(line);
	}
	br.close();

	}*/



	/* Add the staff in List */
	public boolean addStaff(StaffFile staffObj) {
		boolean res = false;
		try {
			staffList.add(staffObj);
			storeObject(staffObj);
			res = true;
		} catch (Exception e) {
			System.out.println(e);
			return res;
		}
		return res;

	}

	/* Display the details by Id */
	public StaffFile getStaffById(String id) {
		for (StaffFile stafffile : staffList) {
			if (id.equals(stafffile.getStaffId())) {
				return stafffile;
			}
		}
		return null;
	}

	/* Update StaffDetails by Id */
	public String updateStaff(String id, StaffFile staff) {
		StaffFile staff2 = getStaffById(id);
		if (staff2 == null) {
			return null;
		} else {
			int index = staffList.indexOf(staff2);
			staffList.set(index, staff);
			return "updated successfully";
		}

	}

	/* Delete the StaffDetails by Id */
	public String deleteStaffById(String id) {
		StaffFile file = getStaffById(id);
		if (file == null)
			return "not found";
		else {
			if (staffList.remove(file)) {
				// storeObject(file);
				return "Success";
			} else
				return "fail";
		}
	}


	 

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		boolean check = true;
		StaffFile StaffFiles = new StaffFile();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		while (check) {
			System.out.println("1.Add Staff \n2.Delete Staff \n3.Display \n4.update\n5.Display from file \n6.exit");
			int choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:

				System.out.println("Enter StaffId : ");
				String staffId = sc.nextLine();
				System.out.println("Enter StaffName : ");
				String name = sc.nextLine();
				System.out.println("Enter Dept : ");
				String dept = sc.nextLine();
				System.out.println("Enter Salary : ");
				Integer salary = sc.nextInt();
				System.out.println("Enter designation: ");
				String designation = sc.next();
				System.out.println("Enter level: ");
				String level = sc.next();
				StaffFile staff = new StaffFile(staffId, name, dept, designation, salary, level);
				if (StaffFiles.addStaff(staff))
					System.out.println("Success");
				break;
			case 2:
				System.out.println("Enter the id to be deleted");
				String delid = sc.nextLine();

				String res1 = StaffFiles.deleteStaffById(delid);
				System.out.println(res1);
				break;
			case 3:
				System.out.println("Enter id to be display");

				String gid = sc.nextLine();
				StaffFile staff2 = StaffFiles.getStaffById(gid);
				System.out.println(staff2);

				break;

			case 4:
				System.out.println("Enter the id to update");
				String uId = sc.nextLine();
				System.out.println("Enter StaffName : ");
				String uname = sc.nextLine();
				System.out.println("Enter Dept : ");
				String udept = sc.nextLine();
				System.out.println("Enter Salary : ");
				Integer usalary = sc.nextInt();
				System.out.println("Enter designation: ");
				String udesignation = sc.next();
				System.out.println("Enter level: ");
				String ulevel = sc.next();
				staff = new StaffFile(uId, uname, udept, udesignation, usalary, ulevel);
				System.out.println(StaffFiles.updateStaff(uId, staff));
				break;

			case 5:
				//StaffFiles.displayObjects();
				StaffFiles.readObjects();
				break;

			case 6:
				System.exit(0);

			}
			System.out.println("Do you want to continue : Press yes to continue ");
			String cont = sc.next();
			check = cont.equalsIgnoreCase("yes");
		}

	}

}
