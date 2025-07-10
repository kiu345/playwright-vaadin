package net.qdevzone.playwright.vaadin;

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
}
