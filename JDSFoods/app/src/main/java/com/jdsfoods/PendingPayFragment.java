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

import com.jdsfoods.Adapter.PendingAdapter;
import com.jdsfoods.Response.PendingModel;
import com.jdsfoods.Response.PendingResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingPayFragment extends Fragment {
    List<PendingModel> pendingModels = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public PendingPayFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_pay, container, false);
        ButterKnife.bind(this, view);
        ((DashboardActivity) getActivity()).setTitle("Payment");
        pendingBill("pending");
        completeBill();
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
    private void pendingBill(final String tag) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        rvStatus.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.pendingBill(Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PendingResponse>() {
                    @Override
                    public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                        if (response.body().getFlag() == 1) {
                            if (tag.equalsIgnoreCase("pending")) {
                                if (response.body().getPendingList().size()==0){
                                    ivNoData.setVisibility(View.VISIBLE);
                                    rvStatus.setVisibility(View.GONE);
                                }
                                else {
                                    ivNoData.setVisibility(View.GONE);
                                    rvStatus.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < response.body().getPendingList().size(); i++) {
                                    PendingModel pendingModel = new PendingModel();
                                    pendingModel.setId(response.body().getPendingList().get(i).getId());
                                    pendingModel.setDate(response.body().getPendingList().get(i).getDate());
                                    pendingModel.setReferenceNo(response.body().getPendingList().get(i).getReferenceNo());
                                    pendingModel.setTotal(response.body().getPendingList().get(i).getTotal());
                                    pendingModel.setProductTax(response.body().getPendingList().get(i).getProductTax());
                                    pendingModel.setTotalTax(response.body().getPendingList().get(i).getTotalTax());
                                    pendingModel.setGrandTotal(response.body().getPendingList().get(i).getGrandTotal());
                                    pendingModel.setPendingAmount(response.body().getPendingList().get(i).getPendingAmount());
                                    pendingModel.setPaymentStatus(response.body().getPendingList().get(i).getPaymentStatus());
                                    pendingModel.setPaid(response.body().getPendingList().get(i).getPaid());
                                    pendingModel.setTotalItems(response.body().getPendingList().get(i).getTotalItems());
                                    pendingModel.setSalePayments(response.body().getPendingList().get(i).getSalePayments());
                                    pendingModels.add(pendingModel);
                                }
                                }
                            } else {
                                if (response.body().getPartialList().size()==0){
                                    ivNoData.setVisibility(View.VISIBLE);
                                    rvStatus.setVisibility(View.GONE);
                                }
                                else {
                                    ivNoData.setVisibility(View.GONE);
                                    rvStatus.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < response.body().getPartialList().size(); i++) {
                                        PendingModel pendingModel = new PendingModel();
                                        pendingModel.setId(response.body().getPartialList().get(i).getId());
                                        pendingModel.setDate(response.body().getPartialList().get(i).getDate());
                                        pendingModel.setReferenceNo(response.body().getPartialList().get(i).getReferenceNo());
                                        pendingModel.setTotal(response.body().getPartialList().get(i).getTotal());
                                        pendingModel.setProductTax(response.body().getPartialList().get(i).getProductTax());
                                        pendingModel.setTotalTax(response.body().getPartialList().get(i).getTotalTax());
                                        pendingModel.setGrandTotal(response.body().getPartialList().get(i).getGrandTotal());
                                        pendingModel.setPendingAmount(response.body().getPartialList().get(i).getPendingAmount());
                                        pendingModel.setPaymentStatus(response.body().getPartialList().get(i).getPaymentStatus());
                                        pendingModel.setPaid(response.body().getPartialList().get(i).getPaid());
                                        pendingModel.setTotalItems(response.body().getPartialList().get(i).getTotalItems());
                                        pendingModel.setSalePayments(response.body().getPartialList().get(i).getSalePayments());
                                        pendingModels.add(pendingModel);
                                    }
                                }
                            }
                            PendingAdapter pendingAdapter = new PendingAdapter(getContext(), pendingModels, "");
                            rvStatus.setAdapter(pendingAdapter);
                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PendingResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void completeBill() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        rvStatus.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.completeBill(Preferences.getInstance().getUserId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PendingResponse>() {
                    @Override
                    public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                        if (response.body().getFlag() == 1) {
                            if (response.body().getPendingList().size() == 0) {
                                ivNoData.setVisibility(View.VISIBLE);
                                rvStatus.setVisibility(View.GONE);
                            } else {
                                ivNoData.setVisibility(View.GONE);
                                rvStatus.setVisibility(View.VISIBLE);
                                for (int i = 0; i < response.body().getPendingList().size(); i++) {
                                    PendingModel pendingModel = new PendingModel();
                                    pendingModel.setId(response.body().getPendingList().get(i).getId());
                                    pendingModel.setDate(response.body().getPendingList().get(i).getDate());
                                    pendingModel.setReferenceNo(response.body().getPendingList().get(i).getReferenceNo());
                                    pendingModel.setTotal(response.body().getPendingList().get(i).getTotal());
                                    pendingModel.setProductTax(response.body().getPendingList().get(i).getProductTax());
                                    pendingModel.setTotalTax(response.body().getPendingList().get(i).getTotalTax());
                                    pendingModel.setGrandTotal(response.body().getPendingList().get(i).getGrandTotal());
                                    pendingModel.setPendingAmount(response.body().getPendingList().get(i).getPendingAmount());
                                    pendingModel.setPaymentStatus(response.body().getPendingList().get(i).getPaymentStatus());
                                    pendingModel.setPaid(response.body().getPendingList().get(i).getPaid());
                                    pendingModel.setTotalItems(response.body().getPendingList().get(i).getTotalItems());
                                    pendingModel.setSalePayments(response.body().getPendingList().get(i).getSalePayments());
                                    pendingModels.add(pendingModel);

                                }
                                PendingAdapter pendingAdapter = new PendingAdapter(getContext(), pendingModels, "");
                                rvStatus.setAdapter(pendingAdapter);
                            }
                        }else {
                            ivNoData.setVisibility(View.VISIBLE);
                            rvStatus.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PendingResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_pending)
    void rlPending() {
        pendingModels.clear();
        pendingBill("pending");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_partial)
    void rlPartial() {
        pendingModels.clear();
        pendingBill("partial");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.rl_complete)
    void rlComplete() {
        pendingModels.clear();
        completeBill();
    }

    @BindView(R.id.rv_status)
    RecyclerView rvStatus;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;
}
