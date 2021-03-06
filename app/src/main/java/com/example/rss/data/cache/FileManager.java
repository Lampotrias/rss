package com.example.rss.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FileManager {

    @Inject
    FileManager() {
    }

    public void writeToFile(File file, String fileContent) {
        //if (!file.exists()) {
            try {
                final FileWriter writer = new FileWriter(file, true);
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }

    void writeToPreferences(Context context, String preferenceFileName, String key,
                            long value) {

        final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    boolean exists(File file) {
        return file.exists();
    }

    String readFileContent(File file) {
        final StringBuilder fileContentBuilder = new StringBuilder();
        if (file.exists()) {
            String stringLine;
            try {
                final FileReader fileReader = new FileReader(file);
                final BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((stringLine = bufferedReader.readLine()) != null) {
                    fileContentBuilder.append(stringLine).append("\n");
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContentBuilder.toString();
    }

    long getFromPreferences(Context context, String preferenceFileName, String key) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    boolean clear(File directory) {
        boolean result = false;
        if (directory.isDirectory()) {
            if (directory.exists()) {
                for (File file : directory.listFiles()) {
                    result = file.delete();
                }
            }
        } else if (directory.isFile()) {
            result = directory.delete();
        }
        return result;
    }
}
