import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
                User32.INSTANCE.SystemParametersInfo(0x0014, 0, "C:\\ass.jpg", 1);
                textField.setText(e.getActionCommand());
            }
        });

        getContentPane().add(panel);
        setPreferredSize(new Dimension(320, 100));
    }

    public static void main(String[] args) throws IOException {
        copyFileUsingStream(new File("ass.jpg"), new File("C:\\ass.jpg"));
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

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            //is = new FileInputStream(source);
            is = Wallpaper.class.getClassLoader().getResourceAsStream("ass.jpg");
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
