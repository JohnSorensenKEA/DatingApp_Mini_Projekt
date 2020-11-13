package com.example.demo.services;


import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PhotoHandler {

   public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/resources/static/user_photos";


   public String takePhoto(MultipartFile file, String fileName, int userID) {

       String contentType = file.getContentType();
       String[] arr = fileName.split("\\.");
       String extension = "." + arr[arr.length - 1];
        if (contentType.startsWith("image/")) {
            fileName = userID + extension;
            // --||-- = userIden.getUserID() "";
            Path filePath = Paths.get(uploadDirectory, fileName);
            //fileNames.append(file.getOriginalFilename() + " ");
            // fileName = fileName + file.getOriginalFilename;
            try {
                Files.write(filePath, file.getBytes());
                // tager imod filens name og vej og files bytes(indhold)
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "user_photos/" + fileName;
        }
        return null;
    }

    public void deletePhoto(){

    }
}

/*
 //StringBuilder fileNames = new StringBuilder();
        // String fileNames = "";
        String contentType = file.getContentType();
        if (contentType.startsWith("image/")) {
           String fileName = "photoTilhoendeUserId " + + userIden.getUserID();
            // --||-- = userIden.getUserID() "";
            Path filePath = Paths.get(uploadDirectory, fileName);
            //fileNames.append(file.getOriginalFilename() + " ");
            // fileName = fileName + file.getOriginalFilename;
            try {
                Files.write(filePath, file.getBytes());
                // tager imod filens name og vej og files bytes(indhold)
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
 */
  /*
         //StringBuilder fileNames = new StringBuilder();
        // String fileNames = "";
        String contentType = file.getContentType();
        if (contentType.startsWith("image/")) {
            String fileName = "photoTilhoendeUserId" + userIden.getUserID();
            // --||-- = userIden.getUserID() "";
            Path filePath = Paths.get(uploadDirectory, fileName);
            //fileNames.append(file.getOriginalFilename() + " ");
            // fileName = fileName + file.getOriginalFilename;

            try {
                Files.write(filePath, file.getBytes());
                // tager imod filens name og vej og files bytes(indhold)
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "ErrorPage";
         */