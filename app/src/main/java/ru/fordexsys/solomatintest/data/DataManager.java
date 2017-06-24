package ru.fordexsys.solomatintest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.fordexsys.solomatintest.data.local.LocalDataSource;
import ru.fordexsys.solomatintest.data.local.PreferencesHelper;
import ru.fordexsys.solomatintest.data.model.Ride;
import ru.fordexsys.solomatintest.data.remote.VKApi;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class DataManager {

    public static final String TAG = "DataManager";

    @Nullable
    private static DataManager INSTANCE = null;

    @NonNull
    private final VKApi remoteDataSource;

    @NonNull
    private final LocalDataSource localDataSource;

    private final PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(@NonNull VKApi VKApi,
                       @NonNull LocalDataSource localDataSource,
                       @NonNull PreferencesHelper preferencesHelper) {
        this.remoteDataSource = checkNotNull(VKApi);
        this.localDataSource = checkNotNull(localDataSource);
        this.preferencesHelper = checkNotNull(preferencesHelper);
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    @NonNull
    public LocalDataSource getLocalDataSource() {
        return localDataSource;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

    public Observable<List<Ride>> getPhotos(boolean remote) {

        if (remote) {
            String token = preferencesHelper.getToken();
            return remoteDataSource
                    .getPhotos(new VKApi().PhotosRequest(token))
                    .doOnNext(new Action1<List<Ride>>() {
                        @Override
                        public void call(List<Ride> rideList) {
                            localDataSource.savePhotos(rideList, preferencesHelper.getCurrentUserId());
                            saveLastBoundsToPrefs(center, distance, zoom);
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Ride>>>() {
                        @Override
                        public Observable<? extends List<Ride>> call(Throwable e) {
                            e.printStackTrace();
                            return localDataSource.getRides();
                        }
                    });
        } else {
            return localDataSource.getRides();
        }
    }

    public Observable<List<Ride>> getMorePhotos(boolean remote, int page) {
        if (remote) {
            String token = preferencesHelper.getToken();
            return remoteDataSource
                    .myRides(new VKApi.MyRidesRequest(token, page, 20))
                    .doOnNext(new Action1<List<Ride>>() {
                        @Override
                        public void call(List<Ride> rideList) {
                            localDataSource.saveRides(rideList, preferencesHelper.getCurrentUserId());
                            preferencesHelper.putUpdateMyRides(false);
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Ride>>>() {
                        @Override
                        public Observable<? extends List<Ride>> call(Throwable e) {
                            e.printStackTrace();
//                            Log.d(TAG, "onErrorResumeNext: " + (Looper.myLooper() == Looper.getMainLooper()));
//                            return localDataSource.getMyRides();
                            return Observable.error(e);
                        }
                    });
        } else {
//            Log.d(TAG, "!remote: " + (Looper.myLooper() == Looper.getMainLooper()));
            return localDataSource.getMyRides();
        }
    }



}
