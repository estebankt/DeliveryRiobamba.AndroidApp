package com.example.proyecto_login.ToolBarMenu;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.proyecto_login.Model_Classes.Product;
import com.example.proyecto_login.R;
import com.example.proyecto_login.Adapters.ShoppingCartHelper;
import com.example.proyecto_login.UserInterface.LogInActivity;
import com.example.proyecto_login.UserInterface.MenuActivity;
import com.example.proyecto_login.UserInterface.ShoppingCartActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class OptionMenuActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    final List<Product> cart = ShoppingCartHelper.getCart();
    int count;


    public void CreateMenu() {



            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.top_menu, menu);
        count=cart.size();
        MenuItem menuItem = menu.findItem(R.id.action_drawer_cart);
        menuItem.setIcon(Converter.convertLayoutToImage(this,count,R.drawable.ic_shopping_cart));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_drawer_cart) {

            Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
            startActivity(viewShoppingCartIntent);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            // Handle the camera action
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_restlist) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.close_session) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }else
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}