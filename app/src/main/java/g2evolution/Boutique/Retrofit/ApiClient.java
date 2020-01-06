package g2evolution.Boutique.Retrofit;

/**
 * Created by Android4 on 3/27/2019.
 */


import g2evolution.Boutique.EndUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //base url
  //  public static final String BASE_URL = "http://padhnalikhna.com/api/";
    public static final String BASE_URL = EndUrl.APIURLMain;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}