package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.SPPermittedActivity;

import java.util.List;

@Repository
public interface SPPermittedActivityRepository extends JpaRepository<SPPermittedActivity, Integer> {
    List<SPPermittedActivity> findBySitePlanIdAndIsDeleted(int sitePlanId, boolean isDeleted);
}
