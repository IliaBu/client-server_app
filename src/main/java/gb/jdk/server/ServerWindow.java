package gb.jdk.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements Chat{

    private static final int WIDTH = 300;
    private static final int HEIGHT = 350;

    private JButton btnStart;
    private JButton btnStop;
    private JTextArea taMessages;
    boolean isServerWorking;

    private List<String> messages;
    private File logFile;
    private IOController ioController;

    public ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnStart = new JButton("Запустить");
        btnStop = new JButton("Остановить");
        setTitle("Чат сервера");
        setLocationRelativeTo(null);
        setLocation(getX() - WIDTH / 2, getY() - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        isServerWorking = false;
        messages = new ArrayList<>();
        taMessages = new JTextArea();
        logFile = new File("./log.txt");
        ioController = new IOControllerDefault();

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(taMessages);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Проверяет запущен ли сервер чата
     *
     * @return результат проверки
     */
    @Override
    public boolean isAvailable() {
        return isServerWorking;
    }

    /**
     * Обрабатывает сообщение клиента
     *
     * @param message сообщение в чат
     */
    @Override
    public void chat(String message) {
        messages.add(message);
        taMessages.append(message);
        taMessages.append("\n");
        ioController.writeFile(messages, logFile);
    }

    /**
     * Запускает сервер чата
     */
    private void startServer() {
        this.isServerWorking = true;
        this.setTitle("Сервер запущен");
        if (logFile.exists()) loadMessages();

    }

    /**
     * Останавливает сервер
     */
    private void stopServer() {
        this.isServerWorking = false;
        this.setTitle("Сервер остановлен");

    }

    /**
     * Обновляет чат
     */
    private void loadMessages() {
        messages = ioController.readFile(logFile);
        for (String message : messages) {
            taMessages.append(message);
            taMessages.append("\n");
        }
        revalidate();
    }
}
