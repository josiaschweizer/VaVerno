package ch.verno.ui.base.components.mapping;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class VABaseColumnMappingPanel<TField> extends Composite<Div> {

  public static final String IGNORE_KEY = "__IGNORE__";

  public record MappingRow(@Nonnull String csvColumn,
                           @Nullable String fieldKey) {
  }

  private final Grid<MappingRow> grid = new Grid<>(MappingRow.class, false);
  private final ListDataProvider<MappingRow> dataProvider;
  private final Map<String, String> selectionByCsvColumn;

  @Nonnull private final String ignoreLabel;
  private final boolean allowDuplicates;

  protected VABaseColumnMappingPanel(@Nonnull final List<String> csvColumns,
                                     @Nonnull final List<TField> availableFields,
                                     final boolean allowDuplicates,
                                     @Nonnull final String ignoreLabel) {
    this.selectionByCsvColumn = new LinkedHashMap<>();

    this.allowDuplicates = allowDuplicates;
    this.ignoreLabel = ignoreLabel;

    final var rows = csvColumns.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .distinct()
            .map(col -> new MappingRow(col, null))
            .toList();

    rows.forEach(r -> selectionByCsvColumn.put(r.csvColumn(), null));
    dataProvider = new ListDataProvider<>(new ArrayList<>(rows));

    buildGrid(availableFields);

    grid.setItems(dataProvider);

    getContent().setSizeFull();
    getContent().add(grid);
  }

  private void buildGrid(@Nonnull final List<TField> availableFields) {
    grid.setSizeFull();

    grid.addColumn(MappingRow::csvColumn)
            .setHeader(getCsvColumnHeader())
            .setAutoWidth(true)
            .setFlexGrow(1);

    grid.addComponentColumn(row -> buildFieldCombo(row, availableFields))
            .setHeader(getTargetHeader())
            .setAutoWidth(true)
            .setFlexGrow(1);

    grid.getStyle().set("min-height", "0");
  }

  private ComboBox<FieldOption<TField>> buildFieldCombo(@Nonnull final MappingRow row,
                                                        @Nonnull final List<TField> availableFields) {

    final var combo = new ComboBox<FieldOption<TField>>();
    combo.setWidthFull();
    combo.setClearButtonVisible(true);

    final var options = new ArrayList<FieldOption<TField>>();
    options.add(FieldOption.ignore(ignoreLabel));

    for (final var f : availableFields) {
      options.add(FieldOption.of(getFieldKey(f), getFieldLabel(f), f));
    }

    combo.setItems(options);
    combo.setItemLabelGenerator(FieldOption::label);

    final var selectedKey = selectionByCsvColumn.get(row.csvColumn());
    if (selectedKey != null) {
      combo.setValue(options.stream().filter(o -> selectedKey.equals(o.key())).findFirst().orElse(null));
    }

    combo.addValueChangeListener(e -> {
      final var oldKey = selectionByCsvColumn.get(row.csvColumn());
      final FieldOption<TField> newValue = e.getValue();
      final var newKey = (newValue == null ? null : newValue.key());

      if (!allowDuplicates && newKey != null && !IGNORE_KEY.equals(newKey)) {
        final boolean alreadyUsedByOther = selectionByCsvColumn.entrySet().stream()
                .anyMatch(en -> !en.getKey().equals(row.csvColumn()) && newKey.equals(en.getValue()));

        if (alreadyUsedByOther) {
          final var oldOpt = options.stream().filter(o -> Objects.equals(oldKey, o.key())).findFirst().orElse(null);
          combo.setValue(oldOpt);
          return;
        }
      }

      selectionByCsvColumn.put(row.csvColumn(), newKey);
      onMappingChanged(row.csvColumn(), oldKey, newKey);
    });

    return combo;
  }

  @Nonnull
  public Map<String, String> getMapping() {
    return selectionByCsvColumn.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .filter(e -> !IGNORE_KEY.equals(e.getValue()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (a, b) -> a,
                    LinkedHashMap::new
            ));
  }

  public boolean isValid() {
    final var mapped = selectionByCsvColumn.values().stream()
            .filter(Objects::nonNull)
            .filter(v -> !IGNORE_KEY.equals(v))
            .toList();

    if (mapped.isEmpty()) {
      return false;
    }

    if (allowDuplicates) {
      return true;
    }

    final var set = new HashSet<>(mapped);
    return set.size() == mapped.size();
  }

  public void setEnabled(boolean enabled) {
    grid.setEnabled(enabled);
  }

  @Nonnull
  protected String getCsvColumnHeader() {
    return "CSV-Spalte";
  }

  @Nonnull
  protected String getTargetHeader() {
    return "Map to";
  }

  @Nonnull
  protected abstract String getFieldKey(@Nonnull TField field);

  @Nonnull
  protected abstract String getFieldLabel(@Nonnull TField field);

  protected void onMappingChanged(@Nonnull final String csvColumn,
                                  @Nullable final String oldFieldKey,
                                  @Nullable final String newFieldKey) {
    // can be overridden by subclasses
  }

  public record FieldOption<T>(@Nonnull String key,
                               @Nonnull String label,
                               @Nullable T value) {

    @Nonnull
    public static <T> FieldOption<T> ignore(@Nonnull final String label) {
      return new FieldOption<>(IGNORE_KEY, label, null);
    }

    @Nonnull
    public static <T> FieldOption<T> of(@Nonnull final String key,
                                        @Nonnull final String label,
                                        @Nonnull final T value) {
      return new FieldOption<>(key, label, value);
    }
  }
}