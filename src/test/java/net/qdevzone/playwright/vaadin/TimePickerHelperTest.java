package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class TimePickerHelperTest {

    private static final String LOCATOR_PATTERN = "vaadin-time-picker > label";
    private static final String INPUT_TAG = "input";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        TimePickerHelper helper = TimePickerHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalTime.of(20, 30));
        verify(mock.element).evaluate(anyString(), eq("20:30"));
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        TimePickerHelper helper = TimePickerHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalTime.of(20, 30));
        verify(mock.element).evaluate(anyString(), eq("20:30"));
    }

}
