package splash.model;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {


    private List<Integer> mPics = null;

    @Override
    public int getCount() {
        if(mPics != null){
            return Integer.MAX_VALUE ;

        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % mPics.size();
        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(mPics.get(realPosition));
        //添加到容器里面
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


    public void setData(List<Integer> colors) {
        this.mPics = colors;
    }

    public int getDataRealSize() {
        if (mPics != null) {
            return mPics.size();
        }
        return 0;
    }
}
