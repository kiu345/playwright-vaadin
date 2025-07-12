package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * A helper class for interacting with Vaadin input components (e.g. text fields, password fields)
 * in Playwright-based UI tests. This class abstracts common actions and validations on
 * Vaadin input elements for test convenience and clarity.
 */

public class InputFieldHelper extends VaadinHelper {

    private final Locator locator;
    private final Locator input;

    /**
     * Constructs an InputFieldHelper for the given locator.
     *
     * @param page       the Playwright Page instance
     * @param targetType the type of locator used (ID, LABEL, NAME, etc.)
     * @param locator    the Vaadin component locator
     */
    protected InputFieldHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
        this.input = locator.locator("input");
        assertExactlyOne(input);
    }

    /**
     * Clicks on the outer component of the Vaadin input field.
     */
    public void click() {
        locator.click();
    }

    /**
     * Sets the value of the input field by replacing the current content.
     *
     * @param value the string value to fill into the input field
     */
    public void setValue(String value) {
        input.fill(value);
    }

    /**
     * Types the given text into the input field with synthetic key events.
     * This simulates real user typing behavior.
     *
     * @param text the text to type
     */
    public void type(String text) {
        input.pressSequentially(text);
    }

    /**
     * Types the given text character by character with a fixed delay between each character.
     *
     * @param text  the text to type
     * @param pause delay in milliseconds between characters
     */
    public void type(String text, int pause) {
        for (char c : text.toCharArray()) {
            input.press(String.valueOf(c));
            try {
                Thread.sleep(pause);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Retrieves the current value of the input field.
     *
     * @return the value currently set in the field
     */
    public String getValue() {
        return input.inputValue();
    }

    /**
     * Checks whether the component is marked as invalid.
     *
     * @return true if the field has the 'invalid' attribute set, false otherwise
     */
    public boolean hasValidationError() {
        return locator.getAttribute("invalid") != null;
    }

    /**
     * Checks whether an error message is visible inside the shadow DOM.
     *
     * @return true if an error message is displayed, false otherwise
     */
    public boolean hasErrorMessage() {
        Locator error = locator.locator("::shadow [part='error-message']");
        return error.isVisible();
    }

    /**
     * Retrieves the placeholder text of the input field.
     *
     * @return the placeholder string, or null if none is set
     */
    public String getPlaceholder() {
        return input.getAttribute("placeholder");
    }

    /**
     * Retrieves the label attribute from the Vaadin component.
     *
     * @return the label string, or null if not set
     */
    public String getLabel() {
        return locator.getAttribute("label");
    }

    /**
     * Checks whether the input field is marked as required.
     *
     * @return true if the field is required, false otherwise
     */
    public boolean isRequired() {
        return "true".equalsIgnoreCase(locator.getAttribute("required"));
    }

    /**
     * Checks whether the input field is marked as read-only.
     *
     * @return true if the field is read-only, false otherwise
     */
    public boolean isReadOnly() {
        return "true".equals(locator.getAttribute("readonly"));
    }

    /**
     * Checks whether the field is visible in the current viewport.
     *
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return input.isVisible();
    }

    /**
     * Checks whether the field is enabled (i.e. not disabled).
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return input.isEnabled();
    }

    /**
     * Simulates pressing the Enter key while the field is focused.
     */
    public void pressEnter() {
        input.press("Enter");
    }

    /**
     * Clears the input field by filling it with an empty string.
     */
    public void clear() {
        input.fill("");
    }

    /**
     * Sets focus on the input field.
     */
    public void focus() {
        input.focus();
    }

    /**
     * Removes focus from the input field (simulates blur event).
     */
    public void blur() {
        page.evaluate("el => el.blur()", input);
    }

    /**
     * Factory method to create an InputFieldHelper based on an HTML ID.
     *
     * @param page the Playwright page
     * @param id   the element ID
     * @return a new InputFieldHelper instance
     */
    public static InputFieldHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new InputFieldHelper(page, TARGETTYPE.ID, locator);
    }

    /**
     * Factory method to create an InputFieldHelper for a Vaadin field with a given label.
     * Supports multiple input field types.
     *
     * @param page  the Playwright page
     * @param label the label text of the field
     * @return a new InputFieldHelper instance
     */
    public static InputFieldHelper fromLabel(Page page, String label) {
        String selector = String.join(
                ", ",
                "vaadin-text-field[label='" + label + "']",
                "vaadin-password-field[label='" + label + "']",
                "vaadin-email-field[label='" + label + "']",
                "vaadin-number-field[label='" + label + "']"
        );
        Locator locator = page.locator(selector);
        assertExactlyOne(locator, "label", label);
        return new InputFieldHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an InputFieldHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     *
     * @param page the Playwright page
     * @param name the name attribute value
     * @return a new InputFieldHelper instance
     */
    public static InputFieldHelper fromName(Page page, String name) {
        Locator locator = page.locator("vaadin-text-field[name='$']".replace("$", name));
        assertExactlyOne(locator, "name", name);
        return new InputFieldHelper(page, TARGETTYPE.TEXT, locator);
    }

}
