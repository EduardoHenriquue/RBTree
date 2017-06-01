/**
 * Created by eduardohenrique on 31/05/17.
 */
public class RBElement {
    /**
     * Key stored in node (a unique positive integer)
     */
    private String key;

    /**
     * True iff the node is black
     */
    private boolean isBlack;

    /**
     * Pointer to a left child node
     */
    private RBElement leftChild;

    /**
     * Pointer to a right child node
     */
    private RBElement rightChild;

    /**
     * Pointer to the parent node, if one exists
     */
    private RBElement parent;

    /**
     * Creates a new node instance, given a key and color.
     *
     * @param key		Key to store in node
     * @param isBlack	True if node is black
     */
    public RBElement(String key, boolean isBlack) {
        this.key = key;
        this.isBlack = isBlack;

        if (!isNil()) {
            setLeftChild(new RBElement());
            setRightChild(new RBElement());
        }
    }

    /**
     * Creates a new black node, given a key.
     *
     * @param key	Key to store in node
     */
    public RBElement(String key) {
        this(key, true);
    }

    /**
     * Creates a new, empty leaf.
     */
    public RBElement() {
        this(RBTree.NIL, true);
    }

    /**
     * Returns true if the node is an empty leaf.
     *
     * @return True if the node is an empty leaf
     */
    public boolean isNil() {
        return this.key.equals(RBTree.NIL);
    }

    /**
     * @return Pointer to parent node
     */
    public RBElement getParent() {
        return this.parent;
    }

    /**
     * Returns true if the node has a parent.
     *
     * @return True iff the node has a parent
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Returns the pointer to the node's grandparent node
     * (it's parent node's parent node).
     *
     * precondition: getParent() != null
     *
     * @return Pointer to parent of parent node
     */
    public RBElement getGrandParent() {
        return getParent().getParent();
    }

    /**
     * Returns true if node has a grandparent node.
     *
     * @return True if node has a parent node that has a parent node
     */
    public boolean hasGrandParent() {
        return hasParent() && getParent().hasParent();
    }

    /**
     * Sets the node's parent node
     *
     * @param parent Pointer to new parent
     */
    public void setParent(RBElement parent) {
        this.parent = parent;
    }

    /**
     * Returns node's key value.
     *
     * @return Node's key value
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the node's key value.
     *
     * precondition: key > 0
     *
     * @param key New key value
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns true if the node is black.
     *
     * @return True iff the node is black
     */
    public boolean isBlack() {
        return isBlack;
    }

    /**
     * Sets node's color to be black
     */
    public void setBlack() {
        this.isBlack = true;
    }

    /**
     * Sets node's blackness.
     * Accepts True for a black color, and False for red.
     *
     * @param isBlack True for a black node, False for red
     */
    public void setBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }

    /**
     * Returns true if node is red.
     *
     * @return True iff node is red
     */
    public boolean isRed() {
        return !isBlack();
    }

    /**
     * Sets node's color to be red.
     */
    public void setRed() {
        this.isBlack = false;
    }

    /**
     * Returns a pointer to the node's left child.
     *
     * @return Pointer to node's left child
     */
    public RBElement getLeftChild() {
        return leftChild;
    }

    /**
     * Sets node's left child.
     * @param leftChild Pointer to new left child
     */
    public void setLeftChild(RBElement leftChild) {
        this.leftChild = leftChild;

        if (hasLeftChild()) {
            leftChild.setParent(this);
        }
    }

    /**
     * Returns a pointer to the node's right child.
     *
     * @return Pointer to node's right child
     */
    public RBElement getRightChild() {
        return rightChild;
    }

    /**
     * Sets node's right child.
     *
     * @param rightChild Pointer to new right child
     */
    public void setRightChild(RBElement rightChild) {
        this.rightChild = rightChild;

        if (hasRightChild()) {
            rightChild.setParent(this);
        }
    }

    /**
     * Returns true if the node is a leaf.
     * A node is considered a leaf if both it has no
     * children, i.e. both its children are empty leaves.
     *
     * @return True iff node is a leaf
     */
    public boolean isLeaf() {
        return !hasLeftChild() && !hasRightChild();
    }

    /**
     * Returns true if node has a left child,
     * i.e. its left child is a non-nil node.
     *
     * @return True iff node has a left child
     */
    public boolean hasLeftChild() {
        return !leftChild.isNil();
    }

    /**
     * Returns true if node has a right child,
     * i.e. its right child is a non-nil node.
     *
     * @return True iff node has a right child
     */
    public boolean hasRightChild() {
        return !rightChild.isNil();
    }

    /**
     * Returns pointer to node containing a requested key.
     *
     * @param i Key to look up
     * @return Node instance containing i, null if not found
     */
    public RBElement search(String z) {
        if (isNil()) {
            return null;
        }
        else if (getKey().equals(z)) {
            return this;
        } else {
            if (z.compareTo(getKey()) < -1 && hasLeftChild()) {
                return getLeftChild().search(i);
            } else if (hasRightChild()) {
                return getRightChild().search(i);
            }
        }
        return null;
    }

    /**
     * Returns true iff the requested key is contained
     * in the node or its offsprings.
     *
     * @param i Key to look up
     * @return True iff i is contained in node's tree
     */
    public boolean contains(int i) {
        return search(i) != null;
    }

    /**
     * Inserts a new node below this node.
     *
     * @param newNode Node to rbInsert.
     * @return True if node inserted, False if key already existed.
     */
    public boolean insert(RBElement newNode) {
        if (newNode.getKey() < getKey()) {
            if (hasLeftChild()) {
                return getLeftChild().insert(newNode);
            } else {
                setLeftChild(newNode);
                return true;
            }
        } else if (newNode.getKey() > getKey()) {
            if (hasRightChild()) {
                return getRightChild().insert(newNode);
            } else {
                setRightChild(newNode);
                return true;
            }
        } else { // key already exists, skip
            return false;
        }
    }

    /**
     * Returns a pointer to the node containing the smallest key.
     *
     * @return Node of smallest key in the tree
     */
    public RBElement minNode() {
        if (hasLeftChild()) {
            return getLeftChild().minNode();
        } else {
            return this;
        }
    }

    /**
     * Returns the key of minimal node (i.e., minimal key in tree).
     *
     * @return Smallest key in the tree.
     */
    public int minValue() {
        return minNode().getKey();
    }

    /**
     * Returns a pointer to the node containing the largest key.
     *
     * @return Node of largest key in the tree
     */
    public RBElement maxNode() {
        if (hasRightChild()) {
            return getRightChild().maxNode();
        } else {
            return this;
        }
    }

    /**
     * Returns the key of maximal node (i.e., maximal key in tree).
     *
     * @return Largest key in the tree.
     */
    public int maxValue() {
        return maxNode().getKey();
    }

    /**
     * Recursively fill tree's keys in an array.
     *
     * @param arr	Values array to fill with the tree's keys
     * @param loc	Current location in array
     * @return	Array index of last number inserted.
     */
    public int fillIntArray(int[] arr, int loc) {
        if (hasLeftChild()) {
            loc = getLeftChild().fillIntArray(arr, loc);
        }

        arr[loc++] = getKey();

        if (hasRightChild()) {
            loc = getRightChild().fillIntArray(arr, loc);
        }

        return loc;
    }

    /**
     * Returns a string representation of the node and its offsprings.
     */
    public String toString() {
        String leftString  = hasLeftChild() ? getLeftChild().toString() : "x";
        String rightString = hasRightChild() ? getRightChild().toString() : "x";

        return String.format("[ %d%s %s %s ]", getKey(), isBlack() ? "b" : "r", leftString, rightString);
    }

    /**
     * Returns the maximum depth of a node in the tree.
     *
     * @return Maximum depth of a node in the tree.
     */
    public int maxDepth() {
        if (isLeaf()) {
            return 0;
        } else {
            if (hasLeftChild() && hasRightChild()) {
                return 1 + Math.max(getLeftChild().maxDepth(),
                        getRightChild().maxDepth());
            } else if (hasLeftChild()) {
                return 1 + getLeftChild().maxDepth();
            } else { // hasRightChild()
                return 1 + getRightChild().maxDepth();
            }
        }
    }

    /**
     * Returns the minimum depth of a leaf in the tree.
     *
     * @return Minimum depth of a leaf in the tree.
     */
    public int minLeafDepth() {
        if (isLeaf()) {
            return 0;
        } else {
            if (hasLeftChild() && hasRightChild()) {
                return 1 + Math.min(getLeftChild().minLeafDepth(),
                        getRightChild().minLeafDepth());
            } else if (hasLeftChild()) {
                return 1 + getLeftChild().minLeafDepth();
            } else { // hasRightchild()
                return 1 + getRightChild().minLeafDepth();
            }
        }
    }


    /**
     * Returns true if and only if the tree is a valid BST,
     * i.e., every node's key is greater than its left child's key
     * and smaller than its right child's key.
     *
     * @return True iff node is a valid BST.
     */
    public boolean isBSTValid() {
        if (isNil()) {
            return true;
        } else {
            if (hasLeftChild() && getKey() < getLeftChild().getKey()) {
                return false;
            } else if (hasRightChild() && getKey() > getRightChild().getKey()) {
                return false;
            } else {
                return getLeftChild().isBSTValid() &&
                        getRightChild().isBSTValid();
            }
        }
    }

    /**
     * Returns true iff node and its offsprings follow the Red rule,
     * i.e., no red node is followed by another red node.
     *
     * @return True iff node follows the Red rule
     */
    public boolean isRedValid() {
        if (isLeaf()) {
            return true;
        } else {
            if (isBlack()) {
                if (hasLeftChild() && hasRightChild()) {
                    return getLeftChild().isRedValid() && getRightChild().isRedValid();
                } else if (hasLeftChild()) {
                    return getLeftChild().isRedValid();
                } else { // hasRightChild()
                    return getRightChild().isRedValid();
                }
            } else { // isRed()
                if (hasLeftChild() && hasRightChild()) {
                    return getLeftChild().isBlack() && getLeftChild().isRedValid() &&
                            getRightChild().isBlack() && getRightChild().isRedValid();
                } else if (hasLeftChild()) {
                    return getLeftChild().isBlack() && getLeftChild().isRedValid();
                } else { // hasRightChild()
                    return getRightChild().isBlack() && getRightChild().isRedValid();
                }
            }
        }
    }

    /**
     * Returns the node's black depth.
     *
     * @return Black depth of current node
     */
    public int blackDepth() {
        int me = isBlack() ? 1 : 0;
        if (hasLeftChild()) {
            return me + getLeftChild().blackDepth();
        } else {
            return me;
        }
    }

    /**
     * Returns true iff node and its offsprings follow the Black rule,
     * i.e., every path from root to a leaf passes through the same
     * number of black nodes.
     *
     * @return True iff node follows the Black rule
     */
    public boolean isBlackValid() {
        if (isLeaf()) {
            return true;
        } else {
            if (hasRightChild() && hasLeftChild()) {
                return getRightChild().blackDepth() == getLeftChild().blackDepth();
            } else if (hasLeftChild()) {
                return getLeftChild().blackDepth() == 0;
            } else { // hasRightChild()
                return getRightChild().blackDepth() == 0;
            }
        }
    }
}