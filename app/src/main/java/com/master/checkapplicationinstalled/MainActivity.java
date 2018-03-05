package com.master.checkapplicationinstalled;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean isAppEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        Button buttonGoogleMap = findViewById(R.id.googleMap);
        buttonGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check for App exists
                //check google map installed or not
                if (isAppInstalled("com.google.android.apps.maps") && isAppEnabled) {
                    navigateTo("19.9662414","79.2054999");
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.install_googleMap_error_msg), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonWhatsApp = findViewById(R.id.whatsApp);
        buttonWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check for App exists
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                //check whatsApp installed or not
                if (isAppInstalled("com.whatsapp") && isAppEnabled) {
                    sendIntent.setComponent(new ComponentName("com.whatsapp",
                            "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("917674001311")
                            + "@s.whatsapp.net");//phone number without "+" prefix
                    startActivity(sendIntent);
                } else {
                    //check whatsApp Business installed or not
                    if (isAppInstalled("com.whatsapp.w4b") && isAppEnabled) {
                        sendIntent.setComponent(new ComponentName("com.whatsapp.w4b",
                                "com.whatsapp.Conversation"));
                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("917674001311")
                                + "@s.whatsapp.net");//phone number without "+" prefix
                        startActivity(sendIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.install_whatsApp_error_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public boolean isAppInstalled(String packageName) {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(packageName, 0);
            isAppEnabled = info.enabled;
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void navigateTo(String destinationLat, String destinationLng) {
        String url = "google.navigation:q=" + destinationLat + "," + destinationLng + "&mode=d";
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
