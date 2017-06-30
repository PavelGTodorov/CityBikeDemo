package at.ac.univie.hci.citybikedemo.utility;

import android.app.Application;
import android.content.Context;

/**
 * Utility class used to get the current Context of the Application
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
       super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    /**
     * @return The context of the application
     */
    public static Context getAppContext(){
        return MyApplication.context;
    }

}
