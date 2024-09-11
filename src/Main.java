import codArvores.*;
import java.util.Random;

public class Main {
    public static void main(String args[]) {
        AVLTree arvoreAVL = new AVLTree();

        int[] array = generateRandomArray(10000);
        insertArrayIntoAVL(array, arvoreAVL);

        System.out.println("Altura da árvore AVL: " + arvoreAVL.getAltura());
        System.out.println("Rotações à esquerda: " + arvoreAVL.getRotacaoEsquerda());
        System.out.println("Rotações à direita: " + arvoreAVL.getRotacaoDireita());

    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);
        }

        return array;
    }

    public static void insertArrayIntoAVL(int[] array, AVLTree avlTree) {
        for (int element : array) {
            avlTree.insert(element);
        }
    }
}
