package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.DateDigest;

public class CachedDigestListFragment extends Fragment {

    ArrayAdapter<DateDigest> listAdapter;
    List<DateDigest> fileList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String path = getActivity().getFilesDir().getAbsolutePath()+"/cached";
        File[] files = new File(path).listFiles();
        if(files.length>0) {
            for (File file : files) {
                String filename = file.getName();
                String[] separated = filename.split("\\.")[0].split("_");
                DateDigest dd = new DateDigest();
                dd.setMonth(separated[0]);
                dd.setDate(separated[1]);
                int tod = 0;
                if (separated[2].equals("morn")) {
                    tod = 0;
                } else if (separated[2].equals("eve")) {
                    tod = 1;
                }
                dd.setTod(tod);
                fileList.add(dd);
            }
        }

        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, fileList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View rootView = inflater.inflate(R.layout.fragment_cache_list, parent, false);
        ListView lvItems = (ListView) rootView.findViewById(R.id.digestList);
        lvItems.setAdapter(listAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DateDigest dd = fileList.get(position);
                listener.onItemSelected(dd.getMonth(), dd.getDate(), dd.getTod());
            }
        });

        return rootView;
    }


    private OnItemSelectedListener listener;


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
