package science.aditya.historewind.ui.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.Event;
import science.aditya.historewind.data.model.HistoryEvent;

/**
 * Created by addiittya on 13/03/17.
 */

public class EventFragment extends Fragment {

    private CardView eventCardView;
    HistoryEvent historyEvent;
    private String year;
    private String desc;
    private String thumb;
    private int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.history_item, container, false);
        eventCardView = (CardView) rootView.findViewById(R.id.eventCard);

        if(historyEvent!= null) {

            type = historyEvent.getEventType();
            String toAdd = "--";

            if (type == 1){
                toAdd = "birth";
            } else if (type == 2) {
                toAdd = "event";
            } else if (type == 3) {
                toAdd = "death";
            }

            year = historyEvent.getYear();
            String added = year+"###"+toAdd;
            TextView yearTextView = (TextView) rootView.findViewById(R.id.yearTextView);
            yearTextView.setText(added);

            desc = historyEvent.getDesc();
            TextView descTextView = (TextView) rootView.findViewById(R.id.descTextView);
            descTextView.setText(desc);
        }
        eventCardView.setMaxCardElevation(eventCardView.getCardElevation() * EventCard.MAX_ELEVATION_FACTOR);
        return rootView;
    }

    public CardView getCardView() {
        return eventCardView;
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


