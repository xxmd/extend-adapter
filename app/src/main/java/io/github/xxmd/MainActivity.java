package io.github.xxmd;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.github.xxmd.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindEvent();
    }

    private void bindEvent() {
        binding.btnSingle.setOnClickListener(v -> {
            Intent intent = new Intent(this, SingleSelectActivity.class);
            startActivity(intent);
        });

        binding.btnMultiple.setOnClickListener(v -> {
            Intent intent = new Intent(this, MultipleSelectActivity.class);
            startActivity(intent);
        });
    }

}
