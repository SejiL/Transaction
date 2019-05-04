package com.example.sejil.myapplication.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.example.sejil.myapplication.R;
import com.example.sejil.myapplication.database.DatabaseHandler;
import com.example.sejil.myapplication.model.TransActions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateExpenseBottomSheet extends BottomSheetDialogFragment {

    View view;
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.edt_expense_price)
    EditText edtPrice;
    @BindView(R.id.edt_expense_details)
    EditText edtDetails;
    @BindView(R.id.btn_save_expense)
    Button btnSave;


    public static CreateExpenseBottomSheet newInstance() {

        Bundle args = new Bundle();
        CreateExpenseBottomSheet fragment = new CreateExpenseBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getContext(), R.layout.bottom_sheet_create_expense, null);
        ButterKnife.bind(this, view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final Date currentTime = Calendar.getInstance().getTime();
        final DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        dialog.setContentView(view);
        edtPrice.addTextChangedListener(new MoneyTextWatcher(edtPrice));

        askForPermission(Manifest.permission.RECEIVE_SMS, 1);

        String sender = "";
        String message = "";

        Intent intent = getActivity().getIntent();
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
                            ivMain.setImageResource(R.drawable.pic2);
                            tvMain.setText(R.string.down_money_msg);
                            edtPrice.setHint(R.string.price_downmoney_hint);
                            edtDetails.setHint(R.string.details_downmoney_hint);
                            edtPrice.setText(money.substring(0, money.length() - 2).replaceAll("[,]", ""));
                        } else if (money.substring(money.length() - 1).equals("+")) {
                            ivMain.setImageResource(R.drawable.pic3);
                            tvMain.setText(R.string.up_money_msg);
                            edtPrice.setHint(R.string.price_upmoney_hint);
                            edtDetails.setHint(R.string.details_upmoney_hint);
                            edtPrice.setText(money.substring(0, money.length() - 2).replaceAll("[,]", ""));
                        }
                    }
                } else if (sender.equals("Bank Mellat")) {
                    // Bank Mellat
                }
            }
        }
        btnSave.setOnClickListener(v -> {

            if (!edtPrice.getText().toString().equals("") && !edtDetails.getText().toString().equals("")) {
                TransActions transActionsObj = new TransActions(edtPrice.getText().toString(), edtDetails.getText().toString(), currentTime.toString());
                databaseHandler.addTransAction(transActionsObj);
                Toast.makeText(getActivity(), R.string.saved_msg, Toast.LENGTH_SHORT).show();
                edtPrice.setText("");
                edtDetails.setText("");
                dialog.dismiss();
            } else {
                Toast.makeText(getActivity(), R.string.complete_fields_msg, Toast.LENGTH_SHORT).show();
            }
        });
        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ActivityCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "آفرین که دسترسی دادی.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "باید دسترسی بدی! :|", Toast.LENGTH_SHORT).show();
            askForPermission(Manifest.permission.RECEIVE_SMS, 1);
        }
    }

}
