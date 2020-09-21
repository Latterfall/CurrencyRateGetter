import javax.xml.parsers.*;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CurrencyRateGetter {
    public static void main(String[] args) throws Exception {
        Set<Currency> currencySet = new HashSet<>();
        InputStream inputStream = new URL("http://www.cbr.ru/scripts/XML_daily.asp").openStream();
        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
        saxParser.parse(inputStream, new CurrencyHandler(currencySet));
        if (args.length != 0) {
            String inputCurrencyCharCode = args[0];
            currencySet.forEach(currency -> {
                if (currency.getCharCode().equals(inputCurrencyCharCode)) {
                    System.out.println("Курс " + currency.getCharCode() + " к RUR - " + currency.getValue());
                }
            });
        } else {
            currencySet.forEach(System.out::println);
        }
    }
}

