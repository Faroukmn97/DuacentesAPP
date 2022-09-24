package com.example.duacentes.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duacentes.fragments.home.AcExpressionFragment;
import com.example.duacentes.fragments.home.EngagementFragment;
import com.example.duacentes.fragments.home.RepresentationFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RepresentationFragment();
            case 1:
                return  new AcExpressionFragment();
            case 2:
                return new EngagementFragment();
            default:
                return new RepresentationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
