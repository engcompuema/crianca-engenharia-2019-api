package br.uema.criancaengenharia.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.uema.criancaengenharia.config.MailConfig;
import br.uema.criancaengenharia.entity.Inscritos;
import br.uema.criancaengenharia.repository.InscritosRepository;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConfig mailConfig;

	@Autowired
	private InscritosRepository repository;

	public static final String URL_PARAMETER_ACCESS_TOKEN = "access-token";

	public boolean buildEmail(Inscritos i) {

		MimeMessage mime = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {

			helper = new MimeMessageHelper(mime, true, "utf-8");

			String emailHTML = "<!DOCTYPE html>\r\n"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\r\n"
					+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "  <meta name=\"viewport\" content=\"initial-scale=1.0\">\r\n"
					+ "  <meta name=\"format-detection\" content=\"telephone=no\">\r\n"
					+ "  <title>MOSAICO Responsive Email Designer</title>\r\n" + "  \r\n"
					+ "  <style type=\"text/css\">\r\n" + "    body{ margin: 0; padding: 0; }\r\n"
					+ "    img{ border: 0px; display: block; }\r\n" + "\r\n" + "    .socialLinks{ font-size: 6px; }\r\n"
					+ "    .socialLinks a{\r\n" + "      display: inline-block;\r\n" + "    }\r\n" + "\r\n"
					+ "    .long-text p{ margin: 1em 0px; }\r\n"
					+ "    .long-text p:last-child{ margin-bottom: 0px; }\r\n"
					+ "    .long-text p:first-child{ margin-top: 0px; }\r\n" + "  </style>\r\n"
					+ "  <style type=\"text/css\">\r\n" + "    /* yahoo, hotmail */\r\n"
					+ "    .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{ line-height: 100%; }\r\n"
					+ "    .yshortcuts a{ border-bottom: none !important; }\r\n"
					+ "    .vb-outer{ min-width: 0 !important; }\r\n" + "    .RMsgBdy, .ExternalClass{\r\n"
					+ "      width: 100%;\r\n" + "      background-color: #3f3f3f;\r\n"
					+ "      background-color: #3f3f3f}\r\n" + "\r\n"
					+ "    /* outlook/office365 add buttons outside not-linked images and safari have 2px margin */\r\n"
					+ "    [o365] button{ margin: 0 !important; }\r\n" + "\r\n" + "    /* outlook */\r\n"
					+ "    table{ mso-table-rspace: 0pt; mso-table-lspace: 0pt; }\r\n"
					+ "    #outlook a{ padding: 0; }\r\n"
					+ "    img{ outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic; }\r\n"
					+ "    a img{ border: none; }\r\n" + "\r\n" + "    @media screen and (max-width: 600px) {\r\n"
					+ "      table.vb-container, table.vb-row{\r\n" + "        width: 95% !important;\r\n"
					+ "      }\r\n" + "\r\n" + "      .mobile-hide{ display: none !important; }\r\n"
					+ "      .mobile-textcenter{ text-align: center !important; }\r\n" + "\r\n"
					+ "      .mobile-full{ \r\n" + "        width: 100% !important;\r\n"
					+ "        max-width: none !important;\r\n" + "      }\r\n" + "    }\r\n"
					+ "    /* previously used also screen and (max-device-width: 600px) but Yahoo Mail doesn't support multiple queries */\r\n"
					+ "  </style>\r\n" + "  <style type=\"text/css\">\r\n" + "    \r\n"
					+ "    #ko_singleArticleBlock_6 .links-color a, #ko_singleArticleBlock_6 .links-color a:link, #ko_singleArticleBlock_6 .links-color a:visited, #ko_singleArticleBlock_6 .links-color a:hover{\r\n"
					+ "      color: #3f3f3f;\r\n" + "      color: #3f3f3f;\r\n" + "      text-decoration: underline\r\n"
					+ "    }\r\n" + "    \r\n"
					+ "    #ko_footerBlock_2 .links-color a, #ko_footerBlock_2 .links-color a:link, #ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{\r\n"
					+ "      color: #cccccc;\r\n" + "      color: #cccccc;\r\n" + "      text-decoration: underline\r\n"
					+ "    }\r\n" + "    </style>\r\n" + "  \r\n" + "</head>\r\n"
					+ "<body style=\"margin: 0; padding: 0; background-color: #3f3f3f; color: #919191;\" vlink=\"#cccccc\" text=\"#919191\" bgcolor=\"#3f3f3f\" alink=\"#cccccc\"><center>\r\n"
					+ "\r\n" + "  \r\n" + "\r\n" + "    \r\n" + "    <!-- preheaderBlock -->\r\n"
					+ "    <table role=\"presentation\" class=\"vb-outer\" style=\"background-color: #3f3f3f;\" id=\"ko_preheaderBlock_1\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#3f3f3f\">\r\n"
					+ "      <tbody><tr><td class=\"vb-outer\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\" valign=\"top\" align=\"center\">\r\n"
					+ "      <div style=\"font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"></div>\r\n"
					+ "      <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n"
					+ "      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" class=\"vb-row\" width=\"570\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\r\n"
					+ "        \r\n" + "        <tbody><tr>\r\n"
					+ "      <td style=\"font-size: 0; padding: 0 9px;\" valign=\"top\" align=\"center\"><div style=\"width: 100%; max-width: 552px; -mru-width: 0px;\"><!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"><tr><![endif]--><!--\r\n"
					+ "        --><!--\r\n"
					+ "          --><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"276\"><![endif]--><!--\r\n"
					+ "      --><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 276px; -mru-width: 0px; min-width: calc(50%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--\r\n"
					+ "        --><table role=\"presentation\" class=\"vb-content\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\" width=\"276\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\" align=\"left\">\r\n"
					+ "          \r\n"
					+ "            <tbody><tr><td style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left;\" width=\"100%\" valign=\"top\" align=\"left\"><a style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\" href=\"[unsubscribe_link]\">Unsubscribe</a></td></tr>\r\n"
					+ "            \r\n" + "          \r\n" + "        </tbody></table><!--\r\n"
					+ "      --></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--\r\n"
					+ "          --><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"276\" class=\"mobile-hide\"><![endif]--><!--\r\n"
					+ "      --><div class=\"mobile-full mobile-hide\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 276px; -mru-width: 0px; min-width: calc(50%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--\r\n"
					+ "        --><table role=\"presentation\" class=\"vb-content\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\" width=\"276\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\" align=\"left\">\r\n"
					+ "          \r\n"
					+ "            <tbody><tr><td style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: right;\" width=\"100%\" valign=\"top\" align=\"right\"><a href=\"[show_link]\" style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\">View in your browser</a></td></tr>\r\n"
					+ "          \r\n" + "        </tbody></table><!--\r\n"
					+ "      --></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--\r\n" + "        --><!--\r\n"
					+ "      --><!--[if (gte mso 9)|(lte ie 8)]></tr></table><![endif]--></div></td>\r\n"
					+ "    </tr>\r\n" + "      \r\n" + "      </tbody></table></div><!--\r\n"
					+ "    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + "    </td></tr>\r\n"
					+ "    </tbody></table>\r\n" + "    <!-- /preheaderBlock -->\r\n" + "    \r\n" + "\r\n" + "  \r\n"
					+ "\r\n"
					+ "  <table role=\"presentation\" class=\"vb-outer\" style=\"background-color: #bfbfbf;\" id=\"ko_titleBlock_8\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#bfbfbf\">\r\n"
					+ "      <tbody><tr><td class=\"vb-outer\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\" valign=\"top\" align=\"center\">\r\n"
					+ "      <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n"
					+ "      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" class=\"vb-row\" style=\"border-collapse: separate; width: 100%; background-color: #ffffff; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\r\n"
					+ "        \r\n" + "        <tbody><tr>\r\n"
					+ "      <td style=\"font-size: 0; padding: 0 9px;\" valign=\"top\" align=\"center\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--\r\n"
					+ "        --><table role=\"presentation\" class=\"vb-content\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\">\r\n"
					+ "          \r\n" + "          <tbody><tr>\r\n"
					+ "      <td style=\"font-weight: normal; color: #3f3f3f; font-size: 22px; font-family: Arial, Helvetica, sans-serif; text-align: center;\" width=\"100%\" valign=\"top\" align=\"center\"><span style=\"font-weight: normal;\"><strong>INFORMAÇÕES GERAIS - CRIANÇA E ENGENHARIA 2019</strong></span></td>\r\n"
					+ "    </tr>\r\n" + "        \r\n" + "        </tbody></table></div></td>\r\n" + "    </tr>\r\n"
					+ "      \r\n" + "      </tbody></table></div><!--\r\n"
					+ "    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + "    </td></tr>\r\n"
					+ "    </tbody></table><table role=\"presentation\" class=\"vb-outer\" style=\"background-color: #bfbfbf;\" id=\"ko_singleArticleBlock_6\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#bfbfbf\">\r\n"
					+ "      <tbody><tr><td class=\"vb-outer\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\" valign=\"top\" align=\"center\">\r\n"
					+ "      <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n"
					+ "      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" class=\"vb-row\" style=\"border-collapse: separate; width: 100%; background-color: #ffffff; mso-cellspacing: 9px; border-spacing: 9px; max-width: 570px; -mru-width: 0px;\" width=\"570\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\">\r\n"
					+ "        \r\n" + "        <tbody><tr>\r\n"
					+ "      <td style=\"font-size: 0;\" valign=\"top\" align=\"center\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--\r\n"
					+ "        --><table role=\"presentation\" class=\"vb-content\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\">\r\n"
					+ "          \r\n"
					+ "          <tbody><tr><td class=\"links-color\" style=\"padding-bottom: 9px;\" width=\"100%\" valign=\"top\" align=\"center\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 534px; -mru-width: 0px;\"><![endif]--><img style=\"border: 0px; display: block; vertical-align: top; height: auto; margin: 0 auto; color: #3f3f3f; font-size: 13px; font-family: Arial, Helvetica, sans-serif; width: 100%; max-width: 534px; height: auto;\" src=\"https://mosaico.io/srv/f-2fio7np/img?src=https%3A%2F%2Fmosaico.io%2Ffiles%2F2fio7np%2Fcrian%25C3%25A7a-logo-sem-fundo-300x169%2520%25282%2529.png&amp;method=resize&amp;params=534%2Cnull\" width=\"534\" vspace=\"0\" hspace=\"0\" border=\"0\" align=\"middle\"><!--[if (lte ie 8)]></div><![endif]--></td></tr>\r\n"
					+ "          \r\n"
					+ "          <tr><td class=\"long-text links-color\" style=\"font-weight: normal; color: #3f3f3f; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left; line-height: normal;\" width=\"100%\" valign=\"top\" align=\"left\"><p style=\"margin: 1em 0px; margin-top: 0px;\">A ação Criança e Engenharia é um projeto realizado por professores e alunos do curso de Engenharia de Computação da Universidade Estadual do Maranhão em parceria com outros cursos e alunos da Instituição que acolhe crianças de instituições de ensino público e privado, bem como estudantes com necessidades educacionais específicas e tem como objetivo estimular a convivência e o fortalecimento de vínculos familiares,comunitários assim como o meio ambiente e tudo isso através de oficinas socioeducativas e culturais com um diferencial de articulação integrada.</p>\r\n"
					+ "<p style=\"margin: 1em 0px;\">No ano de 2018 aproximadamente 1400 crianças participaram de diversas atividades, entre elas: Oficinas de Robótica, Oficinas de Jogos Digitais, Oficina de Mini Baja, Jogos Educativos Ambientais, Oficinas de Reciclagem, Rapel,Exposição de Projetos de Biologia, Tirolesa e Primeiros Socorros.</p>\r\n"
					+ "<p style=\"margin: 1em 0px;\">No ano de 2019 ocorrerá a 4° edição do evento no dia 11 de outubro (sexta-feira)&nbsp;no Campus Paulo VI da Uema, buscando promover a inclusão social das crianças de escolas públicas e privadas da Cidade Operária&nbsp; e também a produção de saberes acerca de novas tecnologias. A estimativa é que cerca de 2000 crianças participem do evento.</p>\r\n"
					+ "<p style=\"margin: 1em 0px; margin-bottom: 0px;\">Em anexo a esse email, você terá sua confirmação de inscrição com instruções adicionais e seus dados que serão utilizados para a frequência no evento. Segue também o link do grupo do evento no WhatsApp: [link aqui].</p></td></tr>\r\n"
					+ "          \r\n" + "        \r\n" + "        </tbody></table></div></td>\r\n" + "    </tr>\r\n"
					+ "      \r\n" + "      </tbody></table></div><!--\r\n"
					+ "    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + "    </td></tr>\r\n"
					+ "    </tbody></table>\r\n" + "\r\n" + "  \r\n" + "    <!-- footerBlock -->\r\n"
					+ "    <table role=\"presentation\" class=\"vb-outer\" style=\"background-color: #3f3f3f;\" id=\"ko_footerBlock_2\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#3f3f3f\">\r\n"
					+ "      <tbody><tr><td class=\"vb-outer\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\" valign=\"top\" align=\"center\">\r\n"
					+ "    <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n"
					+ "      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" class=\"vb-row\" width=\"570\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\r\n"
					+ "        \r\n" + "      <tbody><tr>\r\n"
					+ "      <td style=\"font-size: 0; padding: 0 9px;\" valign=\"top\" align=\"center\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--\r\n"
					+ "        --><table role=\"presentation\" class=\"vb-content\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\" cellspacing=\"9\" cellpadding=\"0\" border=\"0\">\r\n"
					+ "          \r\n"
					+ "        <tbody><tr><td class=\"long-text links-color\" style=\"font-weight: normal; color: #919191; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\" width=\"100%\" valign=\"top\" align=\"center\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">Email sent to <a href=\"mailto:[mail]\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline;\">[mail]</a></p></td></tr>\r\n"
					+ "        <tr><td style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\" width=\"100%\" valign=\"top\" align=\"center\"><a href=\"[unsubscribe_link]\" style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\">Unsubscribe</a></td></tr>\r\n"
					+ "        <tr style=\"text-align: center;\"><td class=\"links-color\" style=\"text-align: center;\" width=\"100%\" valign=\"top\" align=\"center\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 170px; -mru-width: 0px;\"><![endif]--><a target=\"_new\" href=\"https://mosaico.io/?footerbadge\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline;\"> width=\"170\" vspace=\"0\" hspace=\"0\" border=\"0\" align=\"middle\"></a><!--[if (lte ie 8)]></div><![endif]--></td></tr>\r\n"
					+ "        </tbody></table></div></td>\r\n" + "    </tr>\r\n" + "    \r\n"
					+ "      </tbody></table></div><!--\r\n"
					+ "    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + "  </td></tr>\r\n"
					+ "    </tbody></table>\r\n" + "    <!-- /footerBlock -->\r\n" + "    \r\n"
					+ "</center></body></html>";
			helper.setFrom(mailConfig.getUsername());
			helper.setTo(i.getEmail());
			helper.setSubject("Teste Envio de Email");
			helper.setText(emailHTML, true);
			helper.addAttachment("Confirmação.pdf", gerarConfirmacao(i));

			mailSender.send(mime);

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean enviar(Long id) throws IOException {

		Optional<Inscritos> inscrito = repository.findById(id);
		if (inscrito.isPresent()) {
			try {
				buildEmail(inscrito.get());
				inscrito.get().setEmailEnviado(true);
				repository.save(inscrito.get());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public DataSource gerarConfirmacao(Inscritos i) {

		Map<String, Object> params = new HashMap<>();
		params.put("aluno_nome", i.getNome());
		params.put("aluno_cpf", i.getCpf());
		params.put("aluno_curso", "");
		params.put("aluno_centro", "");
		params.put("aluno_turno", "");
		params.put("aluno_funcao", i.getTipoAtividade());
		params.put("aluno_instituicao", "");
		params.put("aluno_email", i.getEmail());
		params.put("qr_image", generateQR(i.getCpf()));
		String jrxml = "src\\main\\java\\relatorios\\Confirmacao.jasper";
		DataSource aAttachment = null;
		try {
			JasperPrint print = JasperFillManager.fillReport(jrxml, params, new JREmptyDataSource(1));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, baos);
			aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
		} catch (JRException ex) {
			ex.printStackTrace();
		}

		return aAttachment;
	}

	public BufferedImage generateQR(String text) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix;
		byte[] pngData = null;
		BufferedImage image = null;
		try {
			bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
			pngData = pngOutputStream.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(pngData);
			image = ImageIO.read(bis);

		} catch (WriterException | IOException ex) {

		}
		return image;
	}

}
