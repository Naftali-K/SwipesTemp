package com.nk.swipestemp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nk.swipestemp.R;
import com.nk.swipestemp.enums.RecyclerViewType;
import com.nk.swipestemp.models.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private RecyclerViewType recyclerViewType;
    private List<Item> itemList = new ArrayList<>();

    private int listPosition;

    public RecyclerViewAdapter(Context context, RecyclerViewType recyclerViewType) {
        this.context = context;
        this.recyclerViewType = recyclerViewType;
    }

    public RecyclerViewAdapter(Context context, RecyclerViewType recyclerViewType, List<Item> itemList) {
        this(context, recyclerViewType);
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        if (recyclerViewType == RecyclerViewType.VERTICAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        }

        if (recyclerViewType == RecyclerViewType.HORIZONTAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_horizontal, parent, false);
        }

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
        listPosition = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView idTextView, titleTextView, descriptionTextView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            idTextView = itemView.findViewById(R.id.id_text_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }

        void bind(Item item, int position) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.icon_android);
            imageView.setImageDrawable(drawable);

            idTextView.setText(String.valueOf(item.getID()));
            titleTextView.setText(item.getTitle());
            descriptionTextView.setText(item.getDescription());
        }
    }

    public void setItemList(List<Item> itemList) {
        if (itemList == null) {
            return;
        }

        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void addItemList(List<Item> itemList) {
        if (itemList == null) {
            return;
        }

        int index = this.itemList.size();

        this.itemList.addAll(itemList);
        notifyItemInserted(index);
    }


    public int getListPosition() {
        return listPosition;
    }
}
