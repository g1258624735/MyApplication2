package com.example.administrator.myapplication.tools;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 响应式
 * Created by Seven on 2016/3/31.
 */
public class RxBus<T> {

    private static volatile RxBus rxBus;

    private Subject<T, T> subject = new SerializedSubject(PublishSubject.create());

    public static RxBus getDefault() {
        if (null == rxBus) {
            synchronized (RxBus.class) {
                if (null == rxBus) {
                    rxBus = new RxBus();
                }
            }
        }

        return rxBus;
    }

    /**
     * 发送一个对象到数列之中
     *
     * @param t
     */
    @SuppressWarnings("unchecked")
    public void post(T t) {
        subject.onNext(t);
    }

    /**
     * 接送到一个对象并进行过滤
     *
     * @param classzz
     * @return
     */

    public Observable<T> event(Class<T> classzz) {
        return subject.ofType(classzz);
    }

    public Observable<T> tObservable() {
        return subject;
    }
}
