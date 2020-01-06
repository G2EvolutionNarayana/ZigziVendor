package g2evolution.Boutique.Retrofit;

/**
 * Created by Android4 on 3/27/2019.
 */


import g2evolution.Boutique.Retrofit.CategoryList.CategoryListJson;
import g2evolution.Boutique.Retrofit.ResourceList.ResourceListJson;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Kamere on 9/1/2018.
 */

public interface Api {



    @GET("api/resource/listing/packages")
    Call<ResourceListJson> ResourceListJson(@Query("user_id") String userid);


    @GET("api/resource/listing/packages-category")
    Call<CategoryListJson> CategoryListJson(@Query("resource_packages_id") String packageid);


}