package ru.fordexsys.solomatintest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.fordexsys.solomatintest.data.local.LocalDataSource;
import ru.fordexsys.solomatintest.data.local.PreferencesHelper;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.data.model.PhotosResponse;
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

    public Observable<List<Photo>> getPhotos(boolean remote, int offset, int count) {

        if (remote) {
            String token = preferencesHelper.getToken();
            return remoteDataSource
                    .photos(token, 1, offset, count)
                    .doOnNext(new Action1<PhotosResponse>() {
                        @Override
                        public void call(PhotosResponse photosResponse) {
                            localDataSource.savePhotos(photosResponse.getPhotosItems().getPhotoList());
                        }
                    })
                    .map(new Func1<PhotosResponse, List<Photo>>() {
                        @Override
                        public List<Photo> call(PhotosResponse response) {
                            return response.getPhotosItems().getPhotoList();
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Photo>>>() {
                        @Override
                        public Observable<? extends List<Photo>> call(Throwable e) {
                            e.printStackTrace();
                            return localDataSource.getPhotos();
                        }
                    });
        } else {
            return localDataSource.getPhotos();
        }
    }

    public Observable<List<Photo>> getMorePhotos(boolean remote, int offset, int count) {
        if (remote) {
            String token = preferencesHelper.getToken();
            return remoteDataSource
                    .photos(token, 1, offset, count)
                    .doOnNext(new Action1<PhotosResponse>() {
                        @Override
                        public void call(PhotosResponse response) {
                            localDataSource.savePhotos(response.getPhotosItems().getPhotoList());
                        }
                    })
                    .map(new Func1<PhotosResponse, List<Photo>>() {
                        @Override
                        public List<Photo> call(PhotosResponse response) {
                            return response.getPhotosItems().getPhotoList();
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Photo>>>() {
                        @Override
                        public Observable<? extends List<Photo>> call(Throwable e) {
                            e.printStackTrace();
                            return Observable.error(e);
                        }
                    });
        } else {
            return localDataSource.getPhotos();
        }
    }



}
