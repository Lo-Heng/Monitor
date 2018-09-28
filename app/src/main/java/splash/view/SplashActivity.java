package splash.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.busterlo.monitor.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import login.view.LoginActivity;
import main.view.MainActivity;
import splash.model.LooperPagerAdapter;
import splash.model.MyViewPager;
import splash.presenter.SplashPresenter;


public class SplashActivity extends AppCompatActivity implements ISplashView, ViewPager.OnPageChangeListener, MyViewPager.onViewPagerTouchListener,View.OnClickListener {
    private ActionBar mActionBar;
    private LinearLayout mLinearLayout;
    private MyViewPager mMyViewPager;
    private LooperPagerAdapter mLooperPagerAdapter;
    private SplashPresenter mSplashPresenter;
    private Handler mHandler;
    private boolean mIsTouched = false;
    private Button mSkipButton;
    private View.OnClickListener btnListener;
    private Runnable mRunnable;
    private Timer mTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();

        //绑定控件

        mLinearLayout = (LinearLayout) findViewById(R.id.splash_base);
        mMyViewPager = (MyViewPager) findViewById(R.id.slash_looper);
        mSkipButton = findViewById(R.id.splash_skip_button);
        //初始化
        requestPower();

//        hideBottomUIMenu();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        showToast("welcome to Gree Z+");
        mSplashPresenter = new SplashPresenter<>(this);
        //显示图片轮播
        mSplashPresenter.fetch();

        //判断
        mSplashPresenter.startMain(this);
        mSkipButton.setOnClickListener(this);


    }

    /**
     * 申请权限
     *
     */
    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
//                申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET,}, 1);
            }
        }
    }
    @Override
    public void showPics(List<Integer> picList) {

        //设置适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        mLooperPagerAdapter.setData(picList);
        mMyViewPager.setAdapter(mLooperPagerAdapter);
        mMyViewPager.setonViewPagerTouchListener(this);
        mMyViewPager.addOnPageChangeListener(this);
        mMyViewPager.setCurrentItem(Integer.MAX_VALUE / picList.size() / 2 * picList.size());

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.splash_skip_button:
                startNewPage(LoginActivity.class);
                finishNow();
                break;
        }
    }

    @Override
    public void countDownTask(){
        // UI thread
// @Override


    }
    @Override
    public void setSkipText(String str){
        mSkipButton.setText(str);

    }
    @Override
    public void startTask(){
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    // @Override
                    public void run() {
                        mSplashPresenter.updateSecond();
                    }
                });
            }
        };
        mTimer.schedule(mTask, 0, 1000);//1000执行一次
    }
    @Override
    public void cancelTask(){
        mTimer.cancel();
//        mSkipButton.setVisibility(View.GONE);//倒计时到0隐藏字体
    }
    @Override
    public String getUserData() {
        SharedPreferences myPreference = getSharedPreferences("UserData",MODE_PRIVATE);
        String token = myPreference.getString("token", "");
        return token;
    }

    private Runnable mLooperTask = new Runnable() {
        @Override
        public void run() {
            //切换图片
            if (!mIsTouched) {
                int currentItem = mMyViewPager.getCurrentItem();
                mMyViewPager.setCurrentItem(++currentItem, true);
            }
            mHandler.postDelayed(this, 2000);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //当界面绑定到窗口
        mMyViewPager.post(mLooperTask);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mLooperTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onPagerTouch(boolean isTouched) {
        mIsTouched = isTouched;
    }

    /*
        打开新的页面
        @参数 newPage 传入新页面
     */
    @Override
    public void startNewPage(Class newPage) {
        startActivity(new Intent(this, newPage));
        overridePendingTransition(R.anim.my_anim_in, android.R.anim.fade_out);

    }
    /*
        结束当前Activity
     */
    @Override
    public void finishNow() {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.my_anim_out);
    }
    /*
        显示消息
     */
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    @Override
    public void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 15 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {

            View decorView = getWindow().getDecorView();//获取当前页面的跟布局
            //用户滑动边缘会暂时退出全屏，一段时间后隐藏
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;

            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    @Override
    public void skip2Login() {
        startNewPage(LoginActivity.class);
        finishNow();
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                startNewPage(LoginActivity.class);
//                finishNow();
//            }
//        };
//        mHandler.postDelayed(mRunnable, 5000);
    }
    @Override
    public void skip2Main() {
        startNewPage(MainActivity.class);
        finishNow();
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                startNewPage(MainActivity.class);
//                finishNow();
//            }
//        };
//        mHandler.postDelayed(mRunnable, 5000);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}