package com.eviro.assessment.grad001.nduduzomthiyane;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.Base64;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class ImagesDB implements FileParser{

    String jdbcURL = "jdbc:h2:mem:images_db";
    Connection conn;
    private static Set<Img> images;
    static final int NAME_COLUMN = 0;
    static final int SURNAME_COLUMN = 1;
    static final int IMAGE_FORMAT_COLUMN = 2;
    static final int IMAGE_DATA_COLUMN = 3;


    public ImagesDB(){

        try {
            conn = DriverManager.getConnection(jdbcURL);
            System.out.println("Connected to database!!!");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseCSV(File csvFile) {

        try (final LineNumberReader in = new LineNumberReader(new FileReader(csvFile))) {
            in.readLine(); // get rid of the header line
            images = parseDataLines(in);
            //in.getLineNumber();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File convertCSVDataToImage(Img base64ImageData) {

        byte[] data = Base64.getDecoder().decode(base64ImageData.getImgEncode());
        String outPutFile = "imagesDB/" +
                base64ImageData.getName() + base64ImageData.getImgFormat();

        System.out.println("processing image");

        try( OutputStream stream = new FileOutputStream(outPutFile))
        {
            stream.write(data);
            return new File(outPutFile);
        }
        catch (Exception e)
        {
            System.err.println("Couldn't write to file...");
        }

        return null;
    }

    @Override
    public URI createImageLink(File fileImage) throws URISyntaxException, MalformedURLException {
        String protocol = "http";
        String host = "IP";
        int port = 8080;
        String path = "/"+fileImage.getPath();
        URL url = new URL(protocol, host, port, path);
        return url.toURI();
    }

    private void createTable() {
        try( final Statement stmt = conn.createStatement() ){
            stmt.executeUpdate( "CREATE TABLE account_profile(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100)," +
                    "surname VARCHAR(100)," +
                    "httpImgLink TEXT);" );
            System.out.println( "Success creating profile table!" );
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
    }

    Set<Img> parseDataLines(final LineNumberReader in) {
        return in.lines()
               .map(this::splitLineIntoValues)
               .filter(this::isLineAWantedFeature)
               .map(this::asImg)
               .collect(Collectors.toSet());
    }

    String[] splitLineIntoValues(String aCsvLine) {
        return aCsvLine.trim().split(",");
    }

    private static final Set<String> NOT_WANTED_FEATURES = Set.of(
            "name".toLowerCase(),
            "surname".toLowerCase(),
            "imageFormat".toLowerCase(),
            "imageData".toLowerCase());


    boolean isLineAWantedFeature(String[] csvValue) {
        return !NOT_WANTED_FEATURES.contains(csvValue[IMAGE_DATA_COLUMN].toLowerCase());
    }

    Img asImg(String[] values) {
        return new Img(values[NAME_COLUMN], values[SURNAME_COLUMN],
                    values[IMAGE_FORMAT_COLUMN], values[IMAGE_DATA_COLUMN]);
    }

    public void insertIntoDB() throws URISyntaxException {
        for(Img image : images){
            File file = convertCSVDataToImage(image);

            try( final Statement stmt = conn.createStatement() ){

                stmt.executeUpdate( "INSERT INTO account_profile (name,surname,httpImgLink)" +
                        " VALUES ('" + image.getName() + "','" +
                        image.getSurname() + "','" +
                        createImageLink(file).toString() + "');");
                System.out.println(createImageLink(file).toString());
        } catch (SQLException | MalformedURLException e) {
                throw new RuntimeException(e);
                //ffff
            }
        }
    }
}
