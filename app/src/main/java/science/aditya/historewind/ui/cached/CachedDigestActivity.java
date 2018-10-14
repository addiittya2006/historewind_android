package science.aditya.historewind.ui.cached;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import science.aditya.historewind.R;

public class CachedDigestActivity extends AppCompatActivity implements CachedDigestListFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cache);

        if (savedInstanceState == null) {

            CachedDigestListFragment listFragment = new CachedDigestListFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragContainer, listFragment);

            ft.commit();
        }
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
