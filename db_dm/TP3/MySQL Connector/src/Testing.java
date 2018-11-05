import java.sql.*;

class Testing {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dba_dm",
                "root", "");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT factorial(5) AS Value");
        resultSet.next();
        String result = resultSet.getString("Value");

        System.out.println("The result is " + result);
    }
}
