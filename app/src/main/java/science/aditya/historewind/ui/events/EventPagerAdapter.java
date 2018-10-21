package science.aditya.historewind.ui.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import science.aditya.historewind.data.model.HistoryEvent;

/**
 * Created by addiittya on 13/03/17.
 */

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 10;
    private static List<EventFragment> mFragments;
    private List<HistoryEvent> list;

    public EventPagerAdapter(FragmentManager fm, List<HistoryEvent> curDigest) {
        super(fm);
        this.list = curDigest;
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
        return NUM_PAGES;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
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

}
