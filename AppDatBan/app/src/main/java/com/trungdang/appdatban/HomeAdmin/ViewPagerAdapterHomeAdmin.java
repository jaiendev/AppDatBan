package com.trungdang.appdatban.HomeAdmin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.trungdang.appdatban.HomeAdmin.Doanhthu.Doanhthu;
import com.trungdang.appdatban.HomeAdmin.QuanlyKH.QuanlyKH;
import com.trungdang.appdatban.HomeAdmin.Quanlyban.Quanlyban;
import com.trungdang.appdatban.HomeAdmin.Quanlymon.Quanlymon;

public class ViewPagerAdapterHomeAdmin extends FragmentStatePagerAdapter {

    public ViewPagerAdapterHomeAdmin(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new Quanlyban();
            case 1: return new Quanlymon();
            case 2: return new QuanlyKH();
            case 3: return new Doanhthu();
            default: return new Quanlyban();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
