package com.example.duacentes.activitys;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duacentes.R;
import com.example.duacentes.config.general_data;
import com.example.duacentes.fragments.AboutFragment;
import com.example.duacentes.fragments.DUAFragment;
import com.example.duacentes.fragments.ExternalresourceFragment;
import com.example.duacentes.fragments.ISguidelineFragment;
import com.example.duacentes.fragments.ISresourcesFragment;
import com.example.duacentes.fragments.IntesearchFragment;
import com.example.duacentes.fragments.LcheckpointFragment;
import com.example.duacentes.fragments.LearningFragment;
import com.example.duacentes.fragments.LguidelineFragment;
import com.example.duacentes.fragments.SearchFragment;
import com.example.duacentes.fragments.SectionsearchFragment;
import com.example.duacentes.fragments.externalresource.ExternalresourceDetailFragment;
import com.example.duacentes.fragments.internalseccion.ISresourceDetailFragment;
import com.example.duacentes.fragments.learning.CheckpointDetailFragment;
import com.example.duacentes.fragments.learning.GuidelineDetailFragment;
import com.example.duacentes.fragments.learning.PrincipleDetailFragment;
import com.example.duacentes.fragments.search.SearchresourceDetailFragment;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.CheckpointModel;
import com.example.duacentes.models.ExternalresourceModel;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;
import com.example.duacentes.models.ToolModel;
import com.google.android.material.navigation.NavigationView;

public class ProcessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iCommunicates_Fragments {


    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    private TextView emailprofile;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser, names, last_name, email, image, birthdate, rol, state, user_token;

    /**
     * Sección Fragments
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    /**
     * Búsqueda por secciones
     */
    private ISguidelineFragment iSguidelineFragment;

    /**
     * ActionBarDrawerToggle
     */


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    FragmentManager fm;

    /**
     * Progreso
     */

    private ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        init();
        sessionuser();
        if (user_token != null) {

            /* UI  */
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigationView);
            toolbar = findViewById(R.id.toolbar);

            // if(firstfragment){
              /* getSupportFragmentManager().beginTransaction().add(R.id.contentf, new HomeFragment()).addToBackStack(null).commit();
               setTitle("Inicio");*/
            //  }

            if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
                getSupportFragmentManager().beginTransaction().add(R.id.contentf, new DUAFragment()).addToBackStack(null).commit();
                setTitle("DUA");
            }

            /*
            SETUP TOOLBAR
             */
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toggle = setUpDrawerToggle();
            drawerLayout.addDrawerListener(toggle);


            //View view = navigationView.inflateHeaderView(R.layout.navigation_header);
            navigationView.setItemIconTintList(null);
            navigationView.setNavigationItemSelectedListener(this);


            View hView = navigationView.getHeaderView(0);
            /**
             * ImageView Imagen del usuario
             */
            ImageView foto = (ImageView) hView.findViewById(R.id.imageProfile);
            /**
             * TextView nombre del usuario
             * TextView rol del usuario
             */
            TextView nameprofile = (TextView) hView.findViewById(R.id.nameprofile);
            emailprofile = (TextView) hView.findViewById(R.id.emailprofile);
            nameprofile.setText(names + " " + last_name);
            emailprofile.setText(email);
            //     Glide.with(this).load(general_data.URLIMAG + image.replace('\\', '/')).into(foto);

            Glide.with(this).load(general_data.URLIMAG + image.replace('\\', '/'))
                    .placeholder(R.drawable.progress_animation)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .error(R.drawable.try_later)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(foto);
        } else {
            Toast.makeText(ProcessActivity.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

    }

    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemnav(item);
        return false;
    }

    private void selectItemnav(MenuItem item) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (item.getItemId()) {

            case R.id.dua:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new DUAFragment()).addToBackStack(null).commit();
                break;

            case R.id.learning:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new LearningFragment()).addToBackStack(null).commit();
                break;

            case R.id.search:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new SearchFragment()).addToBackStack(null).commit();
                break;

            case R.id.intesearch:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new SectionsearchFragment()).addToBackStack(null).commit();
                break;
            case R.id.externalresource:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new ExternalresourceFragment()).addToBackStack(null).commit();
                break;

            case R.id.about:
                ft.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
                ft.replace(R.id.contentf, new AboutFragment()).addToBackStack(null).commit();
                break;

            case R.id.logOff:

                logoff();
                //  System.exit(0);
                finish();
               // Toast.makeText(ProcessActivity.this, "Cerrando sessión", Toast.LENGTH_LONG).show();

                break;

        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * inicialización de componentes
     */

    private void init() {
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }

    /**
     * Sessión del usuario
     */

    public void sessionuser() {
        iduser = preferences.getString("iduser", null);
        names = preferences.getString("names", null);
        last_name = preferences.getString("last_name", null);
        email = preferences.getString("email", null);
        image = preferences.getString("image", null);
        birthdate = preferences.getString("birthdate", null);
        rol = preferences.getString("rol", null);
        state = preferences.getString("state", null);
        user_token = preferences.getString("user_token", null);
        Boolean firstfragment = preferences.getBoolean("firstfragment", false);
    }


    /**
     * Login
     */
    private void gologin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Cerrar sessión (limpiar la variable preferences)
     */

    private void logoff() {
        preferences.edit().clear().apply();
        gologin();
        Toast.makeText(ProcessActivity.this, "Cerrando sessión", Toast.LENGTH_LONG).show();
    }

    /**
     * Interfaz de envío de modelo Principle del fragmento Principle al Detalle del Principle
     *
     * @param principleModel se transportara un objeto de tipo principleModel
     */


    @Override
    public void SendPrincipleforPrincipleDetailLModel(PrincipleModel principleModel) {
        /**
         * Aprendizaje
         */
        PrincipleDetailFragment principleDetailFragment = new PrincipleDetailFragment();// aquí se realiza toda la lógica necesaria para poder realizar el envio
        Bundle bundleSend = new Bundle();// object bundle para transportar la información
        bundleSend.putSerializable("object", principleModel);// enviar el objeto que está llegando con Serializable
        principleDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, principleDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz Modelo Principio de Aprendizaje
     *
     * @param principleModel se transportara un objeto de tipo principleModel
     */
    @Override
    public void SendDetailPrincipleforGuidelineModel(PrincipleModel principleModel) {
        LguidelineFragment lguidelineFragment = new LguidelineFragment();// aquí se realiza toda la lógica necesaria para poder realizar el envio
        Bundle bundleSend = new Bundle();// object bundle para transportar la información
        bundleSend.putSerializable("object", principleModel);// enviar el objeto que está llegando con Serializable
        lguidelineFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, lguidelineFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz de envío de modelo Principle y Pauta del fragmento Pauta al Detalle de Pauta
     *
     * @param guidelineModel
     * @param principleModel
     */

    @Override
    public void SendGuidelineforGuidelineDetailModel(GuidelineModel guidelineModel, PrincipleModel principleModel) {
        GuidelineDetailFragment guidelineDetailFragment = new GuidelineDetailFragment();// aquí se realiza toda la lógica necesaria para poder realizar el envio
        Bundle bundleSend = new Bundle();// object bundle para transportar la información
        bundleSend.putSerializable("object", guidelineModel);// enviar el objeto que está llegando con Serializable
        bundleSend.putSerializable("object2", principleModel);
        guidelineDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, guidelineDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz de envío de modelo Principle y Pauta del fragmento Detalle Pauta al Punto de verificación
     *
     * @param guidelineModel
     */
    @Override
    public void SendDetailGuidelineforCheckpoints(GuidelineModel guidelineModel, PrincipleModel principleModel) {
        LcheckpointFragment lcheckpointFragment = new LcheckpointFragment();// aquí se realiza toda la lógica necesaria para poder realizar el envio
        Bundle bundleSend = new Bundle();// object bundle para transportar la información
        bundleSend.putSerializable("object", guidelineModel);// enviar el objeto que está llegando con Serializable
        bundleSend.putSerializable("object2", principleModel);
        lcheckpointFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, lcheckpointFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz de envío de modelo Punto de verificación del fragmento Punto de verificación al detalle del punto de verificación
     *
     * @param checkpointModel
     */
    @Override
    public void SendCheckpointforCheckpointDetail(CheckpointModel checkpointModel, PrincipleModel principleModel) {
        CheckpointDetailFragment checkpointDetailFragment = new CheckpointDetailFragment();// aquí se realiza toda la lógica necesaria para poder realizar el envio
        Bundle bundleSend = new Bundle();// object bundle para transportar la información
        bundleSend.putSerializable("object", checkpointModel);// enviar el objeto que está llegando con Serializable
        bundleSend.putSerializable("object2", principleModel);
        checkpointDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, checkpointDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz Modelo Pauta de Búsqueda por secciones
     *
     * @param principleModel
     */
    @Override
    public void SendISPrincipleModel(PrincipleModel principleModel) {

        iSguidelineFragment = new ISguidelineFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", principleModel);
        iSguidelineFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, iSguidelineFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Interfaz Modelo Pauta de Búsqueda por secciones
     *
     * @param guidelineModel
     */

    @Override
    public void SendISGuidelineModel(GuidelineModel guidelineModel, PrincipleModel principleModel) {

        ISresourcesFragment iSresourcesFragment = new ISresourcesFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", guidelineModel);
        bundleSend.putSerializable("object2", principleModel);
        iSresourcesFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, iSresourcesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void SendToolModel(ToolModel toolModel) {

        ISresourceDetailFragment iSresourceDetailFragment = new ISresourceDetailFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", toolModel);
        iSresourceDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, iSresourceDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void SendToolModeltoSearchResourceDetail(ToolModel toolModel) {

        /**
         * Búsqueda de recursos
         */
        SearchresourceDetailFragment searchresourceDetailFragment = new SearchresourceDetailFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", toolModel);
        searchresourceDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, searchresourceDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    /**
     * RETORNOS (INTERFACES PARA EL BOTÓN DE REGRESO)
     */

    /**
     * DE RECURSOS A PAUTAS
     *
     * @param principleModel
     */

    @Override
    public void ReturnISGuidelineModel(PrincipleModel principleModel) {

        iSguidelineFragment = new ISguidelineFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", principleModel);
        iSguidelineFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, iSguidelineFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void SendExternalResourceModel(ExternalresourceModel externalresourceModel) {

        /**
         * Recursos Externos
         */
        ExternalresourceDetailFragment externalresourceDetailFragment = new ExternalresourceDetailFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("object", externalresourceModel);
        externalresourceDetailFragment.setArguments(bundleSend);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out);
        fragmentTransaction.replace(R.id.contentf, externalresourceDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Se controla la pulsación del botón atrás
     *
     * @param keyCode
     * @param event
     * @return
     */


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

             new SweetAlertDialog(this,
                    SweetAlertDialog.WARNING_TYPE).setTitleText("¿Desea salir de Duacentes?")
                    .setConfirmButton("Si", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            logoff();
                            finish();
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();

        }
        return super.onKeyDown(keyCode, event);
    }*/

}
