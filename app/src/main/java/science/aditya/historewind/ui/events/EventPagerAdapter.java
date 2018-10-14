package science.aditya.historewind.ui.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import science.aditya.historewind.data.model.HistoryEvent;

/**
 * Created by addiittya on 13/03/17.
 */

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 10;
    private static List<EventFragment> mFragments;
    private float mBaseElevation;
    private List<HistoryEvent> list;

    public EventPagerAdapter(FragmentManager fm, float baseElevation, List<HistoryEvent> curDigest) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        this.list = curDigest;

        for(int i = 0; i< NUM_PAGES; i++) {
            EventFragment ef = new EventFragment();
            if(list.size()>0) {
                ef.setHistoryEvent(list.get(i));
            }
            mFragments.add(ef);
        }
    }


    @Override
    public Fragment getItem(int position) {
        EventFragment eventFragment = (EventFragment) EventFragment.getInstance();
        if(list.size()>0) {
            HistoryEvent historyEvent = list.get(position);
            eventFragment.setHistoryEvent(historyEvent);
        }
        return eventFragment;

    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

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
        } else {
            return POSITION_NONE;
        }
    }

    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

}
