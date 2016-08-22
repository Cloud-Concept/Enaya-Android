package cloudconcept.com.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utilities.Constants;

/**
 * Created by Abanoub Wagdy on 2/2/2016.
 */
public class FormField {

    private String Label;
    private ArrayList<FormFieldValue> values;
    private boolean IsReadOnly;
    private FormFieldType Type;

    public static FormField getObjectFromJson(JSONObject json) {

        return new FormField(json);
    }

    public FormField(JSONObject jFieldObj) {
        try {
            this.setLabel(jFieldObj.getString(Constants.label));
            this.setReadOnly(Boolean.valueOf(jFieldObj.getString(Constants.isEditable)));
            if (jFieldObj.getString(Constants.label).equals("date")) {
                this.setType(FormFieldType.DATE);
            } else if (jFieldObj.getString(Constants.label).equals("picklist")) {
                this.setType(FormFieldType.PICKLIST);
            } else {
                this.setType(FormFieldType.TEXT);
            }

            JSONArray jArrayValues = jFieldObj.getJSONArray(Constants.values);
            ArrayList<FormFieldValue> values = new ArrayList<>();
            for (int k = 0; k < jArrayValues.length(); k++) {
                JSONObject jValueobj = jArrayValues.getJSONObject(k);
                FormFieldValue formFieldValue = new FormFieldValue(jValueobj);
                values.add(formFieldValue);
            }
            this.setValue(values);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public ArrayList<FormFieldValue> getValue() {
        return values;
    }

    public void setValue(ArrayList<FormFieldValue> value) {
        this.values = value;
    }

    public boolean isReadOnly() {

        return IsReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        IsReadOnly = readOnly;
    }

    public FormFieldType getType() {
        return Type;
    }

    public void setType(FormFieldType type) {
        Type = type;
    }
}
