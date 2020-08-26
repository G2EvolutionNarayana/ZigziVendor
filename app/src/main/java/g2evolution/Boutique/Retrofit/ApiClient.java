package g2evolution.Boutique.Retrofit;

/**
 * Created by Android4 on 3/27/2019.
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import g2evolution.Boutique.EndUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //base url
  //  public static final String BASE_URL = "http://padhnalikhna.com/api/";
    public static final String BASE_URL = EndUrl.APIURLMain;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//            httpClient.connectTimeout(100, TimeUnit.SECONDS);
//            httpClient.readTimeout(100,TimeUnit.SECONDS);
//            httpClient.writeTimeout(100,TimeUnit.SECONDS);
//            httpClient.addInterceptor(logging);

//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build();
        }
        return retrofit;
    }
}