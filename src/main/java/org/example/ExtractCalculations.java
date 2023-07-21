package org.example;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractCalculations {
    public static void main(String[] args) {
        String inputFile = "input2.txt";
        String outputFile = "output2.txt";

        // pattern to find the calculations within the brackets
        Pattern pattern = Pattern.compile("<<(.+?)>>");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                boolean firstMatchInLine = true;
                while (matcher.find()) {
                    if (!firstMatchInLine) {
                        writer.write(" ");
                    }
                    writer.write(matcher.group(1));
                    firstMatchInLine = false;
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
