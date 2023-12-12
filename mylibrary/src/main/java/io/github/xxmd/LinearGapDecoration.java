package io.github.xxmd;

import android.graphics.Rect;
import android.text.Layout;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearGapDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int direction;

    public LinearGapDecoration(int space) {
        this(space, LinearLayoutManager.VERTICAL);
    }

    public LinearGapDecoration(int space, int direction) {
        this.space = space;
        this.direction = direction;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            switch (direction) {
                case LinearLayoutManager.VERTICAL:
                    outRect.top = space;
                    break;
                case LinearLayoutManager.HORIZONTAL:
                    outRect.left = space;
                    break;
            }
        }
    }
}
