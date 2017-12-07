package org.giogt.commons.core.collections;


import java.io.Serializable;

public class GeneralTree<T> implements Tree<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private TreeNode<T> root;

    public GeneralTree(T root) {
        this(new TreeNode<>(root));
    }

    public GeneralTree(TreeNode<T> root) {
        this.root = root;
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public void preOrderTraversal(TreeNodeVisitor<T> treeNodeVisitor) {
        doPreOrderTraversal(root, treeNodeVisitor);
    }

    void doPreOrderTraversal(
            TreeNode<T> currentNode,
            TreeNodeVisitor<T> treeNodeVisitor) {

        treeNodeVisitor.visit(currentNode);
        for (TreeNode<T> child : currentNode.getChildren()) {
            doPreOrderTraversal(child, treeNodeVisitor);
        }
    }

    @Override
    public void postOrderTraversal(TreeNodeVisitor<T> treeNodeVisitor) {
        doPostOrderTraversal(root, treeNodeVisitor);
    }

    void doPostOrderTraversal(
            TreeNode<T> currentNode,
            TreeNodeVisitor<T> treeNodeVisitor) {

        for (TreeNode<T> child : currentNode.getChildren()) {
            doPostOrderTraversal(child, treeNodeVisitor);
        }
        treeNodeVisitor.visit(currentNode);
    }
}
