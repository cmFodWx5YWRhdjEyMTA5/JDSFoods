package com.jdsfoods;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jdsfoods.Response.LoginResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;
import com.jdsfoods.Utilities.ValidationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setStatusBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void login() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Sending request...");
        jdsFoodService.login(String.valueOf(etUser.getText()), String.valueOf(etPass.getText()),"android", Preferences.getInstance().getDevice_token()).
                enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            savePreferences(response.body().getUserData());
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void savePreferences(LoginResponse.UserData userData) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(userData.getId());
        Preferences.getInstance().setUserName(userData.getName());
        Preferences.getInstance().setMobile(userData.getPhone());
        Preferences.getInstance().setEmail(userData.getEmail());
        Preferences.getInstance().setAddress(userData.getAddress().toString().trim()+", "+userData.getCity().toString().trim()+", "+userData.getState().toString().trim()+", "+userData.getPostalCode().toString().trim());
        Preferences.getInstance().setAuthKey(userData.getAuthToken());
        Preferences.getInstance().setCompany(userData.getCompany());
        Preferences.getInstance().setIsCredit(String.valueOf(userData.getIsCredit()));
        if (userData.getPriceVisibleOnApp()==null) {
            Preferences.getInstance().setPriceVisible("0");
        }
        else {
            Preferences.getInstance().setPriceVisible(userData.getPriceVisibleOnApp());
        }
        if (userData.getPaymentTerm()==null) {
            Preferences.getInstance().setPaymentTerm(userData.getPaymentTerm());
        }
        else {
            Preferences.getInstance().setPaymentTerm(userData.getPaymentTerm());
        }
        if (userData.getQuoteId()==null)
        {
            Preferences.getInstance().setQuoteId("0");
        }
        else {
            Preferences.getInstance().setQuoteId(userData.getQuoteId());
        }

        if (userData.getPaymentTerm()==null || userData.getPaymentTerm().equalsIgnoreCase("0")){
            Preferences.getInstance().setPaymentTerm("0");
        }
        else {
            Preferences.getInstance().setPaymentTerm(userData.getPaymentTerm());
        }

    }


    public boolean validation() {
        if (etUser.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter User ID.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (!ValidationUtils.isValidEmail(etUser.getText())) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Valid User ID.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (etPass.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please Enter Password.", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }


    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @BindView(R.id.et_pass)
    EditText etPass;

    @BindView(R.id.et_user)
    EditText etUser;

    @BindView(R.id.container)
    RelativeLayout container;

    @OnClick(R.id.tv_forget1)
    void openF1() {
        Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_login)
    void btLogin() {
        if (validation()) {
            login();
        }
    }

    @OnClick(R.id.tv_forget2)
    void openF2() {
        Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }


}
