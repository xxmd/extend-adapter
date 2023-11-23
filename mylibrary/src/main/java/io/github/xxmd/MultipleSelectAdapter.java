package io.github.xxmd;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleSelectAdapter<T, VH extends RecyclerView.ViewHolder> extends SelectableAdapter<T, VH> {

    public MultipleSelectAdapter(List<T> itemList) {
        super(itemList);
        setItemClickListener(new ItemClickListener<View, T>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View targetView, T item) {
                if (!isEditable()) {
                    return;
                }
                if (selectedList.contains(item)) {
                    unSelectByObject(item);
                } else {
                    selectByObject(item);
                }
            }
        });
    }

    public interface SelectedListChangeListener<T> {
        void onSelectedListChange(List<T> selectedList);
    }

    private SelectedListChangeListener listener;
    public void setOnSelectedListChangeListener(SelectedListChangeListener listener) {
        this.listener = listener;
        // invoke once when setOnSelectedListChange listener set
        listener.onSelectedListChange(selectedList);
    }
    private List<T> selectedList = new ArrayList<>();

    public List<T> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<T> selectedList) {
        this.selectedList = selectedList;
        if (listener != null) {
            listener.onSelectedListChange(selectedList);
        }
    }

    @Override
    void addSelectItem(T item) {
        if (!selectedList.contains(item)) {
            selectedList.add(item);
            setSelectedList(selectedList);
        }
    }

    @Override
    void removeSelectItem(T item) {
        selectedList.remove(item);
        setSelectedList(selectedList);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        super.onBindViewHolder(holder, position);

        T item = itemList.get(position);
        if (selectedList.contains(item)) {
            onItemSelected(itemList, position, item, holder);
        } else {
            onItemUnSelected(itemList, position, item, holder);
        }
    }

    @Override
    public boolean isSelected(T item) {
        return selectedList != null && selectedList.contains(item);
    }

    @Override
    public void unSelectAll() {
        setSelectedList(new ArrayList<>());
        notifyDataSetChanged();
    }

    public void selectAll() {
        setSelectedList(new ArrayList<>(itemList));
        notifyDataSetChanged();
    }
}
