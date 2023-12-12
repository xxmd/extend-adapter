package io.github.xxmd;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class SingleSelectAdapter<T, VH extends RecyclerView.ViewHolder> extends SelectableAdapter<T, VH> {
    private boolean cancelable = false;

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public SingleSelectAdapter(List<T> itemList) {
        super(itemList);
        setItemClickListener(new ItemClickListener<View, T>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View targetView, T item) {
                if (!isEditable()) {
                    return;
                }
                if (item.equals(selectItem) && cancelable) {
                    unSelectByObject(item);
                } else {
                    selectByObject(item);
                }
            }
        });
    }

    private T selectItem;

    public interface SelectedItemChangeListener<T> {
        void onSelectedItemChange(T selectedItem);
    }

    private SelectedItemChangeListener<T> listener;

    public void setSelectedItemChangeListener(SelectedItemChangeListener<T> listener) {
        this.listener = listener;
        listener.onSelectedItemChange(selectItem);
    }

    public T getSelectItem() {
        return selectItem;
    }

    private void setSelectItem(T selectItem) {
        if (this.selectItem != null) {
            int preSelectedIndex = itemList.indexOf(this.selectItem);
            this.selectItem = selectItem;
            notifyItemChanged(preSelectedIndex);
        } else {
            this.selectItem = selectItem;
        }
        if (listener != null) {
            listener.onSelectedItemChange(selectItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);
        T item = itemList.get(position);
        if (selectItem != null && selectItem.equals(item)) {
            onItemSelected(itemList, position, item, holder);
        } else {
            onItemUnSelected(itemList, position, item, holder);
        }
    }

    @Override
    void removeSelectItem(T item) {
        setSelectItem(null);
    }

    @Override
    void addSelectItem(T item) {
        setSelectItem(item);
    }

    @Override
    public boolean isSelected(T item) {
        return selectItem != null && selectItem.equals(item);
    }

    @Override
    public void unSelectAll() {
        setSelectItem(null);
    }
}
