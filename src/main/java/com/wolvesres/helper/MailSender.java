package com.wolvesres.helper;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class MailSender extends Thread{
    
    static {
        MailSender sender = new MailSender();
        sender.start();
    }
    static final List<MimeMessage> queue = new ArrayList<>();

    public static void queue(MimeMessage mail) {
        synchronized (queue) {
            queue.add(mail);
            queue.notify();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (queue) {
                    if (queue.size() > 0) {
                        try {
                            MimeMessage mail = queue.remove(0);
                            Transport.send(mail);
                            System.out.println("The mail was send !");
                        } catch (MessagingException e) {
                            System.out.println("Unable to send mail !");
                            System.out.println(e);
                        }
                    } else {
                        queue.wait();
                    }
                }
            } catch (InterruptedException e) {
                break;
            };
        }
    }
}
