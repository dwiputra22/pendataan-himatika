package com.pendataan.workshop.services;

import com.pendataan.workshop.dto.*;
import com.pendataan.workshop.entity.CustomLog;
import com.pendataan.workshop.entity.ProposalWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.CustomLogRepository;
import com.pendataan.workshop.repositories.PropWorkshopRepository;
import com.pendataan.workshop.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProposalService {

    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public final String uploadDirectory = System.getProperty("user.dir") + "/files/proposal-workshop";
    private final PropWorkshopRepository propWorkshopRepository;
    private final WorkshopRepository workshopRepository;
    private final CustomLogRepository logInfoRepository;


    public ModelAndView pageProposal(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop proposal = propWorkshopRepository.getByWorkshopId(workshopId);
        List<Workshop> workshop = workshopRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.getModelMap().addAttribute("proposal", proposal);
        mav.setViewName("proposalWorkshop");
        return mav;
    }

    public PaginatedResponse<List<Workshop>> getAllProposal(int page, int size) {
        Page<Workshop> workshop = workshopRepository.findAll(PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "id")));

        return PaginatedResponse.<List<Workshop>>builder()
                .responseStatus(new ResponseStatus(ApplicationCode.SUCCESS))
                .data(workshop.stream()
                        .map(Workshop::new)
                        .collect(Collectors.toList()))

                .detailPages(PaginatedResponse.PagingMetadata.builder()
                        .page(workshop.getNumber() + 1)
                        .rowPerPage(size)
                        .totalData(workshop.getTotalElements())
                        .build())
                .build();
    }

    public ModelAndView getWorkshop(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        Workshop workshop = workshopRepository.findByJudulWorkshop(judulWorkshop);
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("proposalWorkshop");
        return mav;
    }

    public ModelAndView getProposal(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        ProposalWorkshop proposal = propWorkshopRepository.getByWorkshopId(workshopId);
        Workshop workshop = workshopRepository.getById(workshopId);
        mav.getModelMap().addAttribute("workshop", workshop);
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
                log.info("Folder Has Been Created");
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
                workshop.setStatus(String.valueOf(StatusWorkshop.APPROVED));
                workshop.setUpdatedDate(LocalDateTime.now());
                proposalWorkshop.setWorkshop(workshop);
                return propWorkshopRepository.save(proposalWorkshop);
            });
            if (propWorkshop.isPresent()) {
                Workshop workshop = workshopRepository.getById(workshopId);
                CustomLog customLog = new CustomLog();
                customLog.setNim(proposalWorkshop.getNim());
                customLog.setNama(proposalWorkshop.getNama());
                customLog.setJudulWorkshop(workshop.getJudulWorkshop());
                customLog.setAktivitas(StatusCustomLog.CREATED_PROPOSAL.toString());
                customLog.setTanggal(LocalDateTime.now());
                logInfoRepository.save(customLog);
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

    public ResponseEntity<byte[]> downloadProposal(String docName) {
        Optional<ProposalWorkshop> file = propWorkshopRepository.findByDocName(docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.get().getProposalByte().length)
                .body(file.get().getProposalByte());
    }

    public ModelAndView formDelete(Long id) {
        ModelAndView mav = new ModelAndView();
        CustomLog customLog = new CustomLog();
        propWorkshopRepository.getByWorkshopId(id);

        mav.getModelMap().addAttribute("customLog", customLog);
        mav.setViewName("tampilProposal");
        return mav;
    }

    public ResponseEntity<?> deletedProposal(Long id,
                                             CustomLog logInfo,
                                             HttpServletResponse response) {
        ProposalWorkshop proposal = propWorkshopRepository.getByWorkshopId(id);
        String fileName = proposal.getDocName();
        String path = uploadDirectory + "/" + fileName;
        File file = new File(path);
        CustomLog customLog = new CustomLog();
        customLog.setNim(logInfo.getNim());
        customLog.setNama(logInfo.getNama());
        customLog.setAktivitas(StatusCustomLog.DELETED_PROPOSAL.toString());
        customLog.setTanggal(LocalDateTime.now());
        try {
            if (id != null) {
                propWorkshopRepository.deleteByWorkshopId(id);

                Workshop workshop = workshopRepository.getById(id);
                workshop.setStatus(StatusWorkshop.CREATED.toString());
                workshopRepository.save(workshop);

                customLog.setJudulWorkshop(workshop.getJudulWorkshop());
                logInfoRepository.save(customLog);

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
        Workshop workshop = workshopRepository.getById(id);
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

        CustomLog customLog = new CustomLog();
        customLog.setNim(proposalWorkshop.getNim());
        customLog.setNama(proposalWorkshop.getNama());
        customLog.setJudulWorkshop(workshop.getJudulWorkshop());
        customLog.setAktivitas(StatusCustomLog.UPDATE_PROPOSAL.toString());
        customLog.setTanggal(LocalDateTime.now());
        logInfoRepository.save(customLog);

        response.sendRedirect("/himatika/proposal-workshop");
        return new ResponseEntity<>("Berhasil Memperbarui Proposal", HttpStatus.OK);
    }
}
