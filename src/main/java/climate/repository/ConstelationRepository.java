package climate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import climate.model.Constelation;

public interface ConstelationRepository extends CrudRepository<Constelation, Long> {

    public Constelation findFirstById(long id);

    public List<Constelation> findAll();
}