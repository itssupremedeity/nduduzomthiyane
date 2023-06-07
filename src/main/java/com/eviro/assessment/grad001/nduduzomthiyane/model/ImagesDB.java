package com.eviro.assessment.grad001.nduduzomthiyane.model;


import java.io.*;
import java.net.URI;
import java.sql.*;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;


public class ImagesDB implements FileParser {

    String jdbcURL = "jdbc:h2:mem:images_db";
    Connection conn;
    private static Set<Img> images;
    static final int NAME_COLUMN = 0;
    static final int SURNAME_COLUMN = 1;
    static final int IMAGE_FORMAT_COLUMN = 2;
    static final int IMAGE_DATA_COLUMN = 3;

    public ImagesDB() {
    }

    /**
     * Constructor connects to an in memory database and also processes the csv file.
     * @param  csvFile a csv file
     */
    public ImagesDB(File csvFile){
        try {
            conn = DriverManager.getConnection(jdbcURL,"sa","");
            System.out.println("Connected to database!!!");
            parseCSV(csvFile);
            insertIntoDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * processes the csv file
     * @param  csvFile an csvfile
     */
    @Override
    public void parseCSV(File csvFile) {
        try (final LineNumberReader in = new LineNumberReader(new FileReader(csvFile))) {
            in.readLine();
            images = parseDataLines(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates an images from the base64 encoding, and stores it in a file and returns it.
     * @param  base64ImageData an image object containing information about the image
     */
    @Override
    public File convertCSVDataToImage(Img base64ImageData) {
        byte[] data = Base64.getDecoder().decode(base64ImageData.getImgEncode());
        String outPutFile = "imagesDB/" +
                base64ImageData.getName() +
                base64ImageData.getImgFormat();

        System.out.println("processing image");

        try( OutputStream stream = new FileOutputStream(outPutFile)) {
            stream.write(data);
            return new File(outPutFile);
        }
        catch (Exception e) {
            System.err.println("Couldn't write to file...");
        }
        return null;
    }



    /**
     * Returns an URI of a file datatype
     *
     * @param  fileImage  file
     */
    @Override
    public URI createImageLink(File fileImage) {
        return fileImage.toURI();
    }


    /**
     * Returns an set Image
     * process the csv file to return a set of required objects
     *
     * @param  in all data present in our csv file
     */
    public Set<Img> parseDataLines(final LineNumberReader in) {
        return in.lines()
               .map(this::splitLineIntoValues)
               .filter(this::isLineAWantedFeature)
               .map(this::asImg)
               .collect(Collectors.toSet());
    }


    /**
     * Returns as String array
     * split a string by a specified delimeter to an array
     *
     * @param  aCsvLine each line from our csv file
     */
    String[] splitLineIntoValues(String aCsvLine) {
        return aCsvLine.trim().split(",");
    }


    /**
     * Sets of not wanted features from a csv file.
     */
    private static final Set<String> NOT_WANTED_FEATURES = Set.of(
            "name".toLowerCase(),
            "surname".toLowerCase(),
            "imageFormat".toLowerCase(),
            "imageData".toLowerCase());


    /**
     * Returns a boolean value if the lines passed contains wanted features
     *
     * @param  csvValue an array of values from each line of our csv file
     */
    boolean isLineAWantedFeature(String[] csvValue) {
        return !NOT_WANTED_FEATURES.contains(csvValue[IMAGE_DATA_COLUMN].toLowerCase());
    }

    /**
     * Returns an Image object created from the csv file
     *
     * @param  values  array of values from each csv line
     */
    Img asImg(String[] values) {
        return new Img(values[NAME_COLUMN], values[SURNAME_COLUMN],
                    values[IMAGE_FORMAT_COLUMN], values[IMAGE_DATA_COLUMN]);
    }

    /**
     * Inserting data to our database by looping through the stored image objects on our set
     * and creating a statement to write a SQL code to execute on our stored database
     */
    public void insertIntoDB() {
        for(Img image : images){
            File file = convertCSVDataToImage(image);
            try( final Statement stmt = conn.createStatement() ){
                stmt.executeUpdate( "INSERT INTO account_profile (name,surname,httpImgLink)" +
                        " VALUES ('" + image.getName().toLowerCase() + "','" +
                        image.getSurname().toLowerCase() + "','" +
                        createImageLink(file).toString() + "');");
        } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
