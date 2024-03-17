import java.util.*;

public class sortedSet {
    private ArrayList<Integer> setElements;

    sortedSet(){
        this.setElements = new ArrayList<>();
    }

    sortedSet(Integer... elements){
        this.setElements = new ArrayList<>();
        for(var element: elements){
            add(element);
        }
    }

    sortedSet(int[] elements){
        this.setElements = new ArrayList<>();
        for(var element: elements){
            add(element);
        }
    }

    private sortedSet(ArrayList<Integer> sortedArray){
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

    public void add(int target){
        int placeToInsert = getElementsPlace(target);
        if(placeToInsert != -1){
            setElements.add(placeToInsert, target);
        }
    }

    public void add(sortedSet target){
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

    public void add(int[] a){
        for(var element: a){
            add(a);
        }
    }

    public void add(Integer... elements){
        for(var element: elements){
            add(element);
        }
    }

    public boolean remove(int target){
        return setElements.remove((Object) target);
    }

    // 如果元素存在, 返回 -1 , 否则返回该元素应该插入的位置
    private int getElementPlace(int target, int left, int right){
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

    private int getElementsPlace(int target){
        return getElementPlace(target, 0, size() - 1);
    }

    public boolean includes(int target){
        int search = Collections.binarySearch(setElements, target);
        return search >= 0;
    }

    public int[] getAllElements(){
        int size = setElements.size();
        int[] ret = new int[size];
        for(int i = 0; i < size; i++){
            ret[i] = setElements.get(i);
        }

        return ret;
    }

    public int size(){
        return setElements.size();
    }

    public sortedSet merge(sortedSet target){
        Objects.requireNonNull(target, "Target set cannot be null.");

        ArrayList<Integer> ret_arr = new ArrayList<>();

        // The same as the merge process in merge sort
        int i = 0, j = 0;
        int size_1 = size(), size_2 = target.size();
        int[] set_1 = getAllElements(), set_2 = target.getAllElements();
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

        return new sortedSet(ret_arr);
    }
}