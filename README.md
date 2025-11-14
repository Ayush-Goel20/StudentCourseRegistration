📘 Student Course Registration System
(Java Project with Authentication + File Handling)

This project is a Java-based Student Course Registration System that includes:

Admin authentication

Student registration

Course management

JSON-like file storage

Search & display functionalities

OOP-based structure (AuthService, FileHandler, Student, Course, Main)

It is designed as an intermediate-level Java project, suitable for company application forms, internships, and resume showcases.

🧩 Project Features
🔐 1. Admin Authentication

Login required to access the system

Uses AuthService.java

Default Credentials:

Username: admin

Password: admin123

👨‍🎓 2. Student Management

Register a new student

Auto-generate student IDs

Prevent duplicate registrations

Stored in JSON-like format

📚 3. Course Enrollment

List all available courses

Assign courses to students

Prevent duplicate course selection

Data saved permanently to files

💾 4. File Handling (Persistent Storage)

Handled using FileHandler.java:

Save students

Save courses

Read files from disk

Auto-create required files if missing

JSON-like storage example
{
  "id": "S001",
  "name": "Ayush Goel",
  "courses": ["C101", "C102"]
}


Files created automatically:

students.txt

courses.txt

📁 Project Structure
StudentCourseRegistration/
 ├── src/
 │    ├── Main.java
 │    ├── models/
 │    │     ├── Course.java
 │    │     └── Student.java
 │    ├── services/
 │    │     ├── AuthService.java
 │    │     ├── CourseService.java
 │    │     └── StudentService.java
 │    └── utils/
 │          └── FileHandler.java
 └── data/
      ├── courses.txt
      └── students.txt

⚙️ How the System Works
AuthService.java

Verifies admin login

Ensures secure access to the system

FileHandler.java

Reads and writes JSON-like structured files

Loads and saves students & courses

Creates missing data files automatically

Provides helper functions:

loadStudents()

saveStudents()

loadCourses()

saveCourses()

Student.java

Stores student ID, name, and list of enrolled courses

Course.java

Contains course code and full course name

Main.java

Runs the menu-driven application:

Login

Register student

List courses

Enroll student

View student details

Exit

▶️ How to Run the Project
1. Compile all Java files
javac *.java

2. Run the main program
java Main

3. Recommended IDEs

IntelliJ IDEA

Eclipse

NetBeans

VS Code (Java Extension Pack)

📌 Technologies Used

Java 8+

File Handling

Object-Oriented Programming (OOP)

JSON-like data structure
