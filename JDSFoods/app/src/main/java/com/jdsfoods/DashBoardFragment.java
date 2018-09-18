package com.jdsfoods;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.ShopAdapter;
import com.jdsfoods.Response.AllQuoteResponse;
import com.jdsfoods.Response.ShopResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    ShopAdapter shopAdapter;
    String items;
    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setLocation("Delivery Location \n" + Preferences.getInstance().getAddress());
        tvDelivery.setText("Welcome! "+Preferences.getInstance().getCompany());
        getShopList();
        getAllQuote();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getAllQuote() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        jdsFoodService.getAllQuote(Preferences.getInstance().getQuoteId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AllQuoteResponse>() {
                    @Override
                    public void onResponse(Call<AllQuoteResponse> call, Response<AllQuoteResponse> response) {
                        if (response.body().getFlag()==1){
                            items= String.valueOf(response.body().getQuoteItemList().size());
                            if (items.equalsIgnoreCase("0")){
                                rlCart.setVisibility(View.GONE);
                                //tvCart.setText("ITEMS (" + "0" + ")");
                            }
                            else {
                                rlCart.setVisibility(View.VISIBLE);
                                tvCart.setText("ITEMS (" + items + ")");
                            }
                        }
                        else
                        {

                        }
                    }

                    @Override
                    public void onFailure(Call<AllQuoteResponse> call, Throwable t) {
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getShopList() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvProducts.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.shopList(Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ShopResponse>() {
                    @Override
                    public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                        if (response.body().getFlag()==1){
                            Preferences.getInstance().setUserPhotoPath(response.body().getImagePath());
                            shopAdapter = new ShopAdapter(getActivity(), response.body().getShopDataList(),response.body().getImagePath());
                            rvProducts.setAdapter(shopAdapter);
                        }
                        else
                        {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ShopResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    @OnClick(R.id.et_search)
    void etSearch(){
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @OnClick(R.id.rl_cart)
    void rlCart(){
        Intent intent = new Intent(getContext(), AllQuoteActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;

    @BindView(R.id.tv_cart)
    TextView tvCart;

    @BindView(R.id.tv_delivery)
    TextView tvDelivery;


}
