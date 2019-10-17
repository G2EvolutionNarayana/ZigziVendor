package g2evolution.Boutique.FeederInfo;

/**
 * Created by G2e Android on 17-05-2017.
 */

public class FeederInfo_orderhistory {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    private String id;
    private String deliverystatus;
    private String orderdate;
    private String orderid;
    private String totalprice;
    private String shippingadress;
    private String paymentmode;
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getShippingadress() {
        return shippingadress;
    }

    public void setShippingadress(String shippingadress) {
        this.shippingadress = shippingadress;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }
}
