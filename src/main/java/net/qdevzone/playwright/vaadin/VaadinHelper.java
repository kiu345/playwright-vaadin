package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Generic helper base class
 */
public abstract class VaadinHelper {
    public enum TARGETTYPE {
        ID,
        NAME,
        TEXT,
        LABEL,
        TESTID
    }

    protected final Page page;
    protected final TARGETTYPE targetType;

    protected VaadinHelper(Page page, TARGETTYPE targetType) {
        this.page = page;
        this.targetType = targetType;
    }

    public TARGETTYPE getTargetType() {
        return targetType;
    }

    /**
     * Checks for 1 or 0 elements with this locator.
     * Hidden elements do not appear in the DOM, so 0 could be a valid result for them, too.
     */
    protected static void assertOne(Locator locator) {
        int count = locator.count();
        if (count > 1) {
            throw new IllegalArgumentException("Expected 1 element for " + locator.toString() + ", but found " + count);
        }
    }

    /**
     * Checks for 1 or 0 elements with this locator.
     * Hidden elements do not appear in the DOM, so 0 could be a valid result for them, too.
     */
    protected static void assertOne(Locator locator, String method, String value) {
        int count = locator.count();
        if (count > 1) {
            throw new IllegalArgumentException("Expected 1 element for " + method + ": \"" + value + "\", but found " + count);
        }
    }

    /**
     * Checks for exactly 1 element with this locator.
     */
    protected static void assertExactlyOne(Locator locator) {
        int count = locator.count();
        if (count != 1) {
            throw new IllegalArgumentException("Expected exactly 1 element for " + locator.toString() + ", but found " + count);
        }
    }

    /**
     * Checks for exactly 1 element with this locator.
     */
    protected static void assertExactlyOne(Locator locator, String method, String value) {
        int count = locator.count();
        if (count != 1) {
            throw new IllegalArgumentException("Expected exactly 1 element for " + method + ": \"" + value + "\", but found " + count);
        }
    }

    protected static Locator locateById(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return locator;
    }

    protected static Locator locateByName(Page page, String name) {
        Locator locator = page.locator("vaadin-text-area[name='$']".replace("$", name));
        assertOne(locator, "name", name);
        return locator;
    }

    protected static Locator locateByTestId(Page page, String testId) {
        Locator locator = page.getByTestId(testId);
        assertOne(locator, "testId", testId);
        return locator;
    }

    protected static Locator locateByLabel(Page page, String fieldElement, String label) {
        Locator labelElement = page.locator(fieldElement + " > label")
                .locator("text=\"${label}\"".replace("${label}", label));
        assertExactlyOne(labelElement, "label text", label);
        return labelElement.locator("..");
    }

}
