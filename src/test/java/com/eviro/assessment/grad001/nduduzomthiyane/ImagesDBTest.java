package com.eviro.assessment.grad001.nduduzomthiyane;

import com.eviro.assessment.grad001.nduduzomthiyane.model.ImagesDB;
import com.eviro.assessment.grad001.nduduzomthiyane.model.Img;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.Set;


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







}
