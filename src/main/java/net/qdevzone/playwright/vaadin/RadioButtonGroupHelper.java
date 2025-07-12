package net.qdevzone.playwright.vaadin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Helper class for interacting with Vaadin RadioButtonGroup components in Playwright tests.
 * Supports reading and setting the selected value, checking available options, visibility, and enabled state.
 */
public class RadioButtonGroupHelper extends VaadinHelper {

    private final Locator locator;

    protected RadioButtonGroupHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
    }

    /**
     * Returns the currently selected value of the RadioButtonGroup.
     * 
     * @return selected value as String, or null if none selected
     */
    public String getValue() {
        return (String) locator.evaluate("el => el.value");
    }

    /**
     * Selects the option with the given value.
     * 
     * @param value option value to select
     */
    public void setValue(String value) {
        locator.evaluate("(el,value) => el.value = value", value);
    }

    /**
     * Selects the option with the given option text.
     * 
     * @param value option text to select
     */
    public void setOption(String optionText) {
        Map<String, String> items = getItems();
        String value = items.entrySet()
                .stream().filter(e -> e.getValue().equals(optionText))
                .map(Entry::getKey)
                .findFirst()
                .orElse(null);
        setValue(value);
    }

    /**
     * Returns all possible item values in the RadioButtonGroup.
     * 
     * @return list of item values as Strings
     */
    public Map<String, String> getItems() {
        Locator inputLocator = locator.locator("vaadin-radio-button");
        Map<String, String> result = new HashMap<>();
        for (Locator item : inputLocator.all()) {
            String value = (String) item.locator("input").evaluate("el => el.value");
            String text = item.locator("label").textContent();
            result.put(value, text);
        }
        return result;
    }

    /**
     * Checks whether the RadioButtonGroup is visible.
     * 
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return locator.isVisible();
    }

    /**
     * Checks whether the RadioButtonGroup is enabled (not disabled).
     * 
     * @return true if enabled, false otherwise
     */
    public boolean isEnabled() {
        String disabled = locator.getAttribute("disabled");
        return disabled == null;
    }

    /**
     * Clears the selection in the RadioButtonGroup.
     */
    public void clear() {
        setValue(null);
    }

    /**
     * Factory method to locate a RadioButtonGroup by its id attribute.
     * 
     * @param page Playwright Page
     * @param id   id attribute of the RadioButtonGroup
     * @return RadioButtonGroupHelper instance
     */
    public static RadioButtonGroupHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new RadioButtonGroupHelper(page, TARGETTYPE.ID, locator);
    }

    /**
     * Factory method to locate a RadioButtonGroup by its label attribute.
     * 
     * @param page  Playwright Page
     * @param label label attribute of the RadioButtonGroup
     * @return RadioButtonGroupHelper instance
     */
    public static RadioButtonGroupHelper fromLabel(Page page, String label) {
        Locator locator = page.locator("vaadin-radio-group[label='" + label + "']");
        assertExactlyOne(locator, "label", label);
        return new RadioButtonGroupHelper(page, TARGETTYPE.TEXT, locator);
    }

}
