package com.example.demo.listen;

import lombok.Data;

@Data
public class StringChangeListen {

    private String name;
    private String value;

    public StringChangeListen(String name) {
        this(name, null);
    }

    public StringChangeListen(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
