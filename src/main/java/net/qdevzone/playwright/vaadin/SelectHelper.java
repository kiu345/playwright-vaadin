package net.qdevzone.playwright.vaadin;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class SelectHelper extends VaadinHelper {
    private final Locator locator;

    private static final String ELEMENT_LOCATOR_NAME = "vaadin-select-item";

    protected SelectHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
    }

    public Locator getLocator() {
        return locator;
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

    public Map<String, String> getValues() {
        Locator itemLocator = locator.locator(ELEMENT_LOCATOR_NAME);
        Map<String, String> result = new HashMap<>();
        for (Locator item : itemLocator.all()) {
            result.put(item.getAttribute("value"), item.textContent());
        }
        return result;
    }

    public static SelectHelper forId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new SelectHelper(page, TARGETTYPE.ID, locator);
    }
}
