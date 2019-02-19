package com.infinite.main;

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
	 static ArrayList<StaffFile> staffList = new ArrayList<>();

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



	
	public static void Serialize()
	{
	
	
	// Let's serialize an Object
			try {
				FileOutputStream fileOut = new FileOutputStream("/home/pavithra/Documents/File.txt");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(staffList);
				out.reset();
				out.close();
				fileOut.close();
				System.out.println("\nSerialization Successful... Checkout your specified output file..\n");
	 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static  void deserialize()
	{
			// Let's deserialize an Object
		try
        {
            FileInputStream fis = new FileInputStream("/home/pavithra/Documents/File.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
 
            staffList = (ArrayList<StaffFile>) ois.readObject();
            System.out.println("deserialized***********");
            System.out.println(staffList.toString());
 
            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
         
      
	}

	
	/* Add the staff in List */
	public boolean addStaff(StaffFile staffObj) {
		boolean res = false;
		try {
			staffList.add(staffObj);
			//storeObject(staffObj);
			Serialize();
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
				Serialize();
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
				//StaffFiles.readObjects();
				StaffFiles.deserialize();
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
