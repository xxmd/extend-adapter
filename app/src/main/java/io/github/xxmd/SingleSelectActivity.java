package io.github.xxmd;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Arrays;

import io.github.xxmd.adapter.ImageSingleSelectAdapter;
import io.github.xxmd.databinding.ActivitySelectBinding;

public class SingleSelectActivity extends AppCompatActivity implements SelectableAdapter.EditableChangeListener {
    private ActivitySelectBinding binding;
    private ImageSingleSelectAdapter imageSingleSelectAdapter;

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
        binding.tvRightText.setOnClickListener(view -> imageSingleSelectAdapter.setEditable(!imageSingleSelectAdapter.isEditable()));
        imageSingleSelectAdapter.setEditableChangeListener(this);
    }

    private void initView() {
        binding.tvTitle.setText("SingleSelect");
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.footer.setVisibility(View.GONE);
        imageSingleSelectAdapter = new ImageSingleSelectAdapter(Arrays.asList(DataProvider.imageUrlArr));
        binding.recyclerView.setAdapter(imageSingleSelectAdapter);
        binding.recyclerView.setItemAnimator(null);
    }

    @Override
    public void onEditableChange(boolean editable) {
        binding.tvRightText.setText(editable ? "Cancel" : "Manage");
    }
}
