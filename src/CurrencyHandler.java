import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Set;

public class CurrencyHandler extends DefaultHandler {
    private Set<Currency> currencySet;
    private Currency currency;

    private final StringBuilder data = new StringBuilder();

    private boolean isNumCodeFieldUsed;
    private boolean isCharCodeFieldUsed;
    private boolean isNominalFieldUsed;
    private boolean isNameFieldUsed;
    private boolean isValueFieldUsed;

    public CurrencyHandler(Set<Currency> currencySet) {
        this.currencySet = currencySet;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "Valute":
                currency = new Currency();
                currency.setId(attributes.getValue("ID"));
                break;
            case "NumCode":
                isNumCodeFieldUsed = true;
                break;
            case "CharCode":
                isCharCodeFieldUsed = true;
                currency.setCharCode(attributes.getValue("CharCode"));
                break;
            case "Nominal":
                isNominalFieldUsed = true;
                break;
            case "Name":
                isNameFieldUsed = true;
                currency.setName(attributes.getValue("Name"));
                break;
            case "Value":
                isValueFieldUsed = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Valute")) {
            currencySet.add(currency);
        }

        if (isNumCodeFieldUsed) {
            currency.setNumCode(Short.parseShort(data.toString()));
            isNumCodeFieldUsed = false;
        }
        if (isCharCodeFieldUsed) {
            currency.setCharCode(data.toString());
            isCharCodeFieldUsed = false;
        }
        if (isNominalFieldUsed) {
            currency.setNominal(Short.parseShort(data.toString()));
            isNominalFieldUsed = false;
        }
        if (isNameFieldUsed) {
            currency.setName(data.toString());
            isNameFieldUsed = false;
        }
        if (isValueFieldUsed) {
            int charIndex = data.indexOf(",");
            data.replace(charIndex, charIndex + 1, ".");
            currency.setValue(Double.parseDouble(data.toString()));

            isValueFieldUsed = false;
        }

        data.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
