package jdbcdemo;


import java.sql.*;
import java.util.Scanner;


public class JdbcProject {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Assuming you have initialized the 'connection' object to establish a database connection

    	Connection connection = null;
    	Statement statement = null;
	

		String url = "jdbc:mysql://localhost:3306/codera";

		String username = "root";
		String password = "Nasiruddin@786";
        try {
        	connection =DriverManager.getConnection(url,username,password);
        	
        	if (connection != null) {
				statement = connection.createStatement();

			}
        	
        	if (statement != null) {

			
        		statement.executeUpdate("CREATE TABLE IF NOT EXISTS stud (id INT PRIMARY KEY, name VARCHAR(50), age INT)");
				
			}

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS student (id INT PRIMARY KEY, name VARCHAR(50), age INT)");
            

            // Create a menu loop
            boolean exit = false;

            while (!exit) {
                // Display the menu options
                System.out.println("---- Student CRUD Menu ----");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                // Read the user's choice
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Create
                        createStudent(statement);
                        break;
                    case 2: // Read
                        readStudent(statement);
                        break;
                    case 3: // Update
                        updateStudent(statement);
                        break;
                    case 4: // Delete
                        deleteStudent(statement);
                        break;
                    case 5: // Exit
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                System.out.println();
            }

            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createStudent(Statement statement) throws SQLException {
        // Read student details from the user
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();

        // Execute the INSERT statement
        String sql = "INSERT INTO stud (id, name, age) VALUES (" + id + ", '" + name + "', " + age + ")";
        int rowsAffected = statement.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("Student record created successfully.");
        } else {
            System.out.println("Failed to create student record.");
        }
    }

    private static void readStudent(Statement statement) throws SQLException {
        // Execute the SELECT statement
        String sql = "SELECT * FROM stud";
        ResultSet resultSet = statement.executeQuery(sql);

        // Display the student records
        System.out.println("---- Student Records ----");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");

            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
        }

        // Close the result set
        resultSet.close();
    }

    private static void updateStudent(Statement statement) throws SQLException {
        // Read student ID from the user
        System.out.print("Enter the student ID to update: ");
        int id = scanner.nextInt();

        // Check if the student record exists
        String checkSql = "SELECT * FROM stud WHERE id = " + id;
        ResultSet checkResultSet = statement.executeQuery(checkSql);

        if (checkResultSet.next()) {
            // Read updated student details from the user
            System.out.print("Enter updated student name: ");
            String name = scanner.next();
            System.out.print("Enter updated student age: ");
            int age = scanner.nextInt();

            // Execute the UPDATE statement
            String updateSql = "UPDATE stud SET name = '" + name + "', age = " + age + " WHERE id = " + id;
            int rowsAffected = statement.executeUpdate(updateSql);

            if (rowsAffected > 0) {
                System.out.println("Student record updated successfully.");
            } else {
                System.out.println("Failed to update student record.");
            }
        } else {
            System.out.println("Student with ID " + id + " does not exist.");
        }

        // Close the result set
        checkResultSet.close();
    }

    private static void deleteStudent(Statement statement) throws SQLException {
        // Read student ID from the user
        System.out.print("Enter the student ID to delete: ");
        int id = scanner.nextInt();

        // Check if the student record exists
        String checkSql = "SELECT * FROM stud WHERE id = " + id;
        ResultSet checkResultSet = statement.executeQuery(checkSql);

        if (checkResultSet.next()) {
            // Execute the DELETE statement
            String deleteSql = "DELETE FROM stud WHERE id = " + id;
            int rowsAffected = statement.executeUpdate(deleteSql);

            if (rowsAffected > 0) {
                System.out.println("Student record deleted successfully.");
            } else {
                System.out.println("Failed to delete student record.");
            }
        } else {
            System.out.println("Student with ID " + id + " does not exist.");
        }

        // Close the result set
        checkResultSet.close();
    }
}
