package com.mathChart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
 
 
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

/** Creates a new file on the Google Drive*/
public class NewFile {
 
    // PRIVATE!
	/** Directly creation of the file  and setting permissions readonly for anyone*/
    private static File _createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, AbstractInputStreamContent uploadStreamContent) throws IOException {
 
        File fileMetadata = new File();
        fileMetadata.setName(customFileName);
 
        List<String> parents = Arrays.asList(googleFolderIdParent);
        fileMetadata.setParents(parents);
        //
        Drive driveService = DriveUtils.getDriveService();
 
        File file = driveService.files().create(fileMetadata, uploadStreamContent)
                .setFields("id, webContentLink, webViewLink, parents").execute();
        String permissionType = "anyone";
		String permissionRole = "reader";
		Permission newPermission = new Permission();
        newPermission.setType(permissionType);
        newPermission.setRole(permissionRole);
        driveService.permissions().create(file.getId(), newPermission).execute();
 
        return file;
    }
 
    /** Create Google File from byte[] */
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, byte[] uploadData) throws IOException {
        //
        AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
    /** Create Google File from java.io.File */
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, java.io.File uploadFile) throws IOException {
 
        //
        AbstractInputStreamContent uploadStreamContent = new FileContent(contentType, uploadFile);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
    /** Create Google File from InputStream */
    public static File createGoogleFile(String googleFolderIdParent, String contentType, //
            String customFileName, InputStream inputStream) throws IOException {
 
        //
        AbstractInputStreamContent uploadStreamContent = new InputStreamContent(contentType, inputStream);
        //
        return _createGoogleFile(googleFolderIdParent, contentType, customFileName, uploadStreamContent);
    }
 
//    public static void main(String[] args) throws IOException {
// 
//        java.io.File uploadFile = new java.io.File("/home/vitalii/test.txt");
// 
//        // Create Google File:
// 
//        File googleFile = createGoogleFile(null, "text/plain", "newfile.txt", uploadFile);
// 
//        System.out.println("Created Google file!");
//        System.out.println("WebContentLink: " + googleFile.getWebContentLink() );
//        System.out.println("WebViewLink: " + googleFile.getWebViewLink() );
// 
//        System.out.println("Done!");
//    }
     
}