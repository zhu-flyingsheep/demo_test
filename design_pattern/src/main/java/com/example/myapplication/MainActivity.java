package com.example.myapplication;

import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.Adapter.DevlopAdapter;
import com.example.myapplication.Adapter.IosEngineer;
import com.example.myapplication.Chaino_Responsibility.Schoolmaster;
import com.example.myapplication.Chaino_Responsibility.Student;
import com.example.myapplication.Chaino_Responsibility.Teacher;
import com.example.myapplication.observer.Observable;
import com.example.myapplication.observer.Observer;
import com.example.myapplication.states.Istate;
import com.example.myapplication.states.Married;
import com.example.myapplication.states.Single;
import com.example.myapplication.strategy.ByBus;
import com.example.myapplication.strategy.BySubway;
import com.example.myapplication.strategy.Bybike;
import com.example.myapplication.strategy.IgotoWork;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Dialog");
//        builder.setMessage("一些测试");
//        builder.setPositiveButton("OK", null);
//        builder.setNegativeButton("Cancel", null);
//        builder.show();
        initActionBar();
        init_view();
//        initState();
    }

    private void init_view() {
        this.findViewById(R.id.btn_observer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observer observer = new Observer("张三", MainActivity.this);
                Observer observer1 = new Observer("李四", MainActivity.this);
                Observer observer2 = new Observer("王麻子", MainActivity.this);
                Observable observable = new Observable();
                observable.add(observer);
                observable.add(observer1);
                observable.add(observer2);
                observable.notifa_change();

            }
        });
        this.findViewById(R.id.btn_strategy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IgotoWork igotoWork;
                igotoWork = new BySubway(MainActivity.this);
                igotoWork.goto_work();
                igotoWork = new ByBus(MainActivity.this);
                igotoWork.goto_work();
                igotoWork = new Bybike(MainActivity.this);
                igotoWork.goto_work();

            }
        });
        this.findViewById(R.id.btn_states).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Istate istate;
                istate = new Married(MainActivity.this);
                istate.start();
                istate = new Single(MainActivity.this);
                istate.start();

            }
        });
        this.findViewById(R.id.btn_adapter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevlopAdapter devlopAdapter = new DevlopAdapter(MainActivity.this);
                IosEngineer iosEngineer = new IosEngineer(MainActivity.this);
                devlopAdapter.setIosEngineer(iosEngineer);
                devlopAdapter.devlop_ios_app();
                devlopAdapter.devlop_android_app();
            }
        });
        this.findViewById(R.id.btn_chan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schoolmaster schoolmaster = new Schoolmaster(MainActivity.this);
                Teacher teacher = new Teacher(MainActivity.this);
                teacher.setMyhandler(schoolmaster);
                Student student = new Student(MainActivity.this);
                student.setMyhandler(teacher);

                student.hand_request(1);
                student.hand_request(4);
                student.hand_request(7);
                student.hand_request(9);
                student.hand_request(11);

            }
        });
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setTitle("设计模式");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationContentDescription("BACK");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "finish==", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
