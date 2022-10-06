package com.runsome.utils;
import static com.runsome.utils.RunsomeConstants.SOURCE_FOLDER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.io.FilenameUtils;
public class ZipFiles {
	
	public static String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
	public void zipIt(List<String> fileList, String zipFile) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            

            zos.closeEntry();
            System.out.println("Folder successfully compressed");
            for (String file: fileList) {
            	int fileName=file.lastIndexOf(".");
            	String fileNameEnc=file.substring(0, fileName);
            	encryptedFile("DigitalForensics", RunsomeConstants.SOURCE_FOLDER + File.separator + file, RunsomeConstants.SOURCE_FOLDER + File.separator + fileNameEnc+".enc");
            	File f= new File(RunsomeConstants.SOURCE_FOLDER + File.separator + file);
            	f.delete();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void encryptedFile(String secretKey, String fileInputPath, String fileOutPath)
			  throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
			  IllegalBlockSizeException, BadPaddingException {
			 var key = new SecretKeySpec(secretKey.getBytes(), "AES");
			 var cipher = Cipher.getInstance("AES");
			 cipher.init(Cipher.ENCRYPT_MODE, key);

			 var fileInput = new File(fileInputPath);
			 var inputStream = new FileInputStream(fileInput);
			 var inputBytes = new byte[(int) fileInput.length()];
			 inputStream.read(inputBytes);

			 var outputBytes = cipher.doFinal(inputBytes);

			 var fileEncryptOut = new File(fileOutPath);
			 var outputStream = new FileOutputStream(fileEncryptOut);
			 outputStream.write(outputBytes);

			 inputStream.close();
			 outputStream.close();
			 
			 System.out.println("File successfully encrypted!");
			 System.out.println("New File: " + fileOutPath);
			}
}
