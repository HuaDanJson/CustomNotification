package okhttp.custom.android.customnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import cn.bmob.push.PushConstants;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    RemoteViews mRemoteViews;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            NotificationBean notificationBean = GsonConverter.fromJson(intent.getStringExtra("msg"), NotificationBean.class);
            Log.i("bmob", "NotificationReceiver receive Notification = " + notificationBean.getAlert());

            showNotification();
        }
    }

    //需要当前温度，最高和最低温度，当前情况，图片信息，地址,是否在前台工作
    public void showNotification() {
        //展示标题栏

        PendingIntent contentIntent = PendingIntent.getActivity(CCApplication.getInstance(), 0, new Intent(CCApplication.getInstance(), MainActivity.class), 0);

        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) CCApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);
        }

        if (mBuilder == null) {
            mBuilder = new NotificationCompat.Builder(CCApplication.getInstance());
        }

        //自定义样式的标题栏

        if (mRemoteViews == null) {
            mRemoteViews = new RemoteViews(CCApplication.getInstance().getPackageName(), R.layout.notify_view);
        }

//        mRemoteViews.setTextViewText(R.id.tv_now_tmp, now_tmp);
//        mRemoteViews.setTextViewText(R.id.tv_now, now_range);
//        mRemoteViews.setTextViewText(R.id.tv_state, now_state);
        mRemoteViews.setTextViewText(R.id.tv_city, "Beijing");
//
        mRemoteViews.setImageViewResource(R.id.iv_state, R.drawable.w2);

        //判断是否是当前运行的app
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.mipmap.icon);


        int notifyId = 1;

        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notifyId, mBuilder.build());
    }
}