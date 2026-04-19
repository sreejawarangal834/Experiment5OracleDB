import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Test {

    // METHOD TO GET ORACLE CONNECTION
    public static Connection getConnection() {
        Connection con = null;
        try {
        	Class.forName("oracle.jdbc.driver.OracleDriver");

        con = DriverManager.getConnection(
        	    "jdbc:oracle:thin:@//127.0.0.1:1521/xepdb1",
        	    "system",
        	    "system123"
        	);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    // INSERT
    public static void insertStudent(int id, String name, int age) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO student VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);

            ps.executeUpdate();
            System.out.println("Record Inserted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    public static void viewStudents() {
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " " +
                        rs.getString(2) + " " +
                        rs.getInt(3)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public static void updateStudent(int id, String name) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE student SET name=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, id);

            ps.executeUpdate();
            System.out.println("Record Updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteStudent(int id) {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM student WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Record Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 MAIN METHOD (THIS WAS MISSING)
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Insert 2.View 3.Update 4.Delete 5.Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    insertStudent(id, name, age);
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    System.out.print("ID: ");
                    id = sc.nextInt();
                    System.out.print("New Name: ");
                    name = sc.next();
                    updateStudent(id, name);
                    break;

                case 4:
                    System.out.print("ID: ");
                    id = sc.nextInt();
                    deleteStudent(id);
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }
}
