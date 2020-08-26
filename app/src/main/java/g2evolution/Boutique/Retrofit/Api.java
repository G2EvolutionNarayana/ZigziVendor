package g2evolution.Boutique.Retrofit;

/**
 * Created by Android4 on 3/27/2019.
 */


import g2evolution.Boutique.Retrofit.CategoryList.CategoryListJson;
import g2evolution.Boutique.Retrofit.EmployListDataModel.Example;
import g2evolution.Boutique.Retrofit.ResourceList.ResourceListJson;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Kamere on 9/1/2018.
 */

public interface Api {

    @GET("api/resource/listing/packages")
    Call<ResourceListJson> ResourceListJson(@Query("user_id") String userid);


    @GET("api/resource/listing/packages-category")
    Call<CategoryListJson> CategoryListJson(@Query("resource_packages_id") String packageid);

    @GET("api/rest/get_resources")
    Call<Example> EmployList();

    @Multipart
    @POST("api/user/user_profile_update")
    Call<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example> uploadImage(@Part("user_id") RequestBody userid,@Part MultipartBody.Part image);

}