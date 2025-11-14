package services;

import models.Course;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses;
    private FileHandler fileHandler;

    public CourseService(FileHandler fh) {
        this.fileHandler = fh;
        this.courses = fh.loadCourses();
        if (courses.isEmpty()) {
            // preload sample courses if none exist
            courses.add(new Course("C101", "Object Oriented Programming", 4, 50, 0));
            courses.add(new Course("C102", "Database Management", 3, 40, 0));
            courses.add(new Course("C103", "Operating Systems", 4, 35, 0));
            save();
        }
    }

    public List<Course> getAll() {
        return new ArrayList<>(courses);
    }

    public Course findById(String id) {
        for (Course c : courses)
            if (c.getId().equalsIgnoreCase(id))
                return c;
        return null;
    }

    public boolean addCourse(String id, String name, int credits, int capacity) {
        if (findById(id) != null)
            return false;
        courses.add(new Course(id, name, credits, capacity, 0));
        save();
        return true;
    }

    public boolean enrollInCourse(String courseId) {
        Course c = findById(courseId);
        if (c == null)
            return false;
        boolean ok = c.enrollOne();
        if (ok)
            save();
        return ok;
    }

    public boolean dropFromCourse(String courseId) {
        Course c = findById(courseId);
        if (c == null)
            return false;
        boolean ok = c.dropOne();
        if (ok)
            save();
        return ok;
    }

    public void save() {
        fileHandler.saveCourses(courses);
    }
}
