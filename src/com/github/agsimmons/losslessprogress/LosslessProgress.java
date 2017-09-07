package com.github.agsimmons.losslessprogress;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class LosslessProgress {

    public static final String[] LOSSY_FORMATS = {"mp3", "ogg"};
    public static final String[] LOSSLESS_FORMATS = {"wav", "flac"};

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Music Directory: ");
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

        System.out.println("Lossy Albums:");
        for (String s : buildOutput(lossyFiles)) {
            System.out.println(s);
        }

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

    private static ArrayList<String> buildOutput(ArrayList<File> musicFiles) {

        HashSet<String> set = new HashSet<>();

        for (File file : musicFiles) {
            try {

                Tag tags = AudioFileIO.read(file).getTag();

                if (tags.getFirst(FieldKey.ALBUM_ARTIST) != null) {
                    set.add(tags.getFirst(FieldKey.ALBUM_ARTIST) + " - " + tags.getFirst(FieldKey.ALBUM));
                } else {
                    set.add(tags.getFirst(FieldKey.ARTIST) + " - " + tags.getFirst(FieldKey.ALBUM));
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Could not read \"" + file.getAbsolutePath() + "\"");
            }
        }

        ArrayList<String> returnArraylist = new ArrayList<>(set);

        returnArraylist.sort(String.CASE_INSENSITIVE_ORDER);

        return returnArraylist;

    }

}
