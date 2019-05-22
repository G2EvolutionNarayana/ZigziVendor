package xplotica.littlekites.FeederInfo;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class FeederInfo_leave_history {

    private String date;
    private String fromdate;
    private String todate;
    private String description;
    private String accept;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

