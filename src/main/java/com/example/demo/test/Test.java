package com.example.demo.test;

import com.example.demo.dto.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class Test {

    public static void main(String[] args) {

        try {
            // 创建一个User对象
            User user = new User();
            user.setName("John Doe");
            user.setAge(30);


            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            // 设置格式化输出
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 输出到文件
            marshaller.marshal(user, new File("src/main/resources/user.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }


}
