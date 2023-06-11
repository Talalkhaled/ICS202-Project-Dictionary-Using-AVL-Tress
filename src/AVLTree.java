/*
15/12/2022
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class AVLTree<T extends Comparable<T>> {
    class BTNode<T extends Comparable<T>> {
        protected T data;
        protected BTNode<T> left, right;


        public BTNode() {
            left = right = null;
        }

        public BTNode(T data) {
            this(data,null,null);
        }

        public BTNode(T data, BTNode<T> left, BTNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }



    BTNode<T> root;
    protected int height;

    public AVLTree() {
        super();
        height = -1;
    }

    public AVLTree(BTNode<T> root) {
        this.root = root;
        height = -1;
    }

    public void purge() {
        root = null;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BTNode<T> node) {
        if(node == null)
            return -1;
        else
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private AVLTree<T> getLeftAVL() {
        AVLTree<T> leftsubtree = new AVLTree<T>(root.left);
        return leftsubtree;
    }

    private AVLTree<T> getRightAVL() {
        AVLTree<T> rightsubtree = new AVLTree<T>(root.right);
        return rightsubtree;
    }

    protected int getBalanceFactor() {
        if(isEmpty())
            return 0;
        else
            return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }

    public void printTree(){
        if(root == null){
            System.out.println("[ ]");
            return;
        }
        printTree(root, "", true);
    }
    protected void printTree(BTNode currPtr, String indent, boolean last) {
        if(indent.equals(""))
            System.out.print("Root");
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                if(indent.equals(""))
                    System.out.print("--");
                else
                    System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            System.out.println(currPtr.data);
            printTree(currPtr.left, indent, false);
            printTree(currPtr.right, indent, true);
        }
    }
    public String[] iterativePreorder(String s) {
        String result = "";
        BTNode<T> p = root;
        Stack<BTNode<T>> travStack = new Stack<BTNode<T>>();
        if (p != null) {
            travStack.push(p);
            while (!travStack.isEmpty()) {
                p = travStack.pop();
                if (isSimilar((String)p.data,s)){
                    result = result + p.data + " ";
                }

                if (p.right != null)
                    travStack.push(p.right);
                if (p.left  != null)        // left child pushed after right
                    travStack.push(p.left);// to be on the top of the stack;
            }
            return result.split(" ");
        }else{
            return result.split(" ");
        }
    }
    public void iterativePreorder(FileWriter fileWriter) throws IOException {

        BTNode<T> p = root;

        Stack<BTNode<T>> travStack = new Stack<BTNode<T>>();

        if (p != null) {

            travStack.push(p);

            while (!travStack.isEmpty()) {

                p = travStack.pop();

                fileWriter.write((String) p.data +"\n");

                if (p.right != null)

                    travStack.push(p.right);

                if (p.left != null)
// left child pushed after right

                    travStack.push(p.left);// to be on the top of the stack;

            }

        }

    }
    public static boolean isSimilar(String s1,String s2){
        int count = 0;
        if (s1.length() == s2.length()){

            for (int i = 0; i < s1.length(); i++){
                if (s1.charAt(i) != s2.charAt(i)){
                    count++;
                }

                if (count > 1){

                    return false;
                }

            }

            if (count == 0){
                return false;
            }
            return true;
        }else if (s1.length() == s2.length()-1){
            for (int i = 0, j = 0; i< s1.length(); i++, j++){
                if (i == j && s1.charAt(i) != s2.charAt(j) && s1.charAt(i) != s2.charAt(++j)){
                    return false;
                }else if (s1.charAt(i) != s2.charAt(j)){
                    return false;
                }
            }

            return true;


        }else if (s2.length() == s1.length()-1){
            for (int i = 0, j = 0; i< s2.length(); i++, j++){
                if (i == j && s2.charAt(i) != s1.charAt(j) && s2.charAt(i) != s1.charAt(++j)){
                    return false;
                }else if (s2.charAt(i) != s1.charAt(j)){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }
    int size() { return size(root); }

    /* computes number of nodes in tree */
    int size(BTNode<T> node)
    {
        if (node == null)
            return 0;
        else
            return (size(node.left) + 1 + size(node.right));
    }
    public void deleteByCopying(T el) {
        BTNode<T> node, p = root, prev = null;
        while (p != null && !p.data.equals(el)) {  // find the node p
            prev = p;                           // with element el;
            if (el.compareTo(p.data) < 0)
                p = p.left;
            else
                p = p.right;
        }
        node = p;
        if (p != null && p.data.equals(el)) {
            if (node.right == null)             // node has no right child;
                node = node.left;
            else if (node.left == null)         // no left child for node;
                node = node.right;
            else {
                BTNode<T> tmp = node.left;    // node has both children;
                BTNode<T> previous = node;    // 1.
                while (tmp.right != null) {    // 2. find the rightmost
                    previous = tmp;            //    position in the
                    tmp = tmp.right;           //    left subtree of node;
                }
                node.data = tmp.data;              // 3. overwrite the reference
                //    to the element being deleted;
                if (previous == node)          // if node's left child's
                    previous.left  = tmp.left; // right subtree is null;
                else
                    previous.right = tmp.left; // 4.
            }
            if (p == root)
                root = node;
            else if (prev.left == p)
                prev.left = node;
            else prev.right = node;
        }
        else if (root != null)
            throw new java.util.NoSuchElementException("el " + el + " is not in the tree");
        else
            throw new UnsupportedOperationException("the tree is empty");
    }
    public boolean isEmpty() {
        return root == null;
    }
    public void insert(T el) throws IllegalArgumentException {
        BTNode<T> p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            int result = el.compareTo(p.data);

            if(result == 0) {
                //System.out.println("Duplicate key.");
                throw new IllegalArgumentException();
            }
            else if (result < 0)
                p = p.left;
            else
                p = p.right;
        }
        if (root == null)    // tree is empty;
            root = new BTNode<T>(el);
        else if (el.compareTo(prev.data) < 0)
            prev.left  = new BTNode<T>(el);
        else
            prev.right = new BTNode<T>(el);
    }
    public void insertInitial(T el) throws IllegalArgumentException {
        BTNode<T> p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            int result = el.compareTo(p.data);

            if(result == 0) {
                //System.out.println("Duplicate key.");
                return;
            }
            else if (result < 0)
                p = p.left;
            else
                p = p.right;
        }
        if (root == null)    // tree is empty;
            root = new BTNode<T>(el);
        else if (el.compareTo(prev.data) < 0)
            prev.left  = new BTNode<T>(el);
        else
            prev.right = new BTNode<T>(el);
    }

    public void insertAVL(T el)  {
        insert(el);
        this.balance();
    }

    public void deleteAVL(T el) {
        // to be completed by students
        deleteByCopying(el);
        this.balance();
    }

    protected void balance()
    {
        if(!isEmpty())
        {
            getLeftAVL().balance();
            getRightAVL().balance();

            adjustHeight();

            int balanceFactor = getBalanceFactor();

            if(balanceFactor == -2) {
                //System.out.println("Balance factor = " + balanceFactor);
                //System.out.println("Balancing node with el: "+root.data);


                if(getRightAVL().getBalanceFactor() == 0 && getLeftAVL().getBalanceFactor() == -1)    /// special case
                    rotateRight();
                else if(getLeftAVL().getBalanceFactor() <= 0)
                    rotateRight();
                else
                    rotateLeftRight();
            }

            else if(balanceFactor == 2) {
                //System.out.println("Balance factor = " + balanceFactor);
                //System.out.println("Balancing node with el: "+root.data);

                if(getRightAVL().getBalanceFactor() == 0)         /// special case that cannot be done
                    rotateLeft();                                /// by double rotations
                else if(getRightAVL().getBalanceFactor() > 0)
                    rotateLeft();
                else
                    rotateRightLeft();
            }
        }
    }
    public boolean search(T el) {
        BTNode<T> p = root;
        while (p != null)
            if (el.equals(p.data))
                return  true;
            else if (el.compareTo(p.data) < 0)
                p = p.left;
            else
                p = p.right;
        return false;
    }
    protected void adjustHeight()
    {
        if(isEmpty())
            height = -1;
        else
            height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
    }

    protected void rotateRight() {
        //System.out.println("RIGHT ROTATION");
        // to be completed by students
        BTNode<T> tempNode = root.right;
        root.right = root.left;
        root.left = root.right.left;
        root.right.left = root.right.right;
        root.right.right = tempNode;

        T val = (T) root.data;
        root.data = root.right.data;
        root.right.data = val;

        getLeftAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeft() {
        //System.out.println("LEFT ROTATION");
        BTNode<T> tempNode = root.left;
        root.left = root.right;
        root.right = root.left.right;
        root.left.right = root.left.left;
        root.left.left = tempNode;

        T val = (T) root.data;
        root.data = root.left.data;
        root.left.data = val;

        getLeftAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeftRight()
    {
        //System.out.println("Double Rotation...");
        getLeftAVL().rotateLeft();
        getLeftAVL().adjustHeight();
        this.rotateRight();
        this.adjustHeight();
    }

    protected void rotateRightLeft()
    {
        //System.out.println("Double Rotation...");
        // to be completed by students
        getRightAVL().rotateRight();
        getRightAVL().adjustHeight();
        this.rotateLeft();
        this.adjustHeight();

<<<<<<< HEAD
=======
}
>>>>>>> 35aa03436fdf1e1ca26f8fac0dfabbd577ac69d7
}
}