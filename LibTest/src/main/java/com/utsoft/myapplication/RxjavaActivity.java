package com.utsoft.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.rxjava2.android.samples.ui.OperatorsActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.internal.operators.observable.ObservableJoin;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhu on 2017/3/6.
 * func:rxandroid 简单描述
 */

public class RxjavaActivity extends Activity {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final String TAG = "RxjavaActivity";

    String aa = "我是耗时操作得到的结果";
    Observable observable;
    //    just(T...): 将传入的参数依次发送出来。
    String[] words = {"w", "ww", "www"};
    //    fromArray(T[]) /  fromArray(T... items)) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
    Observable observable1;
    static Observable<Long> Observablekong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxandroid_activity);
        observable1 = Observable.fromArray(words);
        observable = Observable.just(aa, "qqq", "qqqq");
        Observablekong = Observable.interval(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());

        this.findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onRunSchedulerExampleButtonClicked();
                observable1.subscribe(observer);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer);
                Observable.just(System.currentTimeMillis() + "").subscribeWith(observer);
                Observable observable = Observable.defer(new Callable<ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> call() throws Exception {
                        return Observable.just(System.currentTimeMillis() + "defer");
                    }
                });
                observable.subscribeWith(observer);
            }

        });
        this.findViewById(R.id.btn_range).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observable.range(10, 5).subscribeWith(intobserver);
            }
        });
        this.findViewById(R.id.btn_Interval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observablekong.subscribeWith(Longobserver);
            }
        });
        this.findViewById(R.id.btn_unsubscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observablekong.unsubscribeOn(AndroidSchedulers.mainThread());
            }
        });
        this.findViewById(R.id.btn_buffer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).buffer(2, 3).subscribeWith(listObservable);
            }
        });
        this.findViewById(R.id.btn_FlatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable observable = Observable.just(1, 2, 3).flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        return Observable.just("flagmap" + integer);
                    }
                });
                observable.subscribeWith(observer);

            }
        });
        this.findViewById(R.id.btn_FlatMapIterable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        this.findViewById(R.id.btn_group_by).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(1, 2, 3, 4).groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return (integer % 2 == 0) ? "偶数组" : "奇数组";
                    }
                }).subscribeWith(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object value) {

                        Toast.makeText(RxjavaActivity.this, value.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            }
        });
        this.findViewById(R.id.btn_ElementAt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(0, 1, 2, 3, 4, 5).elementAt(4).subscribeWith(new MaybeObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Object value) {

                        Toast.makeText(RxjavaActivity.this, "" + value, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        this.findViewById(R.id.btn_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(0, 1, 2, 3, 4, 5, 6).filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        if (integer % 2 == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }).subscribeWith(intobserver);
            }
        });
        this.findViewById(R.id.btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        this.findViewById(R.id.btn_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                https:
//
                startActivity(new Intent(RxjavaActivity.this, OperatorsActivity.class));
            }
        });

    }

    Observer<List<Integer>> listObservable = new Observer<List<Integer>>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Integer> value) {
            Toast.makeText(RxjavaActivity.this, value.toString(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();

    }

    void onRunSchedulerExampleButtonClicked() {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        Logger.i(TAG, "onComplete()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d(TAG, "onNext(" + string + ")");
                    }
                }));
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(5000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    // 创建观察者
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
//            Log.i(TAG, "Item: " + s);
            Logger.i("Item: " + s);
            Toast.makeText(RxjavaActivity.this, "Item: " + s, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onError(Throwable e) {
            Logger.i(TAG, "Error!");
        }

        @Override
        public void onComplete() {

        }
    };
    // 创建观察者
    Observer<Integer> intobserver = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Integer s) {
//            Log.i(TAG, "Item: " + s);
            Logger.i("Item: " + s);
            Toast.makeText(RxjavaActivity.this, "" + s, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onError(Throwable e) {
            Logger.i(TAG, "Error!");
        }

        @Override
        public void onComplete() {

        }
    };

    // 创建观察者
    Observer<Long> Longobserver = new Observer<Long>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Long s) {
//            Log.i(TAG, "Item: " + s);
            Logger.i("Item: " + s);
            Toast.makeText(RxjavaActivity.this, "" + s, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onError(Throwable e) {
            Logger.i(TAG, "Error!");
        }

        @Override
        public void onComplete() {

        }
    };

    Subscriber<Long> longSubscriber = new Subscriber<Long>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(Long aLong) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };
}
