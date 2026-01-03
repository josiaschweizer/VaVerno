package ch.verno.ui.base.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public abstract class VABaseReport<T> extends BaseReport<T> {

  public enum ReportHorizontalAlignment {
    LEFT(HorizontalAlign.LEFT),
    CENTER(HorizontalAlign.CENTER),
    RIGHT(HorizontalAlign.RIGHT);

    private final HorizontalAlign dj;

    ReportHorizontalAlignment(HorizontalAlign dj) {
      this.dj = dj;
    }
  }

  @Nonnull
  private String title = "";

  @Nonnull
  private String subtitle = "";

  @Nonnull
  private ReportHorizontalAlignment titleAlignment = ReportHorizontalAlignment.LEFT;

  @Nonnull
  private ReportHorizontalAlignment subtitleAlignment = ReportHorizontalAlignment.LEFT;

  protected VABaseReport() {
    applyDefaultReportStyles();
  }

  public final void setTitle(@Nonnull final String title) {
    this.title = Objects.requireNonNull(title);
    reportBuilder.setTitle(this.title);
  }

  public final void setSubtitle(@Nonnull final String subtitle) {
    this.subtitle = Objects.requireNonNull(subtitle);
    reportBuilder.setSubtitle(this.subtitle);
  }

  public final void setTitleAlignment(@Nonnull final ReportHorizontalAlignment alignment) {
    this.titleAlignment = Objects.requireNonNull(alignment);
    applyDefaultReportStyles();
  }

  public final void setSubtitleAlignment(@Nonnull final ReportHorizontalAlignment alignment) {
    this.subtitleAlignment = Objects.requireNonNull(alignment);
    applyDefaultReportStyles();
  }

  protected final void addColumn(@Nonnull final AbstractColumn column) {
    reportBuilder.addColumn(column);
  }

  protected void applyDefaultReportStyles() {
    reportBuilder.setPageSizeAndOrientation(Page.Page_A4_Landscape());
    reportBuilder.setMargins(20, 20, 20, 20);
    reportBuilder.setUseFullPageWidth(true);

    reportBuilder.setTitle(title);
    reportBuilder.setSubtitle(subtitle);

    Style titleStyle = new Style();
    titleStyle.setFont(new Font(14, Font._FONT_VERDANA, true));
    titleStyle.setHorizontalAlign(titleAlignment.dj);

    Style subtitleStyle = new Style();
    subtitleStyle.setFont(new Font(10, Font._FONT_VERDANA, false));
    subtitleStyle.setHorizontalAlign(subtitleAlignment.dj);

    Style headerStyle = new Style();
    headerStyle.setFont(new Font(10, Font._FONT_VERDANA, true));
    headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);

    Style detailStyle = new Style();
    detailStyle.setFont(new Font(9, Font._FONT_VERDANA, false));
    detailStyle.setHorizontalAlign(HorizontalAlign.LEFT);

    reportBuilder.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle);
  }
}