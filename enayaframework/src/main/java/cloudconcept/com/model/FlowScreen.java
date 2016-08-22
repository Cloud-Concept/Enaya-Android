package cloudconcept.com.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utilities.Constants;

/**
 * Created by Abanoub Wagdy on 2/3/2016.
 */
public class FlowScreen {

    private int screenNo;
    private boolean hasDocuments;
    private String serviceIdentifier;
    private ArrayList<Screen> screens;

    public static FlowScreen getObjectFromJson(JSONObject json) {
        return new FlowScreen(json);
    }

    public FlowScreen(JSONObject jsonFullObject) {
        try {
            this.setScreenNo(Integer.valueOf(jsonFullObject.getString(Constants.SCREEN_NUMBER)));
            this.setHasDocuments(Boolean.valueOf(jsonFullObject.getBoolean(Constants.hasDocuments)));
            this.setServiceIdentifier(String.valueOf(jsonFullObject.getString(Constants.ServiceIdentifier)));
            JSONArray jArrayScreens = jsonFullObject.getJSONArray(Constants.Screens);
            ArrayList<Screen> screens = new ArrayList<>();
            for (int i = 0; i < jArrayScreens.length(); i++) {
                JSONObject json = jArrayScreens.getJSONObject(i);
                Screen _screen = new Screen(json);
                screens.add(_screen);
                this.setScreens(screens);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(int screenNo) {
        this.screenNo = screenNo;
    }

    public boolean isHasDocuments() {
        return hasDocuments;
    }

    public void setHasDocuments(boolean hasDocuments) {
        this.hasDocuments = hasDocuments;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public void setScreens(ArrayList<Screen> screens) {
        this.screens = screens;
    }
}
