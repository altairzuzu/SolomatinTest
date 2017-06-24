package ru.fordexsys.solomatintest.ui;

import ru.fordexsys.solomatintest.data.DataManager;
import ru.fordexsys.solomatintest.ui.base.Presenter;
import ru.fordexsys.solomatintest.util.NetworkUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Altair on 11-Apr-17.
 */

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

    public void getMyRaids(boolean remote, int page) {
        subscription = dataManager.getPhotos(remote, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<List<Ride>>() {
                    @Override
                    public void call(List<Ride> rides) {
                        view.onGetMyRidesSuccess(rides);
                    }
                })
                .onErrorReturn(new Func1<Throwable, List<Ride>>() {
                    @Override
                    public List<Ride> call(Throwable e) {
                        view.onGetMyRidesError(NetworkUtil.parseError(e));
                        return null;
                    }
                })
                .subscribe();

        compositeSubscription.add(subscription);
    }

}
