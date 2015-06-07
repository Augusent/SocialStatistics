package com.serovr.vkspy.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCaptchaDialog;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private static final String[] sMyScope = new String[] {
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS
    };

    public static boolean isAuthorized = false;
    public static boolean loadUserInfoFragment = false, loadUserAuthFragment = false;


    public static String path;

    public void onAuthorizationButtonClick(View v) {
        if (!isAuthorized){
            VKSdk.authorize(sMyScope, true, false);
        }
    };


    public void onUserLogoutButtonClick(View view){
        Log.d("MainActivity","onUserLogoutButtonClick()");
        if(isAuthorized){
            VKSdk.logout();
            isAuthorized = false;
            loadUserAuthFragment = true;
            this.onRestart();
            this.onResume();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            path = this.getExternalCacheDir().toString();
        }catch (Exception e){

            e.printStackTrace();
            try{
                path = this.getCacheDir().toString();
            }catch (Exception de){
                de.printStackTrace();
            }
        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



        // инициализация ВК сдк
        VKUIHelper.onCreate(this);
        VKSdk.initialize(sdkListener,"4063135");

        if (VKSdk.wakeUpSession()) {
            isAuthorized = true;
        }else{
            isAuthorized = false;
        }

        Fragment fragment;
        if(isAuthorized) {
            fragment = new UserInfoFragment();
        } else{
            fragment = new UserAuthFragment();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


        Log.d("MainActivity", "MainActivity: onCreate()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "MainActivity: onRestart()");
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0: if(!isAuthorized) {
                fragment = new UserAuthFragment(); }
                else {
                fragment = new UserInfoFragment();
                }
                break;
            case 1: fragment = new FriendsListFragment();
                break;
            case 2: fragment = new FriendsListFragment(); //Добавить новый фрагмент
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
// Немножко описан бэкстек фрагментов, если что
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                .beginTransaction();
//        fragmentTransaction.replace(R.id.container,fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "onResume()");
        super.onResume();
        VKUIHelper.onResume(this);
        if (loadUserInfoFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new UserInfoFragment())
                    .commit();
            loadUserInfoFragment = false;
        }
        if (loadUserAuthFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new UserAuthFragment())
                    .commit();
            loadUserAuthFragment = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
        Log.i("MainActivity", "onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKUIHelper.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG","onActivityResult in MainActivity is called!!! resultCode="+resultCode+" RequestCode="+requestCode);
        if(requestCode == VKSdk.VK_SDK_REQUEST_CODE && resultCode == -1 && isAuthorized){
            loadUserInfoFragment = true;
        }
    }

    private final VKSdkListener sdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        // истечение "срока годности" ключа
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(sMyScope);
            isAuthorized = false;
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(authorizationError.errorMessage)
                    .show();
            isAuthorized = false;
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            // действия при получении нового(!) секретного ключа
            isAuthorized = true;
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            // действия при получении секретного ключа
            isAuthorized = true;
        }
    };

}