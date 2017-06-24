package ru.fordexsys.solomatintest.data.remote;

import android.content.Context;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.fordexsys.solomatintest.data.model.Photo;
import rx.Observable;

//import com.facebook.stetho.okhttp3.StethoInterceptor;
//import okhttp3.logging.HttpLoggingInterceptor;

public interface VKApi {

    String ENDPOINT = "https://api.vk.com/method/";

    @POST("photos.getAll")
    Observable<List<Photo>> photos(@Body PhotosRequest ridesRequest);

    /********
     * Factory class that sets up a new ribot services
     *******/
    class Factory {

        public static VKApi makeBikeWithMeService(Context context) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(VKApi.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(VKApi.class);
        }

    }

    class PhotosRequest {
        private String token;

        public PhotosRequest(String token, int offset, int count) {
            this.token = token;
        }
    }



}