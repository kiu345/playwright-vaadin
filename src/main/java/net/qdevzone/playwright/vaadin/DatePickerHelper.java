package net.qdevzone.playwright.vaadin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DatePickerHelper extends DateTimeHelperBase<LocalDate> {

    
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    protected DatePickerHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    @Override
    public void setValue(LocalDate value) {
        String valueString = formatter.format(value);
        setValue(valueString);
    }

    @Override
    public LocalDate getValue() {
        String value = getValueText();
        if (value == null || "".equals(value)) {
            return null;
        }
        TemporalAccessor temporal = formatter.parse(value);
        return LocalDate.from(temporal);
    }

    public static DatePickerHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new DatePickerHelper(page, TARGETTYPE.ID, locator);
    }

    public static DatePickerHelper fromLabel(Page page, String label) {
        Locator locator = page.locator("vaadin-date-picker").getByLabel(label);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with text: " + label);
        }
        return new DatePickerHelper(page, TARGETTYPE.TEXT, locator);
    }

}
