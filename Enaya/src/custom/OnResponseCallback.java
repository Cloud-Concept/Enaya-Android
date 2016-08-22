package custom;

import com.salesforce.androidsdk.rest.RestResponse;

/**
 * Created by Abanoub Wagdy on 1/28/2016.
 */
public interface OnResponseCallback {

    void onSuccess(String restResponse);

    void onFailure(String exceptionResult);
}
