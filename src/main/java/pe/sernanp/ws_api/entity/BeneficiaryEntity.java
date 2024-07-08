package pe.sernanp.ws_api.entity;

public class BeneficiaryEntity {
    //region DNI
    String apPrimer;
    String apSegundo;
    String direccion;
    String estadoCivil;
    String foto;
    String prenombres;
    String restriccion;
    String ubigeo;
    String coResultado;
    String deResultado;
    String fullNombre;
    //endregion DNI

    //region RUC
    String ddp_nombre;
    String ddp_numruc;
    String ddp_estado;

    //endregion RUC

    //region DNI
    public String getApPrimer() {
        return apPrimer;
    }

    public void setApPrimer(String apPrimer) {
        this.apPrimer = apPrimer;
    }

    public String getApSegundo() {
        return apSegundo;
    }

    public void setApSegundo(String apSegundo) {
        this.apSegundo = apSegundo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPrenombres() {
        return prenombres;
    }

    public void setPrenombres(String prenombres) {
        this.prenombres = prenombres;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getCoResultado() {
        return coResultado;
    }

    public void setCoResultado(String coResultado) {
        this.coResultado = coResultado;
    }

    public String getDeResultado() {
        return deResultado;
    }

    public void setDeResultado(String deResultado) {
        this.deResultado = deResultado;
    }

    public String getFullNombre() {
        return fullNombre == null ? (this.prenombres + " " + this.apPrimer + " " + this.apSegundo) : fullNombre;
    }

    public void setFullNombre(String fullNombre) {
        this.fullNombre = fullNombre;
    }

    //endregion DNI

    //region RUC

    public String getDdp_nombre() {
        return ddp_nombre;
    }

    public void setDdp_nombre(String ddp_nombre) {
        this.ddp_nombre = ddp_nombre;
    }

    public String getDdp_numruc() {
        return ddp_numruc;
    }

    public void setDdp_numruc(String ddp_numruc) {
        this.ddp_numruc = ddp_numruc;
    }

    public String getDdp_estado() { return ddp_estado; }

    public void setDdp_estado(String ddp_estado) { this.ddp_estado = ddp_estado; }

    //endregion RUC
}
