package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckboxHelper extends VaadinHelper {

    private final Locator locator;
    private final Locator input;

    protected CheckboxHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
        this.input = locator.locator("input[type='checkbox']");
        assertExactlyOne(input);
    }

    /**
     * Clicks the checkbox.
     */
    public void click() {
        input.click();
    }

    /**
     * Sets the checkbox to the given value (checked/unchecked).
     * 
     * @param checked true to check the box, false to uncheck
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

    /**
     * Returns whether the checkbox is enabled.
     */
    public boolean isEnabled() {
        return input.isEnabled();
    }

    /**
     * Returns whether the checkbox is visible.
     */
    public boolean isVisible() {
        return input.isVisible();
    }

    /**
     * Returns the label text associated with the checkbox, if available.
     */
    public String getLabel() {
        return locator.getAttribute("label");
    }

    /**
     * Creates a helper for a checkbox by its ID.
     */
    public static CheckboxHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new CheckboxHelper(page, TARGETTYPE.ID, locator);
    }

    /**
     * Creates a helper for a checkbox by its label.
     */
    public static CheckboxHelper fromLabel(Page page, String label) {
        Locator locator = page.locator("vaadin-checkbox[label='" + label + "']");
        assertExactlyOne(locator, "label", label);
        return new CheckboxHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Creates a helper for a checkbox by its data-testid attribute.
     */
    public static CheckboxHelper fromTestId(Page page, String testId) {
        Locator locator = page.getByTestId(testId);
        assertExactlyOne(locator, "testId", testId);
        return new CheckboxHelper(page, TARGETTYPE.TESTID, locator);
    }

}
