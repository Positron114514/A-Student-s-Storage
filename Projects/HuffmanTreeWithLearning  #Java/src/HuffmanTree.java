import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Node extends NodeContent{
    private Node leftNode;
    private Node rightNode;

    private boolean isLeaf;

    Node(NodeContent content, Node leftNode, Node rightNode){
        super(content);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        isLeaf = false;
    }

    Node(NodeContent content){
        super(content);
        this.leftNode = null;
        this.rightNode = null;
        isLeaf = true;
    }

    // 注意可能有bug
    Node(Node node){
        super(node.getContent(), node.getWeight());
        this.leftNode = node.getLeftNode();
        this.rightNode = node.getRightNode();
        isLeaf = node.isLeaf();
    }

    public Node getLeftNode(){
        return leftNode;
    }

    public Node getRightNode(){
        return rightNode;
    }

    public void setLeftNode(Node leftNode) throws CloneNotSupportedException {
        this.leftNode = (Node) leftNode.clone();
    }

    public void setRightNode(Node rightNode) throws CloneNotSupportedException {
        this.rightNode = (Node) rightNode.clone();
    }

    public boolean isLeaf(){
        return isLeaf;
    }

    public int getPlaceInArrayList(ArrayList<Node> nodeList){
        if(nodeList.isEmpty()) return 0;
        int left = 0, right = nodeList.size() - 1;
        int mid = (left + right) / 2;
        while(right >= left){
            if(right == left){
                if(nodeList.get(right).smallerThan(this)){
                    return right + 1;
                }if(nodeList.get(right).biggerThan(this)){
                    return right;
                }
                return right;
            }
            mid = (left + right) / 2;
            if(nodeList.get(mid).biggerThan(this)){
                right = mid - 1;
            }else if(nodeList.get(mid).smallerThan(this)){
                left = mid + 1;
            }else{
                return mid;
            }
        }

        return mid;
    }


    public static Node merge(Node n1, Node n2){
        return new Node(NodeContent.merge(n1, n2), n1, n2);
    }
}

public class HuffmanTree{
    Node root;
    ArrayList<Node> nodeList;
    HuffmanTree(ArrayList<NodeContent> arr){
        arr.sort(new NodeContent.NodeComparator());
        nodeList = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();

        for(var element: arr){
            nodes.add(new Node(element));
        }

        while(true){
            if(nodes.size() == 1){
                root = new Node(nodes.get(0));
                nodeList.add(root);
                break;
            }
            Node leftBranch = new Node(nodes.get(1));
            Node rightBranch = new Node(nodes.get(0));
            nodeList.add(leftBranch);
            nodeList.add(rightBranch);
            Node newNode = Node.merge(leftBranch, rightBranch);
            nodes.remove(0);
            nodes.remove(0);
            nodes.add(newNode.getPlaceInArrayList(nodes), newNode);
        }
    }

    @Override
    public String toString(){
        StringBuilder retBuilder = new StringBuilder();
        for(var element: getAllLeaves()){
            retBuilder.append(element.getContentElements());
            retBuilder.append(": ");
            retBuilder.append(element.getWeight());
            retBuilder.append("\n");
        }

        return retBuilder.toString();
    }

    private ArrayList<NodeContent> getAllLeaves(){
        ArrayList<NodeContent> ret = new ArrayList<>();
        for(var element: nodeList){
            if(element.isLeaf()){
                ret.add(new NodeContent(element));
            }
        }
        ret.sort(new NodeContent.NodeComparator());
        return ret;
    }

    public String code(char target){
        StringBuilder retBuilder = new StringBuilder();

        Node currentNode = root;

        while(!currentNode.isLeaf()){
            if(currentNode.getLeftNode().includes(target)){
                currentNode = currentNode.getLeftNode();
                retBuilder.append('0');
            }else if(currentNode.getRightNode().includes(target)){
                currentNode = currentNode.getRightNode();
                retBuilder.append('1');
            }else{
                System.out.printf("Invalid Character: %c.\n", target);
                System.exit(1);
            }
        }

//        System.out.printf("Coded %c by %s\n", target, retBuilder);

        return retBuilder.toString();
    }

    public String code(char[] target){
        StringBuilder retBuilder = new StringBuilder();
        for (char c : target) {
            retBuilder.append(code(c));
        }

        return retBuilder.toString();
    }

    public String code(String target){
        return code(target.toCharArray());
    }

    public String codeFile(String filePath){
        StringBuilder retBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // operation
                retBuilder.append(code(line));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return retBuilder.toString();
    }

    public String decode(String codes){
        StringBuilder retBuilder = new StringBuilder();
        Node currentNode = root;
        int p = 0;

        while(p < codes.length()){
            if(currentNode.isLeaf()){
                retBuilder.append(currentNode.getContentElements()[0]);
                currentNode = root;
            }else{
                if(codes.charAt(p++) == '1'){
                    currentNode = currentNode.getRightNode();
                }else{
                    currentNode = currentNode.getLeftNode();
                }
            }
        }

        retBuilder.append(currentNode.getContentElements()[0]);

        return retBuilder.toString();
    }

    public String decodeFile(String filePath){
        StringBuilder retBuilder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(new File(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // operation
                retBuilder.append(decode(line));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return retBuilder.toString();
    }
}