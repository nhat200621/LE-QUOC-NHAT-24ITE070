package com.example.manager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
class Student {
    private String fullName;
    private int birthYear;
    private String studentClass;
    private String email;
    private String studentId;

    public Student(String fullName, int birthYear, String studentClass) {
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.studentClass = studentClass;
        this.email = generateEmail();
        this.studentId = generateStudentId();
    }
    private String generateEmail() {
        String[] nameParts = fullName.split(" ");
        StringBuilder email = new StringBuilder();
        email.append(nameParts[nameParts.length - 1].toLowerCase()); // Append last name
        for (int i = 0; i < nameParts.length - 1; i++) {
            email.append(nameParts[i].toLowerCase().charAt(0)); // Append initials
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int graduationYear = (birthYear + 18 <= currentYear) ? currentYear - birthYear + 18 : 24;
        email.append(".").append(graduationYear).append(studentClass.toLowerCase()).append("@vku.udn.vn");
        return email.toString();
    }
    private String generateStudentId() {
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int graduationYear = (birthYear + 18 <= currentYear) ? currentYear - birthYear + 18 : 24;
        return graduationYear + studentClass + randomNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getStudentId() {
        return studentId;
    }
    public String toString() {
        return "Full Name: " + fullName + "\n" +
                "Birth Year: " + birthYear + "\n" +
                "Class: " + studentClass + "\n" +
                "Email: " + email + "\n" +
                "Student ID: " + studentId + "\n";
    }
}
public class StudentManagement extends Application {
    private List<Student> students = new ArrayList<>();
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        TextField birthYearField = new TextField();
        birthYearField.setPromptText("Birth Year");
        TextField classField = new TextField();
        classField.setPromptText("Class");
        Button addButton = new Button("Add Student");
        Button viewButton = new Button("View All Students");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        addButton.setOnAction(e -> {
            try {
                String fullName = fullNameField.getText();
                int birthYear = Integer.parseInt(birthYearField.getText());
                String studentClass = classField.getText();
                if (fullName.isEmpty() || studentClass.isEmpty()) {
                    outputArea.setText("Please fill in all fields.");
                    return;
                }

                Student student = new Student(fullName, birthYear, studentClass);
                students.add(student);
                outputArea.setText("Student added successfully!\n" + student);
                fullNameField.clear();
                birthYearField.clear();
                classField.clear();
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input for birth year.");
            }
        });
        viewButton.setOnAction(e -> {
            if (students.isEmpty()) {
                outputArea.setText("No students available.");
            } else {
                StringBuilder allStudents = new StringBuilder("All Students:\n\n");
                for (Student student : students) {
                    allStudents.append(student).append("\n-------------------\n");
                }
                outputArea.setText(allStudents.toString());
            }
        });
        HBox buttons = new HBox(10, addButton, viewButton);
        buttons.setPadding(new Insets(10, 0, 0, 0));
        root.getChildren().addAll(fullNameField, birthYearField, classField, buttons, outputArea);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Student Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
