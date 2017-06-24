package ru.fordexsys.solomatintest.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Observable;

import ru.fordexsys.solomatintest.RxApplication;


/**
 * Created by Вадим on 12.09.2016.
 */
public class OnlineReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOnline = RxApplication.get(context).isConnectToInternet();
        NetworkObservable.getInstance().setChanged();
        NetworkObservable.getInstance().notifyObservers(isOnline);
    }

    public static class NetworkObservable extends Observable {
        private static NetworkObservable instance = null;

        private NetworkObservable() {
            // Exist to defeat instantiation.
        }

        @Override
        public void setChanged() {
            super.setChanged();
        }

        public static NetworkObservable getInstance(){
            if(instance == null){
                instance = new NetworkObservable();
            }
            return instance;
        }
    }



}
