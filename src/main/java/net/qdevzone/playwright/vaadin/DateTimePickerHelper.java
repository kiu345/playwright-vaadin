package net.qdevzone.playwright.vaadin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DateTimePickerHelper extends DateTimeHelperBase<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");

    
    protected DateTimePickerHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    @Override
    public void setValue(LocalDateTime value) {
        String valueString = formatter.format(value);
        System.out.println(valueString);
        setValue(valueString);
    }

    @Override
    public LocalDateTime getValue() {
        String value = getValueText();
        if (value == null || "".equals(value)) {
            return null;
        }
        TemporalAccessor temporal = formatter.parse(value);
        return LocalDateTime.from(temporal);
    }

    public static DateTimePickerHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new DateTimePickerHelper(page, TARGETTYPE.ID, locator);
    }

    public static DateTimePickerHelper fromLabel(Page page, String label) {
        Locator locator = page.locator("vaadin-date-time-picker").getByLabel(label);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with text: " + label);
        }
        return new DateTimePickerHelper(page, TARGETTYPE.TEXT, locator);
    }

}
