import services.AuthService;
import services.CourseService;
import services.StudentService;
import utils.FileHandler;
import models.Course;
import models.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileHandler fh = new FileHandler();
        CourseService courseService = new CourseService(fh);
        StudentService studentService = new StudentService(fh);
        AuthService auth = new AuthService();

        boolean running = true;
        boolean adminLogged = false;

        while (running) {
            System.out.println("\n====== STUDENT COURSE REGISTRATION (ADVANCED) ======");
            System.out.println("1. Admin Login");
            System.out.println("2. Register Student");
            System.out.println("3. View All Courses");
            System.out.println("4. Student: Add Course");
            System.out.println("5. Student: Drop Course");
            System.out.println("6. View Student Details");
            System.out.println("7. Search Student by Name");
            System.out.println("8. Admin: Add Course (requires login)");
            System.out.println("9. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    adminLogged = auth.login(sc);
                    break;
                case "2":
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine().trim();
                    Student s = studentService.registerStudent(name);
                    System.out.println("Registered: " + s.getId() + " | " + s.getName());
                    break;
                case "3":
                    System.out.println("Courses:");
                    for (Course c : courseService.getAll())
                        System.out.println("  " + c);
                    break;
                case "4":
                    System.out.print("Enter Student ID: ");
                    String sid = sc.nextLine().trim();
                    Student st = studentService.getById(sid);
                    if (st == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("Enter Course ID to add: ");
                    String cid = sc.nextLine().trim();
                    Course cadd = courseService.findById(cid);
                    if (cadd == null) {
                        System.out.println("Course not found.");
                        break;
                    }
                    if (st.getCourses().contains(cid)) {
                        System.out.println("Already registered for this course.");
                        break;
                    }
                    if (!cadd.hasSeat()) {
                        System.out.println("No seats available.");
                        break;
                    }
                    boolean enrolled = courseService.enrollInCourse(cid);
                    if (enrolled && studentService.addCourseToStudent(sid, cid)) {
                        System.out.println("Course added successfully.");
                    } else {
                        System.out.println("Failed to add course.");
                    }
                    break;
                case "5":
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextLine().trim();
                    st = studentService.getById(sid);
                    if (st == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("Enter Course ID to drop: ");
                    String cdrop = sc.nextLine().trim();
                    if (!st.getCourses().contains(cdrop)) {
                        System.out.println("Student not registered in this course.");
                        break;
                    }
                    boolean droppedFromCourse = studentService.dropCourseFromStudent(sid, cdrop);
                    boolean seatFreed = courseService.dropFromCourse(cdrop);
                    if (droppedFromCourse && seatFreed)
                        System.out.println("Course dropped.");
                    else
                        System.out.println("Failed to drop course.");
                    break;
                case "6":
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextLine().trim();
                    st = studentService.getById(sid);
                    if (st == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.println("ID: " + st.getId());
                    System.out.println("Name: " + st.getName());
                    System.out.println("Courses:");
                    if (st.getCourses().isEmpty())
                        System.out.println("  None");
                    else {
                        for (String cc : st.getCourses()) {
                            Course ccObj = courseService.findById(cc);
                            System.out.println("  " + cc + ((ccObj != null) ? (" - " + ccObj.getName()) : ""));
                        }
                    }
                    break;
                case "7":
                    System.out.print("Enter name query: ");
                    String q = sc.nextLine().trim();
                    List<Student> found = studentService.searchByName(q);
                    if (found.isEmpty())
                        System.out.println("No students match.");
                    else {
                        System.out.println("Matches:");
                        for (Student sres : found)
                            System.out.println("  " + sres.getId() + " - " + sres.getName());
                    }
                    break;
                case "8":
                    if (!adminLogged) {
                        System.out.println("Admin login required.");
                        break;
                    }
                    System.out.print("Enter Course ID (e.g., C201): ");
                    String newId = sc.nextLine().trim();
                    System.out.print("Enter Course Name: ");
                    String newName = sc.nextLine().trim();
                    System.out.print("Credits: ");
                    int creds = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Capacity: ");
                    int cap = Integer.parseInt(sc.nextLine().trim());
                    boolean added = courseService.addCourse(newId, newName, creds, cap);
                    System.out.println(added ? "Course added." : "Course ID already exists.");
                    break;
                case "9":
                    System.out.println("Saving data and exiting...");
                    courseService.save();
                    studentService.save();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
