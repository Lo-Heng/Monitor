package splash.model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

    private onViewPagerTouchListener mTouchLitener;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (mTouchLitener != null) {
                        mTouchLitener.onPagerTouch(true);
                        Log.d("myviewpager","down");
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if (mTouchLitener != null) {
                        mTouchLitener.onPagerTouch(false);
                        Log.d("myviewpager","not touched");
                    }
                    break;
                default:
                    break;

            }
        Log.d("myviewpager","touched");
        return super.onTouchEvent(ev);
    }

    public void setonViewPagerTouchListener(onViewPagerTouchListener listener){
        this.mTouchLitener = listener;

    }


    public interface onViewPagerTouchListener{
        void onPagerTouch(boolean isTouched);
    }
}
