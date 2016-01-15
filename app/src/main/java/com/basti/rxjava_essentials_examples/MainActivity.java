package com.basti.rxjava_essentials_examples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private String[] strings = {"abc","cde","cde","asdfw","asdfw","weffwe","a","a","ewry"};
    private int[] datas = {20,20,20,21,22,21,22,23,23};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);


        //initDefer();

        //initRange();

        ///initInterval();

        //initTimer1();

        //initTimer2();

        //initFilter();

        //initTake();

        //initTakeLast();

        //initDistinct();

        initDistinctUntilChanged();
    }

    private void initDistinctUntilChanged() {
        Observable.create(subscriber -> {
            subscriber.onNext(20);
            subscriber.onNext(20);
            subscriber.onNext(20);
            subscriber.onNext(21);
            subscriber.onNext(22);
            subscriber.onNext(21);
            subscriber.onNext(23);
            subscriber.onCompleted();
        }).distinctUntilChanged().subscribe(number -> Log.i("TAG",number+""));
    }

    private void initDistinct() {
        Observable<String> observable7 = Observable.from(strings).distinct();
        observable7.subscribe(string -> {
            Log.i("TAG",string);
        });
    }

    private void initTakeLast() {
        //a b c d e f g
        //结果是 e f g 不是 g f e
        Observable<String> observable6 = Observable.from(strings).takeLast(3);
        observable6.subscribe(string -> {
            Log.i("TAG",string);
        });
    }

    private void initTake() {
        Observable<String> observable5 = Observable.from(strings).take(3);
        observable5.subscribe(string -> {
            Log.i("TAG",string);
        });
    }

    private void initFilter() {

       Observable<String> observable4 =  Observable.from(strings).filter(string -> string.length()==3);
        observable4.subscribe(string -> {
            Log.i("TAG",string);
        });
    }

    private void initTimer2() {
        Observable<Long> observable3 = Observable.timer(3,5, TimeUnit.SECONDS);
        observable3.subscribe(number -> {
            Log.i("TAG", String.valueOf(number));
        });
    }

    private void initTimer1() {

        Observable<Long> observable3 = Observable.timer(3, TimeUnit.SECONDS);
        observable3.subscribe(number -> {
            Log.i("TAG", String.valueOf(number));
        });

    }

    private void initDefer() {
        Observable<Integer> deferred = Observable.defer(this::getInt);

        button.setOnClickListener(v -> {
                deferred.subscribe(number -> {
                    Log.i("TAG", String.valueOf(number));
                });
            });
    }

    private void initInterval() {
        Observable<Long> observable2 = Observable.interval(3, TimeUnit.SECONDS);
        observable2.subscribe(number -> {
           Log.i("TAG", String.valueOf(number));
        });
    }

    private void initRange() {
        Observable<Integer> observable1 = Observable.range(10, 5);
        observable1.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("range onCompleted", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("range onError","onError");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("TAG",String.valueOf(integer));
            }
        });
    }

    public Observable<Integer> getInt() {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()){
                return ;
            }
            Log.i("TAG","GETINT");
            subscriber.onNext(50);
            subscriber.onCompleted();
        });
    }
}
