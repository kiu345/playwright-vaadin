package net.qdevzone.playwright.vaadin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class DateTimeHelperBase<T> extends VaadinHelper {

    protected final Locator locator;

    protected DateTimeHelperBase(Page page, TARGETTYPE targetType, Locator locator) {
        super(page, targetType);
        this.locator = locator;
    }

    public void setValue(String textValue) {
//      locator.evaluate("(date, value) => { date.querySelector('input').value = value; }", textValue);
      locator.evaluate("(element, value) => { element.value = value; }", textValue);
    }

    public abstract void setValue(T value);

    public abstract T getValue();

    public String getValueText() {
//      locator.evaluate("(date, value) => { date.querySelector('input').value = value; }", textValue);
      return (String) locator.evaluate("element => element.value");
    }
}
