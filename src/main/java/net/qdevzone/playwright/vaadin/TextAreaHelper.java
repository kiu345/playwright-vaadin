package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TextAreaHelper extends VaadinHelper {

    private final Locator locator;
    private final Locator input;

    /**
     * Constructs an TextAreaHelper for the given locator.
     *
     * @param page       the Playwright Page instance
     * @param targetType the type of locator used (ID, LABEL, NAME, etc.)
     * @param locator    the Vaadin component locator
     */
    protected TextAreaHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
        this.input = locator.locator("textarea");
        assertExactlyOne(input);
    }

    /**
     * Clicks on the outer component of the Vaadin text area.
     */
    public void click() {
        locator.click();
    }

    /**
     * Sets the value of the text area by replacing the current content.
     *
     * @param value the string value to fill into the text area
     */
    public void setValue(String value) {
        input.fill(value);
    }

    /**
     * Types the given text into the text area with synthetic key events.
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
     * Retrieves the current value of the text area.
     *
     * @return the value currently set in the area
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
     * Retrieves the placeholder text of the text area.
     *
     * @return the placeholder string, or null if none is set
     */
    public String getPlaceholder() {
        return input.getAttribute("placeholder");
    }

    /**
     * Retrieves the label attribute from the text area.
     *
     * @return the label string, or null if not set
     */
    public String getLabel() {
        return locator.getAttribute("label");
    }

    /**
     * Checks whether the text area is marked as required.
     *
     * @return true if the field is required, false otherwise
     */
    public boolean isRequired() {
        return "true".equalsIgnoreCase(locator.getAttribute("required"));
    }

    /**
     * Checks whether the text area is marked as read-only.
     *
     * @return true if the field is read-only, false otherwise
     */
    public boolean isReadOnly() {
        return "true".equals(locator.getAttribute("readonly"));
    }

    /**
     * Checks whether the text area is visible in the current viewport.
     *
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return input.isVisible();
    }

    /**
     * Checks whether the text area is enabled (i.e. not disabled).
     *
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        return input.isEnabled();
    }

    /**
     * Simulates pressing the Enter key while the text area is focused.
     */
    public void pressEnter() {
        input.press("Enter");
    }

    /**
     * Clears the text area by filling it with an empty string.
     */
    public void clear() {
        input.fill("");
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

    /**
     * Factory method to create an TextAreaHelper based on an HTML ID.
     *
     * @param page the Playwright page
     * @param id   the element ID
     * @return a new TextAreaHelper instance
     */
    public static TextAreaHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new TextAreaHelper(page, TARGETTYPE.ID, locator);
    }

    /**
     * Factory method to create an TextAreaHelper for a Vaadin field with a given label.
     *
     * @param page  the Playwright page
     * @param label the label text of the field
     * @return a new TextAreaHelper instance
     */
    public static TextAreaHelper fromLabel(Page page, String label) {
        String selector = "vaadin-text-area";
        Locator locator = page.locator(selector).getByLabel(label);
        assertExactlyOne(locator, "label", label);
        return new TextAreaHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an TextAreaHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     *
     * @param page the Playwright page
     * @param name the name attribute value
     * @return a new TextAreaHelper instance
     */
    public static TextAreaHelper fromName(Page page, String name) {
        Locator locator = page.locator("vaadin-text-area[name='$']".replace("$", name));
        assertExactlyOne(locator, "name", name);
        return new TextAreaHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an TextAreaHelper based on the 'testId' attribute.
     *
     * @param page the Playwright page
     * @param testId the testId
     * @return a new TextAreaHelper instance
     */
    public static TextAreaHelper fromTestId(Page page, String testId) {
        Locator locator = page.locator("vaadin-text-area").getByTestId(testId);
        assertExactlyOne(locator, "testId", testId);
        return new TextAreaHelper(page, TARGETTYPE.TEXT, locator);
    }

}
