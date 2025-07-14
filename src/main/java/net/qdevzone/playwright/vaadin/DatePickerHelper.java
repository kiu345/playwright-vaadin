package net.qdevzone.playwright.vaadin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DatePickerHelper extends DateTimeHelperBase<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

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
     * Factory method to create an DatePickerHelper based on an HTML ID.
     */
    public static DatePickerHelper fromId(Page page, String id) {
        return new DatePickerHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an DatePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static DatePickerHelper fromName(Page page, String name) {
        return new DatePickerHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an DatePickerHelper for a element with a given label.
     */
    public static DatePickerHelper fromLabel(Page page, String label) {
        return new DatePickerHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-date-picker", label));
    }

    /**
     * Factory method to create an DatePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static DatePickerHelper fromTestId(Page page, String testId) {
        return new DatePickerHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
