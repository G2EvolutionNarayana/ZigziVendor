package xplotica.littlekites.FeederInfo_parent;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class parent_fee_history {


    private String time;
    private String description;
    private String amount;
    private String transid;
    private String transstatus;
    private String paymenttype;

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getTransstatus() {
        return transstatus;
    }

    public void setTransstatus(String transstatus) {
        this.transstatus = transstatus;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

