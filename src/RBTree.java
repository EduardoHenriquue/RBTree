
/**
 * Created by eduardohenrique on 30/05/17.
 */
public class RBTree {


    /**
     * Sentinel
     */
    static final String NIL = "NIL";

    /**
     * Pointer to root node
     */
    private RBElement root;

    /**
     * Creates a new, empty instance
     */
    public RBTree() {
        this.root = new RBElement(RBTree.NIL, true);
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
        this.root = root;
    }

    /**
     * Returns true if and only if the tree is empty.
     *
     */
    public boolean empty() {
        return root.isNil();
    }

   
}
