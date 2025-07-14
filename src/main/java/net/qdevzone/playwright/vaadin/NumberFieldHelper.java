package net.qdevzone.playwright.vaadin;

import java.util.StringJoiner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * A helper class for interacting with Vaadin number components
 * in Playwright-based UI tests. This class abstracts common actions and validations on
 * Vaadin input elements for test convenience and clarity.
 */

public class NumberFieldHelper extends VaadinFieldHelper implements TextInputElementHelper {

    /**
     * The HTML input field
     */
    private final Locator input;

    /**
     * Constructs an InputFieldHelper for the given locator.
     */
    protected NumberFieldHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
        this.input = locator.locator("input");
        assertExactlyOne(input);
    }

    @Override
    public Locator getInputLocator() {
        return input;
    }

    @Override
    public void setValue(String value) {
        input.fill(value);
    }

    public void setValue(Number value) {
        String stringVal = value.toString();
        input.fill(stringVal);
    }

    @Override
    public String getValue() {
        return input.inputValue();
    }

    private Object getDataModelValue() {
        return locator.evaluate("element => element.value");
    }

    public Long getLongValue() {
        Object value = getDataModelValue();
        if (value == null) {
            return null;
        }
        return Long.parseLong(value.toString());
    }

    public Double getDoubleValue() {
        Object value = getDataModelValue();
        if (value == null) {
            return null;
        }
        return Double.parseDouble(value.toString());
    }

    /**
     * Retrieves the placeholder text of the input field.
     */
    public String getPlaceholder() {
        return input.getAttribute("placeholder");
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

    @Override
    public void focus() {
        getInputLocator().focus();
    }

    @Override
    public void blur() {
        page.evaluate("el => el.blur()", input);
    }

    /**
     * Factory method to create an NumberFieldHelper based on an HTML ID.
     */
    public static NumberFieldHelper fromId(Page page, String id) {
        return new NumberFieldHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an NumberFieldHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static NumberFieldHelper fromName(Page page, String name) {
        return new NumberFieldHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an NumberFieldHelper for a element with a given label.
     * Supports multiple input field types.
     */
    public static NumberFieldHelper fromLabel(Page page, String label) {
        // we have more than one element to handle, so we can't use the common helper method
        String locatorPattern = new StringJoiner(", ")
                .add("vaadin-number-field > label")
                .add("vaadin-integer-field > label")
                .toString();
        Locator labelElement = page.locator(locatorPattern)
                .locator("text=\"${label}\"".replace("${label}", label));
        assertExactlyOne(labelElement, "label text", label);
        Locator locator = labelElement.locator("..");
        return new NumberFieldHelper(page, TARGETTYPE.LABEL, locator);
    }

    /**
     * Factory method to create an NumberFieldHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static NumberFieldHelper fromTestId(Page page, String testId) {
        return new NumberFieldHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
