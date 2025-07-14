package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GridHelper extends VaadinLocatorHelper {

    protected GridHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType, locator);
    }

    public int getColumnCount() {
        return locator.locator("vaadin-grid-column").count();
    }

    public String getCellContent(int column, int row) {
        int columns = getColumnCount();
        int index = (columns * 3) + (columns * row) + column;
        Locator cell = locator.locator("vaadin-grid-cell-content").nth(index);
        return cell.textContent();
    }

    /**
     * Factory method to locate a GridHelper by its id attribute.
     */
    public static GridHelper fromId(Page page, String id) {
        return new GridHelper(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an GridHelper based on the 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static GridHelper fromName(Page page, String name) {
        return new GridHelper(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an GridHelper based on the 'testId' attribute.
     * Note: Vaadin typically does not expose the 'testId' attribute via API by default.
     */
    public static GridHelper fromTestId(Page page, String testId) {
        return new GridHelper(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }

}
