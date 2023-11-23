package io.github.xxmd.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.xxmd.MultipleSelectAdapter;
import io.github.xxmd.R;

public class ImageMultipleSelectAdapter extends MultipleSelectAdapter<String, ImageMultipleSelectAdapter.ViewHolder> {

    public ImageMultipleSelectAdapter(List<String> itemList) {
        super(itemList);
    }

    @Override
    public void onItemSelected(List<String> itemList, int position, String item, ViewHolder viewHolder) {
        viewHolder.checkBox.setChecked(true);
        viewHolder.imageView.setColorFilter(Color.parseColor("#80000000"));
    }

    @Override
    public void onItemUnSelected(List<String> itemList, int position, String item, ViewHolder viewHolder) {
        viewHolder.checkBox.setChecked(false);
        viewHolder.imageView.setColorFilter(null);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_select, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String imageUrl = itemList.get(position);
        Glide.with(holder.imageView)
                .load(imageUrl)
                .into(holder.imageView);

        holder.checkBox.setVisibility(isEditable() ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(isSelected(imageUrl));
        holder.checkBox.setClickable(false);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_cover);
            checkBox = view.findViewById(R.id.check_box);
        }
    }
}
