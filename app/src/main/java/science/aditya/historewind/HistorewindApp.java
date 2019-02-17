package science.aditya.historewind;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class HistorewindApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
