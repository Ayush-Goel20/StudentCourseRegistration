package models;

public class Course {
    private String id;
    private String name;
    private int credits;
    private int capacity;
    private int enrolled;

    public Course(String id, String name, int credits, int capacity, int enrolled) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public boolean hasSeat() {
        return enrolled < capacity;
    }

    public boolean enrollOne() {
        if (hasSeat()) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropOne() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + credits + "cr) | Seats: " + (capacity - enrolled) + "/" + capacity;
    }

    public String toJsonLine() {
        return String.format("{\"id\":\"%s\",\"name\":\"%s\",\"credits\":%d,\"capacity\":%d,\"enrolled\":%d}",
                escape(id), escape(name), credits, capacity, enrolled);
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
