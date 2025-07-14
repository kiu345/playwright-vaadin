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
public class RadioButtonGroupHelper extends VaadinFieldHelper {

    protected RadioButtonGroupHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
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
     * Returns the currently selected value of the RadioButtonGroup.
     * 
     * @return selected value as String, or null if none selected
     */
    public String getValue() {
        return (String) locator.evaluate("el => el.value");
    }

    /**
     * Clears the selection in the RadioButtonGroup.
     */
    public void clear() {
        setValue(null);
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

    @Override
    public boolean isEnabled() {
        String disabled = locator.getAttribute("disabled");
        return disabled == null;
    }

    /**
     * Factory method to locate a RadioButtonGroup by its id attribute.
     */
    public static RadioButtonGroupHelper fromId(Page page, String id) {
        return new RadioButtonGroupHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an RadioButtonGroupHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static RadioButtonGroupHelper fromName(Page page, String name) {
        return new RadioButtonGroupHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to locate a RadioButtonGroup by its label attribute.
     */
    public static RadioButtonGroupHelper fromLabel(Page page, String label) {
        return new RadioButtonGroupHelper(page, TARGETTYPE.LABEL, locateByLabel(page, "vaadin-radio-group", label));
    }

    /**
     * Factory method to create an RadioButtonGroupHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static RadioButtonGroupHelper fromTestId(Page page, String testId) {
        return new RadioButtonGroupHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
