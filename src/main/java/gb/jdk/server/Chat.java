package gb.jdk.server;

public interface Chat {

    /**
     * Проверяет запущен ли сервер чата
     * @return результат проверки
     */
    boolean isAvailable();

    /**
     * Обрабатывает сообщение клиента
     * @param message сообщение в чат
     */
    void chat(String message);
}
