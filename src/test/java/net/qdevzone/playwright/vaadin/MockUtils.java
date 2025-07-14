package net.qdevzone.playwright.vaadin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class MockUtils {

    private MockUtils() {
    }

    public static class InputElementMock {
        public Page page = Mockito.mock(Page.class);
        public Locator element = Mockito.mock(Locator.class);
        public Locator label = Mockito.mock(Locator.class);
        public Locator input = Mockito.mock(Locator.class);
    }

    public static InputElementMock createIdInputMock(String id, String inputLocator) {
        InputElementMock mock = new InputElementMock();

        when(mock.page.locator(anyString())).thenReturn(mock.element);
        when(mock.element.count()).thenReturn(1);
        when(mock.element.locator(startsWith(inputLocator))).thenReturn(mock.input);
        when(mock.element.locator("label")).thenReturn(mock.label);

        when(mock.input.count()).thenReturn(1);
        when(mock.label.count()).thenReturn(1);

        return mock;
    }

    public static InputElementMock createLabeledInputMock(String elementLabelPattern, String inputLocator) {
        InputElementMock mock = new InputElementMock();

        when(mock.page.locator(elementLabelPattern)).thenReturn(mock.label);
        when(mock.label.locator("..")).thenReturn(mock.element);

        when(mock.element.locator(startsWith(inputLocator))).thenReturn(mock.input);
        when(mock.element.count()).thenReturn(1);

        Locator tooManyElementsMock = Mockito.mock(Locator.class);
        when(tooManyElementsMock.count()).thenReturn(99);
        when(tooManyElementsMock.locator("..")).thenReturn(mock.element);

        when(mock.label.filter(any(Locator.FilterOptions.class))).thenReturn(tooManyElementsMock);
        when(mock.label.locator(startsWith("text="))).thenReturn(mock.label);
        when(mock.label.count()).thenReturn(1);

        when(mock.input.count()).thenReturn(1);

        return mock;
    }

}
