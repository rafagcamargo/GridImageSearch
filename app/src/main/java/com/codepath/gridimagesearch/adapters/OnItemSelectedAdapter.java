package com.codepath.gridimagesearch.adapters;

import android.view.View;
import android.widget.AdapterView;

public abstract class OnItemSelectedAdapter implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //no-op
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //no-op
    }
}
