package com.quguang.springcloudfeign.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by 瞿广 on 2020/4/4 0004.
 */
public class Demo {


    public static void main(String[] args) {

        String TAG = "tag_";
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World1");
                subscriber.onNext("Hello World2");
                subscriber.onNext("Hello World3");

                subscriber.onCompleted();
            }
        });
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println(TAG+ "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG+"onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println(TAG+"onNext: s = " + s);
            }
        };

        observable.subscribe(observer);


    }

}
