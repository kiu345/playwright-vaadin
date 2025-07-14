package net.qdevzone.playwright.vaadin.addons;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import net.qdevzone.playwright.vaadin.VaadinHelper;

/**
 * Helper for https://vaadin.com/directory/component/grid-exporter-add-on
 */
public class GridExporter extends VaadinHelper {

    @SuppressWarnings("unused")
    private final Locator gridLocator;

    protected GridExporter(Page page, TARGETTYPE targetType, Locator gridLocator) {
        super(page, targetType);
        this.gridLocator = gridLocator;
        if (!"VAADIN-GRID".equalsIgnoreCase(gridLocator.elementHandle().getProperty("nodeName").toString())) {
            throw new IllegalArgumentException("node is not a VAADIN-GRID");
        }
    }
    /**
     * Factory method to locate a GridExporter grid element by its id attribute.
     */
    public static GridExporter fromId(Page page, String id) {
        return new GridExporter(page, TARGETTYPE.ID, locateById(page, id));
    }

    /**
     * Factory method to create an GridHelper based on the grids 'name' attribute.
     * Note: Vaadin typically does not expose the 'name' attribute via API by default.
     */
    public static GridExporter fromName(Page page, String name) {
        return new GridExporter(page, TARGETTYPE.NAME, locateByName(page, name));
    }

    /**
     * Factory method to create an GridHelper based on the grids 'testid' attribute.
     * Note: Vaadin typically does not expose the 'testid' attribute via API by default.
     */
    public static GridExporter fromTestId(Page page, String testId) {
        return new GridExporter(page, TARGETTYPE.TESTID, locateByTestId(page, testId));
    }
}
