package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * A helper class for interacting with Vaadin combo box components
 * in Playwright-based UI tests. This class abstracts common actions and validations on
 * Vaadin combo box for test convenience and clarity.
 */

public class ComboBoxHelper extends VaadinFieldHelper implements TextInputElementHelper {

    /**
     * The HTML input field
     */
    private final Locator input;

    /**
     * Constructs an ComboBoxHelper for the given locator.
     */
    protected ComboBoxHelper(Page page, TARGETTYPE targetType, Locator locator) {
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
        locator.click();
        Locator overlay = page.locator("vaadin-combo-box-overlay");
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.VISIBLE));
        overlay.getByText(value).click();
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.HIDDEN));
    }

    @Override
    public String getValue() {
        Object element = locator.evaluate("combo => combo.querySelector('input').value");
        return element != null ? element.toString() : null;
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
        input.focus();
    }

    @Override
    public void blur() {
        page.evaluate("el => el.blur()", input);
    }

    /**
     * Factory method to create an CheckboxHelper based on an HTML ID.
     */
    public static ComboBoxHelper fromId(Page page, String id) {
        return new ComboBoxHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an TextAreaHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static ComboBoxHelper fromName(Page page, String name) {
        return new ComboBoxHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an CheckboxHelper for a element with a given label.
     */
    public static ComboBoxHelper fromLabel(Page page, String label) {
        return new ComboBoxHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-combo-box", label));
    }

    /**
     * Factory method to create an ComboBoxHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static ComboBoxHelper fromTestId(Page page, String testId) {
        return new ComboBoxHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
