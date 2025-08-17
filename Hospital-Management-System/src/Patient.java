import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.next();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        System.out.print("Enter patient gender: ");
        String gender = scanner.next();

        String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Patient added successfully!");
            } else {
                System.out.println("Failed to add patient.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Patients:");
            System.out.println("+--------+----------------------+---------+----------+");
            System.out.println("|   ID   |     Patient Name     |   Age   |  Gender  |");
            System.out.println("+--------+----------------------+---------+----------+");
            while (rs.next()) {
                System.out.printf("|%-8d|%-22s|%-9d|%-10s|\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"));
            }
            System.out.println("+--------+----------------------+---------+----------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
