package com.serovr.vkspy.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FunctionsMenuActivity extends ActionBarActivity {
    int mFriendID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mFriendID = intent.getIntExtra("friendID", 1);

        TextView tvNameSurname = (TextView) findViewById(R.id.tvFriendNameSurname);
        tvNameSurname.setText(intent.getStringExtra("friendName")+" "+intent.getStringExtra("friendSurname"));
        ImageView ivFriendPhoto = (ImageView) findViewById(R.id.ivFriendPhoto);
        ivFriendPhoto.setImageDrawable(Drawable.createFromPath(intent.getStringExtra("friendPhoto")));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        Log.d("FuncMenuActivity", "Up is pressed on FunctionsMenuActivity");
        return true;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.functions_menu, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onOnlineFuncClick(View view){
        Intent intentOFA = new Intent(this, OnlineFuncActivity.class);
        intentOFA.putExtras(getIntent());
        Log.i("FMA.java", "ID is: " + mFriendID);
        startActivity(intentOFA);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("FunctionsMenuActivity", "onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("FunctionsMenuActivity", "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("FunctionsMenuActivity", "onPause()");
    }


}
