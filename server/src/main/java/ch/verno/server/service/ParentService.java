package ch.verno.server.service;

import ch.verno.common.db.dto.ParentDto;
import ch.verno.common.db.service.IParentService;
import ch.verno.common.exceptions.db.DBNotFoundException;
import ch.verno.common.exceptions.db.DBNotFoundReason;
import ch.verno.db.entity.ParentEntity;
import ch.verno.server.mapper.ParentMapper;
import ch.verno.server.repository.ParentRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService implements IParentService {

  @Nonnull
  private final ParentRepository parentRepository;

  public ParentService(@Nonnull final ParentRepository parentRepository) {
    this.parentRepository = parentRepository;
  }

  @Nonnull
  @Override
  public ParentDto createParent(@Nonnull final ParentDto parentDto) {
    final var entity = new ParentEntity(
            ServiceHelper.safeString(parentDto.getFirstName()),
            ServiceHelper.safeString(parentDto.getLastName()),
            ServiceHelper.safeString(parentDto.getEmail()),
            ServiceHelper.safeString(parentDto.getPhone().toString())
    );

    final var saved = parentRepository.save(entity);
    return ParentMapper.toDto(saved);
  }

  @Nonnull
  @Override
  public ParentDto updateParent(@Nonnull final ParentDto parentDto) {
    throw new UnsupportedOperationException("Update parent not yet implemented");
  }

  @Nonnull
  @Override
  public ParentDto getParentById(@Nonnull final Long id) {
    final var foundById = parentRepository.findById(id);
    if (foundById.isEmpty()) {
      throw new DBNotFoundException(DBNotFoundReason.PARENT_BY_ID_NOT_FOUND, id);
    }

    return ParentMapper.toDto(foundById.get());
  }

  @Nonnull
  @Override
  public List<ParentDto> getParents() {
    return parentRepository.findAll()
            .stream()
            .map(ParentMapper::toDto)
            .toList();
  }

  /**
   * Finds an existing parent with the same fields or creates a new one.
   * This prevents creating duplicate parent entries when multiple participants have the same parent.
   */
  @Nonnull
  public ParentDto findOrCreateParent(@Nonnull final ParentDto parentDto) {
    final var firstname = ServiceHelper.safeString(parentDto.getFirstName());
    final var lastname = ServiceHelper.safeString(parentDto.getLastName());
    final var email = ServiceHelper.safeString(parentDto.getEmail());
    final var phone = ServiceHelper.safeString(parentDto.getPhone().toString());

    // Try to find existing parent
    final var existing = parentRepository.findByFields(firstname, lastname, email, phone);
    if (existing.isPresent()) {
      return ParentMapper.toDto(existing.get());
    }

    // Create new parent if not found
    final var entity = new ParentEntity(firstname, lastname, email, phone);
    final var saved = parentRepository.save(entity);
    return ParentMapper.toDto(saved);
  }
}
