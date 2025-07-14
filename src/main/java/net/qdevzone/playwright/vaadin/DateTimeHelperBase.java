package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class DateTimeHelperBase<T> extends VaadinFieldHelper {

    protected final Locator input;

    protected DateTimeHelperBase(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
        this.input = locator.locator("input");
    }

    public void setValue(String textValue) {
        locator.evaluate("(element, value) => { element.value = value; }", textValue);
    }

    /**
     * Types the given text into the element with synthetic key events.
     * This simulates real user typing behavior.
     */
    public void type(String text) {
        input.pressSequentially(text);
    }

    /**
     * Types the given text character by character with a fixed delay between each character.
     */
    public void type(String text, int delay) {
        for (char c : text.toCharArray()) {
            input.press(String.valueOf(c));
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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

    public abstract void setValue(T value);

    public abstract T getValue();

    public String getValueText() {
        return (String) locator.evaluate("element => element.value");
    }
}
