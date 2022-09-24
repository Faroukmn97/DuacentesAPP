package com.example.duacentes.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duacentes.fragments.dua.DuaDetailFragment;
import com.example.duacentes.fragments.dua.PrincipleAllFragment;
import com.example.duacentes.fragments.home.AcExpressionFragment;
import com.example.duacentes.fragments.home.EngagementFragment;
import com.example.duacentes.fragments.home.RepresentationFragment;

public class DuaPagerAdapter extends FragmentStateAdapter {

    public DuaPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DuaDetailFragment();
            case 1:
                return  new PrincipleAllFragment();
            default:
                return new DuaDetailFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
