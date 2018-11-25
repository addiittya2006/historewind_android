package science.aditya.historewind.ui.main.events;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.HistoryEvent;

/**
 * Created by addiittya on 13/03/17.
 */

public class EventFragment extends Fragment {

    HistoryEvent historyEvent;
    private String year;
    private String desc;
    private String thumb;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                  .inflate(R.layout.history_item, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (historyEvent != null) {
                type = historyEvent.getEventType();
                String toAdd = "--";
                int colour = Color.parseColor("#FFFFFF");

                if (type == 1) {
                    toAdd = "Birth";
                    colour = Color.parseColor("#3E50B4");
                } else if (type == 2) {
                    toAdd = "Event";
                    colour = Color.parseColor("#D0021B");
                } else if (type == 3) {
                    toAdd = "Death";
                    colour = Color.parseColor("#757575");
                }

                year = historyEvent.getYear();
                TextView yearTextView = (TextView) view.findViewById(R.id.yearTextView);
                yearTextView.setText(year);

                thumb = historyEvent.getThumb();
                ImageView eventImage = (ImageView) view.findViewById(R.id.eventImage);
                Glide.with(this)
                        .load(thumb)
                        .apply(RequestOptions.placeholderOf(R.drawable.wikimg))
                        .apply(RequestOptions.centerCropTransform())
                        .into(eventImage);

                desc = historyEvent.getDesc();
                TextView descTextView = (TextView) view.findViewById(R.id.descTextView);
                descTextView.setText(Html.fromHtml(desc).toString());

                TextView typeTextView = (TextView) view.findViewById(R.id.typeTextView);
                typeTextView.setText(toAdd);
                typeTextView.setBackgroundColor(colour);
            }
            super.onViewCreated(view, savedInstanceState);
    }

    public static Fragment getInstance() {
        return new EventFragment();
    }

    public HistoryEvent getHistoryEvent() {
        return historyEvent;
    }

    public void setHistoryEvent(HistoryEvent historyEvent) {
        this.historyEvent = historyEvent;
    }
}


