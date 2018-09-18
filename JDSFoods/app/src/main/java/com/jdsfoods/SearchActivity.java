package com.jdsfoods;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdsfoods.Adapter.SeachAdapter;
import com.jdsfoods.Response.SearchData;
import com.jdsfoods.Response.SearchResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    List<SearchResponse.Product.ProductList> products;
    List<SearchData> search1=new ArrayList<>();
    List<SearchData> search2=new ArrayList<>();
    SeachAdapter seachAdapter;
    SearchData searchData;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setTextSize(18f);
        title.setText("Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onQueryTextChange(String newText) {
                addSearch(newText);
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addSearch(String query) {
        search1.clear();
        search2.clear();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvSearch.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Please wait...");
        jdsFoodService.searchFood(query, Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.body().getFlag() == 1) {
                            if (response.body().getProduct().getProductListList()==null){

                            }
                            else {
                                for (int i=0;i<response.body().getProduct().getProductListList().size();i++)
                                {
                                    searchData=new SearchData("0",response.body().getProduct().getProductListList().get(i).getProductId(),response.body().getProduct().getProductListList().get(i).getResult(),response.body().getProduct().getProductListList().get(i).getResult(),"0");
                                    search1.add(searchData);
                                }
                            }
                            if (response.body().getCategoryList()==null){

                            }
                            else {
                                for (int i=0;i<response.body().getCategoryList().size();i++)
                                {
                                    searchData=new SearchData(response.body().getCategoryList().get(i).getCategoryId(),"0",response.body().getCategoryList().get(i).getCategoryName(),"0",response.body().getCategoryList().get(i).getChildCategoryCounter());
                                    search2.add(searchData);
                                }
                                search1.addAll(search2);
                                Collections.sort(search1, new Comparator<SearchData>() {
                                    @Override
                                    public int compare(final SearchData object1, final SearchData object2) {
                                        return object1.getSearchName().compareTo(object2.getSearchName());
                                    }
                                });
                                seachAdapter = new SeachAdapter(SearchActivity.this, search1);
                                rvSearch.setAdapter(seachAdapter);
                                seachAdapter.notifyDataSetChanged();
                            }


                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    @BindView(R.id.rv_recent)
    RecyclerView rvRecent;

    @BindView(R.id.search)
    android.support.v7.widget.SearchView search;


    private List<SearchData> getDataFromSharedPreferences(){
        Gson gson = new Gson();
        List<SearchData> productFromShared = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(PRODUCT_TAG, "");

       // Type type = new TypeToken<List<SearchData>>() {}.getType();
        SearchData searchData = gson.fromJson(jsonPreferences, SearchData.class);
        productFromShared.add(searchData);
        return productFromShared;
    }


}
