package cn.edu.lzu.oss.ecab.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.edu.lzu.oss.ecab.R;
import cn.edu.lzu.oss.ecab.fragment.ItemFragment;
import cn.edu.lzu.oss.ecab.fragment.MapFragment;
import cn.edu.lzu.oss.ecab.interfaces.FragmentInterface;
import cn.edu.lzu.oss.ecab.util.WindowUtil;
import cn.edu.lzu.oss.ecab.view.BaseViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView sendImage;
    private ImageView receiveImage;
    private BaseViewPager viewPager;
    private List<Fragment> fragments;
    private FragmentInterface backInterfaceListener;
    private boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSDK();
        WindowUtil.initWindows(this);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initSDK() {
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initViews() {
        fragments = new ArrayList<>();
        fragments.add(MapFragment.newInstance());
        fragments.add(ItemFragment.newInstance());
        viewPager = findViewById(R.id.main_view_pager);
        viewPager.setScanScroll(false);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sendImage = findViewById(R.id.image_send);
        receiveImage = findViewById(R.id.image_receive);
        List<ImageView> button = new ArrayList<>();
        button.add(sendImage);
        button.add(receiveImage);
        int width = (int)(WindowUtil.getWIDTH() / 2.5);
        if (width != 0) {
            for (ImageView x:button){
                ViewGroup.LayoutParams params = x.getLayoutParams();
                params.width = width;
                x.setLayoutParams(params);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isActive && backInterfaceListener != null) {
            boolean result = backInterfaceListener.onFragmentBackPress();
            if (!result) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setBackInterfaceListener(FragmentInterface backInterfaceListener) {
        this.backInterfaceListener = backInterfaceListener;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
