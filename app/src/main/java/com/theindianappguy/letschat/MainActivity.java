package com.theindianappguy.letschat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.theindianappguy.letschat.CustomClasses.UserClass;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 101;
    public static final int RequestPermissionCode = 1;
    /*UI Components*/
    FloatingActionButton addChat;

    /*Variables*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EnableRuntimePermission();
        addChat = findViewById(R.id.addChat);
        addChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.need_read_contact_permission_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView yes, no;
        yes = dialogView.findViewById(R.id.yes);
        no = dialogView.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
                alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Its Required For Properfuntioning of the app", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.READ_CONTACTS)) {

            // Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            showCustomDialog();
        }
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Read Contacts Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(MainActivity.this, "Read Contacts Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

        super.onActivityResult(RequestCode, ResultCode, ResultIntent);

        switch (RequestCode) {

            case (PICK_CONTACT):
                if (ResultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "";
                    int IDresultHolder;

                    uri = ResultIntent.getData();

                    cursor1 = getContentResolver().query(uri, null, null, null, null);

                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult);

                        if (IDresultHolder == 1) {

                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                //name.setText(TempNameHolder);
                                //number.setText(TempNumberHolder);
                                //Toast.makeText(this, "Name: " + TempNameHolder + " Number:" + TempNumberHolder, Toast.LENGTH_SHORT).show();
                                openChatWithContact(TempNameHolder);

                            }
                        }

                    }
                }
                break;
        }
    }

    private void openChatWithContact(String phone) {
        if(numberAccountExistes()){
            Intent intent = new Intent(MainActivity.this, ChatPage.class);
            UserClass userClass = getUserInfoByPhone(phone);
            intent.putExtra("name", userClass.getName());
            intent.putExtra("profileUrl", userClass.getProfileurl());
            intent.putExtra("status", userClass.getStatus());
            startActivity(intent);

        }else{
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            String playstoreUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;
            String messageToSend = "Check out letschat, I use it to message and call the people i care about. Get it for free at "+playstoreUrl;
            String number = phone;

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", messageToSend);
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        }
    }

    private UserClass getUserInfoByPhone(String phone) {

        UserClass user = new UserClass();
        return  user;
    }

    private boolean numberAccountExistes() {

        //TODO add code to check if there exists an account with the number
        return  false;
    }

}
