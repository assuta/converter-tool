package telran.pma.util;

import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.*;

public record ColumnDefinition(String title, Consumer<Cell> rowHandler) {

}
