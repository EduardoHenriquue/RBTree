/**
 * Created by eduardohenrique on 02/06/17.
 */
public class RBOperations {
    static int ZERO = 0;
    private RBTree rbTree;

    public RBOperations(RBTree rbTree) {
        this.rbTree = rbTree;
    }

    /**
     * This method set the new root
     *
     * @param newRoot
     */
    public void setNewRoot(RBElement newRoot){
        rbTree.setRoot(newRoot);
    }

    /**
     * Prints all elements of the tree
     *
     * @param root
     */
    public void rbPrint(RBElement root){
        String elements = "";
        if(!root.isNil()){
            elements += root.getKey()+" ";
            System.out.println(elements);
            rbPrint(root.getLeftChild());
            rbPrint(root.getRightChild());
        }

    }

    /**
     * Prints all nodes of the tree, present these informations:
     * (node parent key, node key, node color, black height, key left child, key right child)
     *
     * @param root
     */
    public void rbCheck(RBElement root){
        String nodesInfo = "";

        if(!root.isNil()){
            nodesInfo += "("
                    + (root.getParent() != null ? root.getParent().getKey() : "NIL") +", "
                    + root.getKey() + ", "
                    + (root.isBlack() ? "preto" : "vermelho") + ", "
                    + blackHeight(root) + ", "
                    + (root.hasLeftChild() ? root.getLeftChild().getKey() : "NIL") +", "
                    + (root.hasRightChild() ? root.getRightChild().getKey() : "NIL")
                    + ")\n";
            System.out.println(nodesInfo);
            rbCheck(root.getLeftChild());
            rbCheck(root.getRightChild());
        }
//        System.out.println(nodesInfo);
    }


    /**
     * This method searches the element with key c.
     * The param x, in the first call, is the root.
     *
     * @param x
     * @param c
     * @return
     * @throws ElementNonexistentException
     */
    public RBElement search(RBElement x, String c) throws ElementNonexistentException{

        if(x.isNil()){
            throw new ElementNonexistentException("There are not elements with this key!");
        } else if(c.compareTo(x.getKey()) == ZERO){
            return x;
        } else {
            if(c.compareTo(x.getKey()) < ZERO){
                return search(x.getLeftChild(), c);
            } else {
                return search(x.getRightChild(), c);
            }
        }
    }

    /**
     * This method inserts the element z into RBTree
     * The param root is the RBTree.root
     *
     * @param root
     * @param z
     */
    public void rbInsert(RBElement root, RBElement z) throws ElementExistingException{

        if(!root.isNil()) {
            if (z.getKey().compareTo(root.getKey()) < ZERO) {
                if (root.hasLeftChild()) {
                    rbInsert(root.getLeftChild(), z);
                } else {
                    root.setLeftChild(z);
                }

            } else if (z.getKey().compareTo(root.getKey()) > ZERO) {
                if (root.hasRightChild()) {
                    rbInsert(root.getRightChild(), z);
                } else {
                    root.setRightChild(z);
                }

            } else { // key already exists, skip
                throw new ElementExistingException("The element already exists!");
            }

            z.setRed();
            rbInsertFixup(root, z); // Call rbInsertFixup()

        } else {
            setNewRoot(z);
        }
    }

    /**
     *
     * @param root
     * @param z
     */
    public void rbInsertFixup(RBElement root, RBElement z){
        RBElement y;

        while (z != root && z.getParent().isRed()) {
            if (z.getParent() == z.getGrandParent().getLeftChild()) {
                y = z.getGrandParent().getRightChild();

                if (!y.isNil() && y.isRed()) {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getGrandParent().setRed();
                    z = z.getGrandParent();
                } else {
                    if (z == z.getParent().getRightChild()) {
                        z = z.getParent();
                        leftRotate(z);
                    }

                    if (z.hasParent()) {
                        z.getParent().setBlack();
                        if (z.hasGrandParent()) {
                            z.getGrandParent().setRed();
                            rightRotate(z.getGrandParent());
                        }
                    }
                }

            } else {
                y = z.getGrandParent().getLeftChild();

                if (!y.isNil() && y.isRed()) {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getGrandParent().setRed();
                    z = z.getGrandParent();
                } else {
                    if (z == z.getParent().getLeftChild()) {
                        z = z.getParent();
                        rightRotate(z);
                    }

                    if (z.hasParent()) {
                        z.getParent().setBlack();
                        if (z.hasGrandParent()) {
                            z.getGrandParent().setRed();
                            leftRotate(z.getGrandParent());
                        }
                    }
                }
            }

        }
        root.setBlack();
    }

    /**
     * Applies the Left Rotate on a given node.
     *
     * precondition: x != null, x.right != null
     * postcondition: rotates x to the left
     *
     * @param x	Node to rotate
     */
    private void leftRotate(RBElement x) {

        RBElement y = x.getRightChild();
        x.setRightChild(y.getLeftChild());

        if (y.hasLeftChild()) {
            y.getLeftChild().setParent(x);
        }

        y.setParent(x.getParent());

        if (!x.hasParent()) {
            setNewRoot(y);
        } else if (x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        } else {
            x.getParent().setRightChild(y);
        }

        y.setLeftChild(x);
        x.setParent(y);
    }

    /**
     * Applies the Right Rotate on a given node.
     *
     * precondition: x != null, x.right != null
     * postcondition: rotates x to the right
     *
     * @param x Node to rotate
     */
    private void rightRotate(RBElement x) {
        RBElement y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());

        if (y.hasRightChild()) {
            y.getRightChild().setParent(x);
        }

        y.setParent(x.getParent());

        if (!x.hasParent()) {
            setNewRoot(y);
        } else if (x == x.getParent().getRightChild()) {
            x.getParent().setRightChild(y);
        } else {
            x.getParent().setLeftChild(y);
        }

        y.setRightChild(x);
        x.setParent(y);
    }

    /**
     * Deletes the integer i from the binary tree, if it's exist.
     *
     * @param z
     * @throws ElementNonexistentException
     */
    public void rbDelete(RBElement z) throws ElementNonexistentException{

        RBElement x;
        RBElement y;
        z = search(z, z.getKey()); // verifies if z exists

        if (z != null){
            if (!z.hasLeftChild() || !z.hasRightChild()) {
                // z doesn't have 2 child nodes
                y = z;
            } else {
                // z has 2 child nodes
                y = successor(z);
            }

            if (y.hasLeftChild()) {
                x = y.getLeftChild();
            } else {
                x = y.getRightChild();
            }

            x.setParent(y.getParent());
            if (this.rbTree.getRoot() == y) {
                setNewRoot(x);
            } else if (y == y.getParent().getLeftChild()) {
                y.getParent().setLeftChild(x);
            } else {
                y.getParent().setRightChild(x);
            }

            // switch values of y and z
            if (y != z) {
                z.setKey(y.getKey());
            }

            if (y.isBlack()) {
                rbDeleteFixup(x);
            }
        } else {
            throw new ElementNonexistentException("The element do not exists in tree!");
        }
    }

    /**
     * Fixes up tree after a rbDelete.
     *
     * @param x	Child node of the deleted node's successor.
     */
    public void rbDeleteFixup(RBElement x){
        RBElement w;

        while (this.rbTree.getRoot() != x && x.isBlack()) {
            if (x == x.getParent().getLeftChild()) {
                w = x.getParent().getRightChild();

                /* Case 1 */
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                }

                /* Case 2 */
                if (w.getLeftChild().isBlack() && w.getRightChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    /* Case 3 */
                    if (w.getRightChild().isBlack()) {
                        w.getLeftChild().setBlack();
                        w.setRed();
                        rightRotate(w);
                        w = x.getParent().getRightChild();
                    }

                    /* Case 4 */
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getRightChild().setBlack();
                    leftRotate(x.getParent());
                    x = this.rbTree.getRoot();
                }
            } else {
                w = x.getParent().getLeftChild();

                /* Case 1 */
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                }

                /* Case 2 */
                if (w.getRightChild().isBlack() && w.getLeftChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    /* Case 3 */
                    if (w.getLeftChild().isBlack()) {
                        w.getRightChild().setBlack();
                        w.setRed();
                        leftRotate(w);
                        w = x.getParent().getLeftChild();
                    }

                    /* Case 4 */
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getLeftChild().setBlack();
                    rightRotate(x.getParent());
                    x = this.rbTree.getRoot();
                }
            }
        }

        x.setBlack();
    }

    /**
     * Returns the successor node for a given node in the tree.
     *
     * @param x
     * @return y
     */
    public RBElement successor(RBElement x){
        RBElement y;
        if (x.hasRightChild()) {
            return treeMinimum(x.getRightChild());
        } else {
            y = x.getParent();
            while (!y.isNil() && x == y.getRightChild()) {
                x = y;
                y = y.getParent();
            }
            return y;
        }
    }

    /**
     * Returns the largest element immediately less than x.
     *
     * @param x
     * @return x
     */
    public RBElement treeMinimum(RBElement x){
        if(x.hasLeftChild()){
            return treeMinimum(x.getLeftChild());
        } else {
            return x;
        }
    }

    /**
     * Returns the node's black depth.
     *
     * @return Black depth of current node
     */
    public int blackHeight(RBElement x) {
        int me = x.isBlack() ? 1 : 0;
        if (x.hasLeftChild()) {
            return me + blackHeight(x.getLeftChild());
        } else {
            return me;
        }
    }

}
