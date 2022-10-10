package com.example.duacentes.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duacentes.R;
import com.example.duacentes.adapters.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {


    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;

    private String names;
    private String last_name;
    private String email;
    private String image;
    private String birthdate;
    private String rol;
    private String state;
    private String user_token;

    TabLayout tabLayout;
    ViewPager2  viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;

    /**
     *
     * Progreso
     */

    private ProgressDialog proDialoghome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        if (getActivity() != null) {

            proDialoghome = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

            init(view);
            // firstfragment();

            sessionuser();


            tabLayout = view.findViewById(R.id.tab_layouthome);
            viewPager2= view.findViewById(R.id.view_pager);

            myViewPagerAdapter = new MyViewPagerAdapter(getActivity());
            viewPager2.setAdapter(myViewPagerAdapter);


            int[] tabIcons = {
                    R.drawable.cerebrorepresentacion,
                    R.drawable.cerebroaccexpresion,
                    R.drawable.cerebrocompromiso,
            };


            String [] tabColors = {
                    "#B37CF3",
                    "#0098DA",
                    "#00A23B"
            };


            tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor(tabColors[0]), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor(tabColors[1]), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor(tabColors[2]), PorterDuff.Mode.SRC_IN);



            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                    proDialoghome.dismiss();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position).select();
                    tabLayout.getTabAt(position).setIcon(tabIcons[position]);
                    tabLayout.getTabAt(position).getIcon().setColorFilter(Color.parseColor(tabColors[position]), PorterDuff.Mode.SRC_IN);

                }
            });

        }



        return view;
    }

    /**
     * Sessión del usuario
     */


    public void sessionuser(){
        String iduser = preferences.getString("iduser", null);
        names= preferences.getString("names",null);
        last_name= preferences.getString("last_name",null);
        email= preferences.getString("email",null);
        image= preferences.getString("image",null);
        birthdate= preferences.getString("birthdate",null);
        rol= preferences.getString("rol",null);
        state= preferences.getString("state",null);
        user_token= preferences.getString("user_token",null);
        proDialoghome.dismiss();
    }

    /**
     * inicialización de componentes
     */

    private void init(View view){

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);


    }



}