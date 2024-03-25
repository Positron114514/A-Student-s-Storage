import java.io.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Main{
    public static void saveAsFile(String content, String filePath){
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();
            System.out.println("Content has been written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        CodeLearning huffmanTreeLearningResult = new CodeLearning();
        huffmanTreeLearningResult.learnFromText("./res/learn.txt");
        huffmanTreeLearningResult.learnFromText("./res/code.txt");
        huffmanTreeLearningResult.learnFromText("./res/learn2.txt");
        huffmanTreeLearningResult.learnFromText("./res/learn3.txt");

        HuffmanTree huffmanTree = huffmanTreeLearningResult.createHuffmanTreeFromResult();

//        System.out.println(huffmanTree);

        String savePath = "./res/codedFile.txt";

        saveAsFile(huffmanTree.codeFile("./res/code.txt"), savePath);

        System.out.println(huffmanTree);
    }
}