package com.example.android.mybcm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BCMFragmentAdapter extends FragmentPagerAdapter {
    public BCMFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    public Fragment getItem(int position){
        if (position == 0){
            return new ManageFragment();
        }else{
            return new AddCamera();
        }
    }

    public int getCount(){
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "MY BCM";
        }else {
            return "카 메 라";
        }
    }
}
