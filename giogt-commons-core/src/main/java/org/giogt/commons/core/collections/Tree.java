package org.giogt.commons.core.collections;


public interface Tree<T> {

    TreeNode<T> getRoot();

    void preOrderTraversal(TreeNodeVisitor<T> treeNodeVisitor);

    void postOrderTraversal(TreeNodeVisitor<T> treeNodeVisitor);

    // methods to get tree depth, siblings of a node, list of leaves, etc. can be added

}
