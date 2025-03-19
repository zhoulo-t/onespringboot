package com.example.demo.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<Student> list = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("a");
        list.add(student1);

        Student student2 = new Student();
        student2.setName("B");
        list.add(student2);

        Student student3 = new Student();
        student3.setName("c");
        list.add(student3);

        list.forEach(student -> System.out.println(student.getName()));
        list.sort(Comparator.comparing(Student::getName));
        list.forEach(student -> System.out.println(student.getName()));
        // 忽略大小写的排序
        list.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        list.forEach(student -> System.out.println(student.getName()));

    }

    static class Student {
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
