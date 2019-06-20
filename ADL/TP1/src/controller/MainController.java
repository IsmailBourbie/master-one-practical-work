package controller;

import connections.ConnectionDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Student;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Student> TableView;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> averageColumn;

    @FXML
    private void insertButton() {
        String query = "insert into student values(" + idField.getText() + ",'" + titleField.getText() + "','" + authorField.getText() + "'," + yearField.getText() + ")";
        executeQuery(query);
        showStudents();
    }


    @FXML
    private void updateButton() {
        String query = "UPDATE student SET nom='" + titleField.getText() + "',prenom='" + authorField.getText() + "',moyenne=" + yearField.getText() + " WHERE ID=" + idField.getText() + "";
        executeQuery(query);
        showStudents();
    }

    @FXML
    private void deleteButton() {
        String query = "DELETE FROM student WHERE id=" + idField.getText() + "";
        executeQuery(query);
        showStudents();
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStudents();
    }

    public Connection getConnection() {
        ConnectionDatabase conn = new ConnectionDatabase();
        conn.connect();
        return conn.connection;
    }

    public ObservableList<Student> getStudentList() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM student";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Student books;
            while (rs.next()) {
                books = new Student(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("moyenne"));
                students.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    public void showStudents() {
        ObservableList<Student> list = getStudentList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        TableView.setItems(list);
    }

}
