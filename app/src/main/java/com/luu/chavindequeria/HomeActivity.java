package com.luu.chavindequeria;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.luu.chavindequeria.utils.CrossfadeWrapper;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.SystemUtils;
import com.mikepenz.materialize.util.UIUtils;

import fragment.AgregarPedidosFragment;
import fragment.VerPedidosFragment;

public class HomeActivity extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //Save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
/*
        //Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.nombreBarra); */


        if(savedInstanceState == null){
            Fragment f = AgregarPedidosFragment.newInstance("Agregar Pedidos");
            getSupportFragmentManager().beginTransaction().replace(R.id.crossfade_content,f).commit();
        }


        final IProfile profile = new ProfileDrawerItem().withName("Luu Naranjo").withEmail("lagos_2_5@hotmail.com").withIcon(R.drawable.lu_nuevo);


        //Create the account header
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile
                ).withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                //.withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Agregar Pedido").withIcon(R.drawable.restaurant).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Ver Pedidos").withIcon(R.drawable.monitor).withIdentifier(2),
                        new PrimaryDrawerItem().withName("Cuenta").withIcon(R.drawable.payment).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Clientes").withIcon(R.drawable.specialist_user).withIdentifier(4),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Ajustes").withIcon(R.drawable.settings).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem instanceof Nameable){

                            switch ( ((Nameable) drawerItem).getName().getText(getApplicationContext()) ){
                                case "Agregar Pedido":
                                    Fragment f = AgregarPedidosFragment.newInstance("Agregar Pedidos");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.crossfade_content,f).commit();
                                    return false;
                                case "Ver Pedidos":
                                    Fragment f2 = VerPedidosFragment.newInstance("Ver Pedidos");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.crossfade_content,f2).commit();
                                    return false;
                            }
                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .buildView();

        miniResult = result.getMiniDrawer();

        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72,this);


        crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this),secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        //Define the crossfader to be used with the miniDrawer. this is required to be able to automatically toggle open(close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //Define a shadow
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

    }


    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof  Nameable){
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            }else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState = result.saveInstanceState(outState);

        outState = headerResult.saveInstanceState(outState);

        outState = crossFader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (SystemUtils.getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.menu.embedded, menu);
            menu.findItem(R.id.menu_1).setIcon(R.drawable.settings);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader != null && crossFader.isCrossFaded()) {
            crossFader.crossFade();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case R.id.menu_1:
                crossFader.crossFade();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
