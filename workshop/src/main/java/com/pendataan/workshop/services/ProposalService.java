package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.pendataan.LpjWorkshop;
import com.pendataan.workshop.entity.pendataan.ProposalWorkshop;
import com.pendataan.workshop.repositories.LpjWorkshopRepository;
import com.pendataan.workshop.repositories.PropWorkshopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PendataanService {

    private String uploadDirectory;

    private static final Logger log = LoggerFactory.getLogger(PendataanService.class);
    //    public static String uploadDirectory = System.getProperty("proposal.dir") + "/src/main/resource/file/proposal-workshop";
    private final PropWorkshopRepository propWorkshopRepository;
    private final LpjWorkshopRepository lpjWorkshopRepository;
//    private final FileProposalRepository fileRepo;

    @Autowired
    public PendataanService(@Value("${uploadDir}") String uploadDirectory,
                            PropWorkshopRepository propWorkshopRepository,
                            LpjWorkshopRepository lpjWorkshopRepository) {
        this.uploadDirectory = uploadDirectory;
        this.propWorkshopRepository = propWorkshopRepository;
        this.lpjWorkshopRepository = lpjWorkshopRepository;
    }

    public ModelAndView getAllData(ProposalWorkshop propWorkshop) {
        ModelAndView modelAndView = new ModelAndView();
        propWorkshopRepository.findAll();
        lpjWorkshopRepository.findAll();
        modelAndView.getModelMap().addAttribute("propWorkshop", propWorkshop);
        modelAndView.setViewName("himatika");
        return modelAndView;
    }

    public ResponseEntity<?> uploadProposal(
            ProposalWorkshop proposalWorkshop,
            BindingResult bindingResult,
            @RequestParam("suratProposal") MultipartFile suratProposal,
            HttpServletRequest request
    ) {
        try {
            if (proposalWorkshop == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String uploadDir = request.getServletContext().getRealPath(uploadDirectory);
            log.info("uploadDirectory:: " + uploadDir);
            String fileName = StringUtils.cleanPath(suratProposal.getOriginalFilename());
            String filePath = Paths.get(uploadDir, fileName).toString();
            log.info("FileName: " + suratProposal.getOriginalFilename());

            try {
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(new File(filePath).toPath()));
                stream.write(suratProposal.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }

            log.info("nim: " + proposalWorkshop.getNim());
            log.info("nama: " + proposalWorkshop.getNama());
            log.info("judulWorkshop: " + proposalWorkshop.getJudulWorkshop());
            log.info("pembicara: " + proposalWorkshop.getPembicara());
            log.info("tanggalWorkshop: " + proposalWorkshop.getTanggalWorkshop());
            log.info("suratProposalWorkshop: " + fileName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime parsedDateTime = LocalDateTime.parse(proposalWorkshop.getTanggalWorkshop(), formatter);

            ProposalWorkshop workshop = ProposalWorkshop.builder()
                    .nim(proposalWorkshop.getNim())
                    .pembicara(proposalWorkshop.getPembicara())
                    .judulWorkshop(proposalWorkshop.getJudulWorkshop())
                    .nama(proposalWorkshop.getNama())
                    .tanggalWorkshop(String.valueOf(parsedDateTime))
                    .proposalByte(suratProposal.getBytes())
                    .type(suratProposal.getContentType())
                    .docName(fileName)
                    .build();
            propWorkshopRepository.save(workshop);

            if (workshop != null) {
                return new ResponseEntity<>("File uploaded successfully: " + suratProposal.getOriginalFilename(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Workshop Berhasil Dibuat");
        return new ResponseEntity<>("Berhasil Upload Workshop Dengan Judul " + proposalWorkshop.getJudulWorkshop(), HttpStatus.OK);
    }

    public ModelAndView getAllProposal(ProposalWorkshop proposalWorkshop) throws IOException {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop propWorkshop = new ProposalWorkshop();

        propWorkshop.setNim(proposalWorkshop.getNim());
        propWorkshop.setNama(proposalWorkshop.getNama());
        propWorkshop.setJudulWorkshop(proposalWorkshop.getJudulWorkshop());
        propWorkshop.setPembicara(proposalWorkshop.getPembicara());
        propWorkshop.setTanggalWorkshop(proposalWorkshop.getTanggalWorkshop());
        propWorkshop.setSuratProposal(proposalWorkshop.getSuratProposal());
        mav.getModelMap().addAttribute("propWorkshop", propWorkshop);
        mav.setViewName("proposalWorkshop");
        return mav;
    }

    public String uploadLpj(MultipartFile file, LpjWorkshop lpjWorkshop) {
        try {
            lpjWorkshop.setSuratLpj(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String docName = StringUtils.cleanPath(file.getOriginalFilename());
        lpjWorkshop.setDocName(docName);
        lpjWorkshopRepository.save(LpjWorkshop.builder()
                .nim(lpjWorkshop.getNim())
                .nama(lpjWorkshop.getNama())
                .judulWorkshop(lpjWorkshop.getJudulWorkshop())
                .pembicara(lpjWorkshop.getPembicara())
                .tanggalWorkshop(lpjWorkshop.getTanggalWorkshop())
                .docName(lpjWorkshop.getDocName())
                .suratLpj(lpjWorkshop.getSuratLpj())
                .build());
        return "Success Uploaded " + lpjWorkshop.getJudulWorkshop();
    }
}
