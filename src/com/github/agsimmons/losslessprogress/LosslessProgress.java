package com.github.agsimmons.losslessprogress;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class LosslessProgress {
    
    public static final String[] LOSSY_FORMATS = {"mp3", "ogg"};
    public static final String[] LOSSLESS_FORMATS = {"wav", "flac"};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Music Directory: ");
        File musicDirectory = new File(input.nextLine());
        
        
        ArrayList<File> lossyFiles = new ArrayList(FileUtils.listFiles(musicDirectory, LOSSY_FORMATS, true));
        ArrayList<File> losslessFiles = new ArrayList(FileUtils.listFiles(musicDirectory, LOSSLESS_FORMATS, true));
        
        int mp3FileCount = countFilesByType(lossyFiles, "mp3");
        int oggFileCount = countFilesByType(lossyFiles, "ogg");
        
        int wavFileCount = countFilesByType(losslessFiles, "wav");
        int flacFileCount = countFilesByType(losslessFiles, "flac");
        
        System.out.println("File Summary:\n"
                         + "  Lossy:\n"
                         + "    mp3: " + mp3FileCount + "\n"
                         + "    ogg: " + oggFileCount + "\n"
                         + "  Lossless:\n"
                         + "    wav: " + wavFileCount + "\n"
                         + "    flac: " + flacFileCount + "\n\n");
        
        System.out.println("");
    }

    public static int countFilesByType(ArrayList<File> fileCollection, String type) {
        int count = 0;
        
        for (File file : fileCollection) {
            if (file.getName().toLowerCase().endsWith(type.toLowerCase())) {
                count++;
            }
        }
        
        return count;
    }
    
}
