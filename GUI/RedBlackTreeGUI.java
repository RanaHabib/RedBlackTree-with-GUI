import com.company.Node;
import com.company.RedBlackTree;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RedBlackTreeGUI extends Canvas {
    private RedBlackTree rbtree = new RedBlackTree();
    private JPanel panel = new JPanel();
    private int radius = 20;
    private int yOffset = 150;

    RedBlackTreeGUI() {

        JTextField insert = new JTextField(5);
        JButton insertBTN = new JButton("insert");
        insertBTN.setBackground(Color.gray);
        JTextField delete = new JTextField(5);
        JButton deleteBTN = new JButton("Delete");
        deleteBTN.setBackground(Color.gray);
        JButton resetBTN = new JButton("clear");
        resetBTN.setBackground(Color.gray);

        panel.add(insert);
        panel.add(insertBTN);
        panel.add(delete);
        panel.add(deleteBTN);
        panel.add(resetBTN);

        insertBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int num = Integer.parseInt(insert.getText());
                    rbtree.insert(num);
                    paint();
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });
        deleteBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int num = Integer.parseInt(delete.getText());
                    rbtree.remove(num);
                    paint();
                } catch (NumberFormatException ex) {
                    System.out.println(ex);
                }
            }
        });
        resetBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbtree.clear();
                paint();
            }
        });

    }

    public void paint() {
        RedBlackTreeGUI.super.paint(RedBlackTreeGUI.super.getGraphics());
        paint(RedBlackTreeGUI.super.getGraphics());
    }

    public void paint(Graphics g) {
        int x1 = 500;
        int y1 = 50;
        setBackground(Color.lightGray);
        addTree(g, rbtree.getRoot(), x1, y1, 100);
    }

    private void addTree(Graphics g, Node node, int x, int y, int xOffset) {

        addNode(g, node, x, y);
        if (!rbtree.isNil(node.getLeftChild())) {
            addLine(g, x - xOffset, y + yOffset, x, y);
            addTree(g, node.getLeftChild(), x - xOffset, y + yOffset, xOffset / 2);
        }

        if (!rbtree.isNil(node.getRightChild())) {
            addLine(g, x + xOffset, y + yOffset, x, y);
            addTree(g, node.getRightChild(), x + xOffset, y + yOffset, xOffset / 2);
        }
    }

    private void addNode(Graphics g, Node node, int p_x, int p_y) {
        if (node.getColor() == com.company.Color.RED) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);
        int size = 2 * radius;
        g.fillOval(p_x - radius, p_y - radius, size, size);
        addText(g, String.valueOf(node.getValue()), p_x, p_y);
    }

    private void addText(Graphics g, String text, int p_x, int p_y) {
        g.setColor(Color.white);
        double width = g.getFontMetrics().getStringBounds(text, g).getWidth();
        int a = (int) (p_x - width / 2);
        int b = (int) (p_y + g.getFontMetrics().getMaxAscent() / 2);
        g.drawString(text, a, b);
    }

    private void addLine(Graphics g, int x1, int y1, int x2, int y2) {
        double hypot = Math.hypot(yOffset, x2 - x1);
        int x11 = (int) (x1 + radius * (x2 - x1) / hypot);
        int y11 = (int) (y1 - radius * yOffset / hypot);
        int x21 = (int) (x2 - radius * (x2 - x1) / hypot);
        int y21 = (int) (y2 + radius * yOffset / hypot);
        g.drawLine(x11, y11, x21, y21);
    }

    public static void main(String args[]) {
        RedBlackTreeGUI gui = new RedBlackTreeGUI();
        JFrame frame = new JFrame("Red Black Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.getContentPane().add(BorderLayout.SOUTH, gui.panel);
        frame.getContentPane().add(BorderLayout.CENTER, gui);
        frame.setVisible(true);
    }
}
