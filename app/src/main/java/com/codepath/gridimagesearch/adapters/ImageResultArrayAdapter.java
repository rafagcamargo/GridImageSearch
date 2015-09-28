package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

    private static final String TAG = ImageResultArrayAdapter.class.getSimpleName();

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<>();

    private final Random mRandom;

    public ImageResultArrayAdapter(Context context, List<ImageResult> imageResults) {
        super(context, R.layout.item_image_result, imageResults);
        mRandom = new Random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_image_result, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivImageView = (DynamicHeightImageView) convertView.findViewById(R.id.ivImageView);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageResult imageResult = getItem(position);

        viewHolder.ivImageView.setImageResource(0);
        viewHolder.ivImageView.setHeightRatio(getPositionRatio(position));
        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.getTitle()));

        Picasso.with(getContext())
                .load(imageResult.getThumbUrl())
                .placeholder(R.drawable.ic_image_white_24dp)
                .resize(0, imageResult.getThumbnailHeight())
                .into(viewHolder.ivImageView);

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

    static class ViewHolder {
        DynamicHeightImageView ivImageView;
        TextView tvTitle;
    }
}
