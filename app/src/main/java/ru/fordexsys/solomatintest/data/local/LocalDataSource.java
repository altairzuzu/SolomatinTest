package ru.fordexsys.solomatintest.data.local;

import android.support.annotation.Nullable;

import io.realm.Realm;

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



}
