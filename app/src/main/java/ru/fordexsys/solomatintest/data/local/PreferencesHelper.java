package ru.fordexsys.solomatintest.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import ru.fordexsys.solomatintest.injection.ApplicationContext;
import ru.fordexsys.solomatintest.util.SharedKeys;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

@Singleton
public class PreferencesHelper {

    @NonNull
    private final LocalDataSource localDataSource;

    private final SharedPreferences pref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context,
                             @NonNull LocalDataSource localDataSource) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.localDataSource = checkNotNull(localDataSource);
    }

    public void clear() {
        pref.edit().clear().apply();
    }


    public void putToken(String token) {
        pref.edit().putString(SharedKeys.TOKEN, token).apply();
    }

    public String getToken() {
        return pref.getString(SharedKeys.TOKEN, "");
    }

    public void putUserId(String token) {
        pref.edit().putString(SharedKeys.USERID, token).apply();
    }

    public String getUserId() {
        return pref.getString(SharedKeys.USERID, "");
    }


}
