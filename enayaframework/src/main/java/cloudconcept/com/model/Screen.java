package cloudconcept.com.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utilities.Constants;

/**
 * Created by Abanoub Wagdy on 2/3/2016.
 */
public class Screen {

    private String Title;
    private String Type;
    private String ButtonText;
    private String ServiceMethodName;
    private String actionType;

    ArrayList<FormField> formFields;

    public static Screen getObjectFromJson(JSONObject json) {

        return new Screen(json);
    }

    public Screen(JSONObject json) {
        try {
            this.setTitle(json.getString(Constants.Title));
            this.setType(json.getString(Constants.Type));
            this.setButtonText(json.getString(Constants.Button));
            this.setServiceMethodName(json.getString(Constants.ServiceMethodName));
            this.setActionType(json.getString(Constants.actionType));
            JSONArray jArrayFields = json.getJSONArray(Constants.Fields);
            ArrayList<FormField> formFields = new ArrayList<>();
            for (int j = 0; j < jArrayFields.length(); j++) {
                JSONObject jFieldObj = jArrayFields.getJSONObject(j);
                FormField formField = new FormField(jFieldObj);
                formFields.add(formField);
            }
            this.setFormFields(formFields);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getButtonText() {
        return ButtonText;
    }

    public void setButtonText(String buttonText) {
        ButtonText = buttonText;
    }

    public String getServiceMethodName() {
        return ServiceMethodName;
    }

    public void setServiceMethodName(String serviceMethodName) {
        ServiceMethodName = serviceMethodName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public ArrayList<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(ArrayList<FormField> formFields) {
        this.formFields = formFields;
    }
}
