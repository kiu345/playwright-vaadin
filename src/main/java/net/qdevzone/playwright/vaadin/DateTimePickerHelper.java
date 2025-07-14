package net.qdevzone.playwright.vaadin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DateTimePickerHelper extends DateTimeHelperBase<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");

    protected DateTimePickerHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    @Override
    public void setValue(LocalDateTime value) {
        String valueString = formatter.format(value);
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

    @Override
    public boolean isRequired() {
        return "true".equalsIgnoreCase(input.first().getAttribute("required"))
                || "".equalsIgnoreCase(input.first().getAttribute("required"));
    }

    @Override
    public boolean isReadOnly() {
        return "true".equalsIgnoreCase(input.first().getAttribute("readonly"))
                || "".equalsIgnoreCase(input.first().getAttribute("readonly"));
    }

    @Override
    public boolean isEnabled() {
        return input.first().isEnabled();
    }

    @Override
    public boolean isVisible() {
        return input.first().isVisible();
    }

    /**
     * Factory method to create an DateTimePickerHelper based on an HTML ID.
     */
    public static DateTimePickerHelper fromId(Page page, String id) {
        return new DateTimePickerHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an DateTimePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static DateTimePickerHelper fromName(Page page, String name) {
        return new DateTimePickerHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an DateTimePickerHelper for a element with a given label.
     */
    public static DateTimePickerHelper fromLabel(Page page, String label) {
        return new DateTimePickerHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-date-time-picker", label));
    }

    /**
     * Factory method to create an DateTimePickerHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static DateTimePickerHelper fromTestId(Page page, String testId) {
        return new DateTimePickerHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }

}
