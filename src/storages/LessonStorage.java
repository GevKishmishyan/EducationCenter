package storages;


import models.Lesson;

public class LessonStorage implements Storage{

    private Lesson[] lessons;
    public int lessonsCount = 0;

    public LessonStorage(int lessonsCopacity){
        lessons = new Lesson[lessonsCopacity];
    }

    public LessonStorage(){
        lessons = new Lesson[3];
    }

    public void addLesson(Lesson lesson){
        if (lessonsCount == lessons.length){
            extendLessonsCount();
        }
        lessons[lessonsCount++] = lesson;
    }

    private void extendLessonsCount() {
        Lesson[] tmp = new Lesson[lessons.length + 10];
        System.arraycopy(lessons,0,tmp,0,lessons.length);
        lessons = tmp;
    }

    @Override
    public void print(){
        for (int i = 0; i < lessonsCount; i++) {
            System.out.println(lessons[i]);
        }
    }

    public Lesson getLessonDataByName(String searchedLesson) {
        for (int i = 0; i < lessonsCount; i++) {
            if(lessons[i].getName().equals(searchedLesson)){
                return lessons[i];
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty(){
        return lessonsCount == 0;
    }


}
