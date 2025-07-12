package net.qdevzone.playwright.vaadin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Helper class for uploading content via Vaadin Upload component in Playwright tests.
 */
public class UploadHelper extends VaadinHelper {

    private final Locator locator;
    private final Locator input;

    protected UploadHelper(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
        assertExactlyOne(locator);
        this.input = locator.locator("input[type='file']");
        assertExactlyOne(input);
    }

    /**
     * Uploads a string as a file.
     * 
     * @param content  the file content
     * @param fileName the name to assign to the uploaded file
     */
    public void upload(String content, String fileName) {
        upload(content.getBytes(), fileName);
    }

    /**
     * Uploads binary data as a file.
     * 
     * @param data     file content
     * @param fileName name of the file
     */
    public void upload(byte[] data, String fileName) {
        try {
            Path tempFile = Files.createTempFile("playwright-upload-", "-" + fileName);
            Files.write(tempFile, data);
            input.setInputFiles(tempFile);
            tempFile.toFile().deleteOnExit();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to upload file content", e);
        }
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public boolean isEnabled() {
        return locator.isEnabled();
    }

    public static UploadHelper fromId(Page page, String id) {
        Locator locator = page.locator("#" + id);
        assertExactlyOne(locator, "id", id);
        return new UploadHelper(page, TARGETTYPE.ID, locator);
    }

    public static UploadHelper fromTestId(Page page, String testId) {
        Locator locator = page.getByTestId(testId);
        assertExactlyOne(locator, "testId", testId);
        return new UploadHelper(page, TARGETTYPE.TESTID, locator);
    }
}
