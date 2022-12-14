package com.example.duacentes.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duacentes.R;
import com.example.duacentes.adapters.DuaPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DUAFragment extends Fragment {

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;

    TabLayout tab_layoutdua;
    ViewPager2 view_page_dua;
    DuaPagerAdapter duaPagerAdapter;


    /**
     *
     * Progreso
     */

    private ProgressDialog proDialogdua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        proDialogdua = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        View inflate = inflater.inflate(R.layout.fragment_d_u_a, container, false);
        init(inflate);
        firstfragment();
        sessionuser();


        tab_layoutdua = inflate.findViewById(R.id.tab_layoutdua);
        view_page_dua= inflate.findViewById(R.id.view_page_dua);

        duaPagerAdapter = new DuaPagerAdapter(getActivity());
        view_page_dua.setAdapter(duaPagerAdapter);


        tab_layoutdua.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_page_dua.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        view_page_dua.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab_layoutdua.getTabAt(position).select();

            }
        });

        proDialogdua.dismiss();



        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {





    }



    private void firstfragment(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstfragment",false);
        editor.commit();
    }

    public void sessionuser(){
        String iduser = preferences.getString("iduser", null);
        String names = preferences.getString("names", null);
        String last_name = preferences.getString("last_name", null);
        String email = preferences.getString("email", null);
        String image = preferences.getString("image", null);
        String birthdate = preferences.getString("birthdate", null);
        String rol = preferences.getString("rol", null);
        String state = preferences.getString("state", null);
        String user_token = preferences.getString("user_token", null);
        Boolean firstfragment = preferences.getBoolean("firstfragment", false);
        proDialogdua.dismiss();
    }

    /**
     * inicializaci??n de componentes
     */

    private void init(View view){

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);


    }


}