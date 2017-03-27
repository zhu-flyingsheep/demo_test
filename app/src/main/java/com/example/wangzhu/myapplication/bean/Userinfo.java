package com.example.wangzhu.myapplication.bean;

import com.example.wangzhu.myapplication.annotation.FieldInfoInt;
import com.example.wangzhu.myapplication.annotation.FieldInfoString;

import java.lang.reflect.Field;

/**
 * Created by wangzhu on 2017/3/27.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Userinfo {
    public Userinfo() {
        Class<Userinfo> clz = Userinfo.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            boolean fieldHasAnno = field.isAnnotationPresent(FieldInfoInt.class);
            if (fieldHasAnno) {
                FieldInfoInt fieldAnno = field.getAnnotation(FieldInfoInt.class);
                setAge(fieldAnno.value());
            }
            boolean fieldHasstring = field.isAnnotationPresent(FieldInfoString.class);
            if (fieldHasstring) {
                FieldInfoString fieldAnno = field.getAnnotation(FieldInfoString.class);
                setName(fieldAnno.value());
            }
        }
    }

    @FieldInfoString(value = "zhagnshan")
    public String name;
    @FieldInfoInt(value = 19)
    public int age;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
