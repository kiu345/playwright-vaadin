package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class VaadinHelper {
    public enum TARGETTYPE {
        ID,
        TEXT,
        LABEL
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
    
    protected static void assertExactlyOne(Locator locator) {
        int count = locator.count();
        if (count != 1) {
            throw new IllegalArgumentException("Expected exactly 1 element for " + locator.toString() + ", but found " + count);
        }
    }

    protected static void assertExactlyOne(Locator locator, String method, String value) {
        int count = locator.count();
        if (count != 1) {
            throw new IllegalArgumentException("Expected exactly 1 element for " + method + ": \"" + value + "\", but found " + count);
        }
    }

}
