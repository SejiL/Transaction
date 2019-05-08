package com.example.sejil.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sejil.myapplication.R;
import com.example.sejil.myapplication.model.TransActions;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    ArrayList<TransActions> transActions;
    Context context;

    public TransactionsAdapter(Context context, ArrayList<TransActions> transActions) {
        this.context=context;
        this.transActions = transActions;
    }

    @NonNull
    @Override
    public TransactionsAdapter.TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);

        return new TransactionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.TransactionsViewHolder holder, int position) {
        final TransActions transAction = transActions.get(position);
        holder.amount.setText(transAction.price);
        holder.reason.setText(transAction.details);
    }

    @Override
    public int getItemCount() {
        return transActions.size();
    }

    class TransactionsViewHolder extends RecyclerView.ViewHolder{

        TextView amount,reason;
        ImageButton delete,edit;


        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            amount  = itemView.findViewById(R.id.tvAmount);
            reason  = itemView.findViewById(R.id.tvReason);
            delete  = itemView.findViewById(R.id.ibtDelete);
            edit  = itemView.findViewById(R.id.ibtEdit);

        }
    }
}
