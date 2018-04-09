package com.example.sachin.electricityusage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sachin.electricityusage.fragments.BillCalculationFragment;
import com.example.sachin.electricityusage.fragments.CompareFragment;
import com.example.sachin.electricityusage.fragments.LineGraphFragment;
import com.example.sachin.electricityusage.fragments.RealTimeGraphFragment;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    // tab titles
    private String[] tabTitles = new String[]{"Real Time","Current month", "Bill Calculation" ,"Compare"};


    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RealTimeGraphFragment realTimeGraphFragment = new RealTimeGraphFragment();
                return realTimeGraphFragment;
            case 1:
                LineGraphFragment lineGraphFragment = new LineGraphFragment();
                return lineGraphFragment;
            case 2:
                BillCalculationFragment billCalculationFragment = new BillCalculationFragment();
                return billCalculationFragment;
            case 3:
                CompareFragment compare = new CompareFragment();
                return compare;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}