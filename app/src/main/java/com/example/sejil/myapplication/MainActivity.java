package com.example.sejil.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageMain = findViewById(R.id.iv_main);
        final TextView mainText = findViewById(R.id.tv_main);
        final Button btnSave = findViewById(R.id.save_bt);
        final Button btnShow = findViewById(R.id.show_db);
        final EditText priceET = findViewById(R.id.price_et);
        final EditText detailsET = findViewById(R.id.details_et);

        final Date currentTime = Calendar.getInstance().getTime();

        final DatabaseHandler databaseHandler = new DatabaseHandler(this);

        askForPermission(Manifest.permission.RECEIVE_SMS, 1);

        String sender = "";
        String message = "";

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            if (extra.containsKey("MSG_SENDER")) {
                sender = intent.getStringExtra("MSG_SENDER");
                message = intent.getStringExtra("MSG_BODY");
            }

            if (!sender.isEmpty() && !message.isEmpty()) {
                if (sender.equals("+9820004000")) {
                    String[] split_enters = message.split("\n");
                    String[] split_money = split_enters[1].split(":");
                    if (split_money[0].equals("انتقال") || split_money[0].equals("خریداینترنتی") || split_money[0].equals("برداشت") || split_money[0].equals("خودپرداز")) {
                        String money = split_money[1];
                        if (money.substring(money.length() - 1).equals("-")) {
                            imageMain.setImageResource(R.drawable.pic2);
                            mainText.setText(R.string.down_money_msg);
                            priceET.setHint(R.string.price_downmoney_hint);
                            detailsET.setHint(R.string.details_downmoney_hint);
                            priceET.setText(money.substring(0, money.length() - 2).replaceAll("[,]", ""));
                        } else if (money.substring(money.length() - 1).equals("+")) {
                            imageMain.setImageResource(R.drawable.pic3);
                            mainText.setText(R.string.up_money_msg);
                            priceET.setHint(R.string.price_upmoney_hint);
                            detailsET.setHint(R.string.details_upmoney_hint);
                            priceET.setText(money.substring(0, money.length() - 2).replaceAll("[,]", ""));
                        }
                    }
                } else if (sender.equals("Bank Mellat")) {
                    // Bank Mellat
                }
            }
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!priceET.getText().toString().equals("") && !detailsET.getText().toString().equals("")) {
                    TransActions transActionsObj = new TransActions(priceET.getText().toString(), detailsET.getText().toString(), currentTime.toString());
                    databaseHandler.addTransAction(transActionsObj);
                    Toast.makeText(getBaseContext(), R.string.saved_msg, Toast.LENGTH_SHORT).show();
                    priceET.setText("");
                    detailsET.setText("");
                } else {
                    Toast.makeText(getBaseContext(), R.string.complete_fields_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btnShow.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Cursor result = databaseHandler.getAllTransActions();
//                if(result.moveToFirst()){
////                    Log.d("Show DB", "onClick Show db: "+result.getString(1)+" - "+result.getString(2)+" - "+result.getString(3));
//                    Log.d("Show DB", "onClick Show db: "+result.getCount());
//                }
//            }
//        });

    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "آفرین که دسترسی دادی.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "باید دسترسی بدی! :|", Toast.LENGTH_SHORT).show();
            askForPermission(Manifest.permission.RECEIVE_SMS, 1);
        }
    }
}
