import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private Connection connection;

    public Doctors(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Doctors:");
            System.out.println("+--------+----------------------+--------------------+");
            System.out.println("|   ID   |      Doctor Name     |   Specialization   |");
            System.out.println("+--------+----------------------+--------------------+");
            while (rs.next()) {
                System.out.printf("|%-8d|%-22s|%-20s|\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialty"));
            }
            System.out.println("+--------+----------------------+--------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
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
