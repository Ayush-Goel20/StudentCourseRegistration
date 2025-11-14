package services;

import models.Student;
import utils.FileHandler;

import java.util.*;

public class StudentService {
    private Map<String, Student> students;
    private FileHandler fileHandler;
    private int lastIdNumber = 0;

    public StudentService(FileHandler fh) {
        this.fileHandler = fh;
        List<Student> loaded = fh.loadStudents();
        students = new LinkedHashMap<>();
        for (Student s : loaded) {
            students.put(s.getId(), s);
            // extract numeric suffix if format Sxxx
            try {
                if (s.getId().startsWith("S")) {
                    int n = Integer.parseInt(s.getId().substring(1));
                    lastIdNumber = Math.max(lastIdNumber, n);
                }
            } catch (Exception ignore) {
            }
        }
    }

    // auto-generate ID like S001, S002
    private String generateId() {
        lastIdNumber++;
        return String.format("S%03d", lastIdNumber);
    }

    public Student registerStudent(String name) {
        String id = generateId();
        Student s = new Student(id, name);
        students.put(id, s);
        save();
        return s;
    }

    public Student getById(String id) {
        return students.get(id);
    }

    public List<Student> searchByName(String query) {
        List<Student> res = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getName().toLowerCase().contains(query.toLowerCase()))
                res.add(s);
        }
        return res;
    }

    public boolean addCourseToStudent(String studentId, String courseId) {
        Student s = getById(studentId);
        if (s == null)
            return false;
        boolean added = s.addCourse(courseId);
        if (added)
            save();
        return added;
    }

    public boolean dropCourseFromStudent(String studentId, String courseId) {
        Student s = getById(studentId);
        if (s == null)
            return false;
        boolean dropped = s.dropCourse(courseId);
        if (dropped)
            save();
        return dropped;
    }

    public Collection<Student> allStudents() {
        return students.values();
    }

    public void save() {
        fileHandler.saveStudents(students.values());
    }
}
