package com.jdsfoods;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jdsfoods.Adapter.SubQutAdapter;
import com.jdsfoods.Response.SubQutResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmitQutFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    SubQutAdapter subQutAdapter;
    public SubmitQutFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_submit_qut, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setTitle("Submitted Quotation");
        getSubList();
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false).setEnabled(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.no_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getSubList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL,false);
        rvSubQut.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.subQuoteList(Preferences.getInstance().getQuoteId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<SubQutResponse>() {
                    @Override
                    public void onResponse(Call<SubQutResponse> call, Response<SubQutResponse> response) {
                        if (response.body().getFlag()==1){
                            if (response.body().getSubQuoteList().size()==0){
                                ivNoData.setVisibility(View.VISIBLE);
                                rvSubQut.setVisibility(View.GONE);
                            }
                            else {
                                ivNoData.setVisibility(View.GONE);
                                rvSubQut.setVisibility(View.VISIBLE);
                                subQutAdapter = new SubQutAdapter(getActivity(), response.body().getSubQuoteList(), response.body().getImagePath());
                                rvSubQut.setAdapter(subQutAdapter);
                            }
                        }
                        else
                        {
                            ivNoData.setVisibility(View.VISIBLE);
                            rvSubQut.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<SubQutResponse> call, Throwable t) {
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

    @BindView(R.id.rv_sub_qut)
    RecyclerView rvSubQut;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;
}
