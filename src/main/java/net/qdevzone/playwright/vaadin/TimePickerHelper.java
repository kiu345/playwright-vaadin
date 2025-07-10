package net.qdevzone.playwright.vaadin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TimePickerHelper extends DateTimeHelperBase<LocalTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    
    protected TimePickerHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    @Override
    public void setValue(LocalTime value) {
        String valueString = formatter.format(value);
        setValue(valueString);
    }

    @Override
    public LocalTime getValue() {
        String value = getValueText();
        if (value == null || "".equals(value)) {
            return null;
        }
        TemporalAccessor temporal = formatter.parse(value);
        return LocalTime.from(temporal);
    }

    public static TimePickerHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new TimePickerHelper(page, TARGETTYPE.ID, locator);
    }

    public static TimePickerHelper fromLabel(Page page, String label) {
        Locator locator = page.locator("vaadin-time-picker").getByLabel(label);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with text: " + label);
        }
        return new TimePickerHelper(page, TARGETTYPE.TEXT, locator);
    }

}
