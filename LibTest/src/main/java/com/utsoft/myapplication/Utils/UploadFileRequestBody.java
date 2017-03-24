package com.utsoft.myapplication.Utils;

import android.util.Log;

import com.utsoft.myapplication.RetrofitActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by wangzhu on 2017/3/9.
 * func:
 */

public class UploadFileRequestBody extends RequestBody {
    @Override
    public MediaType contentType() {
        return null;
    }

    public void setIprogress(RetrofitActivity.Iprogress iprogress) {
        this.iprogress = iprogress;
    }

    private RetrofitActivity.Iprogress iprogress;

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    public static long tatal_len = 0;

    public UploadFileRequestBody(File file, long tatal_len) {
        this.mRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        try {
            long aaa = contentLength();
            Log.i("", "" + aaa);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tatal_len = tatal_len;

    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private BufferedSink bufferedSink;
    private RequestBody mRequestBody;

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                Log.i("upload_progress", "toatal" + contentLength);
                if (null != iprogress) {
                    iprogress.update((int) (bytesWritten * 100 / contentLength()));
                }
                //回调上传接口
//                Log.i("upload_progress", "toatal" + tatal_len + "\ncurrent" + bytesWritten + "\nprogress\n" + bytesWritten * 100 / tatal_len);
            }
        };
    }
}