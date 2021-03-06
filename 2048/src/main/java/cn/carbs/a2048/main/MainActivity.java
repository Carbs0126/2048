package cn.carbs.a2048.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.comm.util.AdError;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.carbs.a2048.R;
import cn.carbs.a2048.common.CheckUpdateManager;
import cn.carbs.a2048.main.entity.Tile;
import cn.carbs.a2048.main.view.MainView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCORE = "score";
    private static final String HIGH_SCORE = "high score temp";
    private static final String UNDO_SCORE = "undo score";
    private static final String CAN_UNDO = "can undo";
    private static final String UNDO_GRID = "undo";
    private static final String GAME_STATE = "game state";
    private static final String UNDO_GAME_STATE = "undo game state";
    private MainView mMainView;

    private SplashAD splashAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainView = (MainView) findViewById(R.id.main_view);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        mMainView.hasSaveState = settings.getBoolean("save_state", false);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
                load();
            }
        }
        new CheckUpdateManager().checkUpdate(this, false);

        requestPermissions();


        /*if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
            fetchSplashAD(this, container, skipView, Constants.APPID, getPosId(), this, 0);
        }*/
    }

    public void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (Manifest.permission.READ_PHONE_STATE.equals(permission.name)) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("wangwang","request permission onError e : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        setAdBanner(MainActivity.this, (RelativeLayout) findViewById(R.id.rl_ad_container));
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            mMainView.game.move(2);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mMainView.game.move(0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mMainView.game.move(3);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mMainView.game.move(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("hasState", true);
        save();
    }

    protected void onPause() {
        super.onPause();
        save();
    }

    private void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        Tile[][] field = mMainView.game.grid.field;
        Tile[][] undoField = mMainView.game.grid.undoField;
        editor.putInt(WIDTH, field.length);
        editor.putInt(HEIGHT, field.length);
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    editor.putInt(xx + " " + yy, field[xx][yy].getValue());
                } else {
                    editor.putInt(xx + " " + yy, 0);
                }

                if (undoField[xx][yy] != null) {
                    editor.putInt(UNDO_GRID + xx + " " + yy, undoField[xx][yy].getValue());
                } else {
                    editor.putInt(UNDO_GRID + xx + " " + yy, 0);
                }
            }
        }
        editor.putLong(SCORE, mMainView.game.score);
        editor.putLong(HIGH_SCORE, mMainView.game.highScore);
        editor.putLong(UNDO_SCORE, mMainView.game.lastScore);
        editor.putBoolean(CAN_UNDO, mMainView.game.canUndo);
        editor.putInt(GAME_STATE, mMainView.game.gameState);
        editor.putInt(UNDO_GAME_STATE, mMainView.game.lastGameState);
        editor.commit();
    }

    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        //Stopping all animations
        mMainView.game.aGrid.cancelAnimations();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        for (int xx = 0; xx < mMainView.game.grid.field.length; xx++) {
            for (int yy = 0; yy < mMainView.game.grid.field[0].length; yy++) {
                int value = settings.getInt(xx + " " + yy, -1);
                if (value > 0) {
                    mMainView.game.grid.field[xx][yy] = new Tile(xx, yy, value);
                } else if (value == 0) {
                    mMainView.game.grid.field[xx][yy] = null;
                }

                int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
                if (undoValue > 0) {
                    mMainView.game.grid.undoField[xx][yy] = new Tile(xx, yy, undoValue);
                } else if (value == 0) {
                    mMainView.game.grid.undoField[xx][yy] = null;
                }
            }
        }

        mMainView.game.score = settings.getLong(SCORE, mMainView.game.score);
        mMainView.game.highScore = settings.getLong(HIGH_SCORE, mMainView.game.highScore);
        mMainView.game.lastScore = settings.getLong(UNDO_SCORE, mMainView.game.lastScore);
        mMainView.game.canUndo = settings.getBoolean(CAN_UNDO, mMainView.game.canUndo);
        mMainView.game.gameState = settings.getInt(GAME_STATE, mMainView.game.gameState);
        mMainView.game.lastGameState = settings.getInt(UNDO_GAME_STATE, mMainView.game.lastGameState);
    }

    // 加载腾讯横幅广告
    public static void setAdBanner(Context context, final RelativeLayout brContainer) {
        Log.d("wangwang","setAdBanner ");
        //todo 这里错了 posid如何申请
//        BannerView bv = new BannerView((Activity) context, ADSize.BANNER, "1106975200", "KEYizZa7R4KvUPy1vj0");
        BannerView bv = new BannerView((Activity) context, ADSize.BANNER, "1106975200", "9079537218417626401");
//                    = new BannerView(this,               ADSize.BANNER,     Constants.APPID,    posId);
        bv.setRefresh(20);// 广告轮播时间 按钮默认关闭
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError adError) {
                Log.d("wangwang","adError error : " + adError.getErrorMsg());
            }

            @Override
            public void onADReceiv() {
                // 加载广告成功时
                Log.d("wangwang","onADReceiv");
            }

            @Override
            public void onADClicked() {
                // 广告点击时
                super.onADClicked();
                Log.d("wangwang","onADClicked");
            }

        });
        brContainer.addView(bv);
        bv.loadAD();

    }
}
