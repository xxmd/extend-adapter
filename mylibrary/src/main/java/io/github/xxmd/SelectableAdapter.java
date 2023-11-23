package io.github.xxmd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public List<T> itemList = new ArrayList<>();
    private boolean editable = false;

    public interface EditableChangeListener {
        void onEditableChange(boolean editable);
    }

    private EditableChangeListener listener;

    public interface ItemClickListener<V extends View, T> {
        void onItemClick(RecyclerView.ViewHolder viewHolder, V targetView, T item);
    }

    private ItemClickListener itemClickListener;

    public <V extends View> void setItemClickListener(ItemClickListener<V, T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setEditableChangeListener(EditableChangeListener listener) {
        this.listener = listener;
        listener.onEditableChange(editable);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        unSelectAll();
        notifyDataSetChanged();
        if (listener != null) {
            listener.onEditableChange(editable);
        }
    }

    public abstract void unSelectAll();

    public SelectableAdapter(List<T> itemList) {
        this.itemList = itemList;
    }

    abstract void addSelectItem(T item);

    abstract void removeSelectItem(T item);

    public void selectByIndex(int index) {
        T item = itemList.get(index);
        addSelectItem(item);
        notifyItemChanged(index);
    }

    public void selectByObject(T item) {
        selectByIndex(itemList.indexOf(item));
    }

    public void unSelectByIndex(int index) {
        T item = itemList.get(index);
        removeSelectItem(item);
        notifyItemChanged(index);
    }

    public void unSelectByObject(T item) {
        unSelectByIndex(itemList.indexOf(item));
    }

    public abstract void onItemSelected(List<T> itemList, int position, T item, VH viewHolder);

    public abstract void onItemUnSelected(List<T> itemList, int position, T item, VH viewHolder);

    public abstract boolean isSelected(T item);

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(holder, holder.itemView, itemList.get(position));
            }
        });
    }

    public View createViewByLayoutId(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        return view;
    }
}
