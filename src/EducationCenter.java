import exceptions.ExistingLessonException;
import models.Lesson;
import models.Student;
import storages.LessonStorage;
import storages.StudentStorage;


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
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
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
                    System.out.println("WRONG command, please choose the CORRECT.");
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
        if (!studentStorage.isEmpty()) {
            lessonStorage.print();
            System.out.println("Select the lesson to see who are participating on this.");
            String lesson = scanner.nextLine();
            Lesson lessonDataByName = lessonStorage.getLessonDataByName(lesson);
            if (lessonDataByName != null) {
                studentStorage.printStudentsByLessonName(lesson);
            } else {
                System.out.println("Lesson is not exist.");
                printStudentsByLessonName();
            }
        } else {
            System.out.println("Please, add student at first.");
            addStudent();
        }
    }

    private static void changeStudentLesson() {
        if (!studentStorage.isEmpty()) {
            studentStorage.print();
            System.out.println("Input student name, which workshops do you want to change.");
            String searchedStudentName = scanner.nextLine();
            lessonStorage.print();
            Student student = studentStorage.getStudentDataByEmail(searchedStudentName);
            if (student != null) {
                System.out.println("Wrong name");
                changeStudentLesson();
            } else {
                Lesson[] lessons = getLessonsForStudent();
                if (lessons.length > 0) {
                    student.setLessons(lessons);
                    System.out.println("Student lessons are changed.");
                }
            }
        } else {
            addStudent();
        }
    }

    private static void checkLessonInput(int lng) {
        switch (lng) {
            case 1:
                System.out.println("You forgot to fill lecturerName, duration and price.");
                break;
            case 2:
                System.out.println("You forgot to fill duration and price.");
                break;
            case 3:
                System.out.println("You forgot to fill price.");
                break;
        }
    }

    private static void addLesson() {
        System.out.println("Input info about lesson. [name, lecturerName, duration, price]");
        try {
            String lessonDataStr = scanner.nextLine();
            String[] lessonData = lessonDataStr.split(",");
            if (lessonDataStr.equals("")) {
                System.out.println("You forgot to fill all fields.");
                addLesson();
            } else if (lessonData.length < 4 && lessonDataStr != "") {
                checkLessonInput(lessonData.length);
                addLesson();
            } else {
                String uniqueID = UUID.randomUUID().toString();
                int duration = Integer.parseInt(lessonData[2]);
                double price = Double.parseDouble(lessonData[3]);
                Lesson lesson = new Lesson(uniqueID, lessonData[0], lessonData[1], duration, price);
                lessonStorage.addLesson(lesson);
                System.out.println("Lesson was added.");
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Incorrect value! Please try again.");
            addLesson();
        } catch (ExistingLessonException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Lesson[] getLessonsForStudent() {
        lessonStorage.print();
        System.out.println("Please, input which workshops you want to participate. [lesson1, lesson2, lesson3, etc.]");
        String lessonsDataStr = scanner.nextLine();
        String[] lessonsData = lessonsDataStr.split(",");
        Lesson[] lessons = new Lesson[lessonsData.length];
        if (lessonsData.length > lessonStorage.lessonsCount) {
            System.out.println("We don't have " + lessonsData.length + " wokshops. We only have " + lessonStorage.lessonsCount + " workshop.");
            getLessonsForStudent();
        } else {
            Lesson lessonDataByName = lessonStorage.getLessonDataByName(lessonsData[0]);
            if (lessonDataByName != null) {
                for (int i = 0; i < lessonsData.length; i++) {
                    //todo add the feature, to say which workshops we don't have
                    Lesson tmp = lessonStorage.getLessonDataByName(lessonsData[i]);
                    lessons[i] = tmp;
                }
            } else {
                if (lessonsDataStr.equals("")) {
                    System.out.println("You have to choose at least 1 lesson.");
                } else {
                    System.out.println("Lesson is not exist. Choose these lessons which we have.");
                }
                getLessonsForStudent();
            }
        }
        return lessons;
    }

    private static void checkStudentInput(int lng) {
        switch (lng) {
            case 1:
                System.out.println("You forgot to fill surname, phone and email.");
                break;
            case 2:
                System.out.println("You forgot to fill phone and email.");
                break;
            case 3:
                System.out.println("You forgot to fill email.");
                break;
        }
    }

    private static void addStudent() {
        if (!lessonStorage.isEmpty()) {
            try {
                Lesson[] lessons = getLessonsForStudent();
                if (lessons.length > 0) {
                    System.out.println("Please, input your data. [name, surname, phone, email].");
                    String studentDataStr = scanner.nextLine();
                    String[] studentData = studentDataStr.split(",");
                    if (studentDataStr.equals("")) {
                        System.out.println("You forgot to fill all fields.");
                        addStudent();
                    } else if (studentData.length < 4) {
                        checkStudentInput(studentData.length);
                        addStudent();
                    } else {
                        Student studentDataByEmail = studentStorage.getStudentDataByEmail(studentData[3]);
                        if (studentDataByEmail != null) {
                            System.out.println("Student is already exist.");
                            addStudent();
                        } else {
                            String uniqueID = UUID.randomUUID().toString();
                            Student student = new Student(uniqueID, studentData[0], studentData[1], studentData[2], studentData[3], lessons);
                            studentStorage.addStudent(student);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorrect value! Please try again.");
                addStudent();
            }
        } else {
            System.out.println("Lessons list is EMPTY. Please add lesson first.");
            addLesson();
        }

    }

    private static void printCommands() {
        System.out.println("Choose " + EXIT + " for EXIT.");
        System.out.println("Choose " + ADD_LESSON + " for ADD LESSON.");
        System.out.println("Choose " + ADD_STUDENT + " for ADD STUDENT.");
        System.out.println("Choose " + PRINT_STUDENTS + " for PRINT STUDENTS LIST.");
        System.out.println("Choose " + PRINT_LESSONS + " for PRINT LESSONS LIST.");
        System.out.println("Choose " + CHANGE_STUDENT_LESSON + " for CHANGING STUDENT LESSON.");
        System.out.println("Choose " + PRINT_STUDENT_BY_LESSON_NAME + " for PRINT STUDENT BY LESSON NAME.");
    }
}
