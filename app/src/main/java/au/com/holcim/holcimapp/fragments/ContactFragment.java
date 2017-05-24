package au.com.holcim.holcimapp.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.activities.SharedUser;
import au.com.holcim.holcimapp.models.User;
import au.com.holcim.holcimapp.network.HolcimService;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chowii on 24/05/17.
 */

public class ContactFragment extends Fragment {

    private static final int REQUEST_CODE = 123;
    @Bind(R.id.call_iv)
    ImageView callButton;

    @Bind(R.id.email_iv)
    ImageView emailButton;

    SharedUser user;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        this.user = SharedUser.getInstance();
        callButton.setOnClickListener(v -> callIntent() );
        emailButton.setOnClickListener(v -> emailIntent() );
        return view;
    }

    private void emailIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("vnd.android.cursor.dir/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getUser().contactEmail});
        startActivity(emailIntent);
    }


    private void callIntent(){
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getUser().contactNumber));
        int permissionCheck = ContextCompat.checkSelfPermission(
                getActivity(),
                android.Manifest.permission.CALL_PHONE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) startActivity(callIntent);
        else ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ((requestCode == REQUEST_CODE)
                && (grantResults.length > 0)
                && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
            callIntent();
    }
}
