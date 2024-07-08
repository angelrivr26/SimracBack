package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.model.Beneficiary;

import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    Optional<Beneficiary> findByDocumentNumber(String documentNumber);

    @Query(value = "select srl_id as id, txt_nom_corto as name from ge.t_listado_detalle where txt_nom_largo = ?1 ", nativeQuery = true)
    ListDetailDTO getBeneficiaryTypeId(String documentType);

}
