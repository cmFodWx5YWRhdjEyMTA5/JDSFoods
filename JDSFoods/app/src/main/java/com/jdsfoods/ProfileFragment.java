package com.jdsfoods;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jdsfoods.Response.ProfileResponse;
import com.jdsfoods.Retrofit.JDSFoodService;
import com.jdsfoods.Retrofit.RestClient;
import com.jdsfoods.Utilities.BaseActivity;
import com.jdsfoods.Utilities.JDSFood;
import com.jdsfoods.Utilities.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    String time;
    MultipartBody.Part profileImgBody;
    public  File file=null;
    Bitmap bitmap;
    private int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    private static final int RC_SIGN_IN = 0;
    public static byte[] data=null;
    public ProfileFragment() {
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        ((DashboardActivity) getActivity()).setTitle("Profile");
        getProfile();
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
    private void getProfile() {
        JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Sending request...");
        jdsFoodService.userProfile(Preferences.getInstance().getUserId(),Preferences.getInstance().getAuthKey()).
                enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.body().getFlag() == 1) {
                            setField(response.body().getProfileData());
                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public RequestBody requestBody(String name) {
        return RequestBody.create(MediaType.parse("text/plain"), name);
    }

    public boolean validation(){
        if (etName.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Name", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etPhone.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Phone", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etEmail.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Email", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etCompany.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Company", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etAddress.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Address", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etPost.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Post Code", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etCity.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter City", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        else if (etCountry.getText().toString().trim().length()==0){
            Snackbar snackbar = Snackbar.make(container, "Please Enter Country", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            return false;
        }
        return true;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void editCustomerProfile(){
        if (file!=null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            profileImgBody = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        }
        final Map<String, RequestBody> field = new HashMap<String, RequestBody>();
        field.put("customer_id", requestBody(Preferences.getInstance().getUserId()));
        field.put("name", requestBody(etName.getText().toString().trim()));
        field.put("email", requestBody(etEmail.getText().toString().trim()));
        field.put("company", requestBody(etEmail.getText().toString().trim()));
        field.put("phone", requestBody(etPhone.getText().toString().trim()));
        field.put("address", requestBody(etAddress.getText().toString().trim()));
        field.put("city", requestBody(etCity.getText().toString().trim()));
        field.put("state", requestBody(etState.getText().toString().trim()));
        field.put("postal_code", requestBody(etPost.getText().toString().trim()));
        field.put("cf2", requestBody(etBtw.getText().toString().trim()));
        field.put("monday_from_time", requestBody(d1Start.getText().toString().trim()));
        field.put("monday_to_time", requestBody(d1End.getText().toString().trim()));
        field.put("tuesday_from_time", requestBody(d2Start.getText().toString().trim()));
        field.put("tuesday_to_time", requestBody(d2End.getText().toString().trim()));
        field.put("wednesday_from_time",requestBody(d3Start.getText().toString().trim()));
        field.put("wednesday_to_time", requestBody(d3End.getText().toString().trim()));
        field.put("thursday_from_time", requestBody(d4Start.getText().toString().trim()));
        field.put("thursday_to_time", requestBody((d4End.getText().toString().trim())));
        field.put("friday_from_time", requestBody(d5Start.getText().toString().trim()));
        field.put("friday_to_time", requestBody(d5End.getText().toString().trim()));
        field.put("saturday_from_time", requestBody(d6Start.getText().toString().trim()));
        field.put("saturday_to_time", requestBody(d6End.getText().toString().trim()));
        field.put("sunday_from_time", requestBody(d7Start.getText().toString().trim()));
        field.put("sunday_to_time", requestBody(d7End.getText().toString().trim()));
        field.put("authorization", requestBody(Preferences.getInstance().getAuthKey()));
        final JDSFoodService jdsFoodService = RestClient.getInstance().getClient().create(JDSFoodService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        jdsFoodService.editProfile(profileImgBody,field).
                enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, final Response<ProfileResponse> response) {
                            if(response.body().getFlag()==1){
                               showAlert();
                            }
                            else {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            ((BaseActivity) getActivity()).hideProgressBar();

                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), JDSFood.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showAlert() {
            final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("Your request for changes in profile has been submitted.You will get notification when it will be accepted or rejected.");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.parseColor("#FF38B3A7"));

    }

    private void setField(ProfileResponse.ProfileData profileData) {
        etName.setText(profileData.getName());
        tvEmail.setText(profileData.getEmail());
        tvName.setText(profileData.getName());
        etPhone.setText(profileData.getPhone());
        etEmail.setText(profileData.getEmail());
        etAddress.setText(profileData.getAddress());
        etPost.setText(profileData.getPostalCode());
        etCompany.setText(profileData.getCompany());
        etCity.setText(profileData.getCity());
        etState.setText(profileData.getState());
        etCountry.setText(profileData.getCountry());
        etBtw.setText(profileData.getBTWNumber());
        d1Start.setText(profileData.getMondayFromTime());
        d1End.setText(profileData.getMondayToTime());
        d2Start.setText(profileData.getTuesdayFromTime());
        d2End.setText(profileData.getTuesdayToTime());
        d3Start.setText(profileData.getWednesdayFromTime());
        d3End.setText(profileData.getWednesdayToTime());
        d4Start.setText(profileData.getThursdayFromTime());
        d4End.setText(profileData.getThursdayToTime());
        d5Start.setText(profileData.getFridayFromTime());
        d5End.setText(profileData.getFridayToTime());
        d6Start.setText(profileData.getSaturdayFromTime());
        d6End.setText(profileData.getSaturdayToTime());
        d7Start.setText(profileData.getSundayFromTime());
        d7End.setText(profileData.getSundayToTime());

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


    public void showPicker(final EditText editText)
    {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay>=0 && hourOfDay<12){
                    time = hourOfDay + ":" + minute + " AM";
                } else {
                    if(hourOfDay == 12){
                        time = hourOfDay + ":" + minute + " PM";
                    } else{
                        hourOfDay = hourOfDay -12;
                        time = hourOfDay + ":" + minute + " PM";
                    }
                }
                editText.setText(time);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("");
        mTimePicker.show();
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } catch (Exception e) {

                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            Log.e("resolveerror", "onActivityResult");
        }
        if (requestCode == SELECT_FILE)
            try {
                onSelectFromGalleryResult(intent.getData());
            } catch (Exception e) {

            }
        else if (requestCode == REQUEST_CAMERA)
            try {
                onCaptureImageResult(intent);
            } catch (Exception e) {
            }

    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImageBitmap(bitmap);
        if (bitmap != null) {
            file = convertStringToByteToFile(bitmapToString(bitmap));

        }
    }


    private void onSelectFromGalleryResult(Uri uri) {
        if (uri!=null) {
            ParcelFileDescriptor parcelFD = null;
            try {
                parcelFD = getActivity().getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor imageSource = parcelFD.getFileDescriptor();
                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(imageSource, null, o);
                // the new size we want to scale to
                final int REQUIRED_SIZE = 1024;
                // Find the correct scale value. It should be the power of 2.
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
                // decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
                profileImage.setImageBitmap(bitmap);
                if (bitmap != null) {
                    file = convertStringToByteToFile(bitmapToString(bitmap));

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private File convertStringToByteToFile(String path) {
        FileOutputStream fos;
        File file_image = new File(getActivity().getCacheDir(), "Sibi" + System.currentTimeMillis() + ".png");

        try {
            file_image.createNewFile();
            fos = new FileOutputStream(file_image);
            byte[] encodeByte = Base64.decode(path, Base64.DEFAULT);
            fos.write(encodeByte);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file_image;
    }

    private String bitmapToString(Bitmap in) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
    }


    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.container)
    RelativeLayout container;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_post)
    EditText etPost;

    @BindView(R.id.et_city)
    EditText etCity;

    @BindView(R.id.et_state)
    EditText etState;

    @BindView(R.id.et_company)
    EditText etCompany;

    @BindView(R.id.et_country)
    EditText etCountry;

    @BindView(R.id.et_btw)
    EditText etBtw;

    @BindView(R.id.d1_start)
    EditText d1Start;

    @BindView(R.id.d1_end)
    EditText d1End;

    @BindView(R.id.d2_start)
    EditText d2Start;

    @BindView(R.id.d2_end)
    EditText d2End;

    @BindView(R.id.d3_start)
    EditText d3Start;

    @BindView(R.id.d3_end)
    EditText d3End;

    @BindView(R.id.d4_start)
    EditText d4Start;

    @BindView(R.id.d4_end)
    EditText d4End;

    @BindView(R.id.d5_start)
    EditText d5Start;

    @BindView(R.id.d5_end)
    EditText d5End;

    @BindView(R.id.d6_start)
    EditText d6Start;

    @BindView(R.id.d6_end)
    EditText d6End;

    @BindView(R.id.d7_start)
    EditText d7Start;

    @BindView(R.id.d7_end)
    EditText d7End;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @OnClick(R.id.d1_start)
    void d1Start(){
        showPicker(d1Start);
    }

    @OnClick(R.id.d1_end)
    void d1End(){
        showPicker(d1End);
    }

    @OnClick(R.id.d2_start)
    void d2Start()
    {
        showPicker(d2Start);
    }

    @OnClick(R.id.d2_end)
    void d2End(){
        showPicker(d2End);
    }

    @OnClick(R.id.d3_start)
    void d3Start(){
        showPicker(d3Start);
    }

    @OnClick(R.id.d3_end)
    void d3End(){
        showPicker(d3End);
    }

    @OnClick(R.id.d4_start)
    void d4Start(){
        showPicker(d4Start);
    }

    @OnClick(R.id.d4_end)
    void d4End(){
        showPicker(d4End);
    }

    @OnClick(R.id.d5_start)
    void d5Start(){
        showPicker(d5Start);
    }

    @OnClick(R.id.d5_end)
    void d5End(){
        showPicker(d5End);
    }

    @OnClick(R.id.d6_start)
    void d6Start(){
        showPicker(d6Start);
    }

    @OnClick(R.id.d6_end)
    void d6End(){
        showPicker(d6End);
    }

    @OnClick(R.id.d7_start)
    void d7Start(){
        showPicker(d7Start);
    }

    @OnClick(R.id.d7_end)
    void d7End(){
        showPicker(d7End);
    }

    @OnClick(R.id.upload_img)
    void uploadImg(){
      selectImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.bt_save)
    void btSave(){
        if (validation()) {
            editCustomerProfile();
        }
    }















}
