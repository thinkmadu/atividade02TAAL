import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import codArvores.AVLTree;

import java.util.Random;
import java.util.Arrays;

public class PerformanceChart extends ApplicationFrame {

    public PerformanceChart(String applicationTitle, String chartTitle, long[] insertionTimesAVL, long[] deletionTimesAVL, int[] sizes) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Tamanho da Entrada", "Tempo (ns)",
                createDataset(insertionTimesAVL, deletionTimesAVL, sizes),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(long[] insertionTimesAVL, long[] deletionTimesAVL, int[] sizes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < sizes.length; i++) {
            dataset.addValue(insertionTimesAVL[i], "Inserção AVL", Integer.toString(sizes[i]));
            dataset.addValue(deletionTimesAVL[i], "Remoção AVL", Integer.toString(sizes[i]));
        }

        return dataset;
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        // Tamanhos diferentes de entrada
        int[] sizes = {100, 1000, 5000, 10000, 50000};

        // Arrays para armazenar os tempos
        long[] insertionTimesAVL = new long[sizes.length];
        long[] deletionTimesAVL = new long[sizes.length];

        // Testar em diferentes tamanhos de arrays
        for (int i = 0; i < sizes.length; i++) {
            int[] array = generateRandomArray(sizes[i]);

            // Inserção
            insertionTimesAVL[i] = measureInsertionTime(avlTree, array);

            // Remoção
            deletionTimesAVL[i] = measureDeletionTime(avlTree, array);
        }

        // Criar o gráfico e exibi-lo
        PerformanceChart chart = new PerformanceChart(
                "Comparação de Desempenho",
                "Desempenho de Inserção e Remoção em AVL",
                insertionTimesAVL, deletionTimesAVL, sizes);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    // Funções auxiliares para gerar arrays e medir tempos

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000); // Gera números de 0 a 999
        }

        return array;
    }

    public static long measureInsertionTime(AVLTree tree, int[] array) {
        long startTime = System.nanoTime();
        for (int element : array) {
            tree.insert(element);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long measureDeletionTime(AVLTree tree, int[] array) {
        long startTime = System.nanoTime();
        for (int element : array) {
            tree.delete(element);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
