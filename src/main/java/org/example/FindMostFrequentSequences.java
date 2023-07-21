package org.example;

import java.io.*;
import java.util.*;

public class FindMostFrequentSequences {
    public static void main(String[] args) {
        String inputFile = "symbols2.txt";
        String problemsFile = "problemsFR.txt";
        String outputFile = "2FR.txt";
        int n = 1; // Change this to the number of top frequent sequences you want to see

        HashMap<String, Integer> sequenceCounts = new HashMap<>();
        HashMap<String, ArrayList<Integer>> sequenceLines = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                sequenceCounts.put(line, sequenceCounts.getOrDefault(line, 0) + 1);
                sequenceLines.computeIfAbsent(line, k -> new ArrayList<>()).add(lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriorityQueue<Map.Entry<String, Integer>> heap =
                new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : sequenceCounts.entrySet()) {
            heap.offer(entry);
            if (heap.size() > n) {
                heap.poll();
            }
        }

        List<Map.Entry<String, Integer>> topNSequences = new ArrayList<>();
        while (!heap.isEmpty()) {
            topNSequences.add(heap.poll());
        }
        Collections.reverse(topNSequences);

        printTopNSequences(topNSequences);

        if (!topNSequences.isEmpty()) {
            // Extract the n-th most frequent sequence
            Map.Entry<String, Integer> nthMostFrequentEntry = topNSequences.get(n-1);
            List<Integer> lines = sequenceLines.get(nthMostFrequentEntry.getKey());
            writeLinesToFile(lines, problemsFile, outputFile);
        } else {
            System.out.println("No sequences were found in the file.");
        }
    }

    private static void printTopNSequences(List<Map.Entry<String, Integer>> topNSequences) {
        for (Map.Entry<String, Integer> entry : topNSequences) {
            System.out.println("Sequence '" + entry.getKey() + "' occurred " + entry.getValue() + " times.");
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
