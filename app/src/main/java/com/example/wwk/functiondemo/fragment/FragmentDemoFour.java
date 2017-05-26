package com.example.wwk.functiondemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.MyUser;
import com.example.wwk.functiondemo.ui.ExternalDialog;
import com.example.wwk.functiondemo.ui.LoginActivity;
import com.example.wwk.functiondemo.utils.L;
import com.example.wwk.functiondemo.utils.SharedPreferencesUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wwk.functiondemo.R.id.profile_image;

/**
 * Created by wwk on 17/5/18.
 *
 * An interface of User's information
 */

public class FragmentDemoFour extends Fragment implements View.OnClickListener {


    private Button mExitLoginButton;
    private TextView mEditProfileText;

    private EditText mEditProfileName;
    private EditText mEditProfileGender;
    private EditText mEditProfileAge;
    private EditText mEditProfileDescription;

    // button of Update profile
    private Button mUpdateProfileButton;
    // Change image of profile
    private CircleImageView mProfileImage;
    private ExternalDialog mDialog;

    private Button mCameraButton;
    private Button mPictureAlbum;
    private Button mCancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_four, null);

        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        mExitLoginButton = (Button) view.findViewById(R.id.exit_login_button);
        mExitLoginButton.setOnClickListener(this);
        mEditProfileText = (TextView) view.findViewById(R.id.edit_profile);
        mEditProfileText.setOnClickListener(this);
        mUpdateProfileButton = (Button) view.findViewById(R.id.update_profile_button);
        mUpdateProfileButton.setOnClickListener(this);

        mEditProfileName = (EditText) view.findViewById(R.id.edit_username_profile);
        mEditProfileGender = (EditText) view.findViewById(R.id.edit_gender_profile);
        mEditProfileAge = (EditText) view.findViewById(R.id.edit_age_profile);
        mEditProfileDescription = (EditText) view.findViewById(R.id.edit_description_profile);

        mProfileImage = (CircleImageView) view.findViewById(profile_image);
        mProfileImage.setOnClickListener(this);

        // Get string from SharedPreferences(SharedPreferencesUtils)
        String getImageString = SharedPreferencesUtils.getString(getActivity(), "image_title", "");
        if (!getImageString.equals("")) {
            // Transform string to byteArray by base64
            byte[] byteArray = Base64.decode(getImageString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            // Create bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            mProfileImage.setImageBitmap(bitmap);
        }

        // Initialize dialog
        mDialog = new ExternalDialog(getActivity(), 0, 0, R.layout.dialog_set_photo, R.style.anim_style, Gravity.BOTTOM, 0);
        // Set function of can not be cancel when click screen
        mDialog.setCancelable(false);
        mCameraButton = (Button) mDialog.findViewById(R.id.open_camera_button);
        mCameraButton.setOnClickListener(this);
        mPictureAlbum = (Button) mDialog.findViewById(R.id.get_pictures_button);
        mPictureAlbum.setOnClickListener(this);
        mCancelButton = (Button) mDialog.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);

        // Can not edit profile that is default
        setEnabled(false);
        // Set value of default
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        mEditProfileName.setText(userInfo.getUsername());
        mEditProfileAge.setText(userInfo.getAge() + "");
        mEditProfileGender.setText(userInfo.isGender() ? "male" : "female");
        mEditProfileDescription.setText(userInfo.getIntroduction());

    }

    private void setEnabled(boolean is) {
        mEditProfileName.setEnabled(is);
        mEditProfileAge.setEnabled(is);
        mEditProfileGender.setEnabled(is);
        mEditProfileDescription.setEnabled(is);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // Exit login
            case R.id.exit_login_button:
                // clear user's cache
                MyUser.logOut();

                BmobUser currentUser = MyUser.getCurrentUser();
                L.information("currentUser's value: " + currentUser);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

            // Edit profile
            case R.id.edit_profile:
                setEnabled(true);
                mUpdateProfileButton.setVisibility(View.VISIBLE);
                break;

            // Update profile
            case R.id.update_profile_button:
                // Get values from edit
                String name = mEditProfileName.getText().toString();
                String age = mEditProfileAge.getText().toString();
                String gender = mEditProfileGender.getText().toString();
                String description = mEditProfileDescription.getText().toString();

                // Estimate input whether or not is empty
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(gender)) {
                    // Update
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setAge(Integer.parseInt(age));
                    // Gender
                    if (gender.equals("male")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                    // Description
                    //简介
                    if (!TextUtils.isEmpty(description)) {
                        user.setIntroduction(description);
                    } else {
                        user.setIntroduction("This man is lazy and nothing is left.");
                    }

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnabled(false);
                                mUpdateProfileButton.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Successful Update", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Failure to Update", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Input can not be empty", Toast.LENGTH_LONG).show();
                }
                break;

            // change profile's image
            case profile_image:
                mDialog.show();
                break;

            case R.id.cancel_button:
                mDialog.dismiss();
                break;

            case R.id.open_camera_button:
                openCamera();
                break;

            case R.id.get_pictures_button:
                openPictureAlbum();
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImage.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //  Jump and open camera(Phone's)
    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Estimate memory whether or not is usable
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        mDialog.dismiss();
    }

    //  Jump and open Picture Album(Phone's)
    private void openPictureAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        mDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                // data of picture's album
                case IMAGE_REQUEST_CODE:
                    trimPhoto(data.getData());
                    break;
                // data of camera
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    trimPhoto(Uri.fromFile(tempFile));
                    break;

                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        // Get setting of image
                        setImageToView(data);
                       // delete image before
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;

            }
        }
    }

    private void trimPhoto(Uri uri) {
        if (uri == null) {
            L.error("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // Set tailor
        intent.putExtra("crop", "true");
        // ratio of width and height
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // quality of image
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // send data
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void setImageToView(Intent data) {

        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            mProfileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Save
        BitmapDrawable drawable = (BitmapDrawable) mProfileImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        // Transform bitmap to output stream
        ByteArrayOutputStream bytStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, bytStream);
        // Transform output stream to string by base64
        byte[] bytArray = bytStream.toByteArray();
        String imageString = new String(Base64.encodeToString(bytArray, Base64.DEFAULT));
        // Save String to SharedPreferences(SharedPreferencesUtils)
        SharedPreferencesUtils.putString(getActivity(), "image_title", imageString);


    }

}
