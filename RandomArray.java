
// Package

package SortingVisualizer;

// Import statements

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

// RandomArray class

public class RandomArray extends JFrame implements Runnable {

    // Buffer Image to avoid flickering

    BufferedImage buffer;
    Graphics2D bufferGraphics;

    // Threads to execute the sorting algorithms

    Thread t1 = new Thread(this, sortSelected1 + "First");
    Thread t2 = new Thread(this, sortSelected2 + "Second");

    // Delay

    int delay = 330 / (Home.cb1.getSelectedIndex() + 1);

    boolean executed = false;

    // ArrayLists for sorting algorithms

    ArrayList<Integer> rectsFirst = new ArrayList<>();
    ArrayList<Integer> rectsSecond = new ArrayList<>();
    ArrayList<Integer> rects1 = new ArrayList<>();
    ArrayList<Integer> rects2 = new ArrayList<>();
    ArrayList<Integer> rects3 = new ArrayList<>();
    ArrayList<Integer> rects4 = new ArrayList<>();
    ArrayList<Integer> rects5 = new ArrayList<>();

    Random rand = new Random();
    int size = Integer.parseInt(Home.obj.tf.getText());
    int startx = 30;
    int startxx;
    int width, height;

    boolean done1 = false;
    boolean done2 = false;
    boolean sorting = false;
    boolean reset = false;

    // Colors

    Color cola = new Color(167, 144, 192);
    Color cora = new Color(181, 109, 97);
    Color cop = new Color(154, 205, 50);

    // Strings to store the selected sorting algorithms

    static String sortSelected1 = "";
    static String sortSelected2 = "";

    // Collections for determining the already sorted bars

    ArrayList<Integer> sortedRects1 = new ArrayList<>();
    ArrayList<Integer> sortedRects2 = new ArrayList<>();
    ArrayList<Integer> sortedRects3 = new ArrayList<>();

    // Variables to be used for sorting

    boolean merging;
    int l, mid, h, k;
    int comp1, comp2, comp11, comp12, comp21, comp22;
    int pivot, start, end;

    // Sort button

    JButton sort = new JButton("Sort");

    // Constructor

    RandomArray() {

        // Frame initialization

        setUndecorated(true);
        setBackground(Home.c1);
        setSize(Home.maxw, Home.maxh);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        createBufferStrategy(4);

        // Buffered Image initialization

        buffer = new BufferedImage(Home.maxw, Home.maxh, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.createGraphics();

        // On 'sort' button click

        sort.setBounds(Home.maxw / 2, Home.maxh / 108, (int) (Home.maxw / 19.2), Home.maxh / 36);
        add(sort);
        sort.addActionListener(x -> {
            if (!reset) {
                sorting = true;
                sort.setEnabled(false);
                t1.start();
                if (Home.race.isSelected()) t2.start();

                reset = true;
                return;
            }
            try {
                Home.obj.setVisible(true);
            } catch (Exception e) {
                System.out.println();
            }
            setVisible(false);
        });

    }

    // Run method for threads

    public void run() {

        if (Thread.currentThread().getName().startsWith("Bubble sort")) {
            bubbleSort();
            if (Thread.currentThread().getName().endsWith("First")) done1 = true;
            else done2 = true;
        } else if (Thread.currentThread().getName().startsWith("Insertion sort")) {
            insertionSort();
            if (Thread.currentThread().getName().endsWith("First")) done1 = true;
            else done2 = true;
        } else if (Thread.currentThread().getName().startsWith("Selection sort")) {
            selectionSort();
            if (Thread.currentThread().getName().endsWith("First")) done1 = true;
            else done2 = true;

        } else if (Thread.currentThread().getName().startsWith("Merge sort")) {
            if (Thread.currentThread().getName().endsWith("First")) {
                rects4 = rectsFirst;
                mergeSort(rects4, 0, size - 1);
                done1 = true;
            } else {
                rects4 = rectsSecond;
                mergeSort(rects4, 0, size - 1);
                done2 = true;
            }
        } else if (Thread.currentThread().getName().startsWith("Quick sort")) {
            if (Thread.currentThread().getName().endsWith("First")) {
                rects5 = rectsFirst;
                quickSort(rects5, 0, size - 1);
                done1 = true;
            } else {
                rects5 = rectsSecond;
                quickSort(rects5, 0, size - 1);
                done2 = true;
            }
        }

        if (Home.race.isSelected()) {
            if (done1 && done2) {
                paint(getGraphics());
                sort.setText("Reset");
                sort.setEnabled(true);
            }
        } else {
            if (done1) {
                paint(getGraphics());
                sort.setText("Reset");
                sort.setEnabled(true);
            }
        }
    }

    // Bubble sort

    public void bubbleSort() {
        if (Thread.currentThread().getName().endsWith("First")) rects1 = rectsFirst;
        else rects1 = rectsSecond;
        int temp;
        int count = 0;
        for (int i = 0; i < rects1.size() - 1; i++) {
            for (int j = 0; j < rects1.size() - i - 1; j++) {
                if (rects1.get(j) > rects1.get(j + 1)) {
                    temp = rects1.get(j);
                    rects1.set(j, rects1.get(j + 1));
                    rects1.set(j + 1, temp);
                }
                comp1 = j;
                comp2 = j + 1;
                paint(this.getGraphics());
            }
            count++;
            sortedRects1.add(rectsFirst.size() - count);
        }
    }

    // Insertion sort

    public void insertionSort() {
        if (Thread.currentThread().getName().endsWith("First")) rects2 = rectsFirst;
        else rects2 = rectsSecond;
        for (int i = 1; i < rects2.size(); i++) {
            int key = rects2.get(i);
            int j = i - 1;
            while (j >= 0 && rects2.get(j) > key) {
                int temp = rects2.get(j + 1);
                rects2.set(j + 1, rects2.get(j));
                rects2.set(j, temp);
                comp11 = j;
                comp12 = j + 1;
                paint(this.getGraphics());
                j = j - 1;
            }
            rects2.set(j + 1, key);
        }
    }

    // Selection sort

    public void selectionSort() {
        if (Thread.currentThread().getName().endsWith("First")) rects3 = rectsFirst;
        else rects3 = rectsSecond;
        int count = -1;
        for (int i = 0; i < rects3.size() - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < rects3.size(); j++) {
                if (rects3.get(j) < rects3.get(min_idx))
                    min_idx = j;
                comp21 = j;
                comp22 = min_idx;
                paint(this.getGraphics());
            }
            int temp = rects3.get(min_idx);
            rects3.set(min_idx, rects3.get(i));
            rects3.set(i, temp);
            count++;
            sortedRects2.add(count);
        }
    }

    // Merge sort (Recursive division)

    public void mergeSort(ArrayList<Integer> rects, int l, int h) {
        if (l < h) {
            merging = false;
            int mid = (l + h) / 2;
            this.l = l;
            this.mid = mid;
            this.h = h;
            mergeSort(rects, l, mid);
            mergeSort(rects, mid + 1, h);
            merge(rects, l, mid, h);
        }
    }

    // Merge arrays

    public void merge(ArrayList<Integer> rects, int l, int mid, int h) {

        merging = true;

        this.l = l;
        this.mid = mid;
        this.h = h;

        int n1 = mid - l + 1;
        int n2 = h - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = rects.get(l + i);
        for (int j = 0; j < n2; ++j)
            R[j] = rects.get(mid + 1 + j);

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {

            if (L[i] <= R[j]) {
                rects.set(k, L[i]);
                i++;
            } else {
                rects.set(k, R[j]);
                j++;
            }
            this.k = k;
            paint(this.getGraphics());
            k++;

        }

        while (i < n1) {
            rects.set(k, L[i]);
            this.k = k;
            paint(this.getGraphics());
            i++;
            k++;
        }
        while (j < n2) {
            rects.set(k, R[j]);
            this.k = k;
            paint(this.getGraphics());
            j++;
            k++;
        }

    }

    // Quick sort

    public void quickSort(ArrayList<Integer> rects, int lb, int ub) {
        if (lb < ub) {
            int loc = partition(rects, lb, ub);
            quickSort(rects, lb, loc - 1);
            quickSort(rects, loc + 1, ub);
        }
        sortedRects3.add(lb);
    }

    // Partition of array

    public int partition(ArrayList<Integer> rects, int lb, int ub) {

        int temp;

        int pivot = rects.get(lb);
        this.pivot = lb;
        int start = lb;
        int end = ub;

        while (start < end) {
            while (start < rects.size() && pivot >= rects.get(start)) {
                start++;
                this.start = start;
                paint(this.getGraphics());
            }
            while (pivot < rects.get(end)) {
                end--;
                this.end = end;
                paint(this.getGraphics());
            }
            if (start < end) {
                temp = rects.get(start);
                rects.set(start, rects.get(end));
                rects.set(end, temp);
            }
        }

        this.start = -1;
        this.end = -1;

        temp = rects.get(lb);
        rects.set(lb, rects.get(end));
        rects.set(end, temp);
        sortedRects3.add(end);
        paint(this.getGraphics());

        return end;
    }

    // The visualization using paint method

    public void paint(Graphics gg) {

        Graphics2D g = (Graphics2D) bufferGraphics.create();

        // For creating random array

        if (!sorting) {

            if (executed) return;

            gg.setColor(Home.c1);
            gg.fillRect(0, 0, Home.maxw, Home.maxh);

            width = ((Home.maxw - 60) - ((size - 1) * 3)) / size;
            startx = (Home.maxw - (width * size) - (size - 1) * 3) / 2;
            for (int i = 0; i < size; i++) {
                height = rand.nextInt(10, (int) (Home.maxh / 1.1));
                gg.setColor(Home.c2);
                if (!Home.race.isSelected()) {
                    gg.fillRect(startx, Home.maxh - height, width, height);
                    rectsFirst.add(height);
                } else {
                    height /= 2;
                    gg.fillRect(startx, (Home.maxh / 2) - height, width, height);
                    gg.fillRect(startx, Home.maxh - height, width, height);
                    rectsFirst.add(height);
                    rectsSecond.add(height);
                }
                startx += 3 + width;
                try {
                    //noinspection BusyWait
                    Thread.sleep(1500 / size);
                } catch (InterruptedException e) {
                    System.out.println();
                }
            }

            executed = true;

        } else {

            createBufferStrategy(1);

            // For sorting visualization

            // First thread

            if (Thread.currentThread().getName().endsWith("First")) {

                g.setColor(Home.c1);
                if (!Home.race.isSelected()) g.fillRect(0, 0, Home.maxw, Home.maxh);
                else g.fillRect(0, (Home.maxh / 2) + 1, Home.maxw, (Home.maxh / 2) - 1);
                startx = (Home.maxw - (width * size) - (size - 1) * 3) / 2;

                for (int i = 0; i < rectsFirst.size(); i++) {

                    switch (sortSelected1) {
                        case "Bubble sort" -> {
                            if (i == comp1 || i == comp2) g.setColor(Home.c3);
                            else if (!sortedRects1.contains(i)) g.setColor(Home.c2);
                            else g.setColor(cola);
                            if (done1) g.setColor(Home.c2);
                            rectsFirst = rects1;
                        }
                        case "Insertion sort" -> {
                            if (i == comp11 || i == comp12) g.setColor(Home.c3);
                            else g.setColor(Home.c2);
                            if (done1) g.setColor(Home.c2);
                            rectsFirst = rects2;
                        }
                        case "Selection sort" -> {
                            if (i == comp21 || i == comp22) g.setColor(Home.c3);
                            else if (!sortedRects2.contains(i)) g.setColor(Home.c2);
                            else g.setColor(cola);
                            if (done1) g.setColor(Home.c2);
                            rectsFirst = rects3;
                        }
                        case "Merge sort" -> {
                            if (merging) {
                                if (i >= l && i <= mid) g.setColor(cola);
                                else if (i > mid && i <= h) g.setColor(cora);
                                else g.setColor(Home.c2);
                            }
                            if (i == k) g.setColor(Home.c3);
                            if (done1) g.setColor(Home.c2);
                            rectsFirst = rects4;
                        }
                        default -> {
                            if (i == start) g.setColor(cola);
                            else if (i == end) g.setColor(cora);
                            else g.setColor(Home.c2);
                            if (i == pivot) g.setColor(cop);
                            if (sortedRects3.contains(i)) g.setColor(Home.c3);
                            if (done1) g.setColor(Home.c2);
                            rectsFirst = rects5;
                        }
                    }

                    g.fillRect(startx, Home.maxh - rectsFirst.get(i), width, rectsFirst.get(i));
                    startx += 3 + width;
                }
                gg.drawImage(buffer, 0, 0, this);

                if (done2) {
                    g.setColor(Home.c1);
                    g.fillRect(0, 0, Home.maxw, Home.maxh / 2);
                    startxx = (Home.maxw - (width * size) - (size - 1) * 3) / 2;
                    for (int ii : rectsSecond) {
                        g.setColor(Home.c2);
                        g.fillRect(startxx, (Home.maxh / 2) - ii, width, ii);
                        startxx += 3 + width;
                    }
                    gg.drawImage(buffer, 0, 0, this);
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    System.out.println();
                }
                return;
            }

            if (Home.race.isSelected()) {

                // Second thread

                if (Thread.currentThread().getName().endsWith("Second")) {

                    g.setColor(Home.c1);
                    g.fillRect(0, 0, Home.maxw, Home.maxh / 2);
                    startxx = (Home.maxw - (width * size) - (size - 1) * 3) / 2;
                    for (int i = 0; i < rectsFirst.size(); i++) {

                        switch (sortSelected2) {
                            case "Bubble sort" -> {
                                if (i == comp1 || i == comp2) g.setColor(Home.c3);
                                else if (!sortedRects1.contains(i)) g.setColor(Home.c2);
                                else g.setColor(cola);
                                if (done2) g.setColor(Home.c2);
                                rectsSecond = rects1;
                            }
                            case "Insertion sort" -> {
                                if (i == comp11 || i == comp12) g.setColor(Home.c3);
                                else g.setColor(Home.c2);
                                if (done2) g.setColor(Home.c2);
                                rectsSecond = rects2;
                            }
                            case "Selection sort" -> {
                                if (i == comp21 || i == comp22) g.setColor(Home.c3);
                                else if (!sortedRects2.contains(i)) g.setColor(Home.c2);
                                else g.setColor(cola);
                                if (done2) g.setColor(Home.c2);
                                rectsSecond = rects3;
                            }
                            case "Merge sort" -> {
                                if (merging) {
                                    if (i >= l && i <= mid) g.setColor(cola);
                                    else if (i > mid && i <= h) g.setColor(cora);
                                    else g.setColor(Home.c2);
                                }
                                if (i == k) g.setColor(Home.c3);
                                if (done2) g.setColor(Home.c2);
                                rectsSecond = rects4;
                            }
                            default -> {
                                if (i == start) g.setColor(cola);
                                else if (i == end) g.setColor(cora);
                                else g.setColor(Home.c2);
                                if (i == pivot) g.setColor(cop);
                                if (sortedRects3.contains(i)) g.setColor(Home.c3);
                                if (done2) g.setColor(Home.c2);
                                rectsSecond = rects5;
                            }
                        }

                        g.fillRect(startxx, (Home.maxh / 2) - rectsSecond.get(i), width, rectsSecond.get(i));
                        startxx += 3 + width;
                    }
                    gg.drawImage(buffer, 0, 0, this);

                    if (done1) {
                        g.setColor(Home.c1);
                        g.fillRect(0, (Home.maxh / 2) + 1, Home.maxw, (Home.maxh / 2) - 1);
                        startx = (Home.maxw - (width * size) - (size - 1) * 3) / 2;
                        for (int ii : rectsFirst) {
                            g.setColor(Home.c2);
                            g.fillRect(startx, Home.maxh - ii, width, ii);
                            startx += 3 + width;
                        }
                        gg.drawImage(buffer, 0, 0, this);
                    }

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        System.out.println();
                    }
                }
            }
        }
    }
}