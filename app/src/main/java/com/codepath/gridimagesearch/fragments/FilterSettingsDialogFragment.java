package com.codepath.gridimagesearch.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.OnItemSelectedAdapter;
import com.codepath.gridimagesearch.models.SearchFilter;

public class FilterSettingsDialogFragment extends DialogFragment {

    private static final String ARG_SEARCH_FILTER = "searchFilter";

    private EditText etSiteFilter;

    private SearchFilter searchFilter;

    private OnFragmentInteractionListener mListener;

    public static FilterSettingsDialogFragment newInstance(SearchFilter searchFilter) {
        FilterSettingsDialogFragment fragment = new FilterSettingsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SEARCH_FILTER, searchFilter);
        fragment.setArguments(args);
        return fragment;
    }

    public FilterSettingsDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        searchFilter = getArguments().getParcelable(ARG_SEARCH_FILTER);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter_settings, null, false);
        setupView(view);

        MaterialDialog.Builder alertDialogBuilder = new MaterialDialog.Builder(getActivity());
        alertDialogBuilder.title(R.string.filter_settings);
        alertDialogBuilder.positiveText(R.string.done);
        alertDialogBuilder.customView(view, true);
        alertDialogBuilder.cancelable(false);
        alertDialogBuilder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                if (mListener != null) {
                    searchFilter.setSiteFilter(etSiteFilter.getText().toString().trim());
                    mListener.onFragmentInteraction(searchFilter);
                }
            }
        });

        return alertDialogBuilder.build();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupView(View view) {
        Spinner sImageSize = (Spinner) view.findViewById(R.id.sImageSize);
        sImageSize.setOnItemSelectedListener(new OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String imageSize = position == 0 ? null : (String) parent.getAdapter().getItem(position);
                searchFilter.setImageSize(imageSize);
                searchFilter.setImageSizePosition(position);
            }
        });
        sImageSize.setSelection(searchFilter.getImageSizePosition());

        Spinner sImageColorType = (Spinner) view.findViewById(R.id.sImageColorType);
        sImageColorType.setOnItemSelectedListener(new OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String imageColorType = position == 0 ? null : (String) parent.getAdapter().getItem(position);
                searchFilter.setImageColorType(imageColorType);
                searchFilter.setImageColorTypePosition(position);
            }
        });
        sImageColorType.setSelection(searchFilter.getImageColorTypePosition());

        Spinner sImageType = (Spinner) view.findViewById(R.id.sImageType);
        sImageType.setOnItemSelectedListener(new OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String imageType = position == 0 ? null : (String) parent.getAdapter().getItem(position);
                searchFilter.setImageType(imageType);
                searchFilter.setImageTypePosition(position);
            }
        });
        sImageType.setSelection(searchFilter.getImageTypePosition());

        Spinner sResultSize = (Spinner) view.findViewById(R.id.sResultSize);
        sResultSize.setOnItemSelectedListener(new OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String resultSize = position == 0 ? null : (String) parent.getAdapter().getItem(position);
                searchFilter.setResultSize(resultSize);
                searchFilter.setResultSizePosition(position);
            }
        });
        sResultSize.setSelection(searchFilter.getResultSizePosition());

        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        etSiteFilter.setText(searchFilter.getSiteFilter());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(SearchFilter searchFilter);
    }

}
