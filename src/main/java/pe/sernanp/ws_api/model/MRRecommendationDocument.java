package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_acta_seguim_recomendacion_documento")
public class MRRecommendationDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_seguim_recomendacion_documento_srl_id_seq", sequenceName="t_acta_seguim_recomendacion_documento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguim_recomendacion", foreignKey = @ForeignKey(name="fk_seguim_cumpli_documento_acta_seguim_recomendacion"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MRRecommendation mrRecommendation;

    @Column (name= "txt_nom_documento", length = 150)
    private String DocumentName;
    @Column (name= "txt_size_documento")
    private Long DocumentSize;
    @Column (name= "txt_code_documento", length = 150)
    private String DocumentCode;
}
