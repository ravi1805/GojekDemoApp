package com.gojek.sample.domain.interactor;

import androidx.annotation.NonNull;

import com.gojek.sample.domain.thread.IBackgroundThreadExecutor;
import com.gojek.sample.domain.thread.IUIThread;

import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseUseCase<I, O> {


    private final IBackgroundThreadExecutor backgroundExecutor;
    private final IUIThread mainThread;
    private CompositeDisposable compositeDisposable;

    public BaseUseCase(IBackgroundThreadExecutor backgroundExecutor, IUIThread mainThread) {
        this.backgroundExecutor = backgroundExecutor;
        this.mainThread = mainThread;
        this.compositeDisposable = new CompositeDisposable();
    }

    @NonNull
    public abstract Observable<O> build(I params);

    public void execute(@NonNull DisposableObserver<O> observer, I params) {

        dispose();

        final Observable<O> observable = this.build(params)
                .subscribeOn(Schedulers.from(backgroundExecutor))
                .observeOn(mainThread.getMainThread());
        addDisposable(observable.subscribeWith(observer));

    }

    public void dispose() {

        synchronized (BaseUseCase.class) {
            if (!compositeDisposable.isDisposed()) {

                // clear will also dispose
                compositeDisposable.clear();

                // renew object so that this use case can be executed again
                this.compositeDisposable = new CompositeDisposable();

            }
        }
    }

    private void addDisposable(@NonNull Disposable disposable) {
        synchronized (BaseUseCase.class) {
            Preconditions.checkNotNull(disposable);
            Preconditions.checkNotNull(this.compositeDisposable);
            this.compositeDisposable.add(disposable);
        }
    }

}
