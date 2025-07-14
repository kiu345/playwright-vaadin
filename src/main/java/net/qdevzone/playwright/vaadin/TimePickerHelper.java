package net.qdevzone.playwright.vaadin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TimePickerHelper extends DateTimeHelperBase<LocalTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

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

    @Override
    public boolean isRequired() {
        return "true".equalsIgnoreCase(input.getAttribute("required"))
                || "".equalsIgnoreCase(input.getAttribute("required"));
    }

    @Override
    public boolean isReadOnly() {
        return "true".equalsIgnoreCase(input.getAttribute("readonly"))
                || "".equalsIgnoreCase(input.getAttribute("readonly"));
    }

    @Override
    public boolean isEnabled() {
        return input.isEnabled();
    }

    @Override
    public boolean isVisible() {
        return input.isVisible();
    }

    /**
     * Factory method to create an TimePickerHelper based on an HTML ID.
     */
    public static TimePickerHelper fromId(Page page, String id) {
        return new TimePickerHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an TimePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static TimePickerHelper fromName(Page page, String name) {
        return new TimePickerHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an TimePickerHelper for a element with a given label.
     */
    public static TimePickerHelper fromLabel(Page page, String label) {
        return new TimePickerHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-time-picker", label));
    }

    /**
     * Factory method to create an TimePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static TimePickerHelper fromTestId(Page page, String testId) {
        return new TimePickerHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }

}
