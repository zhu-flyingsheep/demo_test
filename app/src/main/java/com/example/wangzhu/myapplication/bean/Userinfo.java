package com.example.wangzhu.myapplication.bean;

import com.example.wangzhu.myapplication.annotation.ClassInfo;
import com.example.wangzhu.myapplication.annotation.FieldInfoInt;
import com.example.wangzhu.myapplication.annotation.FieldInfoString;

import java.lang.reflect.Field;

/**
 * Created by wangzhu on 2017/3/27.
 * email:zhu.wang@utsoft.cn
 * func:
 */
@ClassInfo(value = "Userinfo.class")
public class Userinfo {
    public Userinfo(String a) {
        Class<Userinfo> clz = Userinfo.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); //设置些属性是可以访问的
            boolean fieldHasAnno = field.isAnnotationPresent(FieldInfoInt.class);
            if (fieldHasAnno) {
            }
            boolean fieldHasstring = field.isAnnotationPresent(FieldInfoString.class);
            if (fieldHasstring) {
            }
            String type = field.getType().toString();//得到此属性的类型
            if (type.endsWith("String")) {
                System.out.println(field.getType() + "\t是String");
                FieldInfoString fieldAnno = field.getAnnotation(FieldInfoString.class);
                try {
                    field.set(this, fieldAnno.value());        //给属性设值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (type.endsWith("int") || type.endsWith("Integer")) {
                System.out.println(field.getType() + "\t是int");
                FieldInfoInt fieldAnno = field.getAnnotation(FieldInfoInt.class);
                try {
                    field.set(this, fieldAnno.value());       //给属性设值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(field.getType() + "\t");
            }
        }
    }

    public Userinfo() {

    }


    @FieldInfoInt(value = 19)
    public int age;
    @FieldInfoString(value = "zhagnshan")
    public String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FieldInfoString(value = "ut@.com")
    public String email;

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
