package my.own.adp.data.entity;

public enum Status {

    NEW("NEW"), // новое сообщение
    PROCESSING("PROCESSING"), // обработка записи
    PARSE_ERROR("PARSE_ERROR"), // ошибка парсинга
    SEND_ERROR("SEND_ERROR"), // ошибка отправки в RABBIT_MQ
    PROCESSED("PROCESSED"); // отправлено в Rabbit MQ


    private String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
