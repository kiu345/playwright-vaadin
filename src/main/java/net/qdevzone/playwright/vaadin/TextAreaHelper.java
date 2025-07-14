package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * A helper class for interacting with Vaadin text area components
 * in Playwright-based UI tests. This class abstracts common actions and validations on
 * Vaadin text area components for test convenience and clarity.
 */

public class TextAreaHelper extends VaadinFieldHelper implements TextInputElementHelper {

    private final Locator input;

    /**
     * Constructs an TextAreaHelper for the given locator.
     */
    protected TextAreaHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
        this.input = locator.locator("textarea");
        assertExactlyOne(input);
    }

    @Override
    public Locator getInputLocator() {
        return input;
    }

    public void setValue(String value) {
        input.fill(value);
    }

    public String getValue() {
        return input.inputValue();
    }

    /**
     * Retrieves the placeholder text of the text area.
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

    /**
     * Simulates pressing the Enter key while the text area is focused.
     */
    @Override
    public void pressEnter() {
        input.press("Enter");
    }

    /**
     * Clears the text area by filling it with an empty string.
     */
    @Override
    public void clear() {
        input.fill("");
    }

    @Override
    public void click() {
        input.click();
    }

    @Override
    public void dblclick() {
        input.dblclick();
    }

    /**
     * Sets focus on the text area.
     */
    public void focus() {
        input.focus();
    }

    /**
     * Removes focus from the text area (simulates blur event).
     */
    public void blur() {
        page.evaluate("el => el.blur()", input);
    }

    public boolean isEnabled() {
        return input.isEnabled();
    }

    @Override
    public boolean isVisible() {
        return input.isVisible();
    }

    /**
     * Factory method to create an TextAreaHelper based on an HTML ID.
     */
    public static TextAreaHelper fromId(Page page, String id) {
        return new TextAreaHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an TextAreaHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static TextAreaHelper fromName(Page page, String name) {
        return new TextAreaHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an TextAreaHelper for a element with a given label.
     */
    public static TextAreaHelper fromLabel(Page page, String label) {
        return new TextAreaHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-text-area", label));
    }

    /**
     * Factory method to create an TextAreaHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static TextAreaHelper fromTestId(Page page, String testId) {
        return new TextAreaHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
