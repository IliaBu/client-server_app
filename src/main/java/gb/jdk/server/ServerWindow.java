package gb.jdk.server;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements Chat{

    private static final int WIDTH = 300;
    private static final int HEIGHT = 350;

    private ImageIcon img;
    private JButton btnStart;
    private JButton btnStop;
    private final JTextArea taMessages;
    boolean isServerWorking;

    private List<String> messages;
    private final File logFile;
    private final IOController ioController;

    public ServerWindow() {
        setting();
        isServerWorking = false;
        messages = new ArrayList<>();
        taMessages = new JTextArea();
        logFile = new File("./log.txt");
        ioController = new IOControllerDefault();

        btnStart.addActionListener(e -> startServer());
        btnStop.addActionListener(e -> stopServer());

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(taMessages);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setting() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        btnStart = new JButton("Запустить");
        btnStop = new JButton("Остановить");
        setTitle("Чат сервера");
        img = new ImageIcon(getClass().getResource("/chat.png"));
        setIconImage(img.getImage());
        setLocationRelativeTo(null);
        setLocation(getX() - WIDTH / 2, getY() - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
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
