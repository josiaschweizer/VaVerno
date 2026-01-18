package ch.verno.db.jpa;

import ch.verno.db.entity.ParentEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataParentJpaRepository extends JpaRepository<ParentEntity, Long> {

  Optional<ParentEntity> findByFirstnameAndLastnameAndEmailAndPhone(
          @Nonnull String firstname,
          @Nonnull String lastname,
          @Nonnull String email,
          @Nonnull String phone
  );
}
