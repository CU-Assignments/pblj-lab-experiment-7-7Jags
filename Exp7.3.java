### Instructions for Java JDBC MVC Student Management System  

1. **Setup MySQL Database:**  
   - Install and start MySQL.  
   - Create a database (e.g., `StudentDB`).  
   - Create a table:  
     ```sql
     CREATE TABLE Student (
         StudentID INT PRIMARY KEY,
         Name VARCHAR(100),
         Department VARCHAR(50),
         Marks DOUBLE
     );
     ```

2. **Update Database Credentials:**  
   - Modify `URL`, `USER`, and `PASSWORD` in the code to match your MySQL database credentials.

3. **Add MySQL JDBC Driver:**  
   - Download and add `mysql-connector-java.jar` to your project's classpath.

4. **Compile and Run the Program:**  
   - Compile: `javac StudentManagementApp.java`  
   - Run: `java StudentManagementApp`  

5. **Menu-Driven Operations:**  
   - **Add Student:** Enter StudentID, Name, Department, and Marks.  
   - **View Students:** Displays all students in the table.  
   - **Update Student:** Modify Name, Department, or Marks using StudentID.  
   - **Delete Student:** Remove a student using StudentID.  
   - **Exit:** Quit the program.

6. **Transaction Handling:**  
   - Ensures data integrity by using `conn.setAutoCommit(false)` and `conn.commit()`.  
   - Rolls back changes in case of errors.

7. **Verify Database Changes:**  
   - Use `SELECT * FROM Student;` in MySQL to confirm modifications.


import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "rishuraman1@V";

    // Method to create a new student
    public void createStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");
        }
    }

    // Method to retrieve all students
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                ));
            }
        }
        return students;
    }

    // Method to update student data
    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.setInt(4, student.getStudentID());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found.");
            }
        }
    }

    // Method to delete a student
    public void deleteStudent(int studentID) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID = ?";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found.");
            }
        }
    }
}

