package io.github.xxmd;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Arrays;
import java.util.List;

import io.github.xxmd.adapter.ImageMultipleSelectAdapter;
import io.github.xxmd.databinding.ActivitySelectBinding;

public class MultipleSelectActivity extends AppCompatActivity implements SelectableAdapter.EditableChangeListener, MultipleSelectAdapter.SelectedListChangeListener {
    private ActivitySelectBinding binding;
    private ImageMultipleSelectAdapter imageMultipleSelectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        bindEvent();
    }

    private void bindEvent() {
        binding.tvBack.setOnClickListener(view -> finish());
        binding.tvRightText.setOnClickListener(view -> imageMultipleSelectAdapter.setEditable(!imageMultipleSelectAdapter.isEditable()));
        imageMultipleSelectAdapter.setEditableChangeListener(this);
        imageMultipleSelectAdapter.setOnSelectedListChangeListener(this);
        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        imageMultipleSelectAdapter.selectAll();
                    } else {
                        imageMultipleSelectAdapter.unSelectAll();
                    }
                }
            }
        });
    }

    private void initView() {
        binding.tvTitle.setText("MultipleSelect");
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        imageMultipleSelectAdapter = new ImageMultipleSelectAdapter(Arrays.asList(DataProvider.imageUrlArr));
        binding.recyclerView.setAdapter(imageMultipleSelectAdapter);
        binding.recyclerView.setItemAnimator(null);
    }

    @Override
    public void onEditableChange(boolean editable) {
        binding.tvRightText.setText(editable ? "Cancel" : "Manage");
        binding.footer.setVisibility(editable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSelectedListChange(List selectedList) {
        binding.tvCount.setText(String.format("Checked(%d/%d)", selectedList.size(), imageMultipleSelectAdapter.itemList.size()));
        binding.checkBox.setChecked(imageMultipleSelectAdapter.itemList.size() != 0 && selectedList.size() == imageMultipleSelectAdapter.itemList.size());
    }
}
