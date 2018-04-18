package com.udacity.sandwichclub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sandwichclub.model.Sandwich;

import static android.view.LayoutInflater.from;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichViewHolder> {

    private String[] sandwiches;
    private ListItemClickListener listItemClickListener;

    public SandwichAdapter(String[] sandwiches, ListItemClickListener listItemClickListener) {
        this.sandwiches = sandwiches;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public SandwichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = from(parent.getContext()).inflate(R.layout.item, parent, false);
        final SandwichViewHolder sandwichViewHolder = new SandwichViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener.onItemClick(sandwichViewHolder.getAdapterPosition());
            }
        });
        return sandwichViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SandwichViewHolder holder, final int position) {
        holder.mSandwichName.setText(sandwiches[position]);
    }

    @Override
    public int getItemCount() {
        return sandwiches == null? 0: sandwiches.length;
    }

    public class SandwichViewHolder extends RecyclerView.ViewHolder {

        public final TextView mSandwichName;

        public SandwichViewHolder(View itemView) {
            super(itemView);
            mSandwichName = itemView.findViewById(R.id.sandwitch_title);
        }
    }

}
