package ch.verno.ui.base.report;

import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;
import org.vaadin.reports.PrintPreviewReport;

import java.util.List;

public abstract class VABaseReport<T> extends VerticalLayout {

  @Nonnull
  protected final PrintPreviewReport<T> report;
  @Nonnull
  private final DynamicReportBuilder reportBuilder;

  public VABaseReport() {
    report = new PrintPreviewReport<>(getType(), getColumnProperties());
    reportBuilder = report.getReportBuilder();
  }

  protected void initUI() {
    report.setItems(getDataList());

    add(report);
    setWidthFull();
  }

  @Nonnull
  protected abstract List<T> getDataList();

  @Nonnull
  protected abstract Class<T> getType();

  @Nonnull
  protected abstract String[] getColumnProperties();

}
