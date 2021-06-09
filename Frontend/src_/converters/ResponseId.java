package converters;

public class ResponseId {
    private String  id;
    private String message;

    public ResponseId(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public ResponseId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseId{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
