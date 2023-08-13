package library.repositories;

import library.entities.LogbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogbookRepository extends JpaRepository<LogbookEntity, Integer> {
}
