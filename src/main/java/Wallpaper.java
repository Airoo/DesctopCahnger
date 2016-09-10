import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for change windows wallpaper
 *
 * @version 1.0
 * @autor Trotsenko Konstantin
 */
public class Wallpaper extends JFrame {

    private interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean SystemParametersInfo(int one, int two, String s, int three);
    }

    private JTextField textField;

    public Wallpaper() {
        super("Simple programm");
        createGUI();
    }

    public void createGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton button1 = new JButton("Start");
        button1.setActionCommand("It's motivation for you!)");
        panel.add(button1);

        textField = new JTextField();
        textField.setColumns(23);
        panel.add(textField);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User32.INSTANCE.SystemParametersInfo(0x0014, 0, "ass.jpg", 1);
                textField.setText(e.getActionCommand());
            }
        });

        getContentPane().add(panel);
        setPreferredSize(new Dimension(320, 100));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                Wallpaper frame = new Wallpaper();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
