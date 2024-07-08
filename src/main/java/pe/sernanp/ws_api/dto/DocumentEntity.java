package pe.sernanp.ws_api.dto;

public class DocumentEntity {
    private String id;
    private String name;
    private Long size;

    public DocumentEntity(String id, String name, Long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
