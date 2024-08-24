
// Package

package SortingVisualizer;

// Import statements

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Home class

public class Home extends JFrame {

    JLabel background = new JLabel();

    boolean clicked = false;

    ColorCombination cc;

    // Dimensions of the system screen

    static int maxw = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static int maxh = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    static Home obj;

    // Theme colors

    static Color c1;
    static Color c2;
    static Color c3;

    Font f = new Font("Arial", Font.BOLD, 20);

    // Constraints' initialization components

    JLabel soa = new JLabel("Size of Array");
    JTextField tf = new JTextField();

    static Integer[] speeds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    JLabel speed = new JLabel("Select speed");
    static JComboBox<Integer> cb1 = new JComboBox<>(speeds);
    static JCheckBox race = new JCheckBox("Race");

    static String[] themes = {"Red", "Green", "Blue", "Brown", "Black"};
    JLabel st = new JLabel("Select theme");
    static JComboBox<String> cb2 = new JComboBox<>(themes);

    JRadioButton rb1 = new JRadioButton("Bubble sort");
    JRadioButton rb2 = new JRadioButton("Insertion sort");
    JRadioButton rb3 = new JRadioButton("Selection sort");
    JRadioButton rb4 = new JRadioButton("Merge sort");
    JRadioButton rb5 = new JRadioButton("Quick sort");

    // Method for positioning of radio buttons and their listeners

    public void setPosition(JRadioButton b, double mul) {
        add(b);
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setVerticalAlignment(SwingConstants.CENTER);
        b.setFont(f);
        b.setBackground(c1);
        b.setForeground(Color.white);
        int i = 0;
        try {
            i = (int) (((double) maxh / 10) * mul);
        } catch (NumberFormatException e) {
            System.out.println();
        }
        b.setBounds((int) (maxw / 2.23), i, (int) (maxw / 9.6), 50);
    }

    int selectedCount;
    String sorts = "";

    JButton generate = new JButton("Generate");

    Home() {

        // Frame initialization

        setUndecorated(true);
        setVisible(true);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        setSize(maxw, maxh);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Adding background of home page

        add(background);
        background.setBounds(0, 0, maxw, maxh);
        background.setIcon(new ImageIcon("src//SortingVisualizer//svb.jpg"));

        // Adding the components on any key pressed

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                if (!clicked) {

                    // Removing background on any key pressed

                    remove(background);

                    // Initial value of constraints

                    tf.setText("20");
                    cb1.setSelectedItem(1);
                    cb2.setSelectedItem("Black");
                    cc = new ColorCombination(new Color(15, 15, 15), new Color(246, 237, 227), new Color(230, 193, 122));
                    rb1.setSelected(true);

                    getContentPane().setBackground(c1);

                    // 'Size of array' label

                    add(soa);
                    soa.setHorizontalAlignment(SwingConstants.CENTER);
                    soa.setVerticalAlignment(SwingConstants.CENTER);
                    soa.setFont(f);
                    soa.setBounds((int) (maxw / 2.56), maxh / 18, (int) (maxw / 9.6), (int) (maxh / 21.6));
                    soa.setForeground(Color.white);

                    // 'Size of array' text field

                    add(tf);
                    tf.setHorizontalAlignment(SwingConstants.CENTER);
                    tf.setFont(f);
                    tf.setBounds(maxw / 2, maxh / 18, (int) (maxw / 12.8), (int) (maxh / 21.6));
                    tf.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            if (!Character.isDigit(e.getKeyChar())) {
                                e.consume();
                            }
                        }
                    });

                    // 'Select speed' label

                    add(speed);
                    speed.setHorizontalAlignment(SwingConstants.CENTER);
                    speed.setVerticalAlignment(SwingConstants.CENTER);
                    speed.setFont(f);
                    speed.setBounds((int) (maxw / 2.56), (int) (maxh / 5.7), (int) (maxw / 9.6), (int) (maxh / 21.6));
                    speed.setForeground(Color.white);

                    // Speeds' combo box

                    add(cb1);
                    cb1.setFont(f);
                    cb1.setBounds(maxw / 2, (int) (maxh / 5.7), (int) (maxw / 9.6), (int) (maxh / 21.6));

                    // Race checkbox

                    add(race);
                    race.setBounds((int) (maxw / 1.63), (int) (maxh / 5.7), (maxw / 20), (int) (maxh / 21.6));
                    race.setBackground(c1);
                    race.setForeground(Color.white);
                    race.setFont(f);

                    // 'Select theme' label

                    add(st);
                    st.setHorizontalAlignment(SwingConstants.CENTER);
                    st.setVerticalAlignment(SwingConstants.CENTER);
                    st.setFont(f);
                    st.setBounds((int) (maxw / 2.56), (int) (maxh / 3.375), (int) (maxw / 9.6), (int) (maxh / 21.6));
                    st.setForeground(Color.white);

                    // Themes' combo box

                    add(cb2);
                    cb2.setFont(f);
                    cb2.setBounds(maxw / 2, (int) (maxh / 3.375), (int) (maxw / 9.6), (int) (maxh / 21.6));

                    // Setting theme on theme selected

                    cb2.addItemListener(x -> {
                        String s = cb2.getSelectedItem() + "";

                        // Red theme

                        if (s.equals("Red")) {
                            cc = new ColorCombination(new Color(80, 0, 0), new Color(251, 234, 231), new Color(255, 140, 0));
                        }

                        // Green theme

                        if (s.equals("Green")) {
                            cc = new ColorCombination(new Color(19, 48, 40), new Color(237, 244, 242), new Color(124, 131, 99));
                        }

                        // Blue theme

                        if (s.equals("Blue")) {
                            cc = new ColorCombination(new Color(7, 21, 55), new Color(202, 220, 252), new Color(138, 182, 249));
                        }

                        // Brown theme

                        if (s.equals("Brown")) {
                            cc = new ColorCombination(new Color(43, 23, 0), new Color(251, 234, 231), new Color(178, 69, 110));
                        }

                        // Black theme

                        if (s.equals("Black")) {
                            cc = new ColorCombination(new Color(15, 15, 15), new Color(246, 237, 227), new Color(230, 193, 122));
                        }

                        getContentPane().setBackground(c1);
                        race.setBackground(c1);
                        rb1.setBackground(c1);
                        rb2.setBackground(c1);
                        rb3.setBackground(c1);
                        rb4.setBackground(c1);
                        rb5.setBackground(c1);
                    });

                    // Positioning radio buttons used for sorting techniques

                    setPosition(rb1, 4);
                    setPosition(rb2, 4.9);
                    setPosition(rb3, 5.8);
                    setPosition(rb4, 6.7);
                    setPosition(rb5, 7.6);

                    // 'Generate' button

                    add(generate);
                    generate.setHorizontalAlignment(SwingConstants.CENTER);
                    generate.setVerticalAlignment(SwingConstants.CENTER);
                    generate.setFont(f);
                    generate.setBounds((int) (maxw / 2.37), (int) (maxh / 1.17), (int) (maxw / 6.4), (int) (maxh / 21.6));

                    // On 'generate' button click

                    generate.addActionListener(x -> {
                        if (rb1.isSelected()) {
                            sorts += rb1.getText();
                            selectedCount++;
                        }
                        if (rb2.isSelected()) {
                            sorts += rb2.getText();
                            selectedCount++;
                        }
                        if (rb3.isSelected()) {
                            sorts += rb3.getText();
                            selectedCount++;
                        }
                        if (rb4.isSelected()) {
                            sorts += rb4.getText();
                            selectedCount++;
                        }
                        if (rb5.isSelected()) {
                            sorts += rb5.getText();
                            selectedCount++;
                        }
                        if (selectedCount != 1 && !race.isSelected()) {
                            JOptionPane.showMessageDialog(null, "Please select exactly 1 sorting algorithm");
                            sorts = "";
                            selectedCount = 0;
                            return;
                        }
                        if (selectedCount == 1 && !race.isSelected()) {
                            RandomArray.sortSelected1 = sorts;
                            sorts = "";
                            selectedCount = 0;
                            new RandomArray();
                            setVisible(false);
                        }
                        if (selectedCount != 2 && race.isSelected()) {
                            JOptionPane.showMessageDialog(null, "Please select exactly 2 sorting algorithms");
                            sorts = "";
                            selectedCount = 0;
                            return;
                        }
                        if (selectedCount == 2 && race.isSelected()) {
                            RandomArray.sortSelected1 = sorts.substring(0, sorts.indexOf("sort") + 4);
                            RandomArray.sortSelected2 = sorts.substring(sorts.indexOf("sort") + 4);
                            sorts = "";
                            selectedCount = 0;
                            new RandomArray();
                            setVisible(false);
                        }
                    });
                    clicked = true;
                }
            }
        });

        obj = this;

    }

    // Main method (entry point)

    public static void main(String[] args) throws Exception {
        new Home();
    }
}