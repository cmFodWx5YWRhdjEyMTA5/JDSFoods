package com.jdsfoods;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdsfoods.Adapter.PlaceOdrAdapter;
import com.jdsfoods.Response.OrderResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.Preferences;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceOdrFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    double noItems=0;
    double noTax=0;
    double subTotal=0;
    private static final String TAG = "PlaceOdrFragment";
    public PlaceOdrFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_place_odr, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setTitle("Order Detail");
        getSubList();
        slidingUpPanelLayout.setVisibility(View.GONE);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }

        });
        slidingUpPanelLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
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
        rvOrder.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.orderList(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if (response.body().getFlag()==1){
                            if (response.body().getQuoteList().size()==0){
                                ivNoData.setVisibility(View.VISIBLE);
                                slidingUpPanelLayout.setVisibility(View.GONE);
                            }
                            else {
                                ivNoData.setVisibility(View.GONE);
                                slidingUpPanelLayout.setVisibility(View.VISIBLE);
                                PlaceOdrAdapter placeOdrAdapter = new PlaceOdrAdapter(getActivity(), response.body().getQuoteList(), response.body().getImageBasePath());
                                rvOrder.setAdapter(placeOdrAdapter);
                            }
                            for(int i = 0 ; i < response.body().getQuoteList().size(); i++) {
                                double calTax=Double.parseDouble(response.body().getQuoteList().get(i).getItemTax())*Double.parseDouble(response.body().getQuoteList().get(i).getQuantity());
                                noItems += Double.parseDouble(response.body().getQuoteList().get(i).getQuantity());
                                noTax += calTax;
                                subTotal += Double.parseDouble(response.body().getQuoteList().get(i).getSubtotal());
                            }
                            setFields(response.body().getData());
                        }
                        else
                        {
                            ivNoData.setVisibility(View.VISIBLE);
                            slidingUpPanelLayout.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        ivNoData.setVisibility(View.VISIBLE);
                        slidingUpPanelLayout.setVisibility(View.GONE);
                    }
                });
    }


    private void setFields(OrderResponse.OrderData data) {
        tvDay.setText(getDay(data.getDeliveryDay()));
        tvDate.setText(convertDate(data.getDate()));
        tvRefer.setText("Reference No: "+data.getReferenceNo());
        tvShip.setText("€ "+String.format("%.2f", Double.parseDouble(data.getShipping())));
        tvDis.setText("€ "+String.format("%.2f", Double.parseDouble(data.getProductDiscount())));
        tvItem.setText(String.valueOf(noItems).substring(0, String.valueOf(noItems).lastIndexOf(".")));
        tvSub.setText("€ "+String.format("%.2f", subTotal));
        tvTax.setText("€ "+String.format("%.2f", noTax));
        double total=subTotal+noTax;
        tvTotal.setText("€ "+String.format("%.2f", total));
    }

    public String convertDate(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public String getDay(String dayNo){
        String dayName = "";
        if (dayNo.equalsIgnoreCase("1"))
        {
            dayName="Monday";
        }
        else if (dayNo.equalsIgnoreCase("2")){
            dayName="Tuesday";
        }
        else if (dayNo.equalsIgnoreCase("3")){
            dayName="Wednesday";
        }
        else if (dayNo.equalsIgnoreCase("4")){
            dayName="Thursday";
        }
        else if (dayNo.equalsIgnoreCase("5")){
            dayName="Friday";
        }
        else if (dayNo.equalsIgnoreCase("6")){
            dayName="Saturday";
        }
        else if (dayNo.equalsIgnoreCase("7")){
            dayName="Sunday";
        }
        return dayName;
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

    @BindView(R.id.tv_day)
    TextView tvDay;

    @BindView(R.id.tv_refer)
    TextView tvRefer;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    @BindView(R.id.tv_item)
    TextView tvItem;

    @BindView(R.id.tv_tax)
    TextView tvTax;

    @BindView(R.id.tv_ship)
    TextView tvShip;

    @BindView(R.id.tv_sub)
    TextView tvSub;

    @BindView(R.id.tv_dis)
    TextView tvDis;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.rl_down)
    LinearLayout rlDown;

    @BindView(R.id.rl_pay)
    RelativeLayout rlPay;

    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;

    @OnClick(R.id.bt_pay)
    void btPay(){
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
