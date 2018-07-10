package okhttp.custom.android.customnotification;

public class NotificationBean {

    /**
     * alert : "First send Notification",

     */
    private String alert;

    public String getAlert() { return alert;}

    public void setAlert(String alert) { this.alert = alert;}

    @Override
    public String toString() {
        return "NotificationBean{" +
                "alert='" + alert + '\'' +
                '}';
    }
}
