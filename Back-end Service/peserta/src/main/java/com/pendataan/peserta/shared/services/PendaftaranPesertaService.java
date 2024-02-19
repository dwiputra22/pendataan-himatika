package com.pendataan.peserta.shared.services;

import com.pendataan.peserta.email.EmailSender;
import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.entity.Workshop;
import com.pendataan.peserta.repositories.PesertaRepository;
import com.pendataan.peserta.repositories.WorkshopRepository;
import com.pendataan.peserta.services.AbsensiService;
import com.pendataan.peserta.shared.dto.StatusPeserta;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PendaftaranPesertaService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private final PesertaRepository pesertaRepository;
    private final WorkshopRepository workshopRepository;
    private final EmailSender emailSender;

    public ModelAndView formPendaftaran() {
        ModelAndView mav = new ModelAndView();
        String status = "APPROVED";
//        ProposalWorkshop judul = absensiRepository.findByJudulWorkshop(judulWorkshop);
        PesertaWorkshop formPendaftaran = new PesertaWorkshop();
        List<Workshop> workshop = workshopRepository.getByStatus(status);
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.getModelMap().addAttribute("formPendaftaran", formPendaftaran);
        mav.setViewName("formPendaftaran");
        return mav;
    }

    public ResponseEntity<?> uploadPendaftaran(PesertaWorkshop pendaftaran,
                                               HttpServletResponse response) throws IOException {
        PesertaWorkshop pendaftaranWorkshop = new PesertaWorkshop();

        pendaftaranWorkshop.setNama(pendaftaran.getNama());
        pendaftaranWorkshop.setEmail(pendaftaran.getEmail());
        pendaftaranWorkshop.setAsalInstansi(pendaftaran.getAsalInstansi());
        pendaftaranWorkshop.setPekerjaan(pendaftaran.getPekerjaan());
        pendaftaranWorkshop.setAsalInfo(pendaftaran.getAsalInfo());
        pendaftaranWorkshop.setCreatedDate(LocalDateTime.now());
        pendaftaranWorkshop.setJudulWorkshop(pendaftaran.getJudulWorkshop());
        pendaftaranWorkshop.setStatus(String.valueOf(StatusPeserta.DAFTAR));
        pendaftaranWorkshop.setKodeUnik(UUID.randomUUID().toString());

        String kode = pendaftaranWorkshop.getKodeUnik();

        if (pendaftaranWorkshop != null) {
            log.info("nama: " + pendaftaran.getNama());
            log.info("email: " +pendaftaran.getEmail());
            log.info("Asal Instansi: " + pendaftaran.getAsalInstansi());
            log.info("Status: " + pendaftaran.getStatus());
            log.info("Asal Info: " + pendaftaran.getAsalInfo());
            log.info("Created Date: " + LocalDateTime.now());
            log.info("judulWorkshop: " + pendaftaran.getJudulWorkshop());
            pesertaRepository.save(pendaftaranWorkshop);
        } else {
            return new ResponseEntity<>("Gagal Menyimpan", HttpStatus.BAD_REQUEST);
        }

        Workshop judulWorkshop = workshopRepository.getByJudulWorkshop(pendaftaranWorkshop.getJudulWorkshop());
        String link = judulWorkshop.getLinkWhatsapp();
        emailSender.send(
                pendaftaranWorkshop.getEmail(),
                buildEmail(pendaftaranWorkshop.getNama(), link, kode));
        response.sendRedirect("/");
        return new ResponseEntity<>("Berhasil Input Pendaftaran", HttpStatus.OK);
    }

    private String buildEmail(String nama, String link, String kode) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Simpan Kode Ini!</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Halo, " + nama + " </br>Kode Untuk Mengisi Absen: </br>" + kode + "</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Simpan Kode Tersebut Agar Dapat Melakukan Absensi. Klik Link Dibawah Untuk Masuk Grup Workshop</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Grup Whatsapp</a> </p></blockquote>\n <p>Sampai Jumpa</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
