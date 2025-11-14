package utils;

import models.Course;
import models.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
 Simple file handler that reads/writes JSON-like objects, one per line.
 It expects:
 courses.txt lines like:
 {"id":"C101","name":"OOP","credits":4,"capacity":50,"enrolled":2}

 students.txt lines like:
 {"id":"S001","name":"Ayush","courses":["C101","C102"]}
*/
public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String COURSES_FILE = DATA_DIR + "/courses.txt";
    private static final String STUDENTS_FILE = DATA_DIR + "/students.txt";

    public FileHandler() {
        try {
            Files.createDirectories(Path.of(DATA_DIR));
            new File(COURSES_FILE).createNewFile();
            new File(STUDENTS_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to create data directory/files: " + e.getMessage());
        }
    }

    // Save lists
    public void saveCourses(List<Course> courses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course c : courses) {
                // manual JSON-like line
                String line = String.format(
                        "{\"id\":\"%s\",\"name\":\"%s\",\"credits\":%d,\"capacity\":%d,\"enrolled\":%d}",
                        escape(c.getId()), escape(c.getName()), c.getCredits(), c.getCapacity(), c.getEnrolled());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    public void saveStudents(Collection<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : students) {
                bw.write(s.toJsonLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    // Load courses
    public List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                Map<String, String> m = parseSimpleJsonLine(line);
                String id = m.get("id");
                String name = m.get("name");
                int credits = Integer.parseInt(m.getOrDefault("credits", "0"));
                int capacity = Integer.parseInt(m.getOrDefault("capacity", "0"));
                int enrolled = Integer.parseInt(m.getOrDefault("enrolled", "0"));
                courses.add(new Course(id, name, credits, capacity, enrolled));
            }
        } catch (IOException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
        return courses;
    }

    // Load students
    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                // parse id, name, courses array
                Map<String, String> m = parseSimpleJsonLine(line);
                String id = m.get("id");
                String name = m.get("name");
                List<String> courses = parseJsonArray(line, "courses");
                students.add(new Student(id, name, courses));
            }
        } catch (IOException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    // Very simple parsing for flat JSON-like lines
    private Map<String, String> parseSimpleJsonLine(String line) {
        Map<String, String> map = new HashMap<>();
        // remove outer braces
        String trimmed = line.trim();
        if (trimmed.startsWith("{"))
            trimmed = trimmed.substring(1);
        if (trimmed.endsWith("}"))
            trimmed = trimmed.substring(0, trimmed.length() - 1);

        // split by ",\"key\"" occurrences but keep arrays intact -> a crude approach:
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int bracketLevel = 0;
        for (int i = 0; i < trimmed.length(); i++) {
            char ch = trimmed.charAt(i);
            if (ch == '[')
                bracketLevel++;
            if (ch == ']')
                bracketLevel--;
            if (ch == ',' && bracketLevel == 0) {
                parts.add(current.toString());
                current.setLength(0);
            } else
                current.append(ch);
        }
        if (current.length() > 0)
            parts.add(current.toString());

        for (String p : parts) {
            String[] kv = p.split(":", 2);
            if (kv.length < 2)
                continue;
            String key = kv[0].trim().replaceAll("^\"|\"$", "");
            String value = kv[1].trim();
            // strip quotes if present
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            map.put(key, value);
        }
        return map;
    }

    private List<String> parseJsonArray(String line, String arrayKey) {
        List<String> res = new ArrayList<>();
        int idx = line.indexOf("\"" + arrayKey + "\"");
        if (idx == -1)
            return res;
        int start = line.indexOf('[', idx);
        int end = line.indexOf(']', start);
        if (start == -1 || end == -1)
            return res;
        String inside = line.substring(start + 1, end).trim();
        if (inside.isEmpty())
            return res;
        // split by commas not in quotes (we assume simple strings)
        String[] items = inside.split(",");
        for (String it : items) {
            it = it.trim();
            if (it.startsWith("\"") && it.endsWith("\""))
                it = it.substring(1, it.length() - 1);
            if (!it.isEmpty())
                res.add(it);
        }
        return res;
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
