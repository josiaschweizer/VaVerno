package ch.verno.common.report;

import ch.verno.common.db.dto.CourseDto;
import ch.verno.common.db.dto.ParticipantDto;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface ReportServerGate {

  String generateCourseReportWithTempFile(@Nonnull CourseDto course,
                                          @Nonnull List<ParticipantDto> participants);

  ReportDto loadTempFile(@Nonnull String token);

  void deleteTempFile(@Nonnull String token);

  @Nonnull
  ReportDto generateCourseReportPdf(@Nonnull CourseDto course,
                                    @Nonnull List<ParticipantDto> participants);

}
