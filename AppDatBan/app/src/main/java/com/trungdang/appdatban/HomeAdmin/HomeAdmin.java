package com.trungdang.appdatban.HomeAdmin;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.trungdang.appdatban.R;

public class HomeAdmin extends AppCompatActivity {
    private TabLayout tablayoutHomeAdmin;
    private ViewPager viewPagerHomeAdmin;
    public String idUser;
    private int[] tabIcons = {
            R.drawable.ic_quanlyban,R.drawable.ic_quanlymon,R.drawable.ic_quanlykhachhang,R.drawable.ic_doanhthu
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        tablayoutHomeAdmin=findViewById(R.id.tablayouthomeAdmin);
        viewPagerHomeAdmin=findViewById(R.id.viewpagerhomeAdmin);
        ViewPagerAdapterHomeAdmin viewPagerAdapter= new ViewPagerAdapterHomeAdmin(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerHomeAdmin.setAdapter(viewPagerAdapter);
        tablayoutHomeAdmin.setupWithViewPager(viewPagerHomeAdmin);
        setupTabIcons();
        tablayoutHomeAdmin.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
        tablayoutHomeAdmin.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHomeAdmin.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHomeAdmin.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);

        tablayoutHomeAdmin.getTabAt(0).setText("Quản lý bàn");
        tablayoutHomeAdmin.getTabAt(1).setText("Quản lý món ăn");
        tablayoutHomeAdmin.getTabAt(2).setText("Quản lý KH");
        tablayoutHomeAdmin.getTabAt(3).setText("Doanh thu");
        tablayoutHomeAdmin.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.primarycolor),PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setupTabIcons() {
        tablayoutHomeAdmin.getTabAt(0).setIcon(tabIcons[0]);
        tablayoutHomeAdmin.getTabAt(1).setIcon(tabIcons[1]);
        tablayoutHomeAdmin.getTabAt(2).setIcon(tabIcons[2]);
        tablayoutHomeAdmin.getTabAt(3).setIcon(tabIcons[3]);
    }
}