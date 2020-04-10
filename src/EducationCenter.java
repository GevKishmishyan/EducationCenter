import models.Lesson;
import models.Student;
import storages.LessonStorage;
import storages.StudentStorage;

import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.UUID;

public class EducationCenter implements Commands {

    private static StudentStorage studentStorage = new StudentStorage();
    private static LessonStorage lessonStorage = new LessonStorage();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRun = true;
        do {
            printCommands();
            String commandStr = scanner.nextLine();
            try {
                int command = Integer.parseInt(commandStr);
                switch (command) {
                    case EXIT:
                        isRun = false;
                        System.out.println("Bye Bye.");
                        break;
                    case ADD_STUDENT:
                        addStudent();
                        break;
                    case ADD_LESSON:
                        addLesson();
                        break;
                    case PRINT_STUDENTS:
                        printStudents();
                        break;
                    case PRINT_LESSONS:
                        printLessons();
                        break;
                    case CHANGE_STUDENT_LESSON:
                        changeStudentLesson();
                        break;
                    case PRINT_STUDENT_BY_LESSON_NAME:
                        printStudentsByLessonName();
                        break;
                    default:
                        System.err.println("WRONG command, please choose the CORRECT.");
                }
            } catch (NumberFormatException e){
                System.err.println("Please, input DIGITS to choose command.");
            }

        } while (isRun);

    }

    private static void printLessons() {
        if (!lessonStorage.isEmpty()) {
            lessonStorage.print();
        } else {
            System.out.println("Lessons list is EMPTY.");
        }
    }

    private static void printStudents() {
        if (!studentStorage.isEmpty()) {
            studentStorage.print();
        } else {
            System.out.println("Students list is EMPTY.");
        }
    }

    private static void printStudentsByLessonName() {
        lessonStorage.print();
        System.out.println("Select the workshop to see how many students participating on this.");
        String lesson = scanner.nextLine();
        studentStorage.printStudentsByLessonName(lesson);
    }

    private static void changeStudentLesson() {
        if (!studentStorage.isEmpty()) {
            studentStorage.print();
            System.out.println("Input student name, which workshops do you want to change.");
            String searchedStudentName = scanner.nextLine();
            lessonStorage.print();
            Lesson[] lessons = getLessonsForStudent();
            Student student = studentStorage.getStudentDataByName(searchedStudentName);
            student.setLessons(lessons);
        } else {
            addStudent();
        }
    }

    private static void addLesson() {
        System.out.println("Input info about lesson. [name,lecturerName,duration,price]");
        String lessonDataStr = scanner.nextLine();
        String[] lessonData = lessonDataStr.split(",");
        if (lessonData.length != 4) {
            System.out.println("Error");
            addLesson();
        }
        try {
            String uniqueID = UUID.randomUUID().toString();
            int duration = Integer.parseInt(lessonData[2]);
            double price = Double.parseDouble(lessonData[3]);
            Lesson lesson = new Lesson(uniqueID, lessonData[0], lessonData[1], duration, price);
            lessonStorage.addLesson(lesson);
        } catch (NumberFormatException e) {
            System.out.println("Error");
            addLesson();
        }

    }

    private static Lesson[] getLessonsForStudent() {
        lessonStorage.print();
        System.out.println("Please, input which workshops you want to participate. [lesson1,lesson2,lesson3,etc.]");
        String lessonsDataStr = scanner.nextLine();
        String[] lessonsData = lessonsDataStr.split(",");
        if (lessonsData.length > lessonStorage.lessonsCount) {
            getLessonsForStudent();
        }
        Lesson[] lessons = new Lesson[lessonsData.length];
        for (int i = 0; i < lessonsData.length; i++) {
            Lesson tmp = lessonStorage.getLessonDataByName(lessonsData[i]);
            lessons[i] = tmp;
        }
        return lessons;
    }


    private static void addStudent() {
        if (!lessonStorage.isEmpty()) {
            Lesson[] lessons = getLessonsForStudent();
            System.out.println("Please, input your data. [name,surname,phone,email]");
            String studentDataStr = scanner.nextLine();
            String[] studentData = studentDataStr.split(",");
            if (studentData.length != 4) {
                addStudent();
            }
            String uniqueID = UUID.randomUUID().toString();
            Student student = new Student(uniqueID, studentData[0], studentData[1], studentData[2], studentData[3], lessons);
            studentStorage.addStudent(student);
        } else {
            addLesson();
        }

    }

    private static void printCommands() {
        System.out.println("Choose " + EXIT + " for EXIT.");
        System.out.println("Choose " + ADD_STUDENT + " for ADD STUDENT.");
        System.out.println("Choose " + ADD_LESSON + " for ADD LESSON.");
        System.out.println("Choose " + PRINT_STUDENTS + " for PRINT STUDENTS LIST.");
        System.out.println("Choose " + PRINT_LESSONS + " for PRINT LESSONS LIST.");
        System.out.println("Choose " + CHANGE_STUDENT_LESSON + " for CHANGING STUDENT LESSON.");
        System.out.println("Choose " + PRINT_STUDENT_BY_LESSON_NAME + " for PRINT STUDENT BY LESSON NAME.");
    }
}
