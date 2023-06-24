package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.LpjWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.LpjWorkshopRepository;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LpjService {

    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    //    public static Path uploadDirectory = Paths.get("/workshop/files/lpj-workshop");
    public static String uploadDirectory = System.getProperty("user.dir") + "/files/lpj-workshop";
    private final LpjWorkshopRepository lpjWorkshopRepository;
    private final WorkshopRepository workshopRepository;

    public ModelAndView getAllLpj() {
        ModelAndView mav = new ModelAndView();
        List<Workshop> lpjWorkshop = workshopRepository.findAll();
        mav.getModelMap().addAttribute("lpjWorkshop", lpjWorkshop);
        mav.setViewName("lpjWorkshop");
        return mav;
    }

    public ModelAndView getLpj(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        LpjWorkshop lpj = lpjWorkshopRepository.getById(workshopId);
        mav.getModelMap().addAttribute("lpj", lpj);
        mav.setViewName("tampilLpj");
        return mav;
    }

    public ModelAndView formLpj(Long workshopId, LpjWorkshop lpjWorkshop) {
        ModelAndView mav = new ModelAndView();
        LpjWorkshop lpjInput = new LpjWorkshop();

        lpjInput.setNim(lpjWorkshop.getNim());
        lpjInput.setNama(lpjWorkshop.getNama());
        lpjInput.setTahunKepengurusan(lpjWorkshop.getTahunKepengurusan());
        lpjInput.setSuratLpj(lpjWorkshop.getSuratLpj());

        mav.getModelMap().addAttribute("lpjInput", lpjInput);
        mav.setViewName("formLpj");
        return mav;
    }

    public ResponseEntity<?> uploadlpj(Long workshopId,
                                       LpjWorkshop lpjWorkshop,
                                       MultipartFile suratLpj,
                                       HttpServletResponse response) {
        try {
            if (lpjWorkshop == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = StringUtils.cleanPath(suratLpj.getOriginalFilename());
            Path filePath = Paths.get(uploadDirectory, fileName);
            log.info("FileName: " + suratLpj.getOriginalFilename());

            try {
//                Files.createDirectory(uploadDirectory);
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(new File(filePath.toUri()).toPath()));
                stream.write(suratLpj.getBytes());
                stream.close();
//                Files.copy(suratLpj.getInputStream(), this.uploadDirectory.resolve(fileName));
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }

            log.info("nim: " + lpjWorkshop.getNim());
            log.info("nama: " + lpjWorkshop.getNama());
            log.info("Tahun Kepengurusan: " + lpjWorkshop.getTahunKepengurusan());
            log.info("suratLpjWorkshop: " + fileName);

            LpjWorkshop lpj = LpjWorkshop.builder()
                    .nim(lpjWorkshop.getNim())
                    .nama(lpjWorkshop.getNama())
                    .tahunKepengurusan(lpjWorkshop.getTahunKepengurusan())
                    .lpjByte(suratLpj.getBytes())
                    .type(suratLpj.getContentType())
                    .docName(fileName)
                    .createdDate(LocalDateTime.now())
                    .build();

            Optional<LpjWorkshop> lpjWork = workshopRepository.findById(workshopId).map(workshop -> {
                lpjWorkshop.setLpjByte(lpj.getLpjByte());
                lpjWorkshop.setDocName(lpj.getDocName());
                lpjWorkshop.setType(lpj.getType());
                lpjWorkshop.setCreatedDate(lpj.getCreatedDate());
                lpjWorkshop.setWorkshop(workshop);
                return lpjWorkshopRepository.save(lpjWorkshop);
            });
            if (lpjWork.isPresent()) {
                response.sendRedirect("/himatika/lpj-workshop");
                return new ResponseEntity<>("Berhasil Upload Lpj Workshop", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Workshop Berhasil Dibuat");
        return new ResponseEntity<>("Berhasil Upload Lpj Workshop ", HttpStatus.OK);
    }

    public ResponseEntity<byte[]> downloadlpj(Long id,
                                              String docName) throws IOException {

        //download from memory filesystem
//        MediaType mediaType = FileUtils.getMediaTypeForDocName(this.servletContext, docName);
//        Path files = Paths.get(uploadDirectory, docName);
//        Resource resource = new UrlResource(files.toUri());

        //download from db
        Optional<LpjWorkshop> file = lpjWorkshopRepository.findByDocName(id, docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.get().getLpjByte().length)
                .body(file.get().getLpjByte());
    }

    public ResponseEntity<?> deletedLpj(Long id,
                                        String fileName,
                                        HttpServletResponse response) {
        String path = uploadDirectory + "/" + fileName;
        File file = new File(path);
        try {
            if (id != null) {
                lpjWorkshopRepository.deleteByWorkshopId(id);
                String pathFile = uploadDirectory + "/" + fileName;
                System.out.println("Path=" + pathFile);
                File fileToDelete = new File(pathFile);
                boolean status = fileToDelete.delete();
                System.out.println(this.getClass().getSimpleName() + ":deleting file... " + file);
                System.out.println("Success: " + status + " fileToDelete: " + fileToDelete);
                response.sendRedirect("/himatika/lpj-workshop");
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

    public ModelAndView editLpj(Long id) {
        ModelAndView mav = new ModelAndView();
//        Workshop getId = workshopRepository.getReferenceById(workshopId);
        LpjWorkshop lpjInput = lpjWorkshopRepository.getById(id);
        mav.getModelMap().addAttribute("lpjInput", lpjInput);
        mav.setViewName("editLpj");
        return mav;
    }

    public @ResponseBody ResponseEntity<?> updateLpj(Long id,
                                                     MultipartFile suratLpj,
                                                     HttpServletResponse response,
                                                     LpjWorkshop lpjWorkshop) throws IOException {
        LpjWorkshop lpj = lpjWorkshopRepository.getById(id);

        String fileName = StringUtils.cleanPath(suratLpj.getOriginalFilename());

        lpj.setNim(lpjWorkshop.getNim());
        lpj.setNama(lpjWorkshop.getNama());
        lpj.setTahunKepengurusan(lpjWorkshop.getTahunKepengurusan());
        lpj.setLpjByte(suratLpj.getBytes());
        lpj.setType(suratLpj.getContentType());
        lpj.setDocName(fileName);
        lpj.setUpdatedDate(LocalDateTime.now());

        lpjWorkshopRepository.save(lpj);
        response.sendRedirect("/himatika/lpj-workshop");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
