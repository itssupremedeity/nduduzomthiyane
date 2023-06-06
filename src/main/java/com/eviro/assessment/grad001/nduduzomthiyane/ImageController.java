package com.eviro.assessment.grad001.nduduzomthiyane;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;



@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    String jdbcURL = "jdbc:h2:mem:images_db";
    Connection conn;

    @GetMapping(value = "/{name}")
    public ResponseEntity<FileSystemResource> getHttpImgLink(@PathVariable String name) throws SQLException, IOException {

        URL url1 = new URL(persistDatabase(name.toLowerCase()));

        FileSystemResource resource =
                new FileSystemResource(url1.getPath());
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(resource.contentLength());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        //String path = persistDatabase(name);
        //System.out.println(path);
        //return new FileSystemResource("/home/ndu/Documents/nduduzomthiyane/imagesDB/Momentum.png",);
    }

    private String persistDatabase(String name) throws SQLException {

        conn = DriverManager.getConnection(jdbcURL);

        try( final Statement stmt = conn.createStatement() ){
            boolean gotAResultSet = stmt.execute(
                    "SELECT httpImgLink FROM account_profile "
                            + "WHERE name = '" + name + "';"
            );
            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL resultset, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                int rowNo = 1;
                while( results.next() ){
                    return results.getString( "httpImgLink"
                    );}
            }
        }
        return null;
    }

}
