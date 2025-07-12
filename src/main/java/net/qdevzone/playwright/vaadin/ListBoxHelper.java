package net.qdevzone.playwright.vaadin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ListBoxHelper extends VaadinHelper {

    private final Locator locator;
    private final boolean multiSelect;

    protected ListBoxHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
        this.multiSelect = isMultiSelectComponent(locator);
    }

    private boolean isMultiSelectComponent(Locator locator) {
        String tagName = locator.evaluate("el => el.tagName.toLowerCase()").toString();
        return "vaadin-multi-select-list-box".equals(tagName);
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    /**
     * Selects an item in the list box by value.
     *
     * @param value The value to select.
     */
    public void select(String value) {
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (item.textContent().equals(value) && item.getAttribute("aria-selected").equals("false")) {
                item.click();
            }
        }
    }

    public void select(String... values) {
        List<String> valueList = Arrays.asList(values);
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (valueList.contains(item.textContent()) && item.getAttribute("aria-selected").equals("false")) {
                item.click();
            }
        }
    }

    /**
     * Selects an item in the list box by value.
     *
     * @param value The value to select.
     */
    public void unselect(String value) {
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (item.textContent().equals(value) && item.getAttribute("aria-selected").equals("true")) {
                item.click();
            }
        }
    }

    public void unselect(String... values) {
        List<String> valueList = Arrays.asList(values);
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (valueList.contains(item.textContent()) && item.getAttribute("aria-selected").equals("true")) {
                item.click();
            }
        }
    }

    /**
     * Returns the currently (first for multiselect) selected value of the list box.
     */
    public String getSelectedValue() {
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (item.getAttribute("aria-selected").equals("true")) {
                return item.innerText();
            }
        }
        return null;
    }

    /**
     * Gets the selected values. In single-select mode, returns a singleton list.
     */
    public List<String> getSelectedValues() {
        List<String> result = new ArrayList<>();
        for (Locator item : locator.locator("vaadin-item").all()) {
            if (item.getAttribute("aria-selected").equals("true")) {
                result.add(item.innerText());
            }
        }
        return result;
    }

    /**
     * Selects multiple values. Only works in multi-select mode.
     */
    public void selectMultiple(List<String> values) {
        if (!multiSelect) {
            throw new UnsupportedOperationException("selectMultiple() is only supported in multi-select mode.");
        }
        locator.evaluate(" (el, vals) => { el.selectedValues = vals; el.dispatchEvent(new CustomEvent('change', { bubbles: true })); }", values);
    }

    /**
     * Clears the selection (multi-select only).
     */
    public void clearSelection() {
        if (multiSelect) {
            locator.evaluate("el => el.selectedValues = []");
        }
        else {
            locator.evaluate("el => el.selected = null");
        }
    }

    /**
     * Returns all values (item values) currently available in the list box.
     */
    public List<String> getItems() {
        JSHandle handle = locator.evaluateHandle("el => Array.from(el.items).map(item => item.value)");
        Object raw = handle.jsonValue();

        if (raw instanceof List<?>) {
            return ((List<?>) raw).stream()
                    .map(Object::toString)
                    .toList();
        }
        else {
            throw new IllegalStateException("Expected a list of items, but got: " + raw.getClass());
        }
    }

    /**
     * Checks whether the list box is visible.
     */
    public boolean isVisible() {
        return locator.isVisible();
    }

    /**
     * Checks whether the list box is enabled.
     */
    public boolean isEnabled() {
        return locator.isEnabled();
    }

    /**
     * Creates a ListBoxHelper from a component ID.
     */
    public static ListBoxHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new ListBoxHelper(page, TARGETTYPE.ID, locator);
    }

    /**
     * Creates a ListBoxHelper from a data-testid attribute.
     */
    public static ListBoxHelper fromTestId(Page page, String testId) {
        Locator locator = page.getByTestId(testId);
        assertExactlyOne(locator, "testId", testId);
        return new ListBoxHelper(page, TARGETTYPE.TESTID, locator);
    }
}
