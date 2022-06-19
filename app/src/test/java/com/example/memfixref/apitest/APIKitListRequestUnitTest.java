package com.example.memfixref.apitest;

import static org.junit.Assert.assertEquals;

import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.entities.Kit;
import database.web.APIKitServices;
import database.web.WebRepository;
import database.web.jsondeserializer.KitDeserializer;
import database.web.jsondeserializer.KitListDeserializer;
import retrofit2.Call;
import retrofit2.Response;

public class APIKitListRequestUnitTest {
    @Test
    public void getKitByName(){
        String kitName = "pasa1";
        APIKitServices apiKitServices = WebRepository
                .getRetrofitInstanceWithConverter(Kit.class,new KitDeserializer())
                .create(APIKitServices.class);
        Call<Kit> kitCall = apiKitServices.getKitWithCellsByName(kitName);
        try {
            Response<Kit> response = kitCall.execute();
            if (response.isSuccessful()){
                Kit kit = response.body();

                assertNotNull(kit);
                assertEquals(kitName, kit.kitName);

            }
            else {
                assertTrue(response.isSuccessful());//Заведомый провал
            }

        }
        catch (IOException e){
            System.err.println("Test failed " + e.toString());
        }
    }
    @Test
    public void findKitsByTag(){
        String tag = "Language";
        APIKitServices apiKitServices = WebRepository
                .getRetrofitInstanceWithConverter(new TypeToken<List<Kit>>(){}.getType(),new KitListDeserializer())
                .create(APIKitServices.class);
        Map<String,String> tagMap = new HashMap<>();
        tagMap.put("0",tag);
        Call<List<Kit>> call = apiKitServices.getKitListByTags(tagMap);
        try {
            Response<List<Kit>> response = call.execute();
            if (response.isSuccessful()){
                List<Kit> kits = response.body();

                assertNotNull(kits);
                assertNotSame(0,kits.size());

            }
            else{
                assertTrue(response.isSuccessful());//по факту тест будет провален
            }
        }
        catch (IOException e){
            System.err.println("Test failed " + e.toString());
        }
    }

}
