package ch.verno.db.jpa;

import ch.verno.db.entity.AddressEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataAddressJpaRepository extends JpaRepository<AddressEntity, Long> {

  Optional<AddressEntity> findByStreetAndHouseNumberAndZipCodeAndCityAndCountry(
          @Nonnull String street,
          @Nonnull String houseNumber,
          @Nonnull String zipCode,
          @Nonnull String city,
          @Nonnull String country
  );
}
