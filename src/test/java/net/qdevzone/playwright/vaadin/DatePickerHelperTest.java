package net.qdevzone.playwright.vaadin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import net.qdevzone.playwright.vaadin.MockUtils.InputElementMock;

class DatePickerHelperTest {

    private static final String LOCATOR_PATTERN = "vaadin-date-picker > label";
    private static final String INPUT_TAG = "input";

    @Test
    void testFromId() {
        InputElementMock mock = MockUtils.createIdInputMock("test", INPUT_TAG);

        DatePickerHelper helper = DatePickerHelper.fromId(mock.page, "test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalDate.of(2020, 12, 24));
        verify(mock.element).evaluate(anyString(), eq("2020-12-24"));
    }

    @Test
    void testFromLabel() {
        InputElementMock mock = MockUtils.createLabeledInputMock(LOCATOR_PATTERN, INPUT_TAG);

        DatePickerHelper helper = DatePickerHelper.fromLabel(mock.page, "Test");
        assertThat(helper).isNotNull();

        helper.setValue(LocalDate.of(2020, 12, 24));
        verify(mock.element).evaluate(anyString(), eq("2020-12-24"));
    }

}
