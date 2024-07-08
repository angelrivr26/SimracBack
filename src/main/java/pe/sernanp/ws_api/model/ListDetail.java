package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_listado_detalle", schema = "ge")
public class ListDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
//    @SequenceGenerator(name = "t_listado_detalle_srl_id_seq", sequenceName="t_listado_detalle_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_listado", foreignKey = @ForeignKey(name="FK_listado_tipo_listado"))
    private ListHeader listType;

    @ManyToOne
    @JoinColumn(name = "int_id_listado_detalle", foreignKey = @ForeignKey(name="FK_listado_listado"))
    private ListDetail listDetail;

    @Column (name= "txt_nom_corto", nullable=false, length = 50)
    private String name;

    @Column(name= "txt_nom_largo", length = 150)
    private String description;

    @JsonIgnore
    @Column (name= "bol_flg_activo", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagActive = true;
}
