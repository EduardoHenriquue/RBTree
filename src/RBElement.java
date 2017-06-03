/**
 * Created by eduardohenrique on 01/06/17.
 */
public class RBElement {

    private String key;
    private RBElement leftChild;
    private RBElement rightChild;
    private RBElement parent;
    private boolean isBlack;

    /**
     * Creates a new node instance, given a key and color.
     *
     * @param key
     * @param isBlack
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


    public boolean isNil() {
        return this.key == null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    /*  -------------------    Set and get children    -----------------  */

    public RBElement getLeftChild() {
        return leftChild;
    }

    /**
     * Sets node's left child.
     * @param leftChild Pointer to new left child
     */
    public void setLeftChild(RBElement leftChild) {
        this.leftChild = leftChild;

        if (this.hasLeftChild()) {
            leftChild.setParent(this);
        }
    }

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

        if (this.hasRightChild()) {
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
        return !this.hasLeftChild() && !this.hasRightChild();
    }

    /**
     * Returns true if node has a left child,
     * i.e. its left child is a non-nil node.
     *
     * @return True iff node has a left child
     */
    public boolean hasLeftChild() {
        return !this.leftChild.isNil();
    }

    /**
     * Returns true if node has a right child,
     * i.e. its right child is a non-nil node.
     *
     * @return True iff node has a right child
     */
    public boolean hasRightChild() {
        return !this.rightChild.isNil();
    }


    /*  ------------------- Set and get Parent and GrandParent -----------------  */

    /**
     * @return Pointer to parent node
     */
    public RBElement getParent() {
        return this.parent == null ? RBTree.T_NIL : this.parent;
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
        return this.getParent().getParent();
    }

    /**
     * Returns true if node has a grandparent node.
     *
     * @return True if node has a parent node that has a parent node
     */
    public boolean hasGrandParent() {
        return this.hasParent() && this.getParent().hasParent();
    }


    /*  ------------------- Set and get isBlack -----------------  */

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
}
