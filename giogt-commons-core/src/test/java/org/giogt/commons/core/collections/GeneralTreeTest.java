package org.giogt.commons.core.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class GeneralTreeTest {

    @Test
    public void preOrderTraversal_forOneNode_mustVisitTheNode() {
        String node_1_1 = "1.1";

        GeneralTree<String> tree = new GeneralTree<>(node_1_1);
        List<TreeNode<String>> nodes = preOrderTraversal(tree);

        assertThat(nodes, is(notNullValue()));
        assertThat(nodes.size(), is(1));
        assertThat(nodes.get(0).getValue(), is(node_1_1));
    }

    @Test
    public void preOrderTraversal_for3LevelTree_mustVisitAllNodesPreOrder() {
        String node_1_1 = "1.1";
        String node_2_1 = "2.1";
        String node_2_2 = "2.2";
        String node_3_1 = "3.1";
        String node_3_2 = "3.2";
        String node_3_3 = "3.3";
        String node_3_4 = "3.4";

        GeneralTree<String> tree = new GeneralTree<>(node_1_1);
        TreeNode<String> root = tree.getRoot();
        TreeNode<String> tree_node_2_1 = new TreeNode<>(node_2_1);
        TreeNode<String> tree_node_2_2 = new TreeNode<>(node_2_2);
        root.addChildren(tree_node_2_1, tree_node_2_2);
        tree_node_2_1.addChildren(node_3_1, node_3_2);
        tree_node_2_2.addChildren(node_3_3, node_3_4);

        List<TreeNode<String>> nodes = preOrderTraversal(tree);

        assertThat(nodes, is(notNullValue()));
        assertThat(nodes.size(), is(7));
        assertThat(nodes.get(0).getValue(), is(node_1_1));
        assertThat(nodes.get(1).getValue(), is(node_2_1));
        assertThat(nodes.get(2).getValue(), is(node_3_1));
        assertThat(nodes.get(3).getValue(), is(node_3_2));
        assertThat(nodes.get(4).getValue(), is(node_2_2));
        assertThat(nodes.get(5).getValue(), is(node_3_3));
        assertThat(nodes.get(6).getValue(), is(node_3_4));
    }

    @Test
    public void postOrderTraversal_forOneNode_mustVisitTheNode() {
        String nodeValue = "1.1";

        GeneralTree<String> tree = new GeneralTree<>(nodeValue);
        List<TreeNode<String>> nodes = postOrderTraversal(tree);

        assertThat(nodes, is(notNullValue()));
        assertThat(nodes.size(), is(1));
        assertThat(nodes.get(0).getValue(), is(nodeValue));
    }

    @Test
    public void postOrderTraversal_for3LevelTree_mustVisitAllNodesPostOrder() {
        String node_1_1 = "1.1";
        String node_2_1 = "2.1";
        String node_2_2 = "2.2";
        String node_3_1 = "3.1";
        String node_3_2 = "3.2";
        String node_3_3 = "3.3";
        String node_3_4 = "3.4";

        GeneralTree<String> tree = new GeneralTree<>(node_1_1);
        TreeNode<String> root = tree.getRoot();
        TreeNode<String> tree_node_2_1 = new TreeNode<>(node_2_1);
        TreeNode<String> tree_node_2_2 = new TreeNode<>(node_2_2);
        root.addChildren(tree_node_2_1, tree_node_2_2);
        tree_node_2_1.addChildren(node_3_1, node_3_2);
        tree_node_2_2.addChildren(node_3_3, node_3_4);

        List<TreeNode<String>> nodes = postOrderTraversal(tree);

        assertThat(nodes, is(notNullValue()));
        assertThat(nodes.size(), is(7));
        assertThat(nodes.get(0).getValue(), is(node_3_1));
        assertThat(nodes.get(1).getValue(), is(node_3_2));
        assertThat(nodes.get(2).getValue(), is(node_2_1));
        assertThat(nodes.get(3).getValue(), is(node_3_3));
        assertThat(nodes.get(4).getValue(), is(node_3_4));
        assertThat(nodes.get(5).getValue(), is(node_2_2));
        assertThat(nodes.get(6).getValue(), is(node_1_1));
    }

    private <T> List<TreeNode<T>> preOrderTraversal(Tree<T> tree) {
        final List<TreeNode<T>> nodes = new ArrayList<>();
        tree.preOrderTraversal(currentNode -> nodes.add(currentNode));
        return nodes;
    }

    private <T> List<TreeNode<T>> postOrderTraversal(Tree<T> tree) {
        final List<TreeNode<T>> nodes = new ArrayList<>();
        tree.postOrderTraversal(currentNode -> nodes.add(currentNode));
        return nodes;
    }
}