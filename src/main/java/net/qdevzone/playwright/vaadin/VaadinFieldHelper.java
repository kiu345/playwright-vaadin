package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class VaadinFieldHelper extends VaadinLocatorHelper {

    protected VaadinFieldHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    /**
     * Retrieves the label attribute from the element.
     *
     * @return the label string, or null if not set/not available
     */
    public String getLabel() {
        return locator.locator("label").textContent();
    }

    /**
     * Checks whether the element is marked as required.
     */
    public boolean isRequired() {
        return "true".equalsIgnoreCase(locator.getAttribute("required"))
                || "".equalsIgnoreCase(locator.getAttribute("required"));
    }

    /**
     * Checks whether the element is marked as read-only.
     */
    public boolean isReadOnly() {
        return "true".equalsIgnoreCase(locator.getAttribute("readonly"))
                || "".equalsIgnoreCase(locator.getAttribute("readonly"));
    }

    /**
     * Checks whether the component is marked as invalid.
     *
     * @return true if the field has the 'invalid' attribute set, false otherwise
     */
    public boolean hasValidationError() {
        return locator.getAttribute("invalid") != null;
    }

    /**
     * Checks whether an error message is visible inside the shadow DOM.
     *
     * @return true if an error message is displayed, false otherwise
     */
    public boolean hasErrorMessage() {
        Locator error = locator.locator("::shadow [part='error-message']");
        return error.isVisible();
    }

    /**
     * Checks whether the element is enabled (i.e. not disabled).
     */
    public abstract boolean isEnabled();
}
