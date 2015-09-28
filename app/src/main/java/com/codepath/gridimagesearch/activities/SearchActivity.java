package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.codepath.gridimagesearch.fragments.FilterSettingsDialogFragment;
import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.SearchFilter;
import com.codepath.gridimagesearch.adapters.ImageResultArrayAdapter;
import com.codepath.gridimagesearch.helper.SearchUrlBuilder;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.models.SearchResult;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class SearchActivity extends AppCompatActivity implements FilterSettingsDialogFragment.OnFragmentInteractionListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private StaggeredGridView gridView;
    private SearchResult searchResult;
    private ImageResultArrayAdapter imageResultArrayAdapter;

    private SearchFilter searchFilter;

    private boolean mHasRequestedMore = true;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchFilter = new SearchFilter();

        gridView = (StaggeredGridView) findViewById(R.id.grid_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        imageResultArrayAdapter = new ImageResultArrayAdapter(this, new ArrayList<ImageResult>());

        gridView.setAdapter(imageResultArrayAdapter);
        gridView.setOnScrollListener(this);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFilter.setSearchExpression(query);

                searchView.clearFocus();
                searchItem.collapseActionView();

                search();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FilterSettingsDialogFragment dialogFragment = FilterSettingsDialogFragment.newInstance(searchFilter);
            dialogFragment.show(fragmentManager, "filter_settings_dialog_fragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void search() {
        url = SearchUrlBuilder.with(this)
                .searchExpression(searchFilter.getSearchExpression())
                .imageSize(searchFilter.getImageSize())
                .imageColorType(searchFilter.getImageColorType())
                .imageType(searchFilter.getImageType())
                .numberOfResults(searchFilter.getResultSize())
                .siteSearch(searchFilter.getSiteFilter())
                .build();

        Log.d("DEBUG", "Url=" + url);

        gridView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        imageResultArrayAdapter.clear();

        doQueryRequest(url);
    }

    private void doQueryRequest(String url) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                searchResult = new SearchResult(response);
                Log.d(TAG, "searchResult=" + searchResult);
                imageResultArrayAdapter.addAll(searchResult.getImageResults());
                mHasRequestedMore = false;
                progressBar.setVisibility(View.INVISIBLE);
                gridView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                super.onFailure(statusCode, headers, throwable, jsonObject);
            }
        });
    }

    @Override
    public void onFragmentInteraction(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //no-op
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                onLoadMoreItems();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        ImageResult imageResult = imageResultArrayAdapter.getItem(position);
        intent.putExtra("url", imageResult.getFullUrl());
        intent.putExtra("height", imageResult.getHeight());
        startActivity(intent);
    }

    private void onLoadMoreItems() {
        doQueryRequest(url + "&start=" + searchResult.getStartIndex());
    }
}
