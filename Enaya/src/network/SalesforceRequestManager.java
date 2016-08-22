package network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.cloudconcept.EnayaConfiguration;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import custom.OnResponseCallback;
import utilities.Utilities;

/**
 * Created by Abanoub Wagdy on 1/28/2016.
 */
public class SalesforceRequestManager {

    private String result;
    private String queryParams = "";
    private String soql = "";

    private static SalesforceRequestManager instance = new SalesforceRequestManager();

    public static SalesforceRequestManager getInstance() {
        return instance;
    }

    private SalesforceRequestManager() {
    }


    public void callWebService(String methodName, Activity activity, OnResponseCallback callback) {
        new doRequest(methodName, activity, callback).execute();
    }

    public void doRequestBySoql(final Activity activity, final String soql, final OnResponseCallback callback) {
        Utilities.showloadingDialog(activity);
        try {
            final RestRequest restRequest = RestRequest.getRequestForQuery(activity.getString(com.salesforce.androidsdk.R.string.api_version), soql);
            if (Utilities.getRestClient(activity) != null) {
                Utilities.getRestClient(activity).sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                    @Override
                    public void onSuccess(RestRequest request, RestResponse response) {
                        Utilities.dismissLoadingDialog();
                        callback.onSuccess(response.toString());
                    }

                    @Override
                    public void onError(Exception exception) {
                        VolleyError volleyError = (VolleyError) exception;
                        NetworkResponse response = volleyError.networkResponse;
                        String json = new String(response.data);
                        Log.d("", json);
                        Utilities.dismissLoadingDialog();
                        callback.onFailure(json);
                    }
                });
            } else {
                new ClientManager(activity, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(activity, new ClientManager.RestClientCallback() {
                    @Override
                    public void authenticatedRestClient(final RestClient client) {
                        if (client == null) {
                            SalesforceSDKManager.getInstance().logout(activity);
                            return;
                        } else {
                            Utilities.setRestClient(client);
                            Utilities.getRestClient(activity).sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                                @Override
                                public void onSuccess(RestRequest request, RestResponse response) {
                                    Utilities.dismissLoadingDialog();
                                    callback.onSuccess(response.toString());
                                }

                                @Override
                                public void onError(Exception exception) {
                                    VolleyError volleyError = (VolleyError) exception;
                                    NetworkResponse response = volleyError.networkResponse;
                                    String json = new String(response.data);
                                    Log.d("", json);
                                    Utilities.dismissLoadingDialog();
                                    callback.onFailure(json);
                                }
                            });
                        }
                    }
                });
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void doRequestByQueryObjectRecords(Activity activity, String objectName, ArrayList<String> params, int limit, int offset, final OnResponseCallback callback) {

        for (int i = 0; i < params.size(); i++) {
            queryParams += params.get(i) + ",";
        }

        queryParams = queryParams.substring(0, queryParams.length() - 1);
        if (limit != 0 && offset != 0) {
            soql = "Select " + queryParams + " FROM " + objectName + " LIMIT " + limit + " OFFSET " + offset;
        } else {
            soql = "Select " + queryParams + " FROM " + objectName;
        }

        try {
            RestRequest restRequest = RestRequest.getRequestForQuery(activity.getString(com.salesforce.androidsdk.R.string.api_version), soql);
            Utilities.getRestClient(activity).sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                @Override
                public void onSuccess(RestRequest request, RestResponse response) {
                    callback.onSuccess(response.toString());
                }

                @Override
                public void onError(Exception exception) {
                    callback.onFailure(exception.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void doRequestByUpdateObjectRecords(Activity activity, String objectName, String objectId, Map<String, Object> fieldstoUpdate, int limit, int offset, final OnResponseCallback callback) {

        try {
            RestRequest restRequest = RestRequest.getRequestForUpdate(activity.getString(com.salesforce.androidsdk.R.string.api_version), objectName, objectId, fieldstoUpdate);
            Utilities.getRestClient(activity).sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                @Override
                public void onSuccess(RestRequest request, RestResponse response) {
                    callback.onSuccess(response.toString());
                }

                @Override
                public void onError(Exception exception) {
                    callback.onFailure(exception.toString());
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class doRequest extends AsyncTask<Void, Void, Void> {

        String methodName;
        Activity activity;
        OnResponseCallback callback;

        public doRequest(String methodName, Activity activity, OnResponseCallback callback) {
            this.activity = activity;
            this.methodName = methodName;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            Utilities.showloadingDialog(activity);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String attUrl = Utilities.getRestClient(activity).getClientInfo().resolveUrl(EnayaConfiguration.MOBILE_SERVICE_UTILITY_URL + "/" + methodName).toString();
            HttpClient tempClient = new DefaultHttpClient();
            URI theUrl = null;

            try {
                theUrl = new URI(attUrl);
                HttpPost getRequest = new HttpPost();
                getRequest.setURI(theUrl);
                getRequest.setHeader("Authorization", "Bearer " + Utilities.getRestClient(activity).getAuthToken());
                HttpResponse httpResponse = null;
                StringEntity se = null;
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                getRequest.setEntity(se);
                try {
                    httpResponse = tempClient.execute(getRequest);
                    StatusLine statusLine = httpResponse.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        Log.d("response", httpEntity.toString());
                        if (httpEntity != null) {
                            result = EntityUtils.toString(httpEntity);
                        }
                    } else {
                        httpResponse.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Utilities.dismissLoadingDialog();
            if (result.contains("success")) {
                callback.onSuccess(result);
            } else {
                callback.onFailure(result);
            }
        }
    }
}