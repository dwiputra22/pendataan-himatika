package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.pendataan.ProposalWorkshop;
import com.pendataan.workshop.repositories.PropWorkshopRepository;
import com.pendataan.workshop.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProposalService {

    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public static String uploadDirectory = System.getProperty("user.dir") + "/files/proposal-workshop";
    private final PropWorkshopRepository propWorkshopRepository;

    public ModelAndView getAllProposal() {
        ModelAndView mav = new ModelAndView();
        List<ProposalWorkshop> proposalList = propWorkshopRepository.findAll();
        mav.getModelMap().addAttribute("proposalList", proposalList);
        mav.setViewName("proposalWorkshop");
        return mav;
    }

    public ResponseEntity<?> uploadProposal(
            ProposalWorkshop proposalWorkshop,
            @RequestParam("suratProposal") MultipartFile suratProposal,
            HttpServletResponse response
    ) {
        try {
            if (proposalWorkshop == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = StringUtils.cleanPath(suratProposal.getOriginalFilename());
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + suratProposal.getOriginalFilename());

            try {
                File dir = new File(uploadDirectory);
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
                response.sendRedirect("/himatika/proposal-workshop");
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

    public ModelAndView formProposal(ProposalWorkshop proposalWorkshop) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop propWorkshop = new ProposalWorkshop();

        propWorkshop.setNim(proposalWorkshop.getNim());
        propWorkshop.setNama(proposalWorkshop.getNama());
        propWorkshop.setJudulWorkshop(proposalWorkshop.getJudulWorkshop());
        propWorkshop.setPembicara(proposalWorkshop.getPembicara());
        propWorkshop.setTanggalWorkshop(proposalWorkshop.getTanggalWorkshop());
        propWorkshop.setSuratProposal(proposalWorkshop.getSuratProposal());
        mav.getModelMap().addAttribute("propWorkshop", propWorkshop);
        mav.setViewName("formProposal");
        return mav;
    }

    public byte[] downloadProposal(String docName) {
        Optional<ProposalWorkshop> dbFile = propWorkshopRepository.findByDocName(docName);
        byte[] file = FileUtils.decompressImage(dbFile.get().getProposalByte());
        return file;
    }

    public ResponseEntity<?> deletedWorkshop(@PathVariable("id") Long id,
                                             @PathVariable("judulWorkshop") String judulWorkshop,
                                             String fileName,
                                             HttpServletResponse response) {
        String path = uploadDirectory + "/" + fileName;
        File file = new File(path);
        try {
            if (judulWorkshop != null) {
                propWorkshopRepository.deleteByJudulWorkshop(id, judulWorkshop);
                String pathFile = uploadDirectory + "/" + fileName;
                System.out.println("Path=" + pathFile);
                File fileToDelete = new File(pathFile);
                boolean status = fileToDelete.delete();
                System.out.println(this.getClass().getSimpleName() + ":deleting file... " + file);
                System.out.println("Success: " + status + " fileToDelete: " + fileToDelete);
                response.sendRedirect("/himatika/proposal-workshop");
                return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
            } else {
                log.info("Oops! File Not Found: " + fileName);
                return new ResponseEntity<>("Failed Delete", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
