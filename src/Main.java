import codArvores.*;
import java.util.Random;
import java.util.Scanner;
import codArvores.*;

public class Main {
    public static void main(String args[]) {
        AVLTree arvoreAVL = new AVLTree();
        RedBlackTree arvoreRBT = new RedBlackTree();

        System.out.println("Escolha o tamanho do array:");
        System.out.println("1 - 30000 valores");
        System.out.println("2 - 60000 valores");
        System.out.println("3 - 100000 valores");
        Scanner sc = new Scanner(System.in);
        int var = sc.nextInt();
        int arrayLength = 0;


            switch (var) {
                case 1:
                    arrayLength = 30000;
                    break;
                case 2:
                    arrayLength = 60000;
                    break;
                case 3:
                    arrayLength = 100000;
                    break;
                default:
                    System.out.println("Valor inválido. O array terá tamanho 30000");
                    arrayLength = 30000;
        }
    
        sc.close();

        int[] array = generateRandomArray(arrayLength);

        long startTime = System.nanoTime();
        insertArrayIntoAVL(array, arvoreAVL);
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1e9;

        System.out.println(arrayLength + " elementos:");
        System.out.println();
        System.out.println("Inserção AVL:");
        System.out.println("Tempo de execução: " + elapsedTime + " segundos");
        System.out.println();
        


        startTime = System.nanoTime();
        insertArrayIntoRBT(array, arvoreRBT);
        endTime = System.nanoTime();
        elapsedTime = (endTime - startTime) / 1e9;
        System.out.println("Inserção RBT");
        System.out.println("Tempo de execução: " + elapsedTime + " segundos");
    

        startTime = System.nanoTime();
        removeFromAVL(array, arvoreAVL);
        endTime = System.nanoTime();
        elapsedTime = (endTime - startTime) / 1e9;
        System.out.println();
        System.out.println("Remoção AVL:");
        System.out.println("Tempo de execução: " + elapsedTime + " segundos");


        startTime = System.nanoTime();
        removeFromRBT(array, arvoreRBT);
        endTime = System.nanoTime();
        elapsedTime = (endTime - startTime) / 1e9;
        System.out.println();
        System.out.println("Remoção RBT");
        System.out.println("Tempo de execução: " + elapsedTime + " segundos");

    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }

        return array;
    }

    public static void insertArrayIntoAVL(int[] array, AVLTree avlTree) {
        for (int element : array) {
            avlTree.insert(element);
        }
    }
    
    public static void removeFromAVL(int[] array, AVLTree avlTree) {
            for (int element : array) {
                avlTree.delete(element);
            }
        
    }

    public static void insertArrayIntoRBT(int[] array, RedBlackTree rblTree) {
        for (int element : array) {
            rblTree.insert(element);
        }
    }

    public static void removeFromRBT(int[] array, RedBlackTree rblTree) {
        for (int element : array) {
            rblTree.delete(element);
        }
    }

}
