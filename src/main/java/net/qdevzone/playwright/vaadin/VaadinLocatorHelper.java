package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class VaadinLocatorHelper extends VaadinHelper {

    protected final Locator locator;

    protected VaadinLocatorHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertOne(locator);
    }

    public Locator getLocator() {
        return locator;
    }

    public void hover() {
        locator.hover();
    }

    /**
     * Clicks the element.
     */
    public void click() {
        locator.click();
    }

    /**
     * Doubleclicks the element.
     */
    public void dblclick() {
        locator.dblclick();
    }

    /**
     * Checks whether the element is visible in the current viewport.
     */
    public boolean isVisible() {
        return locator.isVisible();
    }

}
