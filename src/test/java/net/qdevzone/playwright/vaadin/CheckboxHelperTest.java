package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class CheckboxHelperTest {
    private static final String INPUT_TAG = "input[type='checkbox']";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        CheckboxHelper helper = CheckboxHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setChecked(true);
        verify(mock.input).click();
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock("vaadin-checkbox > label", INPUT_TAG);

        CheckboxHelper helper = CheckboxHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setChecked(true);
        verify(mock.input).click();
    }

}
