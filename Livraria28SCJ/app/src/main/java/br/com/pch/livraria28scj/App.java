package br.com.pch.livraria28scj;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by douglas.teixeira on 10/04/2017.
 */

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
