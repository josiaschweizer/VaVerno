package ch.verno.server.report;

import ch.verno.common.db.dto.CourseDto;
import ch.verno.common.db.dto.ParticipantDto;
import ch.verno.common.report.ReportDto;
import ch.verno.common.report.ReportServerGate;
import ch.verno.server.file.FileStorageHandler;
import ch.verno.server.report.course.CourseReportUseCase;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServerGateImpl implements ReportServerGate {

  @Nonnull private final FileStorageHandler fileStorageHandler;
  @Nonnull private final CourseReportUseCase courseReportUseCase;

  public ReportServerGateImpl(@Nonnull final CourseReportUseCase courseReportUseCase) {
    this.courseReportUseCase = courseReportUseCase;
    this.fileStorageHandler = new FileStorageHandler();
  }

  @Override
  public String generateCourseReportWithTempFile(@Nonnull final CourseDto course,
                                                 @Nonnull final List<ParticipantDto> participants) {
    final var report = courseReportUseCase.generate(course, participants);
    return fileStorageHandler.storeFileTemporary(report.filename(), report.pdfBytes());
  }

  @Nonnull
  @Override
  public ReportDto loadTempFile(@Nonnull final String token) {
    return fileStorageHandler.loadTemporaryFile(token);
  }

  @Override
  public void deleteTempFile(@Nonnull final String token) {
    fileStorageHandler.delete(token);
  }

  @Nonnull
  @Override
  public ReportDto generateCourseReportPdf(@Nonnull final CourseDto course,
                                           @Nonnull final java.util.List<ParticipantDto> participants) {
    return courseReportUseCase.generate(course, participants);
  }
}
