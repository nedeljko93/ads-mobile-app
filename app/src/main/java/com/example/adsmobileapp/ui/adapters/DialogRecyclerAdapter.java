package com.example.adsmobileapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adsmobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class DialogRecyclerAdapter extends RecyclerView.Adapter<DialogRecyclerAdapter.ViewHolder> {
    private List<String> dialogItems;
    private List<String> selecetedItems = new ArrayList<>();

    public DialogRecyclerAdapter(List<String> dialogItems) {
        this.dialogItems = dialogItems;
    }

    @NonNull
    @Override
    public DialogRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogRecyclerAdapter.ViewHolder holder, int position) {
        holder.cbItem.setText(dialogItems.get(position));

    }

    @Override
    public int getItemCount() {
        if (dialogItems == null) {
            return 0;
        }
        return dialogItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItem = itemView.findViewById(R.id.cbItemCustomDialog);
            cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selecetedItems.add(dialogItems.get(getAdapterPosition()));
                    } else {
                        selecetedItems.remove(dialogItems.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public List<String> getSelectedDialogItems() {
        return selecetedItems;
    }

    public void setDialogItems(List<String> dialogItems) {
        this.dialogItems = dialogItems;
        notifyDataSetChanged();
    }
}
