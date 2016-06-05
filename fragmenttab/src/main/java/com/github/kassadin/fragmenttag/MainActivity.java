package com.github.kassadin.fragmenttag;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.github.kassadin.fragmenttag.fragment.MainFragment;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;

    FragmentManager mFragmentManager;
    MainFragment mFragment0;
    MainFragment mFragment1;
    MainFragment mFragment2;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) { // 恢复数据
            Logger.d("恢复 fragment %s", savedInstanceState);
            mFragment0 = (MainFragment) mFragmentManager.findFragmentByTag("f0");
            Logger.d("恢复 fragment0 %s", mFragment0 != null ? "成功" : "失败");
            mFragment1 = (MainFragment) mFragmentManager.findFragmentByTag("f1");
            Logger.d("恢复 fragment1 %s", mFragment0 != null ? "成功" : "失败");
            mFragment2 = (MainFragment) mFragmentManager.findFragmentByTag("f2");
            Logger.d("恢复 fragment2 %s", mFragment0 != null ? "成功" : "失败");

//            Fragment hehe = mFragmentManager.getFragment(savedInstanceState, "hehe");
//            Logger.d("恢复 hehe %s", mFragment0 != null ? "成功" : "失败");
            mIndex = savedInstanceState.getInt("index");

        }
        setUpView();
    }

    private void setUpView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabSelected((tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText("首页"));
        mTabLayout.addTab(mTabLayout.newTab().setText("分享"));
        mTabLayout.addTab(mTabLayout.newTab().setText("我的"));
        if (mIndex != 0) {
            TabLayout.Tab tab = mTabLayout.getTabAt(mIndex);
            if (tab != null) {
                tab.select();
            }
        }

    }

    private void setTabSelected(int index) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);

        switch (index) {
            case 0:
                if (mFragment0 == null) {
                    mFragment0 = MainFragment.newInstance("F0");
                    fragmentTransaction.add(R.id.container, mFragment0, "f0");
                } else {
                    fragmentTransaction.show(mFragment0);
                }
                break;
            case 1:
                if (mFragment1 == null) {
                    mFragment1 = MainFragment.newInstance("F1");
                    fragmentTransaction.add(R.id.container, mFragment1, "f1");
                } else {
                    fragmentTransaction.show(mFragment1);
                }
                break;
            case 2:
                if (mFragment2 == null) {
                    mFragment2 = MainFragment.newInstance("F2");
                    fragmentTransaction.add(R.id.container, mFragment2, "f2");
                } else {
                    fragmentTransaction.show(mFragment2);
                }
                break;
            default:
                throw new IllegalStateException();
        }

        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        hideFragment(transaction, mFragment0);
        hideFragment(transaction, mFragment1);
        hideFragment(transaction, mFragment2);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("outState == null : %s", outState == null);
//        mFragmentManager.putFragment(outState, "hehe", mFragment0);
        if (outState != null) { // for ide
            outState.putInt("index", mTabLayout.getSelectedTabPosition());
//            outState.putParcelable("android:support:fragments", null);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("destroy");
    }

    private void hideFragment(FragmentTransaction transaction, Fragment fragment) {
        if (transaction == null || fragment == null) {
            return;
        }

        transaction.hide(fragment);
    }
}
