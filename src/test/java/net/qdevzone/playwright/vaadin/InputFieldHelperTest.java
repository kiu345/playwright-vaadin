package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class InputFieldHelperTest {

    private static final String LOCATOR_PATTERN = new StringJoiner(", ")
            .add("vaadin-text-field > label")
            .add("vaadin-password-field > label")
            .add("vaadin-email-field > label")
            .add("vaadin-number-field > label")
            .toString();
    private static final String INPUT_TAG = "input";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        InputFieldHelper helper = InputFieldHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue("Test");
        verify(mock.input).fill(any());
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        InputFieldHelper helper = InputFieldHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue("Test");
        verify(mock.input).fill(any());
    }

}
