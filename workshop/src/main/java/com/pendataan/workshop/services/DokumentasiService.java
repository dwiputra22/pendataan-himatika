package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.DokumentasiWorkshop;
import com.pendataan.workshop.repositories.DokumentasiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DokumentasiService {

    private DokumentasiRepository dokumentasiRepository;
    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public final String uploadDirectory = System.getProperty("user.dir") + "/files/dokumentasi-workshop";

    public ModelAndView formDokumentasi(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        DokumentasiWorkshop dokumentasiWorkshop = new DokumentasiWorkshop();
        mav.getModelMap().addAttribute("dokumentasiWorkshop", dokumentasiWorkshop);
        mav.setViewName("formDokumentasi");
        return mav;
    }

    public ResponseEntity<?> upload(DokumentasiWorkshop dokumentasiWorkshop,
                                    MultipartFile fileDokumentasi,
                                    String judulWorkshop,
                                    HttpServletResponse response) throws IOException {
        if (dokumentasiWorkshop == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = StringUtils.cleanPath(fileDokumentasi.getOriginalFilename());
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + fileDokumentasi.getOriginalFilename());
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                } else {
                    log.info("Folder Gagal Dibuat");
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(Files.newOutputStream(new File(filePath).toPath()));
                stream.write(fileDokumentasi.getBytes());
                stream.close();

                try {
                    log.info("Tanggal Dokumentasi: " + dokumentasiWorkshop.getTanggalDokumentasi());
                    log.info("Type File: " + dokumentasiWorkshop.getType());
                    log.info("Nama File: " + dokumentasiWorkshop.getDocName());

                    DokumentasiWorkshop dokumen = DokumentasiWorkshop.builder()
                            .tanggalDokumentasi(dokumentasiWorkshop.getTanggalDokumentasi())
                            .dokumentasiByte(dokumentasiWorkshop.getDokumentasiByte())
                            .type(dokumentasiWorkshop.getType())
                            .docName(dokumentasiWorkshop.getDocName())
                            .build();
                    dokumentasiRepository.save(dokumen);
                    if (dokumen != null) {
                        response.sendRedirect("/himatika/proposal-workshop");
                        return new ResponseEntity<>("File uploaded successfully: " + fileName, HttpStatus.OK);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(e.getMessage());
                    return new ResponseEntity<>( "Gagal Mengupload File ke Database",HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info(e.getMessage());
                return new ResponseEntity<>( "Gagal Mengupload File ke Local",HttpStatus.BAD_REQUEST);
            }
            log.info("Dokumentasi Berhasil Dibuat");
            return new ResponseEntity<>("Berhasil Upload Dokumentasi Workshop", HttpStatus.OK);
        }
    }

    public ModelAndView edit(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("");
        return mav;
    }

    public ResponseEntity<?> update() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    public ResponseEntity<?> delete() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
