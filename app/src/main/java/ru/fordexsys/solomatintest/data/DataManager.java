package ru.fordexsys.solomatintest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import ru.fordexsys.solomatintest.data.local.LocalDataSource;
import ru.fordexsys.solomatintest.data.local.PreferencesHelper;
import ru.fordexsys.solomatintest.data.model.Photo;
import ru.fordexsys.solomatintest.data.model.PhotosResponse;
import ru.fordexsys.solomatintest.data.remote.VKApi;

import java.util.ArrayList;
import java.util.Date;
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

    public List<Photo> convertResponse(PhotosResponse photosResponse) {

        List<Photo> resultList = new ArrayList<>();

        if (photosResponse.getPhotosItems() != null &&
                photosResponse.getPhotosItems().getPhotoList() != null) {

            List<Photo> photoList = photosResponse.getPhotosItems().getPhotoList();
            for (int i = 0; i < photoList.size(); i++) {
                Photo photo = photoList.get(i);
                if (!TextUtils.isEmpty(photo.getPhoto_604())) {
                    photo.setPhotoSmall(photo.getPhoto_604());
                } else if (!TextUtils.isEmpty(photo.getPhoto_130())){
                    photo.setPhotoSmall(photo.getPhoto_130());
                } else {
                    photo.setPhotoSmall(photo.getPhoto_75());
                }
                if (!TextUtils.isEmpty(photo.getPhoto_2560())) {
                    photo.setPhotoBig(photo.getPhoto_2560());
                } else if (!TextUtils.isEmpty(photo.getPhoto_1280())){
                    photo.setPhotoBig(photo.getPhoto_1280());
                } else {
                    photo.setPhotoBig(photo.getPhoto_807());
                }
                if (photo.getLikes() != null) {
                    photo.setLikesCount(photo.getLikes().getCount());
                }
                if (photo.getReposts() != null) {
                    photo.setRepostsCount(photo.getReposts().getCount());
                }
                Date date = new Date(photo.getDate());
                photo.setUploaded(date);

                resultList.add(photo);
            }
        }

        return resultList;
    }

    public Observable<List<Photo>> getPhotos(boolean remote, int offset, int count) {

        if (remote) {
            String token = preferencesHelper.getToken();
            return remoteDataSource
                    .photos(token, 1, offset, count, 5.65D)
                    .map(new Func1<PhotosResponse, List<Photo>>() {
                        @Override
                        public List<Photo> call(PhotosResponse response) {
                            return convertResponse(response);
                        }
                    })
                    .doOnNext(new Action1<List<Photo>>() {
                        @Override
                        public void call(List<Photo> photoList) {
                            localDataSource.clearPhotos();
                            localDataSource.savePhotos(photoList);
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
                    .photos(token, 1, offset, count, 5.65D)
                    .map(new Func1<PhotosResponse, List<Photo>>() {
                        @Override
                        public List<Photo> call(PhotosResponse response) {
                            return convertResponse(response);
                        }
                    })
                    .doOnNext(new Action1<List<Photo>>() {
                        @Override
                        public void call(List<Photo> photoList) {
                            localDataSource.savePhotos(photoList);
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

    public void clearPhotos() {
        localDataSource.clearPhotos();
    }



}
