package ridi.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RidiRepository <T, ID> extends JpaRepository<T, ID> {
}
