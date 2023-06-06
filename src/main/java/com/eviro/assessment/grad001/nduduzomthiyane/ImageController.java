package com.eviro.assessment.grad001.nduduzomthiyane;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    String jdbcURL = "jdbc:h2:mem:images_db";
    Connection conn;

    @GetMapping(value = "/{name}/{surname}")
    public ResponseEntity<Object> getHttpImgLink(@PathVariable String name,
                                                             @PathVariable String surname)
                                                            throws SQLException, IOException {

        String queryingDatabase = persistDatabase(name.toLowerCase(),surname.toLowerCase());

        if(queryingDatabase != null){
            URL url1 = new URL(queryingDatabase);

            FileSystemResource resource =
                    new FileSystemResource(url1.getPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(resource.contentLength());

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } else {
            return handleNoSuchElementFoundException(
                    new NoSuchElementException("REQUESTED ITEM CANNOT BE FOUND ON OUR DATABASE" ));
        }
    }

    private String persistDatabase(String name,String surname) throws SQLException {
        conn = DriverManager.getConnection(jdbcURL);
        try( final Statement stmt = conn.createStatement() ){
            boolean gotAResultSet = stmt.execute(
                    "SELECT name, surname, httpImgLink FROM account_profile "
                            + "WHERE name = '" + name + "' " +
                            "AND surname = '" + surname + "';"
            );
            if( ! gotAResultSet ){
                System.out.println("REQUESTED ITEM CANNOT BE FOUND ON OUR DATABASE" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                while( results.next() ){
                    return results.getString( "httpImgLink"
                    );}
            }
        }
        return null;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(
            NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}


