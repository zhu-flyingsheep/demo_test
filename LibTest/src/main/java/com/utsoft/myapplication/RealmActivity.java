package com.utsoft.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.logger.Logger;
import com.utsoft.myapplication.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by wangzhu on 2017/3/3.
 * func:
 */

public class RealmActivity extends Activity {
    private ListView lv_users;
    private EditText et_name_add, et_age_add;
    private EditText et_name_search, et_age_search;

    private Button btn_add_user, btn_search_user;

    private Realm realm;


    private MyAdapter myAdapter;
    private RealmResults<User> users;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_activity);
        realm = Realm.getDefaultInstance();
        Toast.makeText(this, "长按一条记录可以删除！", Toast.LENGTH_LONG).show();
        findviews();

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = et_name_add.getText().toString();
                String age = et_age_add.getText().toString();
                if (null == name || "".equals(name)) {
                    Toast.makeText(RealmActivity.this, "请输入名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == age || "".equals(age)) {
                    Toast.makeText(RealmActivity.this, "请输年龄", Toast.LENGTH_SHORT).show();
                    return;
                }

                final int age_int = Integer.parseInt(et_age_add.getText().toString());

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        user = bgRealm.createObject(User.class);
                        user.setName(name);
                        user.setAge(age_int);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RealmActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                        users = realm.where(User.class).findAll();
                        myAdapter = new MyAdapter(users);
                        lv_users.setAdapter(myAdapter);
                        // Transaction was a success.
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(RealmActivity.this, "插入失败+\n" + error.getMessage(), Toast.LENGTH_SHORT).show();

                        // Transaction failed and was automatically canceled.
                    }
                });


            }
        });
        users = realm.where(User.class).findAll();
        myAdapter = new MyAdapter(users);
        lv_users.setAdapter(myAdapter);
        lv_users.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                User user = new User();
//                user.setAge(users.get(i).getAge());
//                user.setName(users.get(i).getName());
//                users.remove(i);
                realm.beginTransaction();
                users.deleteFromRealm(i);
                realm.commitTransaction();
                myAdapter.notifyDataSetChanged();

                return true;
            }
        });
        btn_search_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = et_name_search.getText().toString();
                String ageString = et_age_search.getText().toString();
                int age;
                if (null == ageString || "".equals(ageString)) {
                    age = -1;
                } else {
                    age = Integer.parseInt(ageString);
                }


                if (null == name || "".equals(name)) {
                    if (age != -1) {
                        users = realm.where(User.class).equalTo("age", age).findAll();
                        myAdapter = new MyAdapter(users);
                        lv_users.setAdapter(myAdapter);
                    } else {
                        users = realm.where(User.class)
                                .findAll();
                        myAdapter = new MyAdapter(users);
                        lv_users.setAdapter(myAdapter);
                    }
                } else {
                    if (age != -1) {
                        users = realm.where(User.class).equalTo("name", name)
                                .equalTo("age", age).findAll();
                        myAdapter = new MyAdapter(users);
                        lv_users.setAdapter(myAdapter);
                    } else {
                        users = realm.where(User.class).equalTo("name", name)
                                .findAll();
                        myAdapter = new MyAdapter(users);
                        lv_users.setAdapter(myAdapter);
                    }
                }
                // Find the first person (no query conditions) and read a field


//                User user = realm.where(User.class).findFirst();
//                Logger.json(user.toString());
//                Logger.i(user.getName());
//                Logger.i(user.getAge()+"");
            }
        });


    }

    private void findviews() {
        lv_users = (ListView) this.findViewById(R.id.lv_users);

        et_name_add = (EditText) this.findViewById(R.id.et_name_add);
        et_age_add = (EditText) this.findViewById(R.id.et_age_add);
        btn_add_user = (Button) this.findViewById(R.id.btn_add_user);

        et_name_search = (EditText) this.findViewById(R.id.et_name_search);
        et_age_search = (EditText) this.findViewById(R.id.et_age_search);
        btn_search_user = (Button) this.findViewById(R.id.btn_search_user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != realm) realm.close();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Realm Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    public class MyAdapter extends BaseAdapter {
        private RealmResults<User> users;

        public MyAdapter(RealmResults<User> users) {
            this.users = users;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int i) {
            return users.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = convertView;
            final ViewHolder viewHolder;
            if (null == view) {
                view = LayoutInflater.from(RealmActivity.this).inflate(
                        R.layout.item_user_info, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_age = (TextView) view.findViewById(R.id.tv_age);
                viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }
            viewHolder.tv_age.setText(users.get(position).getAge() + "");
            viewHolder.tv_name.setText(users.get(position).getName());
            return view;
        }

        class ViewHolder {

            public TextView tv_age, tv_name;

        }
    }

}
