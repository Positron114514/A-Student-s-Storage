import org.w3c.dom.Node;

import java.util.*;

public class NodeContent{
    private SortedCharSet content;
    private int weight;

    NodeContent(char[] content, int weight){
        this.content = new SortedCharSet(content);
        this.weight = weight;
    }

    NodeContent(SortedCharSet set, int weight){
        this.content = new SortedCharSet(set.getAllElements());
        this.weight = weight;
    }

    NodeContent(char c, int weight){
        this.content = new SortedCharSet(c);
        this.weight = weight;
    }

    public NodeContent(NodeContent content) {
        this.content = new SortedCharSet(content.getContentElements());
        this.weight = content.getWeight();
    }

    public int getWeight(){
        return weight;
    }
    public char[] getContentElements(){
        return content.getAllElements();
    }

    public SortedCharSet getContent(){
        return new SortedCharSet(content.getAllElements());
    }

    public boolean includes(char target){
        return content.includes(target);
    }

    public NodeContent merge(NodeContent target){
        return new NodeContent(content.merge(target.getContent()), weight + target.getWeight());
    }

    public boolean biggerThan(NodeContent target){
        return weight > target.weight;
    }

    public boolean smallerThan(NodeContent target){ return weight < target.weight;}

    public static boolean compare(NodeContent t1, NodeContent t2){
        return t1.biggerThan(t2);
    }

    public static NodeContent merge(NodeContent n1, NodeContent n2){
        return new NodeContent(n1.content.merge(n2.content), n1.weight + n2.weight);
    }

    static class NodeComparator implements Comparator<NodeContent>{
        @Override
        public int compare(NodeContent n1, NodeContent n2) {
            // 比较字符串的长度
            return n1.getWeight() - n2.getWeight();
        }
    }
}


class SortedCharSet {
    private ArrayList<Character> setElements;

    SortedCharSet(){
        this.setElements = new ArrayList<>();
    }


    SortedCharSet(Character... elements){
        this.setElements = new ArrayList<>();
        for(var element: elements){
            add(element);
        }
    }

    SortedCharSet(char[] elements){
        this.setElements = new ArrayList<>();
        for(var element: elements){
            add(element);
        }
    }

    private SortedCharSet(ArrayList<Character> sortedArray){
        this.setElements = new ArrayList<>(sortedArray);
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder("(");
        int size = setElements.size();

        for(int i = 0; i < size; i++){
            ret.append(setElements.get(i));
            if(i != size - 1){
                ret.append(", ");
            }
        }

        ret.append(")");
        return ret.toString();
    }

    private void sortTheSet(){
        Collections.sort(setElements);
    }

    public void add(char target){
        int placeToInsert = getElementsPlace(target);
        if(placeToInsert != -1){
            setElements.add(placeToInsert, target);
        }
    }

    public void add(SortedCharSet target){
        Objects.requireNonNull(target, "Target set cannot be null.");

        int start = 0;

        for(var element: target.getAllElements()){
            int placeToInsert = getElementPlace(element, start, size() - 1);
            if(placeToInsert != -1){
                setElements.add(placeToInsert, element);
                start = placeToInsert;
            }
        }
    }

    public void add(char[] a){
        for(var element: a){
            add(a);
        }
    }

    public void add(Character... elements){
        for(var element: elements){
            add(element);
        }
    }

    public boolean remove(int target){
        return setElements.remove((Object) target);
    }

    // 如果元素存在, 返回 -1 , 否则返回该元素应该插入的位置
    private int getElementPlace(char target, int left, int right){
        if(size() == 0) return 0;
        int mid;
        while(right >= left){
            if(right == left){
                if(setElements.get(right) < target){
                    return right + 1;
                }if(setElements.get(right) > target){
                    return right;
                }
                return -1;
            }
            mid = (left + right) / 2;
            if(setElements.get(mid) > target){
                right = mid - 1;
            }else if(setElements.get(mid) < target){
                left = mid + 1;
            }else{
                return -1;
            }
        }

        return -1;
    }

    private int getElementsPlace(char target){
        return getElementPlace(target, 0, size() - 1);
    }

    public boolean includes(char target){
        int search = Collections.binarySearch(setElements, target);
        return search >= 0;
    }

    public char[] getAllElements(){
        int size = setElements.size();
        char[] ret = new char[size];
        for(int i = 0; i < size; i++){
            ret[i] = setElements.get(i);
        }

        return ret;
    }

    public int size(){
        return setElements.size();
    }

    public SortedCharSet merge(SortedCharSet target){
        Objects.requireNonNull(target, "Target set cannot be null.");

        ArrayList<Character> ret_arr = new ArrayList<>();

        // The same as the merge process in merge sort
        int i = 0, j = 0;
        int size_1 = size(), size_2 = target.size();
        char[] set_1 = getAllElements();
        char[] set_2 = target.getAllElements();

        while(i < size_1 && j < size_2){
            if(set_1[i] < set_2[j]){
                ret_arr.add(set_1[i++]);
            }else if(set_1[i] > set_2[j]){
                ret_arr.add(set_2[j++]);
            }else{
                ret_arr.add(set_1[i++]);
                j++;
            }
        }

        while(i < size_1){
            ret_arr.add(set_1[i++]);
        }
        while(j < size_2){
            ret_arr.add(set_2[j++]);
        }

        return new SortedCharSet(ret_arr);
    }
}