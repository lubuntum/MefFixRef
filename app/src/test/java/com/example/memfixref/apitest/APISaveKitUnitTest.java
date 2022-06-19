package com.example.memfixref.apitest;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Message;
import database.web.APIKitServices;
import database.web.WebRepository;
import retrofit2.Call;
import retrofit2.Response;

public class APISaveKitUnitTest {
    @Test
    public void saveKitWithTags(){
        Kit kit = new Kit();
        kit.kitName = "2UnitTest";
        kit.description = "UnitTest Kit description";
        kit.creationDate = "19/06/2022";
        kit.cells = new LinkedList<>();
        for(int i = 0; i < 5;i++)
            kit.cells.add(new Cell("key"+i,"value"+i));
        String[] tagsArr = new String[]{"Language","Spanish","Beginner","Start"};

        APIKitServices apiKitServices = WebRepository.getRetrofitInstance()
                .create(APIKitServices.class);
        Call<Message> saveKitCall = apiKitServices.sendKit(kit,tagsArr);
        try {
            Response<Message> response = saveKitCall.execute();
            if (response.isSuccessful()){
                assert response.body() != null;

                //assertEquals("Kit saved",response.body().message);
                assertEquals("Kit saved", response.body().message);
            }
            else assertTrue(response.isSuccessful());
        }
        catch (IOException e){
            System.err.println("Error occurred " + e.toString());
        }
    }
}
