package cn.carbs.a2048.update;

/**
 * Created by carbs on 2018/1/10.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.a2048.R;
import cn.carbs.a2048.common.ToastUtil;
import cn.carbs.a2048.net.json.ApkUpdateJson;

public class ApkUpdateDialog extends Dialog implements View.OnClickListener {

    private RecyclerView recycler_view;
    private TextView tv_title;
    private TextView tv_subtitle;
    private TextView tv_done;
    private TextView tv_market;
    private TextView tv_cancel;
    private View rl_bottom_container;
    private MyRecyclerAdapter adapter;

    //datas
    private Wrapper wrapper;

    private void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    protected ApkUpdateDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mddialog_apk_update);
        initWindow();
        initView();
    }

    public void initWindow() {
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = (int) (0.8 * getScreenWidth(getContext()));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void initView() {
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_subtitle = (TextView) this.findViewById(R.id.tv_subtitle);
        tv_done = (TextView) this.findViewById(R.id.tv_done);
        tv_market = (TextView) this.findViewById(R.id.tv_market);
        tv_cancel = (TextView) this.findViewById(R.id.tv_cancel);
        recycler_view = (RecyclerView) this.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        if (wrapper == null) {
            wrapper = new Wrapper();
        }
        tv_title.setText(wrapper.title);
        tv_title.setBackgroundDrawable(getRoundRectShapeDrawableForTopItem(getContext(), 4, Color.WHITE));
        if (TextUtils.isEmpty(wrapper.subtitle)){
            tv_subtitle.setVisibility(View.GONE);
        }else{
            tv_subtitle.setText(wrapper.subtitle);
            tv_subtitle.setVisibility(View.VISIBLE);
        }
        tv_done.setOnClickListener(this);
        tv_market.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        rl_bottom_container = findViewById(R.id.rl_bottom_container);
        rl_bottom_container.setBackgroundDrawable(getRoundRectShapeDrawableForBottomItem(getContext(), 4, Color.WHITE));
        adapter = new MyRecyclerAdapter(wrapper.context, wrapper.apkUpdateJson);
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_done:
                pressDone();
                break;
            case R.id.tv_cancel:
                pressCancel();
                break;
            case R.id.tv_market:
                pressMarket();
                break;
        }
    }

    private void pressMarket(){
        try{
            Uri uri = Uri.parse("market://details?id="+ getContext().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }catch(Exception e){
            ToastUtil.showToast(getContext(), "您的手机没有安装Android应用市场");
            e.printStackTrace();
        }
    }

    private void pressDone() {
        if (wrapper.callback != null){
            wrapper.callback.onDone();
        }
        dismiss();
    }

    private void pressCancel(){
        if (wrapper.callback != null){
            wrapper.callback.onCancel();
        }
        dismiss();
    }

    private static class Wrapper {
        public Context context;
        public String title;
        public String subtitle;
        public ApkUpdateJson apkUpdateJson;
        public Callback callback;
    }

    public interface Callback{
        void onDone();
        void onCancel();
    }

    public static class Builder {

        private final Wrapper wrapper;

        public Builder(Context context) {
            wrapper = new Wrapper();
            wrapper.context = context;
        }

        public Context getContext() {
            return wrapper.context;
        }

        public Builder setTitle(CharSequence title) {
            wrapper.title = title.toString();
            return this;
        }

        public Builder setCallback(Callback callback){
            wrapper.callback = callback;
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle){
            wrapper.subtitle = subtitle.toString();
            return this;
        }

        public Builder setApkUpdateInfo(ApkUpdateJson apkUpdateJson) {
            wrapper.apkUpdateJson = apkUpdateJson;
            return this;
        }

        public ApkUpdateDialog create() {
            final ApkUpdateDialog dialog = new ApkUpdateDialog(wrapper.context);
            dialog.setWrapper(wrapper);
            return dialog;
        }

        public ApkUpdateDialog show() {
            ApkUpdateDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    private Drawable getRoundRectShapeDrawable(float[] cornerRadiusPx, int color) {
        RoundRectShape rr = new RoundRectShape(cornerRadiusPx, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

    private Drawable getRoundRectShapeDrawable(Context context, int cornerRadiusDp, int color) {
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[]{cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx,
                cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private Drawable getRoundRectShapeDrawableForTopItem(Context context, int cornerRadiusDp, int color) {
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[]{cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, 0, 0, 0, 0};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private Drawable getRoundRectShapeDrawableForBottomItem(Context context, int cornerRadiusDp, int color) {
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[]{0, 0, 0, 0, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private Drawable getStateListDrawable(Context context, int cornerRadiusDp, int colorPressed, int colorNormal) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawable(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawable(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }

    private Drawable getStateListDrawableForTopItem(Context context, int cornerRadiusDp, int colorPressed, int colorNormal) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawableForTopItem(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawableForTopItem(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }

    private Drawable getStateListDrawableForBottomItem(Context context, int cornerRadiusDp, int colorPressed, int colorNormal) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawableForBottomItem(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawableForBottomItem(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        private Context mContext;
        private List<String> mList = new ArrayList<>();

        public MyRecyclerAdapter(Context context, ApkUpdateJson apkUpdateJson) {
            super();
            this.mContext = context;
            if (this.mList == null){
                this.mList.clear();
            }
            if (apkUpdateJson != null
                    && apkUpdateJson.android != null
                    && apkUpdateJson.android.lawyer != null
                    && apkUpdateJson.android.lawyer.update_info != null){
                this.mList.addAll(apkUpdateJson.android.lawyer.update_info);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            return new MyViewHolder(new ApkInfoUpdateItemView(getContext()));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ApkInfoUpdateItemView itemView = (ApkInfoUpdateItemView) holder.itemView;
            itemView.update(mList.get(position));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}