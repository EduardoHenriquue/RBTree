
/**
 * Created by eduardohenrique on 30/05/17.
 */
public class RBTree {

    /**
     * Constant marking a key as that of an empty leaf
     */
    static final String NIL = null;

    /**
     * Constant returned when requesting a min / max value when tree is empty
     */
    static final int EmptyMinMaxValue = -1;

    /**
     * Pointer to root node
     */
    private RBElement root;

    /**
     * Current size of tree (number of non-nil nodes)
     */
    private int size;

    /**
     * Current smallest key in the tree.
     */
    private int min;

    /**
     * Current greatest key in the tree.
     */
    private int max;

    /**
     * Creates a new, empty instance
     */
    public RBTree() {
        this.root = new RBElement(RBTree.NIL);
        this.size = 0;
        this.min = EmptyMinMaxValue;
        this.max = EmptyMinMaxValue;
    }

    /**
     * Returns pointer to root node
     */
    private RBElement getRoot() {
        return this.root;
    }

    /**
     * Sets root node
     */
    private void setRoot(RBElement root) {
        this.root = root;
    }

    /**
     * Returns true if and only if the tree is empty.
     *
     * Time complexity: O(1)
     *
     * postcondition: return true iff the data structure
     * does not contain any item
     */
    public boolean empty() {
        return root.isNil();
    }

    /**
     * Returns true if and only if the tree contains i.
     *
     * Time complexity: O(logn)
     *
     * postcondition: returns true iff i is in the tree
     */
    public boolean contains(int i) {
        if (!empty()) {
            return root.contains(i);
        } else {
            return false;
        }
    }

    /**
     * Inserts the integer i into the binary tree; the tree
     * must remain valid (keep its invariants).
     *
     * Time complexity: O(logn)
     *
     * precondition:  none
     * postcondition: contains(i) == true (that is, i is in the list)
     */
    public void rbInsert(RBElement z) {
        /*
        Tratar se o elemento já foi inserido
        usar o element.search(indice)
         */


        if (empty()) {
            // first node in tree
            setRoot(z);
            this.min = z.getKey();
            this.max = z.getKey();
        } else {
            rbInsertFixup(z);

            // maintain min and max properties
            if (this.min > z.getKey()) {
                this.min = z.getKey();
            }
            if (this.max < z.getKey()) {
                this.max = z.getKey();
            }
        }

        // maintain size property
        this.size++;
    }

    /**
     * Inserts a node to a Red-Black tree in a valid way.
     * Based on the RB-Insert and RB-Insert-Fixup algorithms.
     *
     * @param newNode New node to rbInsert
     */
    private void rbInsertFixup(RBElement newNode) {
        RBElement y;

        if (getRoot().insert(newNode)) {
            newNode.setRed();

            while (newNode != getRoot() && newNode.getParent().isRed()) {
                if (newNode.getParent() == newNode.getGrandParent().getLeftChild()) {
                    y = newNode.getGrandParent().getRightChild();

                    if (!y.isNil() && y.isRed()) {
                        newNode.getParent().setBlack();
                        y.setBlack();
                        newNode.getGrandParent().setRed();
                        newNode = newNode.getGrandParent();
                    } else {
                        if (newNode == newNode.getParent().getRightChild()) {
                            newNode = newNode.getParent();
                            leftRotate(newNode);
                        }

                        if (newNode.hasParent()) {
                            newNode.getParent().setBlack();
                            if (newNode.hasGrandParent()) {
                                newNode.getGrandParent().setRed();
                                rightRotate(newNode.getGrandParent());
                            }
                        }
                    }

                } else {
                    y = newNode.getGrandParent().getLeftChild();

                    if (!y.isNil() && y.isRed()) {
                        newNode.getParent().setBlack();
                        y.setBlack();
                        newNode.getGrandParent().setRed();
                        newNode = newNode.getGrandParent();
                    } else {
                        if (newNode == newNode.getParent().getLeftChild()) {
                            newNode = newNode.getParent();
                            rightRotate(newNode);
                        }

                        if (newNode.hasParent()) {
                            newNode.getParent().setBlack();
                            if (newNode.hasGrandParent()) {
                                newNode.getGrandParent().setRed();
                                leftRotate(newNode.getGrandParent());
                            }
                        }
                    }
                }

            }

            getRoot().setBlack();
        }
    }

    /**
     * Deletes the integer i from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     *
     * Time complexity: O(logn)
     *
     * precondition:  none
     * postcondition: contains(i) == false (that is, i is in the list)
     */
    public void rbDelete(RBElement z) {
        // Busca o i, mas deveria atribuir z a um y
        RBElement z = getRoot().search(i);

        if (z == null) { // i was not found
            return;
        } else {
            RBElement x, y;

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
            if (getRoot() == y) {
                setRoot(x);
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
                deleteFixup(x);
            }

            // maintain size property
            this.size--;

            // maintain min and max properties
            if (this.size == 0) {
                this.min = EmptyMinMaxValue;
                this.max = EmptyMinMaxValue;
            } else {
                if (this.min == i) {
                    this.min = getRoot().minValue();
                }
                if (this.max == i) {
                    this.max = getRoot().maxValue();
                }
            }
        }
    }

    /**
     * Fixes up tree after a rbDelete action.
     * Based on the RB-Insert algorithm.
     *
     * precondition: x != null
     * postcondition: isValid() == true
     *
     * @param x	Child node of the deleted node's successor.
     */
    private void deleteFixup(RBElement x) {
        RBElement w;

        while (getRoot() != x && x.isBlack()) {
            if (x == x.getParent().getLeftChild()) {
                w = x.getParent().getRightChild();

                // Case 1
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                }

                // Case 2
                if (w.getLeftChild().isBlack() && w.getRightChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    // Case 3
                    if (w.getRightChild().isBlack()) {
                        w.getLeftChild().setBlack();
                        w.setRed();
                        rightRotate(w);
                        w = x.getParent().getRightChild();
                    }

                    // Case 4
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getRightChild().setBlack();
                    leftRotate(x.getParent());
                    x = getRoot();
                }
            } else {
                w = x.getParent().getLeftChild();

                // Case 1
                if (w.isRed()) {
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                }

                // Case 2
                if (w.getRightChild().isBlack() && w.getLeftChild().isBlack()) {
                    w.setRed();
                    x = x.getParent();
                } else {
                    // Case 3
                    if (w.getLeftChild().isBlack()) {
                        w.getRightChild().setBlack();
                        w.setRed();
                        leftRotate(w);
                        w = x.getParent().getLeftChild();
                    }

                    // Case 4
                    w.setBlack(x.getParent().isBlack());
                    x.getParent().setBlack();
                    w.getLeftChild().setBlack();
                    rightRotate(x.getParent());
                    x = getRoot();
                }
            }
        }

        x.setBlack();
    }

    /**
     * Returns the successor node for a given node in the tree.
     * Successor being the node with the smallest key greater
     * than x.getKey().
     *
     * precondition: x != null
     * precondition: x.hasLeftChild() && x.hasRightChild()
     *
     * @param x Node to find the successor of
     */
    private RBElement successor(RBElement x) {
        if (x.hasRightChild()) {
            return x.getRightChild().minNode();
        } else {
            RBElement y = x.getParent();
            while (!y.isNil() && x == y.getRightChild()) {
                x = y;
                y = y.getParent();
            }
            return y;
        }
    }

    /**
     * Returns the smallest key in the tree. If the tree
     * is empty, returns -1;
     *
     * Time complexity: O(1)
     *
     * @return Smallest key in tree, -1 if tree is empty
     */
    public int min() {
        return this.min;
    }

    /**
     * Returns the largest key in the tree. If the tree
     * is empty, returns -1;
     *
     * Time complexity: O(1)
     *
     * @return Largest key in tree, -1 if tree is empty
     */
    public int max() {
        return this.max;
    }

    /**
     * Returns an int[] array containing the values stored in the tree,
     * in ascending order.
     *
     * Time complexity: O(n)
     *
     * postcondition: returns an array containing exactly the tree's elements in
     *                 ascending order.
     *
     * @return An array of the tree's key values, sorted in ascending order.
     */
    public int[] toIntArray() {
        int[] arr = new int[size()];
        getRoot().fillIntArray(arr, 0);
        return arr;
    }

    /**
     * Returns true if and only if the tree is a valid red-black tree.
     *
     * Time complexity: O(n)
     *
     * @return True iff the tree is a valid Red-Black tree.
     */
    public boolean isValid() {
        if (root.isNil()) {
            return true;
        } else {
            return getRoot().isBSTValid() &&
                    getRoot().isBlackValid() &&
                    getRoot().isRedValid();
        }
    }

    /**
     * Returns the maximum depth of a node in the tree. If the tree
     * is empty, returns -1;
     *
     * Time complexity: O(n)
     *
     * @return Maximum depth of a node in the tree, -1 if tree is empty
     */
    public int maxDepth() {
        if (empty()) {
            return -1;
        } else {
            return getRoot().maxDepth();
        }
    }

    /**
     * Returns the minimum depth of a leaf in the tree. If the tree
     * is empty, returns -1;
     *
     * Time complexity: O(n)
     *
     * @return Minimum depth of a leaf in the tree, -1 if tree is empty
     */
    public int minLeafDepth() {
        if (empty()) {
            return -1;
        } else {
            return getRoot().minLeafDepth();
        }
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * Time complexity: O(1)
     *
     * @return Number of nodes in the tree
     */
    public int size() {
        return size;
    }


    /**
     * Returns a string representation of the tree.
     *
     * @return String represntatino of the tree
     */
    public String toString() {
        if (!empty()) {
            return String.format("<Tree %s>", root);
        } else {
            return "<Tree empty>";
        }
    }

    /**
     * Applies the Left Rotate action on a given node.
     * Based on the LeftRotate algorithm.
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
            setRoot(y);
        } else if (x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        } else {
            x.getParent().setRightChild(y);
        }

        y.setLeftChild(x);
        x.setParent(y);
    }

    /**
     * Applies the Right Rotate action on a given node.
     * Based on the RightRotate algorithm.
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
            setRoot(y);
        } else if (x == x.getParent().getRightChild()) {
            x.getParent().setRightChild(y);
        } else {
            x.getParent().setLeftChild(y);
        }

        y.setRightChild(x);
        x.setParent(y);
    }

    /**
     * public class RBElement
     *
     * If you wish to implement classes other than RBTree
     * (for example RBElement), do it in this file, not in
     * another file
     *
     */


    /**
     * @original author Shai Vardi
     * Modified for semester 2011/2012 a
     */

}
