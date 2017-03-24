package com.utsoft.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.orhanobut.logger.Logger;
import com.utsoft.myapplication.Interface.Idoubanuser;
import com.utsoft.myapplication.Interface.IvideoTag;
import com.utsoft.myapplication.Utils.ECNetWorkUtil;
import com.utsoft.myapplication.Utils.ImageFactory;
import com.utsoft.myapplication.Utils.LoginEncrypt;
import com.utsoft.myapplication.Utils.UploadFileRequestBody;
import com.utsoft.myapplication.model.DoubanUser;
import com.utsoft.myapplication.model.ResultVdieotags;
import com.utsoft.myapplication.model.UserResult;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wangzhu on 2017/3/7.
 * func:
 */

public class RetrofitActivity extends Activity {
    private TextView tv_vpload_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        tv_vpload_progress = (TextView) this.findViewById(R.id.tv_vpload_progress);
        this.findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://bubbler.labs.douban.com/j/user/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                Call<DoubanUser> call = userBiz.getUser();
                call.enqueue(new Callback<DoubanUser>() {
                    @Override
                    public void onResponse(Call<DoubanUser> call, Response<DoubanUser> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");
                        Toast.makeText(RetrofitActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(RetrofitActivity.this, "用户头像地址" + ((DoubanUser) response.body()).getIcon(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DoubanUser> call, Throwable t) {

                    }
                });
            }
        });

        this.findViewById(R.id.btn_get_path).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://bubbler.labs.douban.com/j/user/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                Call<DoubanUser> call = userBiz.getUser("zhangsan");
                call.enqueue(new Callback<DoubanUser>() {
                    @Override
                    public void onResponse(Call<DoubanUser> call, Response<DoubanUser> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");
                        Toast.makeText(RetrofitActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(RetrofitActivity.this, "用户标题" + ((DoubanUser) response.body()).getTitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DoubanUser> call, Throwable t) {

                    }
                });
            }
        });
        this.findViewById(R.id.btn_get_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.yundao91.cn/ssh2/")//上个公司的域名，建议接口不要随意调用测试
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IvideoTag ivideoTag = retrofit.create(IvideoTag.class);
                Call<ResultVdieotags> call = ivideoTag.getVdieoTags("queryVideoTag");
                call.enqueue(new Callback<ResultVdieotags>() {
                    @Override
                    public void onResponse(Call<ResultVdieotags> call, Response<ResultVdieotags> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");
                        Toast.makeText(RetrofitActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(RetrofitActivity.this, "返回视频标签个数" + ((ResultVdieotags) response.body()).getResult().size(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResultVdieotags> call, Throwable t) {
                        Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        this.findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://bubbler.labs.douban.com/j/user/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                Call<DoubanUser> call = userBiz.addUser(new DoubanUser("http://www.douban.com/people/ahbei",
                        "http://www.douban.com/people/ahbei",
                        "zhangsan"));
                call.enqueue(new Callback<DoubanUser>() {
                    @Override
                    public void onResponse(Call<DoubanUser> call, Response<DoubanUser> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");
                        Toast.makeText(RetrofitActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DoubanUser> call, Throwable t) {
                        Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        this.findViewById(R.id.btn_FormUrlEncoded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.yundao91.cn/ssh2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                String pwd = LoginEncrypt.Enc("111111");
                Call<UserResult> call = userBiz.login("login", "15281060117", pwd);
                call.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");

                        Toast.makeText(RetrofitActivity.this, ((UserResult) response.body()).getResult() + "\n sessionid为：" + ((UserResult) response.body()).getJSESSIONID(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        this.findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strings.size() == 0) {
                    Toast.makeText(RetrofitActivity.this, "先选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < strings.size(); i++) {
                    String suffix = strings.get(i).substring(strings.get(i).lastIndexOf(".", strings.get(i).length()));
                    String uuid = UUID.randomUUID().toString();
                    String file_tmp_name_tk = "_tk" + suffix;
                    String file_tk_path = strings.get(i).substring(0, strings.get(i).lastIndexOf(".")) + file_tmp_name_tk;
                    ImageFactory ImFct = new ImageFactory();
                    try {
                        ImFct.ratioAndGenThumb(strings.get(i), file_tk_path, 500, 500, false);
                        strings_tk.add(file_tk_path);
                        Log.i("dddddfdfd", file_tk_path + "\n" + strings.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }
                Message message = handler.obtainMessage();
                message.what = 4;
                handler.sendMessage(message);

            }
        });
        this.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_progress.setText("progress " + "0%");
                ActivityCompat.requestPermissions(RetrofitActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
                ActivityCompat.requestPermissions(RetrofitActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
//                http://utouu-web-dev.oss-cn-hangzhou.aliyuncs.com/union/1488715251012.pdf
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.yundao91.cn/ssh2/appserver/yundao/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                Call<ResponseBody> call = userBiz.downloadTest();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        taltal_lenth = response.body().contentLength();
                        final InputStream is = response.body().byteStream();
                        //save file
                        save_file_and_updateProgress(is);

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        tv_progress = (TextView) this.findViewById(R.id.tv_progress);
        this.findViewById(R.id.btn_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strings.clear();
                startActivityForResult(new Intent(RetrofitActivity.this, ChooseAllPictures.class), 202);
            }
        });
        this.findViewById(R.id.btn_choose_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strings.clear();
                startActivityForResult(new Intent(RetrofitActivity.this, ChooseAllPictures.class).putExtra("is_only_one", "is_only_one"), 202);

            }
        });
        this.findViewById(R.id.btn_upload_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strings.size() == 0) {
                    Toast.makeText(RetrofitActivity.this, "先选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < strings.size(); i++) {
                    String suffix = strings.get(i).substring(strings.get(i).lastIndexOf(".", strings.get(i).length()));
                    String uuid = UUID.randomUUID().toString();
                    String file_tmp_name_tk = "_tk" + suffix;
                    String file_tk_path = strings.get(i).substring(0, strings.get(i).lastIndexOf(".")) + file_tmp_name_tk;
                    ImageFactory ImFct = new ImageFactory();
                    try {
                        ImFct.ratioAndGenThumb(strings.get(i), file_tk_path, 500, 500, false);
                        strings_tk.add(file_tk_path);
                        Log.i("dddddfdfd", file_tk_path + "\n" + strings.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }
                Message message = handler.obtainMessage();
                message.what = 5;
                handler.sendMessage(message);
            }
        });


        this.findViewById(R.id.btn_interceptor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.i("aaaaaaaaaaaaaa", "message====" + message);
                        Message message1 = handler.obtainMessage();
                        message1.what = 9;
                        Bundle bundle = new Bundle();
                        bundle.putString("message", message);
                        message1.setData(bundle);
                        handler.sendMessage(message1);
                    }
                });
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();
                                Log.i("zzz", "request====111111111111111111111111111111");
                                Log.i("zzz", "request====" + request.headers().toString());
                                okhttp3.Response proceed = chain.proceed(request);
                                Log.i("zzz", "proceed====" + proceed.headers().toString());
                                return proceed;
                            }
                        }).addInterceptor(httpLoggingInterceptor).build();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.yundao91.cn/ssh2/").client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
                String pwd = LoginEncrypt.Enc("111111");
                Call<UserResult> call = userBiz.login("login", "15281060117", pwd);
                call.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");

                        Toast.makeText(RetrofitActivity.this, ((UserResult) response.body()).getResult() + "\n sessionid为：" + ((UserResult) response.body()).getJSESSIONID(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        this.findViewById(R.id.btn_interceptor_secion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                OkHttpClient client = new OkHttpClient.Builder()
//                        .addInterceptor(new Interceptor() {
//                            @Override
//                            public okhttp3.Response intercept(Chain chain) throws IOException {
//                                Request request = chain.request();
//                                Request.Builder builder1 = request.newBuilder();
//                                Request build = builder1.addHeader("apikey", "ac7c302dc489a69082cbee6********").build();
//                                return chain.proceed(build);
//                            }
//                        }).retryOnConnectionFailure(true)
//                        .build();

                Toast.makeText(RetrofitActivity.this, "请看日志 logcat", Toast.LENGTH_SHORT).show();


                Idoubanuser idoubanuser = ECNetWorkUtil.getInstance();
                String pwd = LoginEncrypt.Enc("111111");
                Call<UserResult> call = idoubanuser.login("login", "15281060117", pwd);
                call.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                        Logger.i("onResponse", "normalGet:" + response.body() + "");

                        Toast.makeText(RetrofitActivity.this, ((UserResult) response.body()).getResult() + "\n sessionid为：" + ((UserResult) response.body()).getJSESSIONID(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        this.findViewById(R.id.btn_rx_retrotif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String pwd = LoginEncrypt.Enc("111111");

                String tel = "15281060117";
                String base_url = "http://www.yundao91.cn/ssh2/";

                new Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                        .create(Idoubanuser.class)
                        .login_rx("login", tel, pwd)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new Observer<UserResult>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {

                            }

                            @Override
                            public void onNext(UserResult userResult) {
                                Logger.i(userResult.getResult());
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {
                            }
                        });


            }
        });
    }

    public interface Iprogress {
        public void update(int progress);
    }

    private void save_file_and_updateProgress(final InputStream is) {
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(), "laiya.apk");
                    ab_file_path = file.getAbsolutePath();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    int progress_len = 0;


                    while ((len = bis.read(buffer)) != -1) {
                        progress_len = progress_len + len;
                        fos.write(buffer, 0, len);
                        fos.flush();
                        Message message = new Message();
                        Bundle b = new Bundle();
                        b.putLong("progress", progress_len);

                        message.setData(b);
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    Message message1 = handler.obtainMessage();
                    message1.what = 2;
                    handler.sendMessage(message1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    ArrayList<String> strings = new ArrayList<>();
    ArrayList<String> strings_tk = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        strings = data.getStringArrayListExtra("CHECKIMGS");
        Toast.makeText(this, "选择了" + strings.size() + "张图片", Toast.LENGTH_SHORT).show();
    }

    private String ab_file_path;
    private TextView tv_progress;
    long taltal_lenth;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int progress = (int) (msg.getData().getLong("progress") * 100 / taltal_lenth);
                    Log.i("handleMessage", "进度" + progress + "==" + taltal_lenth + "==" + msg.getData().getLong("progress"));
                    tv_progress.setText("progress " + progress + "%");
                    break;
                case 2:
                    Toast.makeText(RetrofitActivity.this, "下载完毕", Toast.LENGTH_SHORT).show();
                    Log.i("handleMessage", "下载完成");
                    tv_progress.setText("下载完成" + ab_file_path);

                    break;
                case 4:
                    upload_file1();

                    break;
                case 5:
                    Retrofit retrofit5 = new Retrofit.Builder()
                            .baseUrl("http://www.yundao91.cn/ssh2/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Map<String, RequestBody> photos5 = new HashMap<>();
                    long l5 = 0;
                    Log.i("total_len", "" + l5);
                    for (int i = 0; i < strings.size(); i++) {
                        File file = new File(strings.get(i));
//                        RequestBody photo = UploadFileRequestBody.create(MediaType.parse("image/png"), file);
                        UploadFileRequestBody photo = new UploadFileRequestBody(file, l5);

                        String file_name = file.getName();
                        photos5.put("photos\"; filename=\"" + file_name, photo);

                        File filetk = new File(strings_tk.get(i));
//                        RequestBody phototk = UploadFileRequestBody.create(MediaType.parse("image/png"), filetk);
                        UploadFileRequestBody phototk = new UploadFileRequestBody(filetk, l5);
                        String file_name_tk = filetk.getName();
                        photos5.put("photos\"; filename=\"" + file_name_tk, phototk);
                        phototk.setIprogress(new Iprogress() {
                            @Override
                            public void update(int progress) {
                                Message message = new Message();
                                message.what = 6;
                                message.arg2 = progress;
                                handler.sendMessage(message);
                            }
                        });
                    }
//                    photos.put("cmd", RequestBody.create(null, "addUserTracePic"));
//                    photos.put("tel", RequestBody.create(null, "15281060117"));
//                    photos.put("title", RequestBody.create(null, "这是标题哦"));
//                    photos.put("adress", RequestBody.create(null, "成都市"));
//                    photos.put("isshow", RequestBody.create(null, "0"));

                    Idoubanuser userBiz5 = retrofit5.create(Idoubanuser.class);
                    Call<Object> call5 = userBiz5.upload_file("http://www.yundao91.cn/ssh2/userinfo?" +
                                    "cmd=addUserTracePic&tel=15281060117&title=这是标题&adress=成都市&isshow=0",
                            photos5);
                    call5.enqueue(new retrofit2.Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            Logger.i("onResponse", "normalGet:" + response.body() + "");
                            Toast.makeText(RetrofitActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            Log.i("upload_progress", "上传成功");
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("upload_progress", "上传失败");
                            com.utsoft.myapplication.Myapplication.toatal_len = 0;
                        }
                    });
                    break;
                case 6:
                    tv_vpload_progress.setText(msg.arg2 + "%");
                    Log.i("tv_vpload_progress", "" + msg.arg2 + "%");
                    break;
                case 9:
                    Bundle bundle = msg.getData();
                    Toast.makeText(RetrofitActivity.this, bundle.getString("message"), Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    };

    private void upload_file2() {
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.yundao91.cn/ssh2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String, RequestBody> photos = new HashMap<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://www.yundao91.cn/ssh2/");

        long l = 0;
        for (int i = 0; i < strings.size(); i++) {
            File file = new File(strings.get(i));
            RequestBody photo = new UploadFileRequestBody(file, l);

            String file_name = file.getName();
            photos.put("photos\"; filename=\"" + file_name, photo);

            File filetk = new File(strings_tk.get(i));
            RequestBody phototk = new UploadFileRequestBody(filetk, l);
            String file_name_tk = filetk.getName();
            photos.put("photos\"; filename=\"" + file_name_tk, phototk);
        }
        Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
        Call<Object> call = userBiz.upload_file("http://www.yundao91.cn/ssh2/userinfo?" +
                        "cmd=addUserTracePic&tel=15281060117&title=这是标题&adress=成都市&isshow=0",
                photos);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Logger.i("onResponse", "normalGet:" + response.body() + "");
                Toast.makeText(RetrofitActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                Log.i("upload_progress", "上传成功");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("upload_progress", "上传失败");
                Myapplication.toatal_len = 0;
            }
        });
    }

    private void upload_file1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.yundao91.cn/ssh2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String, RequestBody> photos = new HashMap<>();
        long l = 0;
//                    for (int i = 0; i < strings.size(); i++) {
//                        File file = new File(strings.get(i));
//                        RequestBody mRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
//                        File filetk = new File(strings_tk.get(i));
//                        RequestBody mRequestBodytk = RequestBody.create(MediaType.parse("image/png"), filetk);
//                        try {
//                            long longfile=mRequestBody.contentLength();
//                            long longfile_tk=mRequestBodytk.contentLength();
//                            l = l + longfile + longfile_tk;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
        Log.i("total_len", "" + l);
        for (int i = 0; i < strings.size(); i++) {
            File file = new File(strings.get(i));
//                        RequestBody photo = UploadFileRequestBody.create(MediaType.parse("image/png"), file);
            RequestBody photo = new UploadFileRequestBody(file, l);

            String file_name = file.getName();
            photos.put("photos\"; filename=\"" + file_name, photo);

            File filetk = new File(strings_tk.get(i));
//                        RequestBody phototk = UploadFileRequestBody.create(MediaType.parse("image/png"), filetk);
            RequestBody phototk = new UploadFileRequestBody(filetk, l);
            String file_name_tk = filetk.getName();
            photos.put("photos\"; filename=\"" + file_name_tk, phototk);
        }
//                    photos.put("cmd", RequestBody.create(null, "addUserTracePic"));
//                    photos.put("tel", RequestBody.create(null, "15281060117"));
//                    photos.put("title", RequestBody.create(null, "这是标题哦"));
//                    photos.put("adress", RequestBody.create(null, "成都市"));
//                    photos.put("isshow", RequestBody.create(null, "0"));

        Idoubanuser userBiz = retrofit.create(Idoubanuser.class);
        Call<Object> call = userBiz.upload_file("http://www.yundao91.cn/ssh2/userinfo?" +
                        "cmd=addUserTracePic&tel=15281060117&title=这是标题&adress=成都市&isshow=0",
                photos);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Logger.i("onResponse", "normalGet:" + response.body() + "");
                Toast.makeText(RetrofitActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                Log.i("upload_progress", "上传成功");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("upload_progress", "上传失败");
                Myapplication.toatal_len = 0;
            }
        });
    }


}
