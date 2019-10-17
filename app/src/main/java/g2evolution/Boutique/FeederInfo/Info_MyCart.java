package g2evolution.Boutique.FeederInfo;

/**
 * Created by G2evolution on 12/15/2017.
 */

public class Info_MyCart {
    private String _cartid;
    private String _image;
    private String _title;
    private String _status;
    private String _quantity;
    private String _price;

    public String get_cartid() {
        return _cartid;
    }

    public void set_cartid(String _cartid) {
        this._cartid = _cartid;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }


    public String get_quantity() {
        return _quantity;
    }

    public void set_quantity(String _quantity) {
        this._quantity = _quantity;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }
}


