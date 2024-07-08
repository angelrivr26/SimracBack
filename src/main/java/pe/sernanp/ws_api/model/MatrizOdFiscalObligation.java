package pe.sernanp.ws_api.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(MatrizOdFiscalObligationId.class)
@Table(name = "t_matriz_obligacion_od_obligacion_fiscal")
public class MatrizOdFiscalObligation {
    @Id
    private int matrizObligationId;
    @Id
    private int odFiscalObligationId;
}


class MatrizOdFiscalObligationId implements Serializable {
    @Column(name = "int_id_matriz_obligacion")
    private int matrizObligationId;

    @Column(name = "int_id_od_obligacion_fiscal")
    private int odFiscalObligationId;

    // default constructor

    public MatrizOdFiscalObligationId(int matrizObligationId, int odFiscalObligationId) {
        this.matrizObligationId = matrizObligationId;
        this.odFiscalObligationId = odFiscalObligationId;
    }
}