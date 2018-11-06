package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import science.aditya.historewind.R;

public class CachedDigestActivity extends AppCompatActivity implements CachedDigestListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cache);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (savedInstanceState == null) {

            CachedDigestListFragment listFragment = new CachedDigestListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragContainer, listFragment);
            ft.commit();

        } else {

            CachedDigestListFragment listFragment = new CachedDigestListFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragContainer, listFragment);
            ft.commit();

            String mMonth = savedInstanceState.getString("month");
            int mDate = savedInstanceState.getInt("date");
            int mTod = savedInstanceState.getInt("tod");
            if(mMonth!=null) {
                CachedDigestDetailFragment detailFragment = new CachedDigestDetailFragment();
                Bundle args = new Bundle();
                args.putString("month", mMonth);
                args.putInt("date", mDate);
                args.putInt("tod", mTod);
                detailFragment.setArguments(args);


                FragmentTransaction lft = getSupportFragmentManager().beginTransaction();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getSupportFragmentManager().popBackStack();
                    lft.add(R.id.fragDetailContainer, detailFragment);
                    lft.commit();
                } else {
                    lft.add(R.id.fragContainer, detailFragment).addToBackStack(null);
                    lft.commit();
                }
            }
        }

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CachedDigestActivity.super.onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                && conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            finish();
        }
    }

    @Override
    public void onItemSelected(String month, String date, int tod) {
        CachedDigestDetailFragment detailFragment = new CachedDigestDetailFragment();

        Bundle args = new Bundle();
        args.putString("month", month);
        args.putInt("date", Integer.parseInt(date));
        args.putInt("tod", tod);
        detailFragment.setArguments(args);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragDetailContainer, detailFragment)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragDetailContainer);
            if(fragment!=null&&fragment.isVisible()) {
                outState.putString("month", fragment.getArguments().getString("month"));
                outState.putInt("date", fragment.getArguments().getInt("date"));
                outState.putInt("tod", fragment.getArguments().getInt("tod"));
            }
        }else{
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragContainer);
            if(fragment!=null&&fragment.isVisible()&&fragment instanceof CachedDigestDetailFragment) {
                outState.putString("month", fragment.getArguments().getString("month"));
                outState.putInt("date", fragment.getArguments().getInt("date"));
                outState.putInt("tod", fragment.getArguments().getInt("tod"));
            }
        }

    }
}
