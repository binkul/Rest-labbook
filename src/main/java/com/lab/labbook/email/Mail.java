package com.lab.labbook.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String toCc;
    private String subject;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Mail)) return false;
        Mail mail = (Mail) o;
        return Objects.equals(mailTo, mail.mailTo) &&
                Objects.equals(toCc, mail.toCc) &&
                Objects.equals(subject, mail.subject) &&
                Objects.equals(message, mail.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailTo, toCc, subject, message);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mailTo='" + mailTo + '\'' +
                ", toCc='" + toCc + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
