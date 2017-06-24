package ru.fordexsys.solomatintest.ui;

import java.util.List;

import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.base.Presenter;
import ru.fordexsys.solomatintest.util.NetworkUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter implements Presenter<MainMvpView> {

    DataManager dataManager;
    private MainMvpView view;
    private Subscription subscription;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
        compositeSubscription.unsubscribe();
    }

    public void getPhotos(boolean remote, int offset, int count) {

        if (offset == 0) {

            subscription = dataManager.getPhotos(remote, offset, count)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Action1<List<Photo>>() {
                        @Override
                        public void call(List<Photo> rides) {
                            view.onGetPhotosSuccess(rides);
                        }
                    })
                    .onErrorReturn(new Func1<Throwable, List<Photo>>() {
                        @Override
                        public List<Photo> call(Throwable e) {
                            view.onGetPhotosError(NetworkUtil.parseError(e));
                            return null;
                        }
                    })
                    .subscribe();
            compositeSubscription.add(subscription);

        } else {

            subscription = dataManager.getPhotos(remote, offset, count)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Action1<List<Photo>>() {
                        @Override
                        public void call(List<Photo> rides) {
                            view.onGetMorePhotosSuccess(rides);
                        }
                    })
                    .onErrorReturn(new Func1<Throwable, List<Photo>>() {
                        @Override
                        public List<Photo> call(Throwable e) {
                            view.onGetMorePhotosError(NetworkUtil.parseError(e));
                            return null;
                        }
                    })
                    .subscribe();
            compositeSubscription.add(subscription);

        }
    }

}
