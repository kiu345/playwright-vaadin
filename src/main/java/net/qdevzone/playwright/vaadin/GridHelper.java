package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GridHelper extends VaadinHelper {

    private Locator locator;

    protected GridHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
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

    public static GridHelper forId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        if (locator.count() != 1) {
            throw new IllegalArgumentException("Invalid number (" + locator.count() + "!=1) of elements found with id: " + id);
        }
        return new GridHelper(page, TARGETTYPE.ID, locator);
    }

}
