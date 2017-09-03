import java.util.Stack;

public class BST {

    public static void main(String[] args){
        BST BST = new BST();

        BST.insert(1);
        BST.insert(2);
        BST.insert(3);
        System.out.println(BST.minimum());
        System.out.println(BST.maximum());
        BST.remove(3);
        BST.insert(4);
        System.out.println(BST.maximum());
        System.out.println(BST.search(2).getKey());
        BST.remove(1);
        BST.remove(2);
        BST.remove(3);
        BST.remove(4);
        BST.insert(1000);
        System.out.println(BST.maximum());
        BST.insert(1);
        BST.insert(2);
        BST.insert(3);
        BST.insert(3000);

        BST.remove(1);
        BST.remove(3);
        BST.remove(2);
        BST.remove(1000);


        BST.insert(123);
        BST.insert(41);
        BST.insert(12);
        BST.insert(55);
        BST.insert(100);
        BST.insert(121);
        BST.insert(3241);
        BST.insert(3199);
        BST.insert(3499);
        BST.insert(3599);



        System.out.println("@@@@@@@@@@@@@@@@ " + BST.search(3000).size);
        System.out.println("################ " + BST.os_select(0));
        System.out.println("$$$$$$$$$$$$$$$$ " + BST.os_rank(121));

//        System.out.println(BST.getRoot().right.key);
        BST.displayTree();

    }
    public class Node {
        protected Node left, right; // this Node's children
        protected Node parent;      // this Node's parent
        private int size;
        private int key;              // this Node's key

        public Node(int key) {
            this.key = key;
            this.size = 1;
            parent = left = right = null;
        }

        public int getKey() {
            return key;
        }

        public String toString() {
            return "key = " + key;
        }
    }


    protected Node root;

    public BST() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Node getRoot() {
        if (root == null)
            return null;
        else
            return root;
    }
    public Integer os_rank(int key) {
        return os_rank(search(key));
    }

    public Integer os_rank(Node x) {
        int r;
        if(x.left != null){
            r = x.left.size + 1;
        } else {
            r = 1;
        }
        Node y = x;
        while(y != root) {
            if(y == y.parent.right) {
                if(y.parent.left != null) {
                    r += y.parent.left.size + 1;
                }
                else {
                    r ++;
                }
            }
            y = y.parent;
        }
        return r;
    }

    public Node os_select(int i) {
        return os_select(root, i + 1);
    }

    public Node os_select(Node x, int i) {
        int r;
        if(x.left != null){
            r = x.left.size + 1;
        } else {
            r = 1;
        }

        if(r == i) {
            return x;
        } else if(i < r) {
            return os_select(x.left, i);
        }
        else {
            return os_select(x.right, i - r );
        }
    }


    public String toString() {
        if (root == null)
            return "";
        else
            return print(root, 0);
    }

    private String indent(int s) {
        String result = "";
        for (int i = 0; i < s; i++)
            result += "  ";
        return result;
    }


    private String print(Node x, int depth) {
        if (x == null)
            return "";
        else
            return print(x.right, depth + 1) + indent(depth) + x.toString() + "\n"
                    + print(x.left, depth + 1);
    }

    public Node insert(int key) {
        Node z = new Node(key);
        Node x = root;
        Node y;
        Node xParent = null;

        while (x != null) {
            xParent = x;
            if (key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = xParent;

        if (xParent == null)
            root = z;
        else {
            if (key < xParent.key)
                xParent.left = z;
            else
                xParent.right = z;
        }
        y = z;
        while(y.parent != null){
            y = y.parent;
            y.size++;
        }

        return z;
    }


    public void remove(int key) {
        Node node = search(key);
        if(node != null) {
            remove(node);
        }
    }

    public Node minimum() {
        return minimum(root);
    }

    public Node maximum() {
        return maximum(root);
    }

    protected void transplant(Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;

        if (v != null)
            v.parent = u.parent;
    }

    public void remove(Node z) {
        Node yy  = z;
        while(yy.parent != null) {
            yy = yy.parent;
            yy.size --;
        }
        if (z.left == null)
            transplant(z, z.right);
        else if (z.right == null)
            transplant(z, z.left);
        else {
            Node y = minimum(z.right);


            if (y.parent != z) {

                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
        }
    }


    public Node successor(Node x) {
        if (x.right != null)


            return minimum(x.right);
        else {
            Node y = x.parent;

            while (y != null && x == y.right) {
                x = y;
                y = y.parent;
            }


            if (y == null)
                return null;
            else
                return y;
        }
    }

    public Node predecessor(Node x) {
        if (x.left != null)
            return maximum(x.left);
        else {

            Node y = x.parent;


            while (y != null && x == y.left) {
                x = y;
                y = y.parent;
            }
            if (y == null)
                return null;
            else
                return y;
        }
    }

    public Node minimum(Node x) {

        while (x.left != null)
            x = x.left;

        return x;
    }


    public Node maximum(Node x) {
        // Keep going to the left until finding a node with no right child. That node
        // is the maximum node in x's subtree.
        while (x.right != null)
            x = x.right;

        return x;
    }

    public Node search(int key) {
        Node x = root;

        while (x != null && key != x.key ) {
            if (key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        if (x == null)
            return null;
        else
            return x;
    }


    public void displayTree()
    {
        Stack<Node> globalStack = new Stack<Node>();
        globalStack.push(root);
        int emptyLeaf = 64;
        boolean isRowEmpty = false;
        System.out.println("****............................................................................................................****");
        while(isRowEmpty==false)
        {

            Stack<Node> localStack = new Stack<Node>();
            isRowEmpty = true;
            for(int j=0; j<emptyLeaf; j++)
                System.out.print(' ');
            while(globalStack.isEmpty()==false)
            {
                Node temp = globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.key);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if(temp.left != null ||temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<emptyLeaf*2-2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            System.out.println();
            emptyLeaf /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }
        System.out.println("****............................................................................................................****");
    }
}