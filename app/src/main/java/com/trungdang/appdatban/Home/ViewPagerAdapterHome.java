package com.trungdang.appdatban.Home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trungdang.appdatban.Home.HomeFragment;
import com.trungdang.appdatban.Home.Khuyenmaifragment;
import com.trungdang.appdatban.Home.LichsugiaodichFragment;
import com.trungdang.appdatban.Home.ProfileFragment;

public class ViewPagerAdapterHome extends FragmentStatePagerAdapter {
    private View context;

    public ViewPagerAdapterHome(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new HomeFragment();
            case 1: return new LichsugiaodichFragment();
            case 2: return new Khuyenmaifragment();
            case 3: return new ProfileFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
