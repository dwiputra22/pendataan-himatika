package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.DokumentasiWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.DokumentasiRepository;
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
public class DokumentasiService {

    private final DokumentasiRepository dokumentasiRepository;
    private final WorkshopRepository workshopRepository;
    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public final String uploadDirectory = System.getProperty("user.dir") + "/files/dokumentasi-workshop";


    public ModelAndView getAllDokumentasi() {
        ModelAndView mav = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("dokumentasiWorkshop");
        return mav;
    }

    public ModelAndView getDokumentasi(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        DokumentasiWorkshop dokumentasi = dokumentasiRepository.getById(workshopId);
        mav.getModelMap().addAttribute("dokumentasi", dokumentasi);
        mav.setViewName("tampilDokumentasi");
        return mav;
    }

    public ModelAndView formDokumentasi(Long workshopId) {
        ModelAndView mav = new ModelAndView();
        DokumentasiWorkshop dokumentasiWorkshop = new DokumentasiWorkshop();
        mav.getModelMap().addAttribute("dokumentasiWorkshop", dokumentasiWorkshop);
        mav.setViewName("formDokumentasi");
        return mav;
    }

    public ResponseEntity<?> upload(DokumentasiWorkshop dokumentasiWorkshop,
                                    MultipartFile fileDokumentasi,
                                    Long workshopId,
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
                            .dokumentasiByte(fileDokumentasi.getBytes())
                            .type(fileDokumentasi.getContentType())
                            .docName(fileName)
                            .createdDate(LocalDateTime.now())
                            .build();

                    Optional<DokumentasiWorkshop> dokumentasi = workshopRepository.findById(workshopId).map(workshop -> {
                        dokumentasiWorkshop.setTanggalDokumentasi(dokumen.getTanggalDokumentasi());
                        dokumentasiWorkshop.setDokumentasiByte(dokumen.getDokumentasiByte());
                        dokumentasiWorkshop.setDocName(dokumen.getDocName());
                        dokumentasiWorkshop.setType(dokumen.getType());
                        dokumentasiWorkshop.setCreatedDate(dokumen.getCreatedDate());
                        dokumentasiWorkshop.setWorkshop(workshop);
                        return dokumentasiRepository.save(dokumentasiWorkshop);
                    });
                    if (dokumentasi.isPresent()) {
                        response.sendRedirect("/himatika/dokumentasi");
                        return new ResponseEntity<>("Berhasil Upload Dokumentasi", HttpStatus.OK);
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

    public ResponseEntity<byte[]> downloadDokumentasi(Long id, String docName) {
        DokumentasiWorkshop file = dokumentasiRepository.findByDocName(id, docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.getDokumentasiByte().length)
                .body(file.getDokumentasiByte());
    }

    public ModelAndView edit(Long id) {
        ModelAndView mav = new ModelAndView();
        DokumentasiWorkshop edit = dokumentasiRepository.getById(id);
        mav.getModelMap().addAttribute("edit", edit);
        mav.setViewName("editDokumentasi");
        return mav;
    }

    public ResponseEntity<?> update(Long id,
                                    DokumentasiWorkshop dok,
                                    MultipartFile fileDokumentasi,
                                    HttpServletResponse response) throws IOException {
        DokumentasiWorkshop update = dokumentasiRepository.getById(id);

        update.setNim(dok.getNim());
        update.setNama(dok.getNama());
        update.setThnKepengurusan(dok.getThnKepengurusan());
        update.setTanggalDokumentasi(dok.getTanggalDokumentasi());
        update.setDokumentasiByte(fileDokumentasi.getBytes());
        update.setType(fileDokumentasi.getContentType());
        update.setDocName(fileDokumentasi.getOriginalFilename());
        update.setUpdatedDate(LocalDateTime.now());

        dokumentasiRepository.save(update);
        response.sendRedirect("/himatika/dokumentasi");
        return new ResponseEntity<>("Berhasil Memperbarui Dokumentasi", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id,
                                    String fileName,
                                    HttpServletResponse response) {
        String path = uploadDirectory + "/" + fileName;
        File file = new File(path);
        try {
            if (id != null) {
                dokumentasiRepository.deleteByWorkshopId(id);
                String pathFile = uploadDirectory + "/" + fileName;
                System.out.println("Path=" + pathFile);
                File fileToDelete = new File(pathFile);
                boolean status = fileToDelete.delete();
                System.out.println(this.getClass().getSimpleName() + ":deleting file... " + file);
                System.out.println("Success: " + status + " fileToDelete: " + fileToDelete);
                response.sendRedirect("/himatika/dokumentasi");
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
}
