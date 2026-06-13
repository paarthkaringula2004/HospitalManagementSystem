package com.company.inter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "invoice", schema = "hospital")   // <-- IMPORTANT: not "prescription" anymore
@DynamicUpdate
public class invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceid")                 // PK: invoiceid
    private Integer invoiceID;

    @Column(name = "patient_name")              // keep column names consistent with DB
    private String patientName;

    @Column(name = "appointment_id")
    private Integer appointmentID;

    @Column(name = "invoice_text")              // or whatever column name you actually use
    private String invoice;

    public Integer getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public invoice(Integer invoiceID, String patientName, Integer appointmentID, String invoice) {
        super();
        this.invoiceID = invoiceID;
        this.patientName = patientName;
        this.appointmentID = appointmentID;
        this.invoice = invoice;
    }

    public invoice() {
    }
}
