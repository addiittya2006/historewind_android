package science.aditya.historewind.ui.cached;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.HistoryEvent;

public class CachedDigestDetailFragment extends Fragment {

    int tod = 0;
    String month;
    int date = 1;

    RecyclerView rv;

    List<HistoryEvent> prevDigest = new ArrayList<>();
    FileInputStream cachedDigest = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            if(getArguments() != null) {
                tod = getArguments().getInt("tod", 0);
                date = getArguments().getInt("date", 0);
                month = getArguments().getString("month");
            }
        }

        String timeOfDay;
        if(tod == 0) {
            timeOfDay = "morn";
        } else {
            timeOfDay = "eve";
        }
        String filename = month+"_"+date+"_"+timeOfDay+".digest";

        try {
            File cacheDirectory = new File(getActivity().getFilesDir().getAbsolutePath()+File.separator+"cached");
            File saveFile = new File( cacheDirectory.getAbsolutePath()+File.separator+filename);
            cachedDigest = new FileInputStream(saveFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(new InputStreamReader(cachedDigest, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }

            Gson gson = new Gson();
            Digest digest = gson.fromJson(sb.toString(), Digest.class);
            prevDigest.addAll(digest.getBirths());
            prevDigest.addAll(digest.getEvents());
            prevDigest.addAll(digest.getDeaths());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cache_digest, parent, false);

        TextView headerView = (TextView) getActivity().findViewById(R.id.headingView);
        String setText;
        if(tod == 0) {
            setText = month+" "+Integer.toString(date)+", Morning";
        } else {
            setText = month+" "+Integer.toString(date)+", Evening";
        }
        headerView.setText(setText);

        rv = rootView.findViewById(R.id.digestRV);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);

        rv.setAdapter(new PrevDigestAdapter(getContext(), prevDigest));
        return rootView;
    }

}
