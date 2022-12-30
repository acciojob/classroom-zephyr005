package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class StudentRepository {
    private HashMap<String,Student> studentDB;
    private HashMap<String,Teacher> teacherDB;
    private HashMap<String,List<String>> teacherStudentDB;

    //Pair: TeacherName, List of StudentName

    public StudentRepository(){
        this.studentDB = new HashMap<>();
        this.teacherDB = new HashMap<>();
        this.teacherStudentDB = new HashMap<>();
    }

    public void saveStudent(Student student){
        studentDB.put(student.getName(),student);
    }

    public void saveTeacher(Teacher teacher){
        teacherDB.put(teacher.getName(),teacher);
    }

    public void saveStudentTeacherPair(String student, String teacher){
        if(studentDB.containsKey(student) && teacherDB.containsKey(teacher)){
            List<String> currentStudentTeacherPair = new ArrayList<>();
            if(teacherStudentDB.containsKey(teacher))
                currentStudentTeacherPair = teacherStudentDB.get(teacher);
            currentStudentTeacherPair.add(student);
            teacherStudentDB.put(teacher,currentStudentTeacherPair);
        }
    }

    public Student findStudent(String student){
        return studentDB.get(student);
    }

    public Teacher findTeacher(String teacher){
        return teacherDB.get(teacher);
    }

    public List<String> findAllStudentsByTeacher(String teacher){
        List<String> studentList = new ArrayList<>();
        if(teacherStudentDB.containsKey(teacher)){
            studentList = teacherStudentDB.get(teacher);
        }
        return studentList;
    }

    public List<String> findAllStudents(){
        return new ArrayList<>(studentDB.keySet());
    }

    public void deleteTeacher(String teacher){
        List<String> students = new ArrayList<>();
        if(teacherStudentDB.containsKey(teacher)){
            //Find the student's name by teacher's name from teacherStudentDB
            students = teacherStudentDB.get(teacher);

            //Deleting all students from studentDB
            for(String student : students){
                if(studentDB.containsKey(student)){
                    studentDB.remove(student);
                }
            }

            //Deleting the teacher from teacherDB
            if(teacherDB.containsKey(teacher)){
                teacherDB.remove(teacher);
            }

            //Deleting the teacher-student pair
            teacherStudentDB.remove(teacher);
        }
    }

    public void deleteAllTeacher(){
        HashSet<String> studentSet = new HashSet<>();

        //Finding all the students by all the teachers from teacherStudentDB
        for(String teacher : teacherStudentDB.keySet()){
            for(String student : teacherStudentDB.get(teacher)){
                studentSet.add(student);
            }
        }

        //Deleting the students from studentDb
        for(String student : studentSet){
            if(studentDB.containsKey(student)){
                studentDB.remove(student);
            }
        }

        //Deleting the teachers from teacherDB
        teacherDB = new HashMap<>();

        //Deleting the teacher-student pair
        teacherStudentDB = new HashMap<>();
    }
}
