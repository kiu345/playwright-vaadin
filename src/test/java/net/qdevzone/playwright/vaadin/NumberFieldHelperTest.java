package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class NumberFieldHelperTest {

    private static final String LOCATOR_PATTERN = new StringJoiner(", ")
            .add("vaadin-number-field > label")
            .add("vaadin-integer-field > label")
            .toString();
    private static final String INPUT_TAG = "input";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        NumberFieldHelper helper = NumberFieldHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue(9);
        verify(mock.input).fill(eq("9"));
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        NumberFieldHelper helper = NumberFieldHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue(9);
        verify(mock.input).fill(eq("9"));
    }

}
