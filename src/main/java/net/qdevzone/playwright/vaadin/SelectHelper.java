package net.qdevzone.playwright.vaadin;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class SelectHelper extends VaadinFieldHelper {

    private static final String ELEMENT_LOCATOR_NAME = "vaadin-select-item";

    protected SelectHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    public void setValue(String value) {
        locator.click();
        Locator overlay = page.locator("vaadin-select-overlay");
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.VISIBLE));
        overlay.getByText(value).click();
        overlay.getByText(value).waitFor((new WaitForOptions()).setState(WaitForSelectorState.HIDDEN));
    }

    public String getValue() {
        Locator itemLocator = locator.locator(ELEMENT_LOCATOR_NAME);
        for (Locator item : itemLocator.all()) {
            if (item.getAttribute("selected") != null) {
                return item.getAttribute("value");
            }
        }
        return null;
    }

    public String getText() {
        Locator itemLocator = locator.locator(ELEMENT_LOCATOR_NAME);
        for (Locator item : itemLocator.all()) {
            if (item.getAttribute("selected") != null) {
                return item.textContent();
            }
        }

        return null;
    }

    @Override
    public boolean isEnabled() {
        String disabled = locator.getAttribute("disabled");
        return disabled == null;
    }

    public Map<String, String> getValues() {
        Locator itemLocator = locator.locator(ELEMENT_LOCATOR_NAME);
        Map<String, String> result = new HashMap<>();
        for (Locator item : itemLocator.all()) {
            result.put(item.getAttribute("value"), item.textContent());
        }
        return result;
    }

    /**
     * Factory method to create an SelectHelper based on an HTML ID.
     */
    public static SelectHelper fromId(Page page, String id) {
        return new SelectHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an SelectHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static SelectHelper fromName(Page page, String name) {
        return new SelectHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an SelectHelper for a element with a given label.
     */
    public static SelectHelper fromLabel(Page page, String label) {
        return new SelectHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-select", label));
    }

    /**
     * Factory method to create an SelectHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static SelectHelper fromTestId(Page page, String testId) {
        return new SelectHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
