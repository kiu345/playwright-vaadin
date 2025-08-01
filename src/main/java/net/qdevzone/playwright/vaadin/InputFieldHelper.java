package net.qdevzone.playwright.vaadin;

import java.util.StringJoiner;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * A helper class for interacting with Vaadin input components (e.g. text fields, password fields)
 * in Playwright-based UI tests. This class abstracts common actions and validations on
 * Vaadin input elements for test convenience and clarity.
 */

public class InputFieldHelper extends VaadinFieldHelper implements TextInputElementHelper {

    /**
     * The HTML input field
     */
    private final Locator input;

    /**
     * Constructs an InputFieldHelper for the given locator.
     */
    protected InputFieldHelper(Page page, TARGETTYPE targetType, Locator locator) {
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

    @Override
    public String getValue() {
        return input.inputValue();
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
     * Factory method to create an InputFieldHelper based on an HTML ID.
     */
    public static InputFieldHelper fromId(Page page, String id) {
        return new InputFieldHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an InputFieldHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static InputFieldHelper fromName(Page page, String name) {
        return new InputFieldHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an InputFieldHelper for a element with a given label.
     * Supports multiple input field types.
     */
    public static InputFieldHelper fromLabel(Page page, String label) {
        // we have more than one element to handle, so we can't use the common helper method
        String locatorPattern = new StringJoiner(", ")
                .add("vaadin-text-field > label")
                .add("vaadin-password-field > label")
                .add("vaadin-email-field > label")
                .add("vaadin-number-field > label")
                .toString();
        Locator labelElement = page.locator(locatorPattern)
                .locator("text=\"${label}\"".replace("${label}", label));
        assertExactlyOne(labelElement, "label text", label);
        Locator locator = labelElement.locator("..");
        return new InputFieldHelper(page, TARGETTYPE.LABEL, locator);
    }

    /**
     * Factory method to create an InputFieldHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static InputFieldHelper fromTestId(Page page, String testId) {
        return new InputFieldHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
