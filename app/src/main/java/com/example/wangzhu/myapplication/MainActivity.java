package com.example.wangzhu.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wangzhu.myapplication.annotation.ClassInfo;
import com.example.wangzhu.myapplication.bean.Car;
import com.example.wangzhu.myapplication.bean.Userinfo;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@ClassInfo()
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StringBuffer sb = new StringBuffer();
        Class<?> cls = MainActivity.class;
        ClassInfo classInfo = cls.getAnnotation(ClassInfo.class);
        if (classInfo != null) {
            sb.append(Modifier.toString(cls.getModifiers())).append("\n")
                    .append(cls.getSimpleName()).append("\n");
            sb.append("注解值: ").append(classInfo.value()).append("\n\n");
        }
        Log.i("MainActivity", sb.toString());
        ///////////////////
        Userinfo userinfo = new Userinfo("");
        Log.i("MainActivity", "age" + userinfo.getAge() + "\n" + userinfo.getName() + "\n" + userinfo.getEmail());
        //////////////////////
        Car<String> s = getCar();
        Log.i("MainActivity", s.getPrice() + "\n" + s.getType() + "\n" + s + "\n" + s.getCar());
        List<String> ddddd = new ArrayList<>();
        ddddd.add("ssd");
        ddddd.add("bbbs");
        ddddd.add("ssssss");
        Car<List<String>> listCar = new Car<>(ddddd);
        getCar1(new Car<Number>(1));
        Log.i("MainActivity", listCar.getPrice() + "\n" + listCar.getType() + "\n" + listCar + "\n" + listCar.getCar());

    }


    public Car<String> getCar() {
        Car<String> s = new Car<>("111");

        return s;
    }

    public Car getCar1(Car<? extends Number> car) {

        return car;
    }
}
