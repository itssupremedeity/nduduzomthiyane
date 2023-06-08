package com.eviro.assessment.grad001.nduduzomthiyane;

import com.eviro.assessment.grad001.nduduzomthiyane.controller.ImageController;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.sql.SQLException;


public class ControllerTest {


    @BeforeAll
    public static void startUp() throws IOException {
        NduduzomthiyaneApplication.main(new String[]{});
    }

    @Test
    public void persistingRightInfo() throws SQLException {
        String infoFromDB = ImageController.persistDatabase("momentum","health");
        assert infoFromDB != null;
    }

    @Test
    public void persistingWrongInfo() throws SQLException {
        String infoFromDB = ImageController.persistDatabase("mod","he");
        assert infoFromDB == null;
    }

}
