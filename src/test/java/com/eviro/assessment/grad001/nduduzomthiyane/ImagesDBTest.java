package com.eviro.assessment.grad001.nduduzomthiyane;

import com.eviro.assessment.grad001.nduduzomthiyane.model.ImagesDB;
import com.eviro.assessment.grad001.nduduzomthiyane.model.Img;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ImagesDBTest {

    private ImagesDB parser;
    private LineNumberReader input;
    private Set<Img> fakeImgData;

    @BeforeEach
    public void setUp() throws IOException {
        input = new LineNumberReader( new StringReader(TestData.CSV_DATA ));
        parser = new ImagesDB();
        fakeImgData = parser.parseDataLines(input);
    }



    @Test
    public void checkIfOurSetHasObjects() throws IOException {
        assertNotEquals( 0, fakeImgData.size());
    }

    @Test
    public void checkIfFirstLineSkipped() throws IOException {
        assertNotEquals( 4, fakeImgData.size());
    }

    @Test
    public void checkIfNameIsStoredCorrectly() throws IOException {
        Img img = fakeImgData.iterator().next();
        assertEquals( "Amatikulu", img.getName());
    }

    @Test
    public void checkIfSurnameIsStoredCorrectly() throws IOException {
        Img img = fakeImgData.iterator().next();
        assertEquals( "Station", img.getSurname());
    }


    @Test
    public void checkIfImgDataIsStoredCorrectly() throws IOException {
        Img img = fakeImgData.iterator().next();
        assertEquals( "-29.05111111", img.getImgEncode());
    }











}
