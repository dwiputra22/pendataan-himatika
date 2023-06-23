package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.ProposalWorkshop;
import com.pendataan.workshop.repositories.PropWorkshopRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProposalService {

    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public final String uploadDirectory = System.getProperty("user.dir") + "/files/proposal-workshop";
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
            MultipartFile suratProposal,
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
            log.info("Tahun Kepngurusan: " + proposalWorkshop.getTahunKepengurusan());
            log.info("judulWorkshop: " + proposalWorkshop.getJudulWorkshop());
            log.info("pembicara: " + proposalWorkshop.getPembicara());
            log.info("tanggalWorkshop: " + proposalWorkshop.getTanggalWorkshop());
            log.info("suratProposalWorkshop: " + fileName);

            ProposalWorkshop workshop = ProposalWorkshop.builder()
                    .nim(proposalWorkshop.getNim())
                    .pembicara(proposalWorkshop.getPembicara())
                    .judulWorkshop(proposalWorkshop.getJudulWorkshop())
                    .nama(proposalWorkshop.getNama())
                    .tahunKepengurusan(proposalWorkshop.getTahunKepengurusan())
                    .tanggalWorkshop(proposalWorkshop.getTanggalWorkshop())
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
        mav.getModelMap().addAttribute("propWorkshop", propWorkshop);
        mav.setViewName("formProposal");
        return mav;
    }

    public ResponseEntity<byte[]> downloadProposal(String docName) {
        Optional<ProposalWorkshop> file = propWorkshopRepository.findByDocName(docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.get().getProposalByte().length)
                .body(file.get().getProposalByte());
    }

    public ResponseEntity<?> deletedProposal(Long id,
                                             String judulWorkshop,
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
        return new ResponseEntity<>("Berhasil Menghapus Data", HttpStatus.OK);
    }

    public ModelAndView editProposal(Long id) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop propWorkshop = propWorkshopRepository.getById(id);
        mav.getModelMap().addAttribute("propWorkshop", propWorkshop);
        mav.setViewName("editProposal");
        return mav;
    }

    public @ResponseBody ResponseEntity<?> updateProposal(Long id,
                                                          MultipartFile suratProposal,
                                                          HttpServletResponse response,
                                                          ProposalWorkshop proposalWorkshop) throws IOException {
        ProposalWorkshop propWorkshop = propWorkshopRepository.getById(id);

        String fileName = StringUtils.cleanPath(suratProposal.getOriginalFilename());

        propWorkshop.setNim(proposalWorkshop.getNim());
        propWorkshop.setPembicara(proposalWorkshop.getPembicara());
        propWorkshop.setJudulWorkshop(proposalWorkshop.getJudulWorkshop());
        propWorkshop.setNama(proposalWorkshop.getNama());
        propWorkshop.setTahunKepengurusan(proposalWorkshop.getTahunKepengurusan());
        propWorkshop.setTanggalWorkshop(proposalWorkshop.getTanggalWorkshop());
        propWorkshop.setProposalByte(suratProposal.getBytes());
        propWorkshop.setType(suratProposal.getContentType());
        propWorkshop.setUpdatedDate(LocalDateTime.now());
        propWorkshop.setDocName(fileName);

        propWorkshopRepository.save(propWorkshop);
        response.sendRedirect("/himatika/proposal-workshop");
        return new ResponseEntity<>("Berhasil Memperbarui Proposal", HttpStatus.OK);
    }
}
