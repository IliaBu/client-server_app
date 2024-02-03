package gb.jdk.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 350;

    private JButton btnStart;
    private JButton btnStop;

    boolean isServerWorking;

    public ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        setTitle("Чат сервера");
        setLocationRelativeTo(null);
        setLocation(getX() - WIDTH / 2, getY() - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                System.out.println("Сервер запущен!");
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = false;
                System.out.println("Сервер остановлен!");
            }
        });
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

}
