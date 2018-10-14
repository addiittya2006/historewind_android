package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
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
        }

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CachedDigestActivity.super.onBackPressed();
            }
        });
//        TODO: implement landscape mode
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//            CachedDigestDetailFragment detailFragment = new CachedDigestDetailFragment();
//            Bundle args = new Bundle();
//            args.putInt("position", 0);
//            detailFragment.setArguments(args);
//            FragmentTransaction lft = getSupportFragmentManager().beginTransaction();
//            lft.add(R.id., secondFragment);
//            lft.commit();
//        }
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

//        TODO: implement landscape mode
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id., secondFragment)
//                    .addToBackStack(null)
//                    .commit();
//        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, detailFragment)
                    .addToBackStack(null)
                    .commit();
//        }

    }

}
