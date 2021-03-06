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

    public void savePhotos(final List<Photo> photoList) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(photoList);
            }
        });
        realm.close();
    }

    public Observable<List<Photo>> getPhotos() {
        return Observable.fromCallable(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<Photo> realmResults = realm.where(Photo.class).findAll();
                List<Photo> photoList;
                if (realmResults.size() > 40) {
                    photoList = realm.copyFromRealm(realmResults, 1).subList(0, 40);
                } else {
                    photoList = realm.copyFromRealm(realmResults);
                }
                realm.close();
                return photoList;
            }
        });
    }

    public void clearPhotos() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//        realm.deleteAll();
        realm.delete(Photo.class);
        realm.commitTransaction();
        realm.close();
    }

}
