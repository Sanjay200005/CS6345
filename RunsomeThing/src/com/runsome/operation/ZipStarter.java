package com.runsome.operation;
import static com.runsome.utils.RunsomeConstants.OUTPUT_ZIP_FILE;
import static com.runsome.utils.RunsomeConstants.SOURCE_FOLDER;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.runsome.utils.GetFiles;
import com.runsome.utils.ZipFiles;

public class ZipStarter {
	public ZipStarter() {
		fileList = new ArrayList<String>();
	}
	static List <String> fileList;
//    private static final String OUTPUT_ZIP_FILE = "C:\\Documents\\Temp\\CN.zip";
//    private static final String SOURCE_FOLDER = "C:\\Users\\saisa\\Documents\\CS-5376-CN"; // SourceFolder path
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    	ZipStarter zipStarter = new ZipStarter();
        ZipFiles appZip = new ZipFiles();
        GetFiles getFileUtil = new GetFiles();
        getFileUtil.generateFileList(fileList, new File(SOURCE_FOLDER));
        System.out.println(fileList);
        //appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(fileList,OUTPUT_ZIP_FILE);
    }
}
