package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.ProposalWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.PropWorkshopRepository;
import com.pendataan.workshop.repositories.WorkshopRepository;
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
    private final WorkshopRepository workshopRepository;

    public ModelAndView getAllProposal() {
        ModelAndView mav = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("proposalWorkshop");
        return mav;
    }

    public ModelAndView getProposal(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop proposal = propWorkshopRepository.getById(workshopId);
        mav.getModelMap().addAttribute("proposal", proposal);
        mav.setViewName("tampilProposal");
        return mav;
    }

    public ResponseEntity<?> uploadProposal(Long workshopId,
                                            ProposalWorkshop proposalWorkshop,
                                            MultipartFile suratProposal,
                                            HttpServletResponse response) {
        try {
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
            log.info("Tahun Kepengurusan: " + proposalWorkshop.getTahunKepengurusan());
            log.info("suratProposalWorkshop: " + fileName);

//            Workshop work = Workshop.builder()
//                    .judulWorkshop(workshop.getJudulWorkshop())
//                    .pembicara(workshop.getPembicara())
//                    .tanggalWorkshop(workshop.getTanggalWorkshop())
//                    .createdDate(LocalDateTime.now())
//                    .build();
//            workshopRepository.save(work);

            ProposalWorkshop proposal = ProposalWorkshop.builder()
                    .proposalByte(suratProposal.getBytes())
                    .type(suratProposal.getContentType())
                    .docName(fileName)
                    .createdDate(LocalDateTime.now())
                    .build();

            Optional<ProposalWorkshop> propWorkshop = workshopRepository.findById(workshopId).map(workshop -> {
                proposalWorkshop.setProposalByte(proposal.getProposalByte());
                proposalWorkshop.setDocName(proposal.getDocName());
                proposalWorkshop.setCreatedDate(proposal.getCreatedDate());
                proposalWorkshop.setType(proposal.getType());
                proposalWorkshop.setWorkshop(workshop);
                return propWorkshopRepository.save(proposalWorkshop);
            });
            if (propWorkshop.isPresent()) {
                response.sendRedirect("/himatika/proposal-workshop");
                return new ResponseEntity<>("Berhasil Upload Proposal", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Workshop Berhasil Dibuat");
        return new ResponseEntity<>("Berhasil Upload Proposal Workshop", HttpStatus.OK);
    }

    public ModelAndView formProposal(Long workshopId, ProposalWorkshop proposalWorkshop) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop proposalInput = new ProposalWorkshop();

        proposalInput.setNim(proposalWorkshop.getNim());
        proposalInput.setNama(proposalWorkshop.getNama());
        proposalInput.setTahunKepengurusan(proposalWorkshop.getTahunKepengurusan());
        proposalInput.setSuratProposal(proposalWorkshop.getSuratProposal());

        mav.getModelMap().addAttribute("proposalInput", proposalInput);
        mav.setViewName("formProposal");
        return mav;
    }

    public ResponseEntity<byte[]> downloadProposal(Long id, String docName) {
        Optional<ProposalWorkshop> file = propWorkshopRepository.findByDocName(id, docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.get().getProposalByte().length)
                .body(file.get().getProposalByte());
    }

    public ResponseEntity<?> deletedProposal(Long id,
                                             String fileName,
                                             HttpServletResponse response) {
        String path = uploadDirectory + "/" + fileName;
        File file = new File(path);
        try {
            if (id != null) {
                propWorkshopRepository.deleteByWorkshopId(id);
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
//        Workshop getId = workshopRepository.getReferenceById(workshopId);
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
//        Workshop getId = workshopRepository.getReferenceById(workshopId);

        String fileName = StringUtils.cleanPath(suratProposal.getOriginalFilename());

        propWorkshop.setNim(proposalWorkshop.getNim());
        propWorkshop.setNama(proposalWorkshop.getNama());
        propWorkshop.setTahunKepengurusan(proposalWorkshop.getTahunKepengurusan());
        propWorkshop.setProposalByte(suratProposal.getBytes());
        propWorkshop.setType(suratProposal.getContentType());
        propWorkshop.setDocName(fileName);
        propWorkshop.setUpdatedDate(LocalDateTime.now());

        propWorkshopRepository.save(propWorkshop);
        response.sendRedirect("/himatika/proposal-workshop");
        return new ResponseEntity<>("Berhasil Memperbarui Proposal", HttpStatus.OK);
    }
}
