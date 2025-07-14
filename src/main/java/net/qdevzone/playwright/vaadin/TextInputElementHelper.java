package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;

/**
 * Element that has some kind of a text box which can be used with a keyboard.
 */
public interface TextInputElementHelper {

    public Locator getInputLocator();

    /**
     * Sets the text value of the element by replacing the current content.
     */
    public void setValue(String value);

    /**
     * Types the given text into the element with synthetic key events.
     * This simulates real user typing behavior.
     */
    public default void type(String text) {
        getInputLocator().pressSequentially(text);
    }

    /**
     * Types the given text character by character with a fixed delay between each character.
     */
    public default void type(String text, int delay) {
        for (char c : text.toCharArray()) {
            getInputLocator().press(String.valueOf(c));
            try {
                Thread.sleep(delay);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Retrieves the current value of the element.
     */
    public String getValue();

    /**
     * Simulates pressing the Enter key while the field is focused.
     */
    public default void pressEnter() {
        getInputLocator().press("Enter");
    }

    /**
     * Clears the input field by filling it with an empty string.
     */
    public default void clear() {
        getInputLocator().fill("");
    }

    /**
     * Sets focus on the input field.
     */
    public void focus();

    /**
     * Removes focus from the input field (simulates blur event).
     */
    public void blur();
}
