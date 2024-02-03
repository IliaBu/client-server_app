package gb.jdk.client;

import gb.jdk.server.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientGUI extends JFrame {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 350;
    private ImageIcon img;
    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIPAdress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin= new JTextField("ilya_bunakov");
    private final JPasswordField tfPassword = new JPasswordField("19754825");
    private final JButton btnLogin = new JButton("Войти");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Отправить");

    private String login;

    private final Chat serverWindow;

    public ClientGUI(Chat serverWindow){
        this.serverWindow = serverWindow;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Чат клиента");
        img = new ImageIcon(getClass().getResource("/chat.png"));
        setIconImage(img.getImage());
        setLocationRelativeTo(null);
        setLocation(getX() - WIDTH / 2, getY() - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);

        tfIPAdress.setToolTipText("Введите IP адрес");
        tfPort.setToolTipText("Введите номер порта");
        tfLogin.setToolTipText("Введите логин");
        tfPassword.setToolTipText("Введите пароль");
        btnLogin.setToolTipText("Нажмите, чтобы войти в учётную запись");

        panelTop.add(tfIPAdress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        tfLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

        btnSend.setToolTipText("Нажмите, чтобы отправить сообщение");
        btnSend.setEnabled(false);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        tfMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        setVisible(true);
    }

    /**
     * Авторизует пользователя
     */
    private void logIn() {
        if (!tfLogin.getText().equals("")) {
            login = tfLogin.getText();
            this.setTitle(tfLogin.getText());
            tfMessage.setEnabled(true);
            btnSend.setEnabled(true);
        }
    }

    /**
     * Отправляет сообщение
     */
    private void sendMessage() {
        if (!serverWindow.isAvailable()) {
            JOptionPane.showMessageDialog(this, "Сервер не доступен!");
        } else {
            if (!tfMessage.getText().equals("")) {
                String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"));
                serverWindow.chat(String.format("%s, %s: %s", today, login, tfMessage.getText()));
            }
        }
    }

}
