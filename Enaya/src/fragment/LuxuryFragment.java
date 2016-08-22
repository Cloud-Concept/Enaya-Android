package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudconcept.R;

/**
 * Created by Abanoub Wagdy on 2/1/2016.
 */
public class LuxuryFragment extends Fragment{

    public static Fragment newInstance(String luxury) {
        LuxuryFragment temp = new LuxuryFragment();
        return temp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.luxury, container, false);
        return view;
    }
}
