package cn.carbs.a2048.update;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.carbs.a2048.R;

public class ApkInfoUpdateItemView extends RelativeLayout{

    private TextView text_view_title;
    private String info;

    public ApkInfoUpdateItemView(Context context) {
        this(context, null);
    }

    public ApkInfoUpdateItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApkInfoUpdateItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.item_view_apk_update_info, this);
        text_view_title = (TextView) findViewById(R.id.text_view_title);
    }

    public void update(String info) {
        this.info = info;
        text_view_title.setText(info);
    }
}
