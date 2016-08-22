package utilities;

import android.content.Context;
import android.content.Intent;

import com.cloudconcept.DashboardActivity;
import com.cloudconcept.HomepageActivity;
import com.cloudconcept.ProductsActivity;

/**
 * Created by Abanoub Wagdy on 1/26/2016.
 */
public class ActivitiesLauncher {

    private static Intent intent;

    public static void openDashboardActivity(Context applicationContext) {
        intent = new Intent(applicationContext, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    public static void openHomepageActivity(Context applicationContext) {
        intent = new Intent(applicationContext, HomepageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    public static void openProductsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, ProductsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }
}
