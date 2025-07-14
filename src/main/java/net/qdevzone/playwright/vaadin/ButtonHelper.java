package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ButtonHelper extends VaadinLocatorHelper {

    protected ButtonHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    public boolean isEnabled() {
        return locator.isEnabled();
    }

    @Override
    public boolean isVisible() {
        return locator.isVisible();
    }

    /**
     * Factory method to create an ButtonHelper based on an HTML ID.
     */
    public static ButtonHelper fromId(Page page, String id) {
        return new ButtonHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an ButtonHelper based on the button with a specific text.
     */
    public static ButtonHelper fromText(Page page, String text) {
        Locator locator = page.locator("vaadin-button").getByText(text);
        if (locator.count() > 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + ">1) of elements found with text: " + text);
        }
        return new ButtonHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an ButtonHelper based on the "nth" button with a specific text.
     */
    public static ButtonHelper fromText(Page page, String text, int nth) {
        Locator locator = page.locator("vaadin-button").getByText(text).nth(nth);
        if (locator.count() > 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + ">1) of elements found with text: " + text);
        }
        return new ButtonHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an ButtonHelper based on the button with a specific vaadin-menu-bar-button text.
     */
    public static ButtonHelper fromMenuText(Page page, String text) {
        Locator locator = page.locator("vaadin-menu-bar-button").getByText(text);
        if (locator.count() > 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + ">1) of elements found with text: " + text);
        }
        return new ButtonHelper(page, TARGETTYPE.TEXT, locator);
    }

    /**
     * Factory method to create an ButtonHelper based on the "nth" button with a specific vaadin-menu-bar-button text.
     */
    public static ButtonHelper fromMenuText(Page page, String text, int nth) {
        Locator locator = page.locator("vaadin-menu-bar-button").getByText(text).nth(nth);
        if (locator.count() > 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + ">1) of elements found with text: " + text);
        }
        return new ButtonHelper(page, TARGETTYPE.TEXT, locator);
    }

}
