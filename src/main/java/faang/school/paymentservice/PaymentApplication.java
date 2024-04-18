package faang.school.paymentservice;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@EnableFeignClients(basePackages = "faang.school.paymentservice.client")
public class PaymentApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PaymentApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
        //SpringApplication.run(PaymentApplication.class, args);
    }
}