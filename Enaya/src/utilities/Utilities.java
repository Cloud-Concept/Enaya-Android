package utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Abanoub Wagdy on 1/27/2016.
 */
public class Utilities {

    private static ProgressDialog _progress;

    static RestClient client;

    public static RestClient getRestClient(Activity activity) {

        final String accountType = SalesforceSDKManager.getInstance().getAccountType();
        final ClientManager.LoginOptions loginOptions = SalesforceSDKManager.getInstance().getLoginOptions();

        RestClient restClient = new ClientManager(activity, accountType, loginOptions,
                SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).peekRestClient();

        return restClient;
    }

    public static String getUnauthenticatedRestClient(Activity activity) {

        final String accountType = SalesforceSDKManager.getInstance().getAccountType();
        final ClientManager.LoginOptions loginOptions = SalesforceSDKManager.getInstance().getLoginOptions();
        RestResponse response = null;

        RestClient unauthenticatedRestClient = new ClientManager(activity, accountType, loginOptions,
                SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).peekUnauthenticatedRestClient();

        RestRequest request = new RestRequest(RestRequest.RestMethod.GET, "https://api.spotify.com/v1/search?q=James%20Brown&type=artist", null);

        try {
            response = unauthenticatedRestClient.sendSync(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static void showloadingDialog(Activity activity) {
        _progress = new ProgressDialog(activity);
        _progress.setMessage("Loading ...");
        _progress.setCancelable(false);
        _progress.show();
    }

    /**
     * check whether progress is showing to user or not
     */
    public static boolean getIsProgressLoading() {
        if (_progress != null) {
            return _progress.isShowing();
        } else {
            return false;
        }
    }

    /**
     * @param activity:container activity that want this diaog to be shown
     * @param text:text          displayed within loading dialog
     *                           Show Loading dialog which is called all over the application.
     */
    public static void showloadingDialog(Activity activity, String text) {
        _progress = new ProgressDialog(activity);
        _progress.setMessage(text);
        _progress.setCancelable(false);
        _progress.show();
    }

    /**
     * dismiss current shown loading dialog
     */
    public static void dismissLoadingDialog() {
        _progress.dismiss();
    }

    public static void showToast(Activity act, String message) {
        Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param act:container activity that want this message to be shown
     * @param message:the   message to be shown within toast
     *                      Show long message indicating what message want to be displayed to user
     */
    public static void showLongToast(Activity act, String message) {
        Toast.makeText(act, message, Toast.LENGTH_LONG).show();
    }

    public static String getCurrentDate() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    public static void setRestClient(RestClient client) {
        Utilities.client = client;
    }
}
