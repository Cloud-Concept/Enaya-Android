package utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SimpleSpinnerStringAdapter;
import cloudconcept.com.model.FlowScreen;
import cloudconcept.com.model.FormField;
import cloudconcept.com.model.FormFieldType;
import cloudconcept.com.model.FormFieldValue;
import cloudconcept.com.model.R;
import cloudconcept.com.model.Screen;

/**
 * Created by Abanoub Wagdy on 2/3/2016.
 */
public class Utilities {

    static FlowScreen flowScreen;

    public static View DrawFormFields(Context applicationContext, String jsonStr, int ScreenNo) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        flowScreen = new FlowScreen(jsonObject);
        return DrawFlowScreenFields(applicationContext, flowScreen, ScreenNo);
    }

    private static View DrawFlowScreenFields(Context applicationContext, FlowScreen flowScreen, int ScreenNo) {

        LayoutInflater li = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = li.inflate(R.layout.linear_template, null, false);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear);

        for (int i = 0; i < flowScreen.getScreens().get(ScreenNo - 1).getFormFields().size(); i++) {
            FormField formField = flowScreen.getScreens().get(ScreenNo - 1).getFormFields().get(i);
            View view = null;
            if (formField.getType() == FormFieldType.DATE) {
                li = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.form_field_text_enabled, null, false);
            } else if (formField.getType() == FormFieldType.PICKLIST) {
                li = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.form_field_picklist, null, false);
                Spinner spinner = (Spinner) view.findViewById(R.id.spinnerFormFieldValue);
                ArrayList<String> values = new ArrayList<>();
                for (int j = 0; j < formField.getValue().size(); j++) {
                    values.add(formField.getValue().get(i).getValue());
                }

                SimpleSpinnerStringAdapter adapter = new SimpleSpinnerStringAdapter(applicationContext, R.layout.spinner_item_wizard, R.id.spinnertext, values);
                spinner.setAdapter(adapter);

            } else {
                if (formField.isReadOnly()) {
                    li = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = li.inflate(R.layout.form_field_text_disabled, null, false);
                } else {
                    li = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = li.inflate(R.layout.form_field_text_enabled, null, false);
                }
            }

            linearLayout.addView(view);
        }

        return convertView;
    }

    public void resetFlowScreen() {
        flowScreen = null;
    }
}
