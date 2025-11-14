# 📘 Student Course Registration System  
### *(Java Project with Authentication + File Handling)*

This project is a **Java-based Student Course Registration System** that includes:

- Admin authentication  
- Student registration  
- Course management  
- JSON-like file storage  
- Search & display functionalities  
- OOP-based structure (`AuthService`, `FileHandler`, `Student`, `Course`, `Main`)

It is designed as an **intermediate-level Java project**, suitable for company application forms, internships, and resume showcases.

---

## 🧩 Project Features

### 🔐 1. Admin Authentication
- Login required to access the system  
- Uses `AuthService.java`  
- **Default Credentials:**
  - **Username:** `admin`  
  - **Password:** `admin123`

---

### 👨‍🎓 2. Student Management
- Register a new student  
- Auto-generate student IDs  
- Prevent duplicate registrations  
- Stores student data in JSON-like format  

---

### 📚 3. Course Enrollment
- View list of available courses  
- Assign courses to students  
- Prevent duplicate course selection  
- Save all changes to the file  

---

## 💾 4. File Handling (Persistent Storage)

Handled using **`FileHandler.java`**:

- Save students  
- Save courses  
- Read JSON-like content from disk  
- Auto-create required files if missing  


### Example storage format:
```json
{
  "id": "S001",
  "name": "Ayush Goel",
  "courses": ["C101", "C102"]
}
```
## 📁 Project Structure

```
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
```




