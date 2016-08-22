package cloudconcept.com.model;

import org.json.JSONException;
import org.json.JSONObject;

import utilities.Constants;

/**
 * Created by Abanoub Wagdy on 2/2/2016.
 */
public class FormFieldValue {

    private String ID;
    private String Value;

    public static FormFieldValue getObjectFromJson(JSONObject json) {

        return new FormFieldValue(json);
    }

    public FormFieldValue(JSONObject jValueobj) {
        try {
            this.setID(jValueobj.getString(Constants.ID));
            this.setValue(jValueobj.getString(Constants.value));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }
}
