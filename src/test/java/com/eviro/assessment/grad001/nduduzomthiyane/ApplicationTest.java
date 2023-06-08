package com.eviro.assessment.grad001.nduduzomthiyane;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {


    @BeforeAll
    public static void startUp() throws IOException {
        NduduzomthiyaneApplication.main(new String[]{});
    }

    private String serverUrl(){
        return "http://localhost:8080/v1/api/image";
    }


    @Test
    public void responseToAProperEndPoint(){
        HttpResponse<JsonNode> response = Unirest.get( serverUrl() + "/momentum/health" ).asJson();
        assertEquals( HttpStatus.OK, response.getStatus() );
        assertEquals( "image/png", response.getHeaders().getFirst( "Content-Type" ) );
    }

    @Test
    public void responseToSomethingNotInDatabase(){
        HttpResponse<String> response = Unirest.get( serverUrl() + "/momum/heah" ).asString();
        assertEquals( HttpStatus.NOT_FOUND, response.getStatus() );
        assertEquals( "REQUESTED ITEM CANNOT BE FOUND ON OUR DATABASE", response.getBody());
    }

    @Test
    public void responseToWrongEndPoint(){
        HttpResponse<String> response = Unirest.get( serverUrl() + "/momum" ).asString();
        assertEquals( HttpStatus.NOT_FOUND, response.getStatus() );
        assertEquals( "THE REQUESTED URL IS NOT PART OF OUR SCHEMA, PLEASE INPUT A PROPER URL",
                response.getBody());
    }

}
