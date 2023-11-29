package dev.buga.springbatchexample.utility;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileMoveListener implements JobExecutionListener {

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            System.out.println("Job failed, will not move file.");
            return;
        }

        try {
            String sourceFilePath = "src/main/resources/dwellings4.csv";
            String destinationFolderPath = "src/main/resources/completed";

            Path sourcePath = new File(sourceFilePath).toPath();
            Path destinationPath = new File(destinationFolderPath, sourcePath.getFileName().toString()).toPath();

            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved to: " + destinationPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
