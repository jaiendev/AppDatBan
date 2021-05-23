package com.trungdang.appdatban.Home;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.trungdang.appdatban.R;

public class Home extends AppCompatActivity {
    private TabLayout tablayoutHome;
    private ViewPager viewPagerHome;
    public String idUser;
    private int[] tabIcons = {
        R.drawable.ic_baseline_home,R.drawable.ic_baseline_list_alt_24,R.drawable.ic_baseline_khuyenmai,R.drawable.ic_baseline_account_circle_24
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tablayoutHome=findViewById(R.id.tablayouthome);
        viewPagerHome=findViewById(R.id.viewpagerhome);
        ViewPagerAdapterHome viewPagerAdapter= new ViewPagerAdapterHome(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerHome.setAdapter(viewPagerAdapter);
        tablayoutHome.setupWithViewPager(viewPagerHome);
        setupTabIcons();
        tablayoutHome.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
        tablayoutHome.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHome.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHome.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);

        tablayoutHome.getTabAt(0).setText("Trang chủ");
        tablayoutHome.getTabAt(1).setText("Lịch sử giao dịch");
        tablayoutHome.getTabAt(2).setText("Khuyến mãi");
        tablayoutHome.getTabAt(3).setText("Profile");
        tablayoutHome.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        tablayoutHome.getTabAt(0).setIcon(tabIcons[0]);
        tablayoutHome.getTabAt(1).setIcon(tabIcons[1]);
        tablayoutHome.getTabAt(2).setIcon(tabIcons[2]);
        tablayoutHome.getTabAt(3).setIcon(tabIcons[3]);
    }
}