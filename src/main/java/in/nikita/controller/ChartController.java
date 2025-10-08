package in.nikita.controller;

import in.nikita.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/admin/chart")
public class ChartController {

    @Autowired
    private OrdersRepository orderRepo;

    @GetMapping("/sales")
    public List<Object[]> getSalesChart(
            @RequestParam String period,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        LocalDate today = LocalDate.now();
        int y = (year != null) ? year : today.getYear();
        int m = (month != null) ? month : today.getMonthValue();

        switch (period.toLowerCase()) {
            case "month":
                return orderRepo.findTopSellingProductsByMonth(y, m);

            case "week":
                // Calculate start (Monday) and end (Sunday) of the current week
                LocalDate weekStart = today.with(DayOfWeek.MONDAY);
                LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

                LocalDateTime startDateTime = weekStart.atStartOfDay();
                LocalDateTime endDateTime = weekEnd.atTime(LocalTime.MAX);

                return orderRepo.findTopSellingProductsByWeek(startDateTime, endDateTime);

            case "year":
            default:
                return orderRepo.findTopSellingProductsByYear(y);
        }
    }
}
