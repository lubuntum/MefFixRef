package database.web;

import java.util.List;
import java.util.Map;

import database.entities.Kit;
import database.entities.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIKitServices {
    /* deprecated API
    @POST("api/task/create")
    Call<Kit> sendKit(@Body Kit kit);
     */
    @FormUrlEncoded
    @POST("api/get-kit-with-cells-by-name")
    Call<Kit> getKitWithCellsByName(@Field("kit_name") String kitName);

    @FormUrlEncoded
    @POST("api/kits-by-tags")
    Call<List<Kit>> getKitListByTags(@FieldMap Map<String,String> tags);

    @Multipart
    @POST("api/save-kit-with-tags")
    Call<Message> sendKit(@Part("kit") Kit kit,@Part("tags[]") String[] tags);
}
