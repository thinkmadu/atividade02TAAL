package codArvores;

import absClass.*;
import java.util.LinkedList;
import java.util.Queue;
// import mathUtils;

public class AVLTree extends AbstractSelfBalancingBinarySearchTree {
    @Override
    public Node insert(int element) {
        Node newNode = super.insert(element);
        rebalance((AVLNode) newNode);
        return newNode;
    }

    @Override
    public Node delete(int element) {
        Node deleteNode = super.search(element);
        if (deleteNode != null) {
            Node successorNode = super.delete(deleteNode);
            if (successorNode != null) {
                // if replaced from getMinimum(deleteNode.right) then come back there and update
                // heights
                AVLNode minimum = successorNode.right != null ? (AVLNode) getMinimum(successorNode.right)
                        : (AVLNode) successorNode;
                recomputeHeight(minimum);
                rebalance(minimum);
            } else {
                recomputeHeight((AVLNode) deleteNode.parent);
                rebalance((AVLNode) deleteNode.parent);
            }
            return successorNode;
        }
        return null;
    }

    private int countRotacaoEsquerda = 0;
    private int countRotacaoDireita = 0;
    private int countDuplaRotacaoDireitaEsquerda = 0;
    private int countDuplaRotacaoEsquerdaDireita = 0;

    @Override
    protected Node createNode(int value, Node parent, Node left, Node right) {
        return new AVLNode(value, parent, left, right);
    }

    private void rebalance(AVLNode node) {
        while (node != null) {

            Node parent = node.parent;

            int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
            int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
            int nodeBalance = rightHeight - leftHeight;
            // rebalance (-2 means left subtree outgrow, 2 means right subtree)
            if (nodeBalance == 2) {
                if (node.right.right != null) {
                    node = (AVLNode) avlRotateLeft(node);
                    break;
                } else {
                    node = (AVLNode) doubleRotateRightLeft(node);
                    break;
                }
            } else if (nodeBalance == -2) {
                if (node.left.left != null) {
                    node = (AVLNode) avlRotateRight(node);
                    break;
                } else {
                    node = (AVLNode) doubleRotateLeftRight(node);
                    break;
                }
            } else {
                updateHeight(node);
            }

            node = (AVLNode) parent;
        }
    }

    private Node avlRotateLeft(Node node) {
        Node temp = super.rotateLeft(node);

        updateHeight((AVLNode) temp.left);
        updateHeight((AVLNode) temp);
        countRotacaoEsquerda++; // alteração para contar rotações
        return temp;
    }

    private Node avlRotateRight(Node node) {
        Node temp = super.rotateRight(node);

        updateHeight((AVLNode) temp.right);
        updateHeight((AVLNode) temp);
        countRotacaoDireita++; // alteração para contar rotações
        return temp;
    }

    protected Node doubleRotateRightLeft(Node node) {
        node.right = avlRotateRight(node.right);
        countDuplaRotacaoDireitaEsquerda++; // alteração para contar rotações
        return avlRotateLeft(node);
    }

    protected Node doubleRotateLeftRight(Node node) {
        node.left = avlRotateLeft(node.left);
        countDuplaRotacaoEsquerdaDireita++; // alteração para contar rotações
        return avlRotateRight(node);
    }

    private void recomputeHeight(AVLNode node) {
        while (node != null) {
            node.height = maxHeight((AVLNode) node.left, (AVLNode) node.right) + 1;
            node = (AVLNode) node.parent;
        }
    }

    private int maxHeight(AVLNode node1, AVLNode node2) {
        if (node1 != null && node2 != null) {
            return Math.max(node1.height, node2.height);
        } else if (node1 == null) {
            return node2 != null ? node2.height : -1;
        } else if (node2 == null) {
            return node1.height;
        }
        return -1;
    }

    private static final void updateHeight(AVLNode node) {
        int leftHeight = (node.left == null) ? -1 : ((AVLNode) node.left).height;
        int rightHeight = (node.right == null) ? -1 : ((AVLNode) node.right).height;
        node.height = 1 + mathUtils.getMax(leftHeight, rightHeight);
    }

    protected static class AVLNode extends Node {
        public int height;

        public AVLNode(int value, Node parent, Node left, Node right) {
            super(value, parent, left, right);
        }
    }

    // gets para pegar as rotações
    public int getRotacaoEsquerda() {
        return countRotacaoEsquerda;
    }

    public int getRotacaoDireita() {
        return countRotacaoDireita;
    }

    public int getDuplaRotacaoDireitaEsquerda() {
        return countDuplaRotacaoDireitaEsquerda;
    }

    public int getDuplaRotacaoEsquerdaDireita() {
        return countDuplaRotacaoEsquerdaDireita;
    }

    public int getAltura() {
        return getAltura((AVLNode) root);
    }

    private int getAltura(AVLNode node) {
        if (node == null) {
            return -1;
        }

        int altura = 0;
        Queue<AVLNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            int size = queue.size();
            altura++;

            for (int i = 0; i < size; i++) {
                AVLNode currentNode = queue.poll();

                if (currentNode.left != null) {
                    queue.add((AVLNode) currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add((AVLNode) currentNode.right);
                }
            }
        }
        return altura;
    }
}
