package com.example.askforleave.views;

import androidx.fragment.app.Fragment;

import com.example.askforleave.fragments.DetailFragment;
import com.example.askforleave.fragments.QRCodeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AK on 2021/3/3 11:11.
 * God bless my code!
 */
public class FragmentCreator {
    public final static int INDEX_DETAIL = 0;
    public final static int INDEX_RQ_CODE = 1;

    private static Map<Integer, Fragment> sCache = new HashMap<>();

    public static Fragment getFragment(int index) {
        Fragment fragment = sCache.get(index);
        if (fragment != null) {
            return fragment;
        }

        switch (index) {
            case INDEX_DETAIL:
                fragment = new DetailFragment();
                break;
            case INDEX_RQ_CODE:
                fragment = new QRCodeFragment();
                break;
        }
        sCache.put(index, fragment);
        return fragment;
    }

}
