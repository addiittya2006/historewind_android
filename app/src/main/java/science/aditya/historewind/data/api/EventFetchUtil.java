package science.aditya.historewind.data.api;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.main.events.EventPagerAdapter;

public class EventFetchUtil {

    private Context context;
    private ViewPager mPager;
    private EventPagerAdapter mPagerAdapter;
    private List<HistoryEvent> curDigest;

    public EventFetchUtil(final Context context, ViewPager viewPager, EventPagerAdapter adapter, List<HistoryEvent> curDigest) {
        this.context = context;
        this.mPager = viewPager;
        this.mPagerAdapter = adapter;
        this.curDigest = curDigest;
    }

    public void fetchDigest(final String date, int tod) {
        final String digestType;

        if (tod==1){
            digestType = "eve";
        } else {
            digestType = "morn";
        }

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        final String curFile = date + "/" + digestType + "_digest.json";

        StorageReference pathReference = mStorageRef.child(curFile);

        FileInputStream cachedDigest;

        try {
            cachedDigest = context.openFileInput(date + "_" + digestType + ".digest");
            parseFile(cachedDigest);
        } catch (FileNotFoundException fnfe) {
            try {
                final File localFile = File.createTempFile("curFile", "json");
                pathReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                FileOutputStream outputStream;
                                try {
                                    File[] digestfiles = context.getFilesDir().listFiles();
                                    for (File file : digestfiles) {
                                        if (file.isFile() && file.getPath().endsWith(".digest")) {
                                            if (file.delete()) {
                                                Log.d("File", "");
                                            }
                                        }
                                    }
                                    outputStream = context.openFileOutput(date + "_" + digestType + ".digest", Context.MODE_PRIVATE);
                                    int length = (int) localFile.length();
                                    byte[] content = new byte[length];
                                    FileInputStream fin = new FileInputStream(localFile);
                                    if (fin.read(content) > 0) {
                                        Log.d("File", "");
                                    }
                                    outputStream.write(content);
                                    outputStream.close();
                                    FileInputStream cachedDigest = context.openFileInput(date + "_" + digestType + ".digest");
                                    parseFile(cachedDigest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseFile(FileInputStream fileInputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        Gson gson = new Gson();
        Digest digest = gson.fromJson(sb.toString(), Digest.class);
        curDigest.addAll(digest.getBirths());
        curDigest.addAll(digest.getEvents());
        curDigest.addAll(digest.getDeaths());
        mPagerAdapter.notifyDataSetChanged();
        mPager.setVisibility(View.VISIBLE);
    }
}
