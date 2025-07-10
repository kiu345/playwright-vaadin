package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ButtonHelper extends VaadinHelper {

    private Locator locator;

    protected ButtonHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
    }

    public void click() {
        locator.click();
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public boolean isEnabled() {
        return locator.isEnabled();
    }

    public static ButtonHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new ButtonHelper(page, TARGETTYPE.ID, locator);
    }

    public static ButtonHelper fromText(Page page, String text) {
        Locator locator = page.locator("vaadin-button").getByText(text);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with text: " + text);
        }
        return new ButtonHelper(page, TARGETTYPE.TEXT, locator);
    }

}
