package com.jdsfoods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Adapter.AcceptAdapter;
import com.jdsfoods.Response.AcceptQutResponse;
import com.jdsfoods.Response.PayResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceptQutFragment extends Fragment {

    public static final int RESULT_OK = -1;
    double totalPrice = 0;
    double totalQuantity = 0;
    double totalTax = 0;
    double grandTotal= 0;
    String payMethod="";
    RelativeLayout rlAmt;
    TextView tvAmt;
    Button btOnline,btBank;

    private OnFragmentInteractionListener mListener;
    public AcceptQutFragment() {
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
        View view= inflater.inflate(R.layout.fragment_accept_qut, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setTitle("Accepted Quotation");
        slidingUpPanelLayout.setVisibility(View.GONE);
        getAcceptList();
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
    private void getAcceptList() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSubQut.setLayoutManager(layoutManager);
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.acceptedQuote(Preferences.getInstance().getQuoteId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<AcceptQutResponse>() {
                    @Override
                    public void onResponse(Call<AcceptQutResponse> call, Response<AcceptQutResponse> response) {
                        if (response.body().getFlag()==1) {
                            if (response.body().getQuoteDetail() == null) {
                            } else {
                                if (response.body().getQuoteDetail().getQuoteItem().size() == 0) {
                                    ivNoData.setVisibility(View.VISIBLE);
                                    slidingUpPanelLayout.setVisibility(View.GONE);
                                } else {
                                    ivNoData.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setVisibility(View.VISIBLE);
                                    AcceptAdapter acceptAdapter = new AcceptAdapter(getActivity(), response.body().getQuoteDetail().getQuoteItem(), response.body().getImageBase());
                                    rvSubQut.setAdapter(acceptAdapter);
                                }
                                for (int i = 0; i < response.body().getQuoteDetail().getQuoteItem().size(); i++) {
                                    totalPrice += Double.parseDouble(response.body().getQuoteDetail().getQuoteItem().get(i).getSubtotal());
                                    totalQuantity += Double.parseDouble(response.body().getQuoteDetail().getQuoteItem().get(i).getQuantity());
                                    totalTax += Double.parseDouble(response.body().getQuoteDetail().getQuoteItem().get(i).getItemTax()) * Double.parseDouble(response.body().getQuoteDetail().getQuoteItem().get(i).getQuantity());
                                }
                                setItems(totalPrice, totalQuantity, totalTax);
                            }
                        }
                        else
                        {
                            ivNoData.setVisibility(View.VISIBLE);
                            slidingUpPanelLayout.setVisibility(View.GONE);
                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AcceptQutResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setItems(double totalPrice, double totalQuantity, double totalTax) {
        tvSub.setText("€ " +String.valueOf(totalPrice));
        tvItem.setText(String.valueOf(totalQuantity));
        tvTax.setText("€ " +String.format("%.2f", totalTax));
        tvTotal.setText("€ " +String.valueOf(totalPrice+totalTax));
        grandTotal=totalPrice+totalTax;
    }

    public void showDialog1(final Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        TextView tvPrice = (TextView) dialog.findViewById(R.id.tv_price);
        LinearLayout llPayLater = (LinearLayout) dialog.findViewById(R.id.ll_pay_later);
        TextView tvDay = (TextView) dialog.findViewById(R.id.tv_day);
        tvPrice.setText("TOTAL: "+msg);
        if (Preferences.getInstance().getPaymentTerm()==null|| Preferences.getInstance().getPaymentTerm().equalsIgnoreCase("0"))
        {
            llPayLater.setVisibility(View.GONE);
        }
        else {
            llPayLater.setVisibility(View.VISIBLE);
            tvDay.setText("You have " + Preferences.getInstance().getPaymentTerm()+" days to pay this amount.");
        }

        Button payLater = (Button) dialog.findViewById(R.id.bt_pay_later);
        Button payNow = (Button) dialog.findViewById(R.id.bt_pay_now);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialog2(activity,msg);
            }
        });

        payLater.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                placeOrder("0","");
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void placeOrder(String payNow,String amount) {
        if (payNow.equalsIgnoreCase("0"))
        {
            payMethod="";
        }
        JDSFoodService jdsSaleService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsSaleService.placeOrder(Preferences.getInstance().getQuoteId(), Preferences.getInstance().getIsCredit(),payNow,payMethod,Preferences.getInstance().getPaymentTerm(),amount,Preferences.getInstance().getUserId(),String.valueOf(totalTax),String.valueOf(totalPrice),String.valueOf(grandTotal) ,"1",Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<PayResponse>() {
                    @Override
                    public void onResponse(Call<PayResponse> call, Response<PayResponse> response) {
                            if (response.body().getFlag() == 1) {
                                Intent intent = new Intent(getContext(), WebviewActivity.class);
                                intent.putExtra("link", response.body().getPaymentUrl().getPaymentUrl());
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<PayResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void showDialog2(Activity activity, final String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two);
        final EditText etAmount = (EditText) dialog.findViewById(R.id.et_amount);
        TextView tvTotal = (TextView) dialog.findViewById(R.id.tv_total);
        tvTotal.setText(msg);
        Button btPay = (Button) dialog.findViewById(R.id.bt_pay);
        rlAmt = (RelativeLayout) dialog.findViewById(R.id.rl_amt);
        tvAmt = (TextView) dialog.findViewById(R.id.tv_amt);
        btOnline = (Button) dialog.findViewById(R.id.bt_online);
        btBank = (Button) dialog.findViewById(R.id.bt_bank);
        btPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (etAmount.getText().toString().length()!=0){
                    placeOrder("1",etAmount.getText().toString());
                    dialog.dismiss();
                }
                else {
                    if (payMethod.equalsIgnoreCase("bank"))
                    {
                        placeOrder("0",etAmount.getText().toString());
                        dialog.dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "Please add amount to pay", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PaymentMethodActivity.class);
                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        btBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BankActivity.class);
                intent.putExtra("price",msg);
                startActivityForResult(intent, 2);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        dialog.show();

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

    @OnClick(R.id.bt_pay)
    void btPay(){
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_place)
    void rlSubmit(){
        showDialog1(getActivity(), tvTotal.getText().toString());
    }

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.bt_place)
    Button btPlace;

    @BindView(R.id.tv_tax)
    TextView tvTax;

    @BindView(R.id.tv_sub)
    TextView tvSub;

    @BindView(R.id.tv_item)
    TextView tvItem;

    @BindView(R.id.iv_no_data)
    ImageView ivNoData;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                payMethod = data.getStringExtra("payment");
                tvAmt.setVisibility(View.VISIBLE);
                rlAmt.setVisibility(View.VISIBLE);
                btBank.setBackgroundColor(getResources().getColor(R.color.dgray));
                btBank.setText("BANK");
                btOnline.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                btOnline.setText(payMethod);
                // Toast.makeText(getApplicationContext(), payMethod, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 2){
            if(resultCode == RESULT_OK) {
                payMethod = data.getStringExtra("payment");
                tvAmt.setVisibility(View.GONE);
                rlAmt.setVisibility(View.GONE);
                btOnline.setBackgroundColor(getResources().getColor(R.color.dgray));
                btOnline.setText("ONLINE PAYMENT");
                btBank.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                btBank.setText(payMethod);
                //Toast.makeText(getApplicationContext(), payMethod, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
