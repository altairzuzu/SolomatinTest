package ru.fordexsys.solomatintest.ui.detail;

import java.util.List;

import javax.inject.Inject;

import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.ui.MainMvpView;
import ru.fordexsys.solomatintest.ui.base.Presenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Altair on 25-Jun-17.
 */

public class DetailPresenter implements Presenter<DetailMvpView> {

    DataManager dataManager;
    private DetailMvpView view;
    private Subscription subscription;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject
    public DetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
        compositeSubscription.unsubscribe();
    }

    public void getPhotos(boolean remote, int offset, int count) {

        if (offset == 0) {

            subscription = dataManager.getLocalDataSource().getPhotos()
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
                            view.onGetPhotosError();
                            return null;
                        }
                    })
                    .subscribe();
            compositeSubscription.add(subscription);

        } else {

            subscription = dataManager.getMorePhotos(remote, offset, count)
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
                            view.onGetMorePhotosError();
                            return null;
                        }
                    })
                    .subscribe();
            compositeSubscription.add(subscription);

        }
    }
}
