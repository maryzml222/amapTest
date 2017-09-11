package com.example.mylibrary.domain.usecase;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T, R> {

    private Subscription subscription = Subscriptions.empty();

    public void subscribe(Observer<T> UseCaseSubscriber, R params) {

        UseCase.this.subscription = this.interactor(params)//
                .onBackpressureBuffer()//
                .take(1)//
                .filter(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return !subscription.isUnsubscribed();
                    }
                }).subscribe(UseCaseSubscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract Observable<T> interactor(R params);
}
