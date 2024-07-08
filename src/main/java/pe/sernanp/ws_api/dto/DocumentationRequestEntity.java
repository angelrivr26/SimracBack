package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentationRequestEntity {
     private int id;
     private String descriptionDocument;
     private String comment;
     private ListDetailEntity compromise;
     private LocalDate agreedDate;
     private ListDetailEntity state;
     private DocumentEntity documentEvaluate;

}
