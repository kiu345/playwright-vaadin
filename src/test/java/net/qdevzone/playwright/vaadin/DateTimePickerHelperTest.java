package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class DateTimePickerHelperTest {

    private static final String LOCATOR_PATTERN = "vaadin-date-time-picker > label";
    private static final String INPUT_TAG = "input";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        DateTimePickerHelper helper = DateTimePickerHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalDateTime.of(2020,12,24,20, 30));
        verify(mock.element).evaluate(anyString(), eq("2020-12-24T20:30"));
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        DateTimePickerHelper helper = DateTimePickerHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalDateTime.of(2020,12,24,20, 30));
        verify(mock.element).evaluate(anyString(), eq("2020-12-24T20:30"));
    }

}
