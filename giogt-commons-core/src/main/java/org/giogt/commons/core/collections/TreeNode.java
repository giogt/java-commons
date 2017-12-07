package org.giogt.commons.core.collections;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> implements Serializable {
    private static final long serialVersionUID = 2015051900000000L;

    private TreeNode<T> parent;

    private T value;

    private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

    public TreeNode() {
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public TreeNode(T value) {
        this.value = value;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public TreeNode<T> getChild(T child) {
        for (TreeNode<T> currentChild : children) {
            if (currentChild.getValue().equals(child)) {
                return currentChild;
            }
        }
        return null;
    }

    public boolean hasChild(T child) {
        for (TreeNode<T> currentChild : children) {
            if (currentChild.getValue().equals(child)) {
                return true;
            }
        }
        return false;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public void addChild(T child) {
        addChild(new TreeNode<T>(child));
    }

    public void addChild(TreeNode<T> child) {
        children.add(child);
        child.setParent(this);
    }

    public void addChildren(T... children) {
        for (T child : children) {
            addChild(child);
        }
    }

    public void addChildren(TreeNode<T>... children) {
        for (TreeNode<T> child : children) {
            addChild(child);
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode<?> treeNode = (TreeNode<?>) o;

        if (parent != null ? !parent.equals(treeNode.parent) : treeNode.parent != null) return false;
        if (children != null ? !children.equals(treeNode.children) : treeNode.children != null) return false;
        return value != null ? value.equals(treeNode.value) : treeNode.value == null;

    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                '}';
    }
}
