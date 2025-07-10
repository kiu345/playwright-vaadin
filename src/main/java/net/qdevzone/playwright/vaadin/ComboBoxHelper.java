package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ComboBoxHelper extends VaadinHelper {
    private final Locator locator;

    protected ComboBoxHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
    }

    public Locator getLocator() {
        return locator;
    }

    public void setValue(String value) {
        locator.click();
        Locator overlay = page.locator("vaadin-combo-box-overlay");
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.VISIBLE));
        overlay.getByText(value).click();
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.HIDDEN));
    }

    public String getValue() {
        Object element = locator.evaluate("combo => combo.querySelector('input').value");
        return element != null ? element.toString() : null;
    }

    public static ComboBoxHelper forId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new ComboBoxHelper(page, TARGETTYPE.ID, locator);
    }
}
