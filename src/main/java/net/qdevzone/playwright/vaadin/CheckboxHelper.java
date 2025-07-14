package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckboxHelper extends VaadinFieldHelper {

    private final Locator input;

    protected CheckboxHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
        this.input = locator.locator("input[type='checkbox']");
        assertExactlyOne(input);
    }

    /**
     * Sets the checkbox to the given value (checked/unchecked).
     */
    public void setChecked(boolean checked) {
        if (isChecked() != checked) {
            click();
        }
    }

    /**
     * Returns whether the checkbox is currently checked.
     */
    public boolean isChecked() {
        return Boolean.parseBoolean(input.getAttribute("checked"));
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
     * Factory method to create an CheckboxHelper based on an HTML ID.
     */
    public static CheckboxHelper fromId(Page page, String id) {
        return new CheckboxHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an CheckboxHelper for a element with a given label.
     */
    public static CheckboxHelper fromLabel(Page page, String label) {
        return new CheckboxHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-checkbox", label));
    }

    /**
     * Factory method to create an CheckboxHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static CheckboxHelper fromTestId(Page page, String testId) {
        return new CheckboxHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }

}
