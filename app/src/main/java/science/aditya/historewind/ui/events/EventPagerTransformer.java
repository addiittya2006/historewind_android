package science.aditya.historewind.ui.events;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class EventPagerTransformer implements ViewPager.PageTransformer {

    private int baseElevation;
    private int maxElevation;
    private float scaleY;
    private float pagerDrawOffset;

    public EventPagerTransformer(float pagerDrawOffset) {
        this.baseElevation = EventCard.MIN_ELEVATION_FACTOR;
        this.maxElevation = EventCard.MAX_ELEVATION_FACTOR;
        this.scaleY = EventCard.Y_SCALE_FACTOR;
        this.pagerDrawOffset = pagerDrawOffset;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float absPosition = Math.abs(position - pagerDrawOffset);
            if (absPosition >= 1) {
                page.setElevation(baseElevation);
                page.setScaleY(scaleY);
            } else {
                page.setElevation(((1 - absPosition) * maxElevation + baseElevation));
                page.setScaleY((scaleY - 1) * absPosition + 1);
            }
        }
    }
}
