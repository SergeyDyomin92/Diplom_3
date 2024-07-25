package site.nomoreparties.stellarburgers.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class Utils {
    Faker faker = new Faker((new Locale("ru")));

    public String email = faker.internet().emailAddress();
    public String password = faker.funnyName().name();
    public String name = faker.name().firstName();

    //public String firstName = faker.name().firstName();
    //public String lastName = faker.name().lastName();
    //public String phoneNumber = String.valueOf(faker.number().numberBetween(79000000000L, 79999999999L));
   //public String address = faker.address().streetAddress();

}
