package com.example.demo.test;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TransientTest {

    @Setter
    @Getter
    static class TransientClass implements Serializable {

        private static final long serialVersionUID = 1L;

        private transient String transientField;

        private String nonTransientField;

    }

    private final TransientClass transientClass = new TransientClass();

    public TransientTest() {
        transientClass.setTransientField("This is a transient field");
        transientClass.setNonTransientField("This is a non-transient field");
    }

    public static void main(String[] args) {

        TransientTest test = new TransientTest();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/main/resources/transientTest.ser"))) {
            outputStream.writeObject(test.transientClass);
            System.out.println("对象已序列化到 transientTest.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("src/main/resources/transientTest.ser"))) {
            TransientClass deserializedObject = (TransientClass) inputStream.readObject();
            System.out.println("反序列化后的 transientField: " + deserializedObject.getTransientField());
            System.out.println("反序列化后的 nonTransientField: " + deserializedObject.getNonTransientField());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
