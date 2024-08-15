package TASK5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeManager {
    public void addGrade(int studentId, String courseName, String grade) {
        String sql = "INSERT INTO grades (student_id, course_name, grade) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setString(2, courseName);
            pstmt.setString(3, grade);
            pstmt.executeUpdate();
            System.out.println("Grade added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGrade(int id, String courseName, String grade) {
        String sql = "UPDATE grades SET course_name = ?, grade = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName);
            pstmt.setString(2, grade);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Grade updated successfully.");
            } else {
                System.out.println("Grade not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrade(int id) {
        String sql = "DELETE FROM grades WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Grade deleted successfully.");
            } else {
                System.out.println("Grade not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewGrades(int studentId) {
        String sql = "SELECT * FROM grades WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String courseName = rs.getString("course_name");
                    String grade = rs.getString("grade");
                    System.out.println("ID: " + id + ", Course: " + courseName + ", Grade: " + grade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calculateGPA(int studentId) {
        String sql = "SELECT grade FROM grades WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                double totalPoints = 0;
                int count = 0;
                while (rs.next()) {
                    String grade = rs.getString("grade");
                    totalPoints += getGradePoints(grade);
                    count++;
                }
                if (count > 0) {
                    double gpa = totalPoints / count;
                    System.out.println("GPA: " + gpa);
                } else {
                    System.out.println("No grades found for student.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double getGradePoints(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }
}
