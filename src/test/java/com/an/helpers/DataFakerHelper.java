package com.an.helpers;

import net.datafaker.Faker;
import java.util.Locale;

public class DataFakerHelper {
// Các loại data khác bạn có thể xem thêm tại https://www.datafaker.net/documentation/providers/
    private static Faker faker;

    public static Faker createFaker() {

        faker = new Faker(new Locale(PropertiesHelper.getValue("LOCATE")));
        return faker;
    }

    public static Faker createFakerByLocate(String locateName) {
        faker = new Faker(new Locale(locateName));
        return faker;
    }

    public static Faker getFaker() {
        if (faker == null) {
            faker = createFaker();
        }
        return faker;
    }

    public static Faker getFakerByLocate(String locateName) {
        faker = createFakerByLocate(locateName);
        return faker;
    }

    public static void setFaker(Faker faker) {
        DataFakerHelper.faker = faker;
    }
}
