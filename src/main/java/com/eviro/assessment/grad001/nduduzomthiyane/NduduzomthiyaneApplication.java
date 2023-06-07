package com.eviro.assessment.grad001.nduduzomthiyane;

import com.eviro.assessment.grad001.nduduzomthiyane.model.Imag;
import com.eviro.assessment.grad001.nduduzomthiyane.model.ImagesDB;
import com.eviro.assessment.grad001.nduduzomthiyane.repository.ImageRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;


@SpringBootApplication
public class NduduzomthiyaneApplication {
	private static String dataDir = "1672815113084-GraduateDev_AssessmentCsv_Ref003.csv";
	private static ImagesDB imgDB;

	public static void main(String[] args) {
		SpringApplication.run(NduduzomthiyaneApplication.class, args);
		imgDB = new ImagesDB(new File(dataDir));
	}

}
