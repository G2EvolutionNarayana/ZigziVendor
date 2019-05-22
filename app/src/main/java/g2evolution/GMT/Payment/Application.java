package g2evolution.GMT.Payment;

/**
 * Created by Jana on 4/7/2018.
 */

public class Application extends android.app.Application {

    Environment environment;

    @Override
    public void onCreate() {
        super.onCreate();
        environment = Environment.PRODUCTION;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}