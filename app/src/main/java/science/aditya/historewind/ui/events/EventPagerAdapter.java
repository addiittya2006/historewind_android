package science.aditya.historewind.ui.events;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.Event;
import science.aditya.historewind.data.model.HistoryEvent;

/**
 * Created by addiittya on 13/03/17.
 */

public class EventPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 10;
    private static List<EventFragment> mFragments;
    private float mBaseElevation;
    private List<HistoryEvent> list;
    private Context appContext;
    private LayoutInflater mLayoutInflater;

    public EventPagerAdapter(Context c, FragmentManager fm, float baseElevation, List<HistoryEvent> curDigest) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        this.list = curDigest;
        appContext = c;


        for(int i = 0; i< NUM_PAGES; i++) {
            mFragments.add(new EventFragment());
        }
    }


    @Override
    public Fragment getItem(int position) {
//        return EventFragment;

//        List<Fragment> fragmentsList = mFragmentManager.getFragments();
//        int size = 0;
//        if (fragmentsList != null) {
//            size = fragmentsList.size();
//        }
        EventFragment eventFragment = (EventFragment) EventFragment.getInstance(/*dummyItem.getImageUrl(), dummyItem.getImageTitle()*/);
        if(list.size()>0) {
            HistoryEvent historyEvent = list.get(position);
            eventFragment.setHistoryEvent(historyEvent);
        }
        return eventFragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        View view = mLayoutInflater.inflate(R.id.eventRel, container, false);
//        TextView yearTextView = (TextView) view.findViewById(R.id.yearTextView);
//        TextView descTextView = (TextView) view.findViewById(R.id.descTextView);
//        HistoryEvent historyEvent = list.get(position);
//        yearTextView.setText(historyEvent.getYear());
//        descTextView.setText(historyEvent.getDesc());
//        view.setTag(historyEvent);
//        container.addView(view);
//        return view;

        if (mFragments != null && position <= (mFragments.size() - 1)) {
            EventFragment eventFragment = (EventFragment) mFragments.get(position);
            if (list.size() > 0) {
                HistoryEvent historyEvent = list.get(position);
                if (!historyEvent.equals(eventFragment.getHistoryEvent())) {
                    eventFragment.setHistoryEvent(historyEvent);
                }
            }
        }

//        return super.instantiateItem(container, position);
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (EventFragment) fragment);

        return fragment;

    }

    @Override
    public int getItemPosition(Object object) {
        EventFragment fragment = (EventFragment) object;
        HistoryEvent historyEvent = fragment.getHistoryEvent();
        if(list.size()>0) {
            int position = list.indexOf(historyEvent);

            if (position >= 0) {
                return position;
            } else {
                return POSITION_NONE;
            }
        }
        return POSITION_NONE;
    }

    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

}
