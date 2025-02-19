import java.io.*;
import java.util.*;

// Student class to represent individual students
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;
    
    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }
    
    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    
    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    
    @Override
    public String toString() {
        return "Student {" +
               "Name='" + name + '\'' +
               ", Roll Number=" + rollNumber +
               ", Grade='" + grade + '\'' +
               '}';
    }
}

// StudentManagementSystem class to manage students
class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";
    
    public StudentManagementSystem() {
        this.students = new ArrayList<>();
        loadStudents();
    }
    
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }
    
    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        saveStudents();
    }
    
    public Student searchStudent(int rollNumber) {
        return students.stream().filter(student -> student.getRollNumber() == rollNumber).findFirst().orElse(null);
    }
    
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            students.forEach(System.out::println);
        }
    }
    
    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }
    
    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }
}

// Main class to interact with the system
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter Roll Number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();
                    
                    sms.addStudent(new Student(name, rollNumber, grade));
                    System.out.println("Student added successfully.");
                    break;
                case 2:
                    System.out.print("Enter Roll Number to remove: ");
                    int removeRoll = scanner.nextInt();
                    sms.removeStudent(removeRoll);
                    System.out.println("Student removed successfully.");
                    break;
                case 3:
                    System.out.print("Enter Roll Number to search: ");
                    int searchRoll = scanner.nextInt();
                    Student found = sms.searchStudent(searchRoll);
                    System.out.println(found != null ? found : "Student not found.");
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
