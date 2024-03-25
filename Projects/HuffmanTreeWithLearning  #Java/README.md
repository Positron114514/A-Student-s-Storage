# HuffmanTree by Java

> Wrote when learning SICP



In fact the whole project can be unwritten by `sortedMap` , but I wrote this after wrote my own `sortedSet` , so in class `HuffmanTree` and class `NodeContent` I use class `SortedCharSet` , and it can be easily writtem by `sortedMap`



## How to use:

1. Use `learnFromText` in `CodeLearning` to get the frequency of each characteristic and learn from texts
2. Use `getLearningResult` in `CodeLearning` to get learning result, whose type is `ArrayList<NodeContent>`
3. Use the learning result to create a `HuffmanTree`
4. Use `code` `codeFile` `decode` `decodeFile` in `HuffmanTree` to code / decode your text



PS: In the new version, you can simply use `createHuffmanTreeFromResult`  in `CodeLearning` to create a `HuffmanTree` from your learning result. (Skip Step 2 && 3)



An Example:

```java
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
        ArrayList<NodeContent> result = huffmanTreeLearningResult.getLeaningResult();

        HuffmanTree huffmanTree = new HuffmanTree(result);

//        System.out.println(huffmanTree);

        String savePath = "./res/codedFile.txt";

        saveAsFile(huffmanTree.codeFile("./res/code.txt"), savePath);
    }
}
```



### Code Learning

```java
// construnter
CodeLearning();
// learn from the file you want
public void learnFromText(String filePath);
// get learn result
public ArrayList<NodeContent> getLeaningResult();
// create a HuffmanTree from learning result
public HuffmanTree createHuffmanTreeFromResult();
```



### Huffman Tree

```java
// constructor, construct the tree through arr
HuffmanTree(ArrayList<NodeContent> arr);
// toString
public String toString();
// code the content
public String code(char target);
public String code(char[] target);
public String code(String target);
public String codeFile(String filePath);
// decode teh content
public String decode(String codes);
public String decodeFile(String filePath);
```

