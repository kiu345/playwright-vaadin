package net.qdevzone.playwright.vaadin;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;

public final class VaadinUtils {
    private VaadinUtils() {
    }

    public static void waitForLoadingFinished(Page page) {
        assertThat(page.locator("vaadin-connection-indicator[loading]")).hasCount(0);
    }

}
