package com.talch.utils;

import com.talch.beans.Coupon;
import com.talch.beans.CouponType;
import com.talch.beans.Role;
import com.talch.beans.User;
import com.talch.repo.CouponRepository;
import com.talch.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@RequiredArgsConstructor
public class DBInitialRunner implements CommandLineRunner {


    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    Date date = new Date(System.currentTimeMillis());
    Date dateMinusDayDate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
    Date dateMinusFiveDyes = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 5));
    Date datePlus5Days = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 5));
    Date datePlus1Min = new Date(System.currentTimeMillis() + (1000 * 60));

    CouponType coupType = CouponType.SPORTS;
    CouponType coupType2 = CouponType.HEALTH;
    CouponType coupType3 = CouponType.RESTURANS;
    @Override
    public void run(String... args) throws Exception {


            userRepository.deleteAll();
            couponRepository.deleteAll();

            List<Coupon> coup = new ArrayList<>();

            coup.add(new Coupon(
                    1582, "1+1", date, datePlus5Days, 5, coupType, "just now!", 50,
                    "https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));
            coup.add(new Coupon(
                    152, "3+1", dateMinusFiveDyes, dateMinusDayDate, 5, coupType, "wow!", 50,
                    "https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));
            coup.add(new Coupon(
                    12, "2+1", date, datePlus5Days, 5, coupType2, "just today!", 82,
                    "https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));
            coup.add(new Coupon(
                    82, "second helf price", date, datePlus1Min, 5, coupType3, "Sale!", 25,
                    "https://www.searchpng.com/wp-content/uploads/2019/09/Sale-PNG.jpg"));

            couponRepository.saveAll(coup);

            List<User> users = new ArrayList<>();

            users.add(new User(201, Role.Company, "Kia", "kiamotors", "kiamotors@kiamotors.net", 1000000, null));
            users.add(new User(521, Role.Company, "Cola", "cocacola", "Cola@cola.net", 1000000, null));
            users.add(new User(17, Role.Company, "Osem", "bisli", "osem@osem.com", 1000000, null));

            users.add(new User(2010, Role.Customer, "Gabi", "151285", null, 1000, null));
            users.add(new User(20, Role.Customer, "Tomer", "goodday", null, 1500, null));
            users.add(new User(30, Role.Customer, "Igor", "987456321", null, 1200, null));

            userRepository.saveAll(users);
        }

}
