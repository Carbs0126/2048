package cn.carbs.a2048.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import cn.carbs.a2048.BuildConfig;
import cn.carbs.a2048.net.json.ApkUpdateJson;
import cn.carbs.a2048.update.ApkUpdateDialog;

/**
 * Created by carbs on 2018/6/8.
 */

public class CheckUpdateManager {

    public void checkUpdate(final Activity context, final boolean positiveCheck) {
        if (!NetUtil.isNetworkAvailable(context)) {
            if (positiveCheck) {
                ToastUtil.showToast(context, "请先开启网络");
            }
            return;
        }
        //TODO
        if (positiveCheck){
//            context.showProgressDialog("提示", "正在检查最新版本");
        }
        ApkUpdateManager.getInstance().requestApkUpdateInfo(new ApkUpdateManager.ApkInfoUpdateCallback() {
            @Override
            public void onInfoSuccess(ApkUpdateJson apkUpdateJson) {
                if (context == null || context.isFinishing()) {
                    return;
                }
//                context.hideProgressDialog();
                checkShouldUpdateApk(context, apkUpdateJson, positiveCheck);
            }

            @Override
            public void onInfoFail() {
                if (context == null || context.isFinishing()) {
                    return;
                }
//                context.hideProgressDialog();
                if (positiveCheck) {
                    ToastUtil.showToast(context, "获取更新信息失败");
                }
            }
        });
    }

    private void checkShouldUpdateApk(final Activity context, ApkUpdateJson apkUpdateJson, boolean positiveCheck) {
        if (apkUpdateJson == null) {
            return;
        }
        if (apkUpdateJson.android != null
                && apkUpdateJson.android.lawyer != null
                && !TextUtils.isEmpty(apkUpdateJson.android.lawyer.current_version_code)) {
            try {
                int versionCode = Integer.valueOf(apkUpdateJson.android.lawyer.current_version_code);
                if (versionCode > BuildConfig.VERSION_CODE) {
                    showApkUpdateInfoDialog(context, apkUpdateJson);
                } else {
                    if (positiveCheck){
                        ToastUtil.showToastL(context, "已经是最新版本");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (positiveCheck){
                ToastUtil.showToastL(context, "未能检测到最新的版本信息");
            }
        }
    }

    private void showApkUpdateInfoDialog(final Activity context, ApkUpdateJson apkUpdateJson){
        if (context == null || context.isFinishing()) {
            return;
        }

        String subtitle = null;

        if (apkUpdateJson != null
                && apkUpdateJson.android != null
                && apkUpdateJson.android.lawyer != null
                && !TextUtils.isEmpty(apkUpdateJson.android.lawyer.current_version_name)){
            subtitle = "最新版本为：" + apkUpdateJson.android.lawyer.current_version_name
                    + "，更新日志如下";
        }

        ApkUpdateDialog mApkUpdateDialog = new ApkUpdateDialog.Builder(context)
                .setTitle("版本更新")
                .setApkUpdateInfo(apkUpdateJson)
                .setSubtitle(subtitle)
                .setCallback(new ApkUpdateDialog.Callback() {
                    @Override
                    public void onDone() {
                        downloadApk(context);
                    }

                    @Override
                    public void onCancel() {

                    }
                })
                .create();
        mApkUpdateDialog.show();
    }

    private void downloadApk(final Activity context) {
        if (context == null || context.isFinishing()) {
            return;
        }

        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "请先连接网络");
            return;
        }

        if (!NetUtil.hasWifiConnection(context)) {
            DialogUtil.showMDDialog(context, "提示", "当前为非wifi网络，确定要更新？",
                    "确定", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    toDownload(context);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else {
            toDownload(context);
        }
    }

    private void toDownload(Activity context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(Constant.ApkUpdateInfo.url_apk);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
