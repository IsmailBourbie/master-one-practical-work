package library;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String average;

    public Student(int id, String firstName, String lastName, String average) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.average = average;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAverage() {
        return average;
    }
}
