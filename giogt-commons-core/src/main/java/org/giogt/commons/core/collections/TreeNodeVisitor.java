package org.giogt.commons.core.collections;


public interface TreeNodeVisitor<T> {

    void visit(TreeNode<T> currentNode);

}
