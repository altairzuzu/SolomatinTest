package ru.fordexsys.solomatintest.data.local;

import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.fordexsys.solomatintest.data.model.Photo;
import rx.Observable;

import static dagger.internal.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class LocalDataSource {

    private static final String TAG = "LocalDataSource";

    @Nullable
    private static LocalDataSource INSTANCE;

    private Realm realm2;

    private LocalDataSource() {
//        Log.d(TAG, "LocalDataSource: " + (Looper.myLooper() == Looper.getMainLooper()));
        realm2 = Realm.getDefaultInstance();
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void savePhotos(List<Photo> photoList) {

    }

    public Observable<List<Photo>> getPhotos() {
        return Observable.fromCallable(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<Photo> realmResults = realm.where(Photo.class)
                        .findAll();
//                int size = realmResults.size();
//                List<Ride> rideList;
//                if (size > 20) {
//                    rideList = realm.copyFromRealm(realmResults, 1).subList(0, 20);
//                } else {
                List<Photo> rideList = realm.copyFromRealm(realmResults);
//                }
                realm.close();
                return rideList;
            }
        });
    }

}
