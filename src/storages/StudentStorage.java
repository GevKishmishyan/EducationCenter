package storages;


import models.Student;

public class StudentStorage implements Storage{
    
    private Student[] students;
    private int studentsCount = 0;
    
    public StudentStorage(int studentCapacity){
        students = new Student[studentCapacity];
    }
    
    public StudentStorage(){
        students = new Student[3];
    }

    public void addStudent(Student student){
        if (studentsCount == students.length){
            extendStudentsCount();
        }
        students[studentsCount++] = student;
    }

    private void extendStudentsCount() {
        Student[] tmp = new Student[students.length + 10];
        System.arraycopy(students, 0, tmp,0,students.length);
        students = tmp;
    }

    @Override
    public void print(){
        for (int i = 0; i < studentsCount; i++) {
            System.out.println(students[i]);
        }
    }

    public Student getStudentDataByEmail(String searchedStudentEmail) {
        for (int i = 0; i < studentsCount; i++) {
            if(students[i].getEmail().equals(searchedStudentEmail)){
                return students[i];
            }
        }
        return null;
    }

    public void printStudentsByLessonName(String searchedLessonName){
        for (int i = 0; i < studentsCount; i++) {
            for (int j = 0; j < students[i].getLessons().length; j++) {
                if (students[i].getLessons()[j].getName().equals(searchedLessonName)){
                    System.out.println(students[i]);
                }
            }
        }

    }

    @Override
    public boolean isEmpty() {
        return studentsCount == 0;
    }
}
