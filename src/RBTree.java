
/**
 * Created by eduardohenrique on 30/05/17.
 */
public class RBTree {


    /**
     * Sentinel
     */
    static final String NIL = null;
    static final RBElement T_NIL = new RBElement(NIL, true);

    /**
     * Pointer to root node
     */
    private RBElement root;

    /**
     * Creates a new, empty instance
     */
    public RBTree() {
        this.root = new RBElement(NIL, true);
        this.root.setParent(T_NIL);
    }

    /**
     * Returns pointer to root node
     */
    public RBElement getRoot() {
        return this.root;
    }

    /**
     * Sets root node
     */
    public void setRoot(RBElement root) {
        if (this.root.isNil()) {
            this.root = root;
            root.setLeftChild(new RBElement());
            root.setRightChild(new RBElement());

        } else {
            this.root.setKey(root.getKey());
        }

    }

    /**
     * Returns true if and only if the tree is empty.
     *
     */
    public boolean empty() {
        return root.isNil();
    }

   
}
