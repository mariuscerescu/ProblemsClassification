package org.example;

import java.io.*;
import java.util.*;

public class FindMySequnce {
    public static void main(String[] args) {
        String inputFile = "symbols2.txt";
        String problemsFile = "problemsFR.txt";
        String outputFile = "impartire.txt";
        List<String> desiredSymbols = Arrays.asList("/"); // replace with your symbols

        HashMap<String, ArrayList<Integer>> sequenceLines = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (desiredSymbols.contains(line)) {
                    sequenceLines.computeIfAbsent(line, k -> new ArrayList<>()).add(lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!sequenceLines.isEmpty()) {
            // Extract the line numbers for each desired symbol
            for (String symbol : desiredSymbols) {
                List<Integer> lines = sequenceLines.get(symbol);
                if (lines != null && !lines.isEmpty()) {
                    writeLinesToFile(lines, problemsFile, outputFile);
                }
            }
        } else {
            System.out.println("None of the desired symbols were found in the file.");
        }
    }

    private static void writeLinesToFile(List<Integer> lineNumbers, String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            int currentLine = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumbers.contains(currentLine)) {
                    writer.write(line + "\n");
                }
                currentLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
