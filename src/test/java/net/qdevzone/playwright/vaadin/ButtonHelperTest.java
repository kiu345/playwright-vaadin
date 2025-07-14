package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

class ButtonHelperTest {

    @Test
    void testFromId() {
        Locator locatorMockMain = Mockito.mock(Locator.class);
        Page pageMock = Mockito.mock(Page.class);
        when(pageMock.locator(anyString())).thenReturn(locatorMockMain);
        when(locatorMockMain.count()).thenReturn(1);

        ButtonHelper helper = ButtonHelper.fromId(pageMock, "test");
        assertThat(helper).isNotNull();
        helper.click();
        verify(locatorMockMain).click();
    }

    @Test
    void testFromText() {
        Locator locatorMockMain = Mockito.mock(Locator.class);
        Locator locatorMockButton = Mockito.mock(Locator.class);
        Page pageMock = Mockito.mock(Page.class);

        when(pageMock.locator(anyString())).thenReturn(locatorMockMain);

        when(locatorMockMain.getByText("Test")).thenReturn(locatorMockButton);

        when(locatorMockButton.count()).thenReturn(1);

        ButtonHelper helper = ButtonHelper.fromText(pageMock, "Test");
        assertThat(helper).isNotNull();
        helper.click();
        verify(locatorMockButton).click();
    }

    @Test
    void testFromTextFailOnMultiple() {
        Locator locatorMockMain = Mockito.mock(Locator.class);
        Locator locatorMockButton = Mockito.mock(Locator.class);
        Page pageMock = Mockito.mock(Page.class);

        when(pageMock.locator(anyString())).thenReturn(locatorMockMain);

        when(locatorMockMain.getByText("Test")).thenReturn(locatorMockButton);

        when(locatorMockButton.count()).thenReturn(2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ButtonHelper.fromText(pageMock, "Test");
        });

        assertThat(exception.getMessage()).contains("2>1");
    }
}
