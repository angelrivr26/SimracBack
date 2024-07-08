package pe.sernanp.ws_api.dto;

import lombok.Data;

import java.io.File;

public class DocumentoDTO {
    Msg msg;

    File file;

    byte[] files;

    // #Alfresco

    String id;

    String name;

    // #Alfresco

    String messagge;

    boolean success = true;

    boolean isAlfresco = false;

    public byte[] getFiles() { return files; }

    public void setFiles(byte[] files) { this.files = files; }

    public Msg getMsg() { return msg; }

    public void setMsg(Msg msg) { this.msg = msg; }

    public File getFile() { return file; }

    public void setFile(File file) { this.file = file; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean getIsAlfresco() { return isAlfresco; }

    public void setIsAlfresco(boolean alfresco) { isAlfresco = alfresco; }

    public String getMessagge() { return messagge; }

    public void setMessagge(String messagge) { this.messagge = messagge; }

    public boolean getSuccess() { return success; }

    public void setSuccess(boolean success) { this.success = success; }
}
class Msg {
    Integer responseCode;
    String message;
    String path;
    listRecursos listRecursos;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public listRecursos getListRecursos() {
        return listRecursos;
    }

    public void setListRecursos(listRecursos listRecursos) {
        this.listRecursos = listRecursos;
    }

}

@Data
class listRecursos {
    Integer srl_id_recurso;
    Integer int_id_recurso_principal;
    String var_descripcion;
    Integer int_id_recurso_tipo;
    String var_descripcion_tipo;
    String var_nombre_file;
    String var_rutacompleta;

    public Integer getSrl_id_recurso() {
        return srl_id_recurso;
    }

    public void setSrl_id_recurso(Integer srl_id_recurso) {
        this.srl_id_recurso = srl_id_recurso;
    }

    public Integer getInt_id_recurso_principal() {
        return int_id_recurso_principal;
    }

    public void setInt_id_recurso_principal(Integer int_id_recurso_principal) {
        this.int_id_recurso_principal = int_id_recurso_principal;
    }

    public String getVar_descripcion() {
        return var_descripcion;
    }

    public void setVar_descripcion(String var_descripcion) {
        this.var_descripcion = var_descripcion;
    }

    public Integer getInt_id_recurso_tipo() {
        return int_id_recurso_tipo;
    }

    public void setInt_id_recurso_tipo(Integer int_id_recurso_tipo) {
        this.int_id_recurso_tipo = int_id_recurso_tipo;
    }

    public String getVar_descripcion_tipo() {
        return var_descripcion_tipo;
    }

    public void setVar_descripcion_tipo(String var_descripcion_tipo) {
        this.var_descripcion_tipo = var_descripcion_tipo;
    }

    public String getVar_nombre_file() {
        return var_nombre_file;
    }

    public void setVar_nombre_file(String var_nombre_file) {
        this.var_nombre_file = var_nombre_file;
    }

    public String getVar_rutacompleta() {
        return var_rutacompleta;
    }

    public void setVar_rutacompleta(String var_rutacompleta) {
        this.var_rutacompleta = var_rutacompleta;
    }
}
