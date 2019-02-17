package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import science.aditya.historewind.R;
import science.aditya.historewind.data.model.DateDigest;

public class CachedDigestListFragment extends Fragment {

    private CachedDigestListAdapter listAdapter;
    private List<DateDigest> fileList = new ArrayList<>();
    private OnItemSelectedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            String path = getActivity().getFilesDir().getAbsolutePath() + "/cached";
            File[] files = new File(path).listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    String filename = file.getName();
                    String[] separated = filename.split("\\.")[0].split("_");
                    DateDigest dd = new DateDigest();
                    dd.setMonth(separated[0]);
                    dd.setDate(separated[1]);
                    int tod = 0;
                    if (separated[2].equals("eve")) {
                        tod = 1;
                    }
                    dd.setTod(tod);
                    fileList.add(dd);
                }
            }
        }
        listAdapter = new CachedDigestListAdapter(getContext(), fileList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_cache_list, parent, false);

        if (getActivity() != null) {
            TextView headerView = getActivity().findViewById(R.id.headingView);
            headerView.setText(getResources().getText(R.string.offline_head));
        }
        TextView noDigestView =  rootView.findViewById(R.id.noDigestView);
        ListView lvItems = rootView.findViewById(R.id.digestList);

        if (fileList.size()>0) {
            lvItems.setAdapter(listAdapter);
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DateDigest dd = fileList.get(position);
                    listener.onItemSelected(dd.getMonth(), dd.getDate(), dd.getTod());
                }
            });
        } else {
           lvItems.setVisibility(View.GONE);
           noDigestView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){
            this.listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement Adapter's Listener");
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String month, String date, int tod);
    }

}