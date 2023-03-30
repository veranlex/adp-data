package my.own.adp.data.rabbit.service;

import my.own.adp.data.dto.IncomingMsgJournalDto;

import java.util.List;

public interface RabbitReceiver {

    /**
     * Метод получает ответ из очереди
     * @param listMessages Список записей из БД
     * @param response Ответ от очереди
     */
    void receive(List<IncomingMsgJournalDto> listMessages, Object response);

    /**
     * Обработка сообщения, полученного из очереди
     * @param listMessages Список записей из БД
     * @param exception Описание ошибки полученной из сервиса
     */
    void handleMessage(List<IncomingMsgJournalDto> listMessages, String exception);
}
