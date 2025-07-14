package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class TextAreaHelperTest {

    private static final String LOCATOR_PATTERN = "vaadin-text-area > label";
    private static final String INPUT_TAG = "textarea";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        TextAreaHelper helper = TextAreaHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue("Test");
        verify(mock.input).fill(any());
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        TextAreaHelper helper = TextAreaHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue("Test");
        verify(mock.input).fill(any());
    }

}
