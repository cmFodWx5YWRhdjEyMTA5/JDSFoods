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

import com.jdsfoods.Response.ForgetResponse;
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

public class ChangePwdActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        setStatusBar();
    }


    public boolean validation() {
        if (etOldPwd.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please enter old password", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (etOldPwd.getText().toString().trim().length() <6 || etNewPwd.getText().toString().trim().length() <6) {
            Snackbar snackbar = Snackbar.make(container, "Password must be minimum 6 characters long", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        } else if (etNewPwd.getText().toString().trim().length() == 0) {
            Snackbar snackbar = Snackbar.make(container, "Please enter new password", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }

    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;

    @BindView(R.id.container)
    RelativeLayout container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changePassword() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Sending request...");
        jdsFoodService.changePwd(Preferences.getInstance().getUserId(),etOldPwd.getText().toString(),etNewPwd.getText().toString()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag() == 1) {
                            Intent intent = new Intent(ChangePwdActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ForgetResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_request)
    void btRequest()
    {
        if (validation()) {
            changePassword();
        }
    }


    @OnClick(R.id.tv_forget)
    void tvForget()
    {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        this.finish();
    }


    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


}
