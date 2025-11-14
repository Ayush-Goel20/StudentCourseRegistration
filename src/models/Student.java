package models;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Student {
    private String id;
    private String name;
    private List<String> courses; // store course IDs only

    public Student(String id, String name, List<String> courses) {
        this.id = id;
        this.name = name;
        this.courses = (courses == null) ? new ArrayList<>() : courses;
    }

    public Student(String id, String name) {
        this(id, name, new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public boolean addCourse(String courseId) {
        if (!courses.contains(courseId)) {
            courses.add(courseId);
            return true;
        }
        return false;
    }

    public boolean dropCourse(String courseId) {
        return courses.remove(courseId);
    }

    public String coursesAsString() {
        if (courses.isEmpty())
            return "";
        StringJoiner j = new StringJoiner("|");
        for (String c : courses)
            j.add(c);
        return j.toString();
    }

    // JSON-like serialization
    public String toJsonLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":\"").append(escape(id)).append("\",");
        sb.append("\"name\":\"").append(escape(name)).append("\",");
        sb.append("\"courses\":[");
        for (int i = 0; i < courses.size(); i++) {
            sb.append("\"").append(escape(courses.get(i))).append("\"");
            if (i < courses.size() - 1)
                sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
