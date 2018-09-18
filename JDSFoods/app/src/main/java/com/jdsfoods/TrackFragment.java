package com.jdsfoods;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Response.TrackResponse;
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


public class TrackFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public TrackFragment() {
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
        View view= inflater.inflate(R.layout.fragment_track, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setTitle("Track Order");
        trackOrder();
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
    private void trackOrder() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.trackOrder(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<TrackResponse>() {
                    @Override
                    public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                        if (response.body().getFlag()==1){
                            tvDate.setText("Order Placed: "+response.body().getTrackOrder().getDate());
                            tvSale.setText("Sale ID: "+response.body().getTrackOrder().getSaleId());
                             setColor(response.body().getTrackOrder());

                        }
                        else
                        {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<TrackResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setColor(TrackResponse.TrackOrder trackOrder) {
        if (trackOrder.getOrderConfirmed()==1)
        {
            rlPlace.setBackgroundResource(R.drawable.circle_lg);
            vPlace.setBackgroundColor(Color.parseColor("#FF38B3A7"));
        }
        else {
            rlPlace.setBackgroundResource(R.drawable.circle_gg);
            vPlace.setBackgroundColor(Color.parseColor("#DEDEDE"));
        }

        if (trackOrder.getOrderConfirmed()==1)
        {
            rlConfirm.setBackgroundResource(R.drawable.circle_lg);
            vConfirm.setBackgroundColor(Color.parseColor("#FF38B3A7"));
        }
        else {
            rlConfirm.setBackgroundResource(R.drawable.circle_gg);
            vConfirm.setBackgroundColor(Color.parseColor("#DEDEDE"));
        }
        if (trackOrder.getOrderShip()==1)
        {
            rlShip.setBackgroundResource(R.drawable.circle_lg);
            vShip.setBackgroundColor(Color.parseColor("#FF38B3A7"));
        }
        else {
            rlShip.setBackgroundResource(R.drawable.circle_gg);
            vShip.setBackgroundColor(Color.parseColor("#DEDEDE"));
        }
        if (trackOrder.getOrderDelivered()==1)
        {
            rlDeliver.setBackgroundResource(R.drawable.circle_lg);
        }
        else {
            rlDeliver.setBackgroundResource(R.drawable.circle_gg);
        }
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

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_sale)
    TextView tvSale;


    @BindView(R.id.rl_deliver)
    RelativeLayout rlDeliver;


    @BindView(R.id.rl_ship)
    RelativeLayout rlShip;


    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;


    @BindView(R.id.rl_place)
    RelativeLayout rlPlace;


    @BindView(R.id.v_place)
    View vPlace;


    @BindView(R.id.v_confirm)
    View vConfirm;

    @BindView(R.id.v_ship)
    View vShip;









}
