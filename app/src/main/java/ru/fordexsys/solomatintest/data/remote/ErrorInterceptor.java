package ru.fordexsys.solomatintest.data.remote;//package ru.velogame.bikewithme.data.remote;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//
//import com.squareup.otto.Bus;
//
//import java.io.IOException;
//
//import javax.inject.Inject;
//
//import okhttp3.Interceptor;
//import okhttp3.Response;
//import ru.velogame.bikewithme.RxApplication;
//import ru.velogame.bikewithme.data.BusEvent;
//
///**
// * Created by Altair on 18-Nov-16.
// */
//
//public class ErrorInterceptor implements Interceptor {
//
//    @Inject
//    Bus eventBus;
////
////    public ErrorInterceptor(Context context) {
////        RxApplication.get(context).getComponent().inject(this);
////    }
////
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Response response = chain.proceed(chain.request());
//        if (response.code() == 500) {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    eventBus.post(new BusEvent.CommonError());
//                }
//            });
//        }
//        return response;
//    }
//
//}
