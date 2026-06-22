package StudentCRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Complete_CRUD {
	private static final String URL = "jdbc:mysql://localhost:3306/jdbc_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	public static void main(String[] args) {
		Scanner sc = null;

		try {
			sc = new Scanner(System.in);
			while (true) {
				System.out.println("========WELCOME TO STUDENT MANAGEMENT SYSTEM=========");

				System.out.println("Enter 1: Insert Student Data");
				System.out.println("Enter 2: Update Student Data");
				System.out.println("Enter 3: Delete Student Data");
				System.out.println("Enter 4: GetAll Student Data");

				System.out.println("==================================================");

				int input = sc.nextInt();

				switch (input) {
				case 1:
					// InsertData(sc);
					insertData(sc);
					break;
				case 2:
					// UpdateData(sc);
					updateName(sc);
					break;
				case 3:
					// DeleteData(sc);
					deleteDataById(sc);
					break;
				case 4:
					// GetData(sc);
					getAllData(sc);
					break;

				default:
					System.out.println("Invalide input");
					System.exit(0);
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	// Insert data method
	public static void insertData(Scanner sc) throws SQLException {
		String sql = "insert into student values(?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);

		) {
			// we will take input from user
			System.out.println("Enter User id");
			int id = sc.nextInt();

			System.out.println("Enter User Name");
			String name = sc.next();

			System.out.println("Enter User City");
			String city = sc.next();

			System.out.println("Enter User Address");
			String address = sc.next();

			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, city);
			ps.setString(4, address);

			ps.execute();
			System.out.println("Data Insert SuccessFully...!");
		}

	}

	// Update data method
	public static void updateName(Scanner sc) throws SQLException {
		System.out.println("What do you want to update?");
		System.out.println("1. Name");
		System.out.println("2. City");
		System.out.println("3. Address");

		int choice = sc.nextInt();

		System.out.println("Enter student id:");
		int id = sc.nextInt();

		sc.nextLine();

		String sql = "";
		String newValue = "";

		switch (choice) {
		case 1:
			sql = "update student set name=? where id=?";
			System.out.println("Enter New Name");
			newValue = sc.nextLine();
			break;

		case 2:
			sql = "update student set city=? where id=?";
			System.out.println("Enter New City");
			newValue = sc.nextLine();
			break;

		case 3:
			sql = "update student set address=? where id=?";
			System.out.println("Enter New Address");
			newValue = sc.nextLine();
			break;

		default:
			System.out.println("Invalide Choice...");
			return;
		}

		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, newValue);
			ps.setInt(2, id);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Data Updated Successfully...!");
			} else {
				System.out.println("Student Id Not Found...");
			}
		}
	}

	// delete by id
	public static void deleteDataById(Scanner sc) throws SQLException {
		String sql = "delete from student where id=?";

		System.out.println("Enter id whose data u want to delete..!");
		int id = sc.nextInt();

		sc.nextLine();

		System.out.println("Are you sure to delete? yes/no");
		String comfirm = sc.nextLine();

		if (comfirm.equalsIgnoreCase("yes")) {
			try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, id);

				int rows = ps.executeUpdate();

				if (rows > 0) {
					System.out.println("Data deleted Successfully..!");
				} else {
					System.out.println("Student Id Not Found...");
				}
			}
		} else {
			System.out.println("Delete Operation Cancelled...");
		}
	}
	//Get All Data
	
	public static void getAllData(Scanner sc) throws SQLException {
		String sql = "select * from student";
		
		try(
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PreparedStatement ps = connection.prepareStatement(sql);
				){
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String city = rs.getString(3);
				String address = rs.getString(4);
				
				System.out.println(id+" "+name+" "+city+" "+address);
			}
		}
	}
}
