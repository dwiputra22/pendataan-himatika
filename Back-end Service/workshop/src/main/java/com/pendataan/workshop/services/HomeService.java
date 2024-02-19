package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.CustomLog;
import com.pendataan.workshop.entity.PesertaWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.CustomLogRepository;
import com.pendataan.workshop.repositories.PesertaRepository;
import com.pendataan.workshop.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {

    private final WorkshopRepository workshopRepository;
    private final PesertaRepository pesertaRepository;
    private final CustomLogRepository customLogRepository;

    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        Month currentMonth = LocalDate.now().getMonth();
        List<PesertaWorkshop> absensi = pesertaRepository.findAll();
        List<Workshop> workshop = workshopRepository.findAll();
        List<CustomLog> customLogs = customLogRepository.findAll();

        modelAndView.addObject("absensi", absensi.stream()
                .filter(a -> {
                    LocalDateTime updatedDate = a.getUpdatedDate();
                    return updatedDate != null && updatedDate.getMonth() == currentMonth;
                })
                .toList().size());
        modelAndView.addObject("countW", workshop.stream()
                .filter(w -> w.getTanggalWorkshop().getMonth() == currentMonth)
                .toList().size());

        modelAndView.addObject("customLogs", customLogs);
        modelAndView.addObject("workshop", workshop);
        modelAndView.setViewName("himatika");
        return modelAndView;
    }
}
