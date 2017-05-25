package com.example.wwk.functiondemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wwk on 17/5/18.
 *
 * An interface of User's information
 */

public class FragmentDemoFour extends Fragment implements View.OnClickListener {

    // Bmob's Key
    public static final String BMOB_APP_ID = "4d345003be01bddf52e5e1bfbe0f2ea2";
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
        // Initialize Bmob
        Bmob.initialize(getContext(), BMOB_APP_ID);
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

        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        mProfileImage.setOnClickListener(this);

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
            case R.id.profile_image:
                mDialog.show();
                break;

            case R.id.cancel_button:
                mDialog.dismiss();
                break;

            case R.id.open_camera_button:
                mDialog.dismiss();
                break;

            case R.id.get_pictures_button:
                mDialog.dismiss();
                break;
        }
    }
}
