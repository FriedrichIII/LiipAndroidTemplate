package ch.template.rx;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RxOperations {

    private static final Observable.Transformer schedulerTransformer = new Observable.Transformer<Object, Object>() {
        @Override
        public Observable<Object> call(Observable<Object> observable) {
            return observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    };

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>)schedulerTransformer;
    }

    public static void run(Observable observable) {
        ((ConnectableObservable)observable).connect();
    }

    public static <T> T getAndWait(Observable<T> observable, Subscriber<T> subscriber) {
        final Semaphore semaphore = new Semaphore(0);

        final AtomicReference<T> value = new AtomicReference<>();
        final AtomicReference<Throwable> exception = new AtomicReference<>();
        observable
                .doOnNext(t -> {
                    value.set(t);
                    semaphore.release();
                })
                .doOnError(throwable -> {
                    exception.set(throwable);
                    semaphore.release();
                })
                .subscribe(subscriber);

        try {
            boolean gotValue = semaphore.tryAcquire(6, TimeUnit.SECONDS);
            if (!gotValue) {
                throw new RuntimeException("Timeout while waiting for value");
            }
            if (exception.get() != null) {
                throw new RuntimeException(exception.get());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted while waiting for value of type %s", e);
        }
        return value.get();
    }

}

