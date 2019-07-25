package br.uema.criancaengenharia.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.uema.criancaengenharia.config.MailConfig;


@Component
public class MailService{
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private MailConfig mailConfig;
	
	public static final String URL_PARAMETER_ACCESS_TOKEN = "access-token";

	
    public Boolean enviar(String destino) {
    	
		
            	
    	MimeMessage mime = mailSender.createMimeMessage();
    		
    	MimeMessageHelper helper;
		try {
			
			helper = new MimeMessageHelper(mime, true, "utf-8");

	    	String emailHTML = "<!DOCTYPE html>\r\n" + 
	    			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\r\n" + 
	    			"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
	    			"  <meta name=\"viewport\" content=\"initial-scale=1.0\">\r\n" + 
	    			"  <meta name=\"format-detection\" content=\"telephone=no\">\r\n" + 
	    			"  <title>MOSAICO Responsive Email Designer</title>\r\n" + 
	    			"  \r\n" + 
	    			"  <style type=\"text/css\">\r\n" + 
	    			"    body{ margin: 0; padding: 0; }\r\n" + 
	    			"    img{ border: 0px; display: block; }\r\n" + 
	    			"\r\n" + 
	    			"    .socialLinks{ font-size: 6px; }\r\n" + 
	    			"    .socialLinks a{\r\n" + 
	    			"      display: inline-block;\r\n" + 
	    			"    }\r\n" + 
	    			"\r\n" + 
	    			"    .long-text p{ margin: 1em 0px; }\r\n" + 
	    			"    .long-text p:last-child{ margin-bottom: 0px; }\r\n" + 
	    			"    .long-text p:first-child{ margin-top: 0px; }\r\n" + 
	    			"  </style>\r\n" + 
	    			"  <style type=\"text/css\">\r\n" + 
	    			"    /* yahoo, hotmail */\r\n" + 
	    			"    .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{ line-height: 100%; }\r\n" + 
	    			"    .yshortcuts a{ border-bottom: none !important; }\r\n" + 
	    			"    .vb-outer{ min-width: 0 !important; }\r\n" + 
	    			"    .RMsgBdy, .ExternalClass{\r\n" + 
	    			"      width: 100%;\r\n" + 
	    			"      background-color: #3f3f3f;\r\n" + 
	    			"      background-color: #3f3f3f}\r\n" + 
	    			"\r\n" + 
	    			"    /* outlook/office365 add buttons outside not-linked images and safari have 2px margin */\r\n" + 
	    			"    [o365] button{ margin: 0 !important; }\r\n" + 
	    			"\r\n" + 
	    			"    /* outlook */\r\n" + 
	    			"    table{ mso-table-rspace: 0pt; mso-table-lspace: 0pt; }\r\n" + 
	    			"    #outlook a{ padding: 0; }\r\n" + 
	    			"    img{ outline: none; text-decoration: none; border: none; -ms-interpolation-mode: bicubic; }\r\n" + 
	    			"    a img{ border: none; }\r\n" + 
	    			"\r\n" + 
	    			"    @media screen and (max-width: 600px) {\r\n" + 
	    			"      table.vb-container, table.vb-row{\r\n" + 
	    			"        width: 95% !important;\r\n" + 
	    			"      }\r\n" + 
	    			"\r\n" + 
	    			"      .mobile-hide{ display: none !important; }\r\n" + 
	    			"      .mobile-textcenter{ text-align: center !important; }\r\n" + 
	    			"\r\n" + 
	    			"      .mobile-full{ \r\n" + 
	    			"        width: 100% !important;\r\n" + 
	    			"        max-width: none !important;\r\n" + 
	    			"      }\r\n" + 
	    			"    }\r\n" + 
	    			"    /* previously used also screen and (max-device-width: 600px) but Yahoo Mail doesn't support multiple queries */\r\n" + 
	    			"  </style>\r\n" + 
	    			"  <style type=\"text/css\">\r\n" + 
	    			"    \r\n" + 
	    			"    #ko_footerBlock_2 .links-color a, #ko_footerBlock_2 .links-color a:link, #ko_footerBlock_2 .links-color a:visited, #ko_footerBlock_2 .links-color a:hover{\r\n" + 
	    			"      color: #cccccc;\r\n" + 
	    			"      color: #cccccc;\r\n" + 
	    			"      text-decoration: underline\r\n" + 
	    			"    }\r\n" + 
	    			"    </style>\r\n" + 
	    			"  \r\n" + 
	    			"</head>\r\n" + 
	    			"<body bgcolor=\"#3f3f3f\" text=\"#919191\" alink=\"#cccccc\" vlink=\"#cccccc\" style=\"margin: 0; padding: 0; background-color: #3f3f3f; color: #919191;\"><center>\r\n" + 
	    			"\r\n" + 
	    			"  \r\n" + 
	    			"\r\n" + 
	    			"    \r\n" + 
	    			"    <!-- preheaderBlock -->\r\n" + 
	    			"    <table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#3f3f3f\" style=\"background-color: #3f3f3f;\" id=\"ko_preheaderBlock_1\">\r\n" + 
	    			"      <tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">\r\n" + 
	    			"      <div style=\"font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"></div>\r\n" + 
	    			"      <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n" + 
	    			"      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" class=\"vb-row\">\r\n" + 
	    			"        \r\n" + 
	    			"        <tbody><tr>\r\n" + 
	    			"      <td align=\"center\" valign=\"top\" style=\"font-size: 0; padding: 0 9px;\"><div style=\"width: 100%; max-width: 552px; -mru-width: 0px;\"><!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"552\"><tr><![endif]--><!--\r\n" + 
	    			"        --><!--\r\n" + 
	    			"          --><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"276\"><![endif]--><!--\r\n" + 
	    			"      --><div class=\"mobile-full\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 276px; -mru-width: 0px; min-width: calc(50%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--\r\n" + 
	    			"        --><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\" width=\"276\" align=\"left\">\r\n" + 
	    			"          \r\n" + 
	    			"            <tbody><tr><td width=\"100%\" valign=\"top\" align=\"left\" style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: left;\"><a style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\" href=\"[unsubscribe_link]\">Unsubscribe</a></td></tr>\r\n" + 
	    			"            \r\n" + 
	    			"          \r\n" + 
	    			"        </tbody></table><!--\r\n" + 
	    			"      --></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--\r\n" + 
	    			"          --><!--[if (gte mso 9)|(lte ie 8)]><td align=\"left\" valign=\"top\" width=\"276\" class=\"mobile-hide\"><![endif]--><!--\r\n" + 
	    			"      --><div class=\"mobile-full mobile-hide\" style=\"display: inline-block; vertical-align: top; width: 100%; max-width: 276px; -mru-width: 0px; min-width: calc(50%); max-width: calc(100%); width: calc(304704px - 55200%);\"><!--\r\n" + 
	    			"        --><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px; -yandex-p: calc(2px - 3%);\" width=\"276\" align=\"left\">\r\n" + 
	    			"          \r\n" + 
	    			"            <tbody><tr><td width=\"100%\" valign=\"top\" align=\"right\" style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: right;\"><a href=\"[show_link]\" style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\">View in your browser</a></td></tr>\r\n" + 
	    			"          \r\n" + 
	    			"        </tbody></table><!--\r\n" + 
	    			"      --></div><!--[if (gte mso 9)|(lte ie 8)]></td><![endif]--><!--\r\n" + 
	    			"        --><!--\r\n" + 
	    			"      --><!--[if (gte mso 9)|(lte ie 8)]></tr></table><![endif]--></div></td>\r\n" + 
	    			"    </tr>\r\n" + 
	    			"      \r\n" + 
	    			"      </tbody></table></div><!--\r\n" + 
	    			"    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + 
	    			"    </td></tr>\r\n" + 
	    			"    </tbody></table>\r\n" + 
	    			"    <!-- /preheaderBlock -->\r\n" + 
	    			"    \r\n" + 
	    			"\r\n" + 
	    			"  \r\n" + 
	    			"\r\n" + 
	    			"  \r\n" + 
	    			"\r\n" + 
	    			"  \r\n" + 
	    			"    <!-- footerBlock -->\r\n" + 
	    			"    <table role=\"presentation\" class=\"vb-outer\" width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"0\" bgcolor=\"#3f3f3f\" style=\"background-color: #3f3f3f;\" id=\"ko_footerBlock_2\">\r\n" + 
	    			"      <tbody><tr><td class=\"vb-outer\" align=\"center\" valign=\"top\" style=\"padding-left: 9px; padding-right: 9px; font-size: 0;\">\r\n" + 
	    			"    <!--[if (gte mso 9)|(lte ie 8)]><table role=\"presentation\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"570\"><tr><td align=\"center\" valign=\"top\"><![endif]--><!--\r\n" + 
	    			"      --><div style=\"margin: 0 auto; max-width: 570px; -mru-width: 0px;\"><table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 0px; border-spacing: 0px; max-width: 570px; -mru-width: 0px;\" width=\"570\" class=\"vb-row\">\r\n" + 
	    			"        \r\n" + 
	    			"      <tbody><tr>\r\n" + 
	    			"      <td align=\"center\" valign=\"top\" style=\"font-size: 0; padding: 0 9px;\"><div style=\"vertical-align: top; width: 100%; max-width: 552px; -mru-width: 0px;\"><!--\r\n" + 
	    			"        --><table role=\"presentation\" class=\"vb-content\" border=\"0\" cellspacing=\"9\" cellpadding=\"0\" style=\"border-collapse: separate; width: 100%; mso-cellspacing: 9px; border-spacing: 9px;\" width=\"552\">\r\n" + 
	    			"          \r\n" + 
	    			"        <tbody><tr><td class=\"long-text links-color\" width=\"100%\" valign=\"top\" align=\"center\" style=\"font-weight: normal; color: #919191; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\"><p style=\"margin: 1em 0px; margin-bottom: 0px; margin-top: 0px;\">Email sent to <a href=\"mailto:[mail]\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline;\">[mail]</a></p></td></tr>\r\n" + 
	    			"        <tr><td width=\"100%\" valign=\"top\" align=\"center\" style=\"font-weight: normal; color: #ffffff; font-size: 13px; font-family: Arial, Helvetica, sans-serif; text-align: center;\"><a href=\"[unsubscribe_link]\" style=\"color: #ffffff; text-decoration: underline;\" target=\"_new\">Unsubscribe</a></td></tr>\r\n" + 
	    			"        <tr style=\"text-align: center;\"><td width=\"100%\" valign=\"top\" align=\"center\" class=\"links-color\" style=\"text-align: center;\"><!--[if (lte ie 8)]><div style=\"display: inline-block; width: 170px; -mru-width: 0px;\"><![endif]--><a target=\"_new\" href=\"https://mosaico.io/?footerbadge\" style=\"color: #cccccc; color: #cccccc; text-decoration: underline;\"><img alt=\"MOSAICO Responsive Email Designer\" border=\"0\" hspace=\"0\" align=\"center\" vspace=\"0\" width=\"170\" src=\"https://mosaico.io/img/mosaico-badge.gif\" style=\"border: 0px; display: block; vertical-align: top; height: auto; margin: 0 auto; color: #3f3f3f; font-size: 13px; font-family: Arial, Helvetica, sans-serif; width: 100%; max-width: 170px;\"></a><!--[if (lte ie 8)]></div><![endif]--></td></tr>\r\n" + 
	    			"        </tbody></table></div></td>\r\n" + 
	    			"    </tr>\r\n" + 
	    			"    \r\n" + 
	    			"      </tbody></table></div><!--\r\n" + 
	    			"    --><!--[if (gte mso 9)|(lte ie 8)]></td></tr></table><![endif]-->\r\n" + 
	    			"  </td></tr>\r\n" + 
	    			"    </tbody></table>\r\n" + 
	    			"    <!-- /footerBlock -->\r\n" + 
	    			"    \r\n" + 
	    			"</center></body></html>";
	    	helper.setFrom(mailConfig.getUsername());
	    	helper.setTo(destino);
	    	helper.setSubject("Recuperar Senha");
	    	helper.setText(emailHTML,true);		
	       
	    	
	        mailSender.send(mime);
	    		        
	        return true;
		} catch (MessagingException e) {
			e.printStackTrace();
            return false;
		}

    }
    
}
