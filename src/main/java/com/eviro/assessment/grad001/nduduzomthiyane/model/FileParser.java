package com.eviro.assessment.grad001.nduduzomthiyane.model;

import com.eviro.assessment.grad001.nduduzomthiyane.model.Img;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public interface FileParser {
    void parseCSV(File csvFile);
    File convertCSVDataToImage(Img base64ImageData);
    URI createImageLink(File fileImage) throws URISyntaxException, MalformedURLException;
}
