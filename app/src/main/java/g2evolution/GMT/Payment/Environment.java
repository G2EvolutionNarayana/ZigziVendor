package g2evolution.GMT.Payment;

/**
 * Created by Jana on 4/7/2018.
 */

public enum Environment {

    TEST {
        @Override
        public String merchant_Key() {
            return "WwjaE26F";
        }

        @Override
        public String merchant_ID() {
            return "6170493";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public boolean debug() {
            return true;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "WwjaE26F";
        }

        @Override
        public String merchant_ID() {
            return "6170493";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract boolean debug();

}