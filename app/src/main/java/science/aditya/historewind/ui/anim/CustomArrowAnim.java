package science.aditya.historewind.ui.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;

public class CustomArrowAnim {

    private int screenWidth;
    private AnimatorSet arrowAnimatorSet;
    private ImageView arrow1;
    private ImageView arrow2;
    private ImageView arrow3;

    public CustomArrowAnim(int screenWidth, ImageView arr1, ImageView arr2, ImageView arr3) {
        this.screenWidth = screenWidth;
        this.arrow1 = arr1;
        this.arrow2 = arr2;
        this.arrow3 = arr3;
    }

    public void start() {

        final ValueAnimator arr1Animator = ObjectAnimator.ofFloat(
                arrow1, "x"
                , arrow1.getX() - 40
                , arrow1.getX() - 50
                , (screenWidth / 2) + 10f
                , (screenWidth / 2) + 25f
                , (screenWidth / 2) + 50f
                //, (screenWidth / 2)+55f
                //, (screenWidth / 2)+80f
                //, (screenWidth / 2)+25f
                //, (screenWidth / 2)+30f
                //, (screenWidth / 2)+35f
                //, (screenWidth / 2)+40f
                //, (screenWidth / 2)+45f
                //, (screenWidth / 2)+6.6f
                //, (screenWidth / 2)+7.7f
                //, (screenWidth / 2)+8.8f
                //, (screenWidth / 2)+9
                //, (screenWidth / 2)+10
                , screenWidth * .92f, screenWidth + 5);

        arr1Animator.setDuration(5200);
        arr1Animator.setRepeatCount(0);
        arr1Animator.setRepeatMode(ValueAnimator.REVERSE);

        arr1Animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                arrow1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        final ValueAnimator arr2Animator = ObjectAnimator.ofFloat(
                arrow2, "x"
                , arrow2.getX() - 50
                , (screenWidth / 2.1f) + 10f
                , (screenWidth / 2.1f) + 25f
                , (screenWidth / 2.1f) + 50f
//                , (screenWidth / 2.1f) +55f
//                , (screenWidth / 2.1f) +80f
//                , (screenWidth / 2.1f) +25f
//                ,(screenWidth / 2.1f) +30f
                , screenWidth * .94f, screenWidth + 5);

        arr2Animator.setDuration(6000);
        arr2Animator.setRepeatCount(0);
        arr2Animator.setStartDelay(200);
        arr2Animator.setRepeatMode(ValueAnimator.REVERSE);
        arr2Animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                arrow2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        final ValueAnimator arr3Animator = ObjectAnimator.ofFloat(arrow3, "x", arrow3.getX() - 50, (screenWidth / 2.2f) + 10f, (screenWidth / 2.2f) + 25f, (screenWidth / 2.2f) + 50f//,(screenWidth / 2.2f) +55f//,(screenWidth / 2.2f) +80f//,(screenWidth / 2.2f) +25f,(screenWidth / 2.2f) +30f
                , screenWidth * .94f, screenWidth + 5);

        arr3Animator.setDuration(6500);
        arr3Animator.setRepeatCount(0);
        arr2Animator.setStartDelay(500);
        arr3Animator.setRepeatMode(ValueAnimator.REVERSE);

        arr3Animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                arrow3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

        arrowAnimatorSet = new AnimatorSet();
        arrowAnimatorSet.playTogether(arr1Animator, arr2Animator, arr3Animator);

        arrowAnimatorSet.start();
        arrowAnimatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                arrowAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });

    }

    public void stop() {
        arrowAnimatorSet.cancel();
        arrowAnimatorSet.removeAllListeners();
    }

}
