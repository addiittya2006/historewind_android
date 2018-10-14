package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.DateDigest;

public class CachedDigestListAdapter extends ArrayAdapter<DateDigest> {

    private List<DateDigest> cachedDigests = new ArrayList<>();
    private Context c;

    static class DigestListViewHolder {
        ImageView digestImage;
        TextView digestDate;
    }

    public CachedDigestListAdapter(Context context, List<DateDigest> digestList) {
        super(context, 0, digestList);
        this.cachedDigests = digestList;
        this.c = context;
    }

    @Override
    public void add(DateDigest digest) {
        cachedDigests.add(digest);
        super.add(digest);
    }

    @Override
    public int getCount() {
        return this.cachedDigests.size();
    }

    @Override
    public DateDigest getItem(int index) {
        return this.cachedDigests.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DigestListViewHolder viewHolder = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.digest_item_lv, parent, false);
            viewHolder = new DigestListViewHolder();
            viewHolder.digestDate = (TextView) row.findViewById(R.id.lvDateView);
            viewHolder.digestImage = (ImageView) row.findViewById(R.id.lvImgView);
        }

        DateDigest dd = getItem(position);
        String dateSet = dd.getMonth()+" "+dd.getDate();
        viewHolder.digestDate.setText(dateSet);
        if(dd.getTod() == 0) {
            Glide.with(c).load(R.drawable.sun).into(viewHolder.digestImage);
        } else {
            Glide.with(c).load(R.drawable.moon).into(viewHolder.digestImage);
        }
        return row;
    }
}
