package pe.sernanp.ws_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class ListDetailEntity {
    private Integer _id;
    private String _name;
    private String _description;

    public ListDetailEntity(Integer _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public ListDetailEntity(Integer _id, String _name, String _description) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }
}
