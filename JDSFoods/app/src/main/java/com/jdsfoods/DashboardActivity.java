package com.jdsfoods;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jdsfoods.Response.ForgetResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.FragmentChangeListener;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity implements FragmentChangeListener,NavigationView.OnNavigationItemSelectedListener,DashBoardFragment.OnFragmentInteractionListener ,ProfileFragment.OnFragmentInteractionListener,BrandFragment.OnFragmentInteractionListener,SubmitQutFragment.OnFragmentInteractionListener,PendingPayFragment.OnFragmentInteractionListener,HistoryFragment.OnFragmentInteractionListener,TrackFragment.OnFragmentInteractionListener,PlaceOdrFragment.OnFragmentInteractionListener,AcceptQutFragment.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener,HelpFragment.OnFragmentInteractionListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    CircleImageView dashboardImage;TextView dashboardName;
    TextView title;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        String type = getIntent().getStringExtra("notificationTrainer");
        title.setText("Delivery Location \n"+Preferences.getInstance().getAddress());
        toolbar.setTitle(null);
        toolbar.bringToFront();
        toolbar.invalidate();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_black_24dp, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);
        dashboardImage = (CircleImageView) navView.getHeaderView(0).findViewById(R.id.cv_user);
        dashboardName = (TextView) navView.getHeaderView(0).findViewById(R.id.tv_user);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new DashBoardFragment()).commit();
        }
        setHeader();
        if (type != null) {
            switch (type) {
                case "quotation_accepted":
                    replaceFragment(new AcceptQutFragment(),"Accepted Quotation");
                    break;
                case "approval_done":
                    replaceFragment(new ProfileFragment(),"Profile");
                    break;
                case "New Sale":
                    replaceFragment(new HistoryFragment(),"Sale History");
                    break;
                case "sale created":
                    replaceFragment(new HistoryFragment(),"Sale History");
                    break;


            }
        }
    }

    private void setHeader() {
        dashboardName.setText(Preferences.getInstance().getUserName());
        //Picasso.with(dashboardImage.getContext()).load(R.drawable.ic_account_box_black_24dp).into(dashboardImage);
    }

    public void setTitle(String titletxt) {
        title.setText(titletxt);
        title.setTextSize(18f);
    }

    public void setLocation(String titletxt) {
        title.setText(titletxt);
        title.setTextSize(13f);
    }


    public void logout(){
        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(DashboardActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.appicon);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutApi();
                Preferences.getInstance().setLogIn(false);
                Preferences.getInstance().clearUserDetails();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawer.closeDrawer(GravityCompat.START);
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        pbutton.setTextColor(Color.parseColor("#FF38B3A7"));
        nbutton.setTextColor(Color.parseColor("#FF38B3A7"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void logoutApi() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        showProgressbar("Loading", "Logging Out...");
        jdsFoodService.logout(Preferences.getInstance().getUserId()).
                enqueue(new Callback<ForgetResponse>() {
                    @Override
                    public void onResponse(Call<ForgetResponse> call, Response<ForgetResponse> response) {
                        if (response.body().getFlag()==1) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.body().getData(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = DashBoardFragment.class;
        } else if (id == R.id.nav_brand) {
            fragmentClass = BrandFragment.class;
        } else if (id == R.id.nav_submit) {
            fragmentClass = SubmitQutFragment.class;
        } else if (id == R.id.nav_accept) {
            fragmentClass = AcceptQutFragment.class;
        }
        else if (id == R.id.nav_pending) {
            fragmentClass=PendingPayFragment.class;
        }
        else if (id== R.id.nav_about)
        {
            fragmentClass=AboutFragment.class;
        }
        else if (id== R.id.nav_help)
        {
            fragmentClass=HelpFragment.class;
        }
        else if (id== R.id.nav_sale)
        {
            fragmentClass=HistoryFragment.class;
        }
        else if (id== R.id.nav_trust)
        {
            fragmentClass=AboutFragment.class;
        }
        else if (id== R.id.nav_terms)
        {
            fragmentClass=AboutFragment.class;
        }
        else if (id== R.id.nav_track)
        {
            fragmentClass=TrackFragment.class;
        }
        else if (id== R.id.nav_order){
            fragmentClass = PlaceOdrFragment.class;
        }
        else if (id == R.id.nav_change) {
            Intent intent = new Intent(DashboardActivity.this, ChangePwdActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (id == R.id.nav_profile)
        {
            fragmentClass = ProfileFragment.class;
        }
        else if (id == R.id.nav_logout)
        {
            logout();
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void replaceFragment(Fragment fragment, String fragmentTitle) {
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
        title.setText(fragmentTitle);
    }
}
