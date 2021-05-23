package com.trungdang.appdatban.Datmon;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.trungdang.appdatban.R;

public class Home_Datmon extends AppCompatActivity {
    private TabLayout tablayoutHomedatmon;
    private ViewPager viewPagerHomedatmon;
    private int[] tabIcons = {
            R.drawable.ic_hot_pot,R.drawable.ic_dessert,R.drawable.ic_cocktail,R.drawable.ic_bill
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__datmon);
        tablayoutHomedatmon=findViewById(R.id.tablayoutHomedatmon);
        viewPagerHomedatmon=findViewById(R.id.viewpagerHomedatmon);
        ViewPagerAdapterHomeDatmon viewPagerAdapter= new ViewPagerAdapterHomeDatmon(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerHomedatmon.setAdapter(viewPagerAdapter);
        tablayoutHomedatmon.setupWithViewPager(viewPagerHomedatmon);
        setupTabIcons();
        tablayoutHomedatmon.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
        tablayoutHomedatmon.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHomedatmon.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
        tablayoutHomedatmon.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);

        tablayoutHomedatmon.getTabAt(0).setText("Món chính");
        tablayoutHomedatmon.getTabAt(1).setText("Tráng miệng");
        tablayoutHomedatmon.getTabAt(2).setText("Nước uống");
        tablayoutHomedatmon.getTabAt(3).setText("Hóa đơn");
        tablayoutHomedatmon.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        tablayoutHomedatmon.getTabAt(0).setIcon(tabIcons[0]);
        tablayoutHomedatmon.getTabAt(1).setIcon(tabIcons[1]);
        tablayoutHomedatmon.getTabAt(2).setIcon(tabIcons[2]);
        tablayoutHomedatmon.getTabAt(3).setIcon(tabIcons[3]);
    }
}