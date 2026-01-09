package ch.verno.report.dto;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CourseReportDto(@Nonnull String title,
                              @Nonnull List<ParticipantReportDto> participants,
                              @Nonnull Integer capacity,
                              @Nonnull String courseLevels,
                              @Nonnull String courseSchedule,
                              @Nonnull List<LocalDate> courseDates,
                              @Nonnull LocalTime startTime,
                              @Nonnull LocalTime endTime) {
}
