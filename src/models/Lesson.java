package models;

public class Lesson {

    private String name;
    private String lecturerName;
    private int duration;
    private double price;

    public Lesson(String name, String lecturerName, int duration, double price) {
        this.name = name;
        this.lecturerName = lecturerName;
        this.duration = duration;
        this.price = price;
    }

    public Lesson(String name, String lecturerName) {
        this.name = name;
        this.lecturerName = lecturerName;
    }

    public Lesson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (duration != lesson.duration) return false;
        if (Double.compare(lesson.price, price) != 0) return false;
        if (name != null ? !name.equals(lesson.name) : lesson.name != null) return false;
        return lecturerName != null ? lecturerName.equals(lesson.lecturerName) : lesson.lecturerName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lecturerName != null ? lecturerName.hashCode() : 0);
        result = 31 * result + duration;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", lecturerName='" + lecturerName + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
