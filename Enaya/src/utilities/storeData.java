package utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abanoub Wagdy on 1/26/2016.
 */
public class storeData {

    private Context context;
    String DATABASE_NAME = "DWC";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String TAG = "StoreData";

    public storeData(Context ctx) {
        super();
        this.context = ctx;
        sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIsIntroDisplayed(boolean introDisplayed) {
        editor.putBoolean("introDisplayed", introDisplayed);
        editor.commit();
    }

    public boolean getIsIntroDisplayed() {
        boolean UserID = sharedPreferences.getBoolean("introDisplayed",
                false);
        return UserID;
    }
}
