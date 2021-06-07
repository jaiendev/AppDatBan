package com.trungdang.appdatban.HomeAdmin.Quanlyban;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class swipeitemreyclerview extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    private itemswipe listener;
    public swipeitemreyclerview(int dragDirs, int swipeDirs, itemswipe listener) {
        super(dragDirs, swipeDirs);
        this.listener=listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener!=null)
        {
            listener.onSwipe(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder!=null)
        {
            View foregroundView=((QuanlybanAdapter.ViewHolder) viewHolder).relativeLayout;
//            foregroundView.setBackgroundResource(R.color.xam);
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView=((QuanlybanAdapter.ViewHolder) viewHolder).relativeLayout;
//        foregroundView.setBackgroundResource(R.color.xam);
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX/2,dY/2,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView=((QuanlybanAdapter.ViewHolder) viewHolder).relativeLayout;
//        foregroundView.setBackgroundResource(R.color.xam);
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX/2,dY/2,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView=((QuanlybanAdapter.ViewHolder) viewHolder).relativeLayout;
//        foregroundView.setBackgroundResource(R.color.xam);
        getDefaultUIUtil().clearView(foregroundView);
    }
}
