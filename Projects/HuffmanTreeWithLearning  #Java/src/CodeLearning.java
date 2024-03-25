import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CodeLearning {
    SortedMap<Character, Integer> leaningResult;

    CodeLearning(){
        leaningResult = new TreeMap<>();
    }
    public void learnFromText(String filePath){
        try {
            Scanner scanner = new Scanner(new File(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // operation
                analyseSentence(line);
                normalizeResult();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.printf("file %s not found.", filePath);
            e.printStackTrace();
        }
    }

    public ArrayList<NodeContent> getLeaningResult(){
        return toArrayList();
    }

    private ArrayList<NodeContent> toArrayList(){
        ArrayList<NodeContent> arr = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : leaningResult.entrySet()) {
            arr.add(new NodeContent(entry.getKey(), entry.getValue()));
        }

        return arr;
    }

    public HuffmanTree createHuffmanTreeFromResult(){
        return new HuffmanTree(toArrayList());
    }

    private void analyseSentence(String sentence){
        for(int i = 0; i < sentence.length(); i++){
            char tempCh = sentence.charAt(i);
            if(leaningResult.get(tempCh) != null) {
                leaningResult.put(tempCh, leaningResult.get(tempCh) + 1);
            }else{
                leaningResult.put(tempCh, 1);
            }
        }
    }

    private boolean examNumSize(){
        int MAX_NUM_SIZE = 10000000;
        for (Map.Entry<Character, Integer> entry : leaningResult.entrySet()) {
            if(entry.getValue() > MAX_NUM_SIZE){
                return true;
            }
        }

        return false;
    }

    private void normalizeResult(){
        if(examNumSize()){
            for (Map.Entry<Character, Integer> entry : leaningResult.entrySet()) {
                entry.setValue(entry.getValue() / 10);
            }
        }
    }
}