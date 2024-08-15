package TASK5;

public class Grade {
    private int id;
    private int studentId;
    private String courseName;
    private String grade;

    public Grade(int id, int studentId, String courseName, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.courseName = courseName;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getGrade() {
        return grade;
    }
}
