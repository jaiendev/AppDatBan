package com.trungdang.appdatban.Datmon;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trungdang.appdatban.Datmon.fragment_Datmon.Bill.Bill;
import com.trungdang.appdatban.Datmon.fragment_Datmon.NuocGiaiKhat.Giaikhat;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.Monchinh;
import com.trungdang.appdatban.Datmon.fragment_Datmon.TrangMieng.Trangmieng;

public class ViewPagerAdapterHomeDatmon extends FragmentStatePagerAdapter {
    private View context;
    public ViewPagerAdapterHomeDatmon(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new Monchinh();
            case 1: return new Trangmieng();
            case 2: return new Giaikhat();
            case 3: return new Bill();
            default: return new Monchinh();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
