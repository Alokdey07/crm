package com.crm.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import com.crm.entity.Lead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.knf.dev.model.Employee;

public class PDFHelperServiceImpl {



    public static ByteArrayInputStream employeePDFReport
            (List<Lead> leads) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory
                    .getFont(FontFactory.COURIER, 14,BaseColor.BLACK);
            Paragraph para = new Paragraph("Employee Table", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(10);
            // Add PDF Table Header ->
            Stream.of("lid","First Name", "Last Name", "Email", "Mobile","Lead Type", "Address", "Designation", "Company","Note" )
                    .forEach(headerTitle ->
                    {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.
                                getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (Lead lead : leads) {
                PdfPCell lidCell = new PdfPCell(new Phrase(lead.getLid().
                        toString()));
                lidCell.setPaddingLeft(4);
                lidCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lidCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(lidCell);

                PdfPCell firstNameCell = new PdfPCell(new Phrase
                        (lead.getFirstName()));
                firstNameCell.setPaddingLeft(4);
                firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(firstNameCell);

                PdfPCell lastNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getLastName())));
                lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lastNameCell.setPaddingRight(4);
                table.addCell(lastNameCell);

                PdfPCell emailNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getEmail())));
                emailNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                emailNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                emailNameCell.setPaddingRight(4);
                table.addCell(emailNameCell);

                PdfPCell mobileNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getMobile())));
                mobileNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                mobileNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                mobileNameCell.setPaddingRight(4);
                table.addCell(mobileNameCell);

                PdfPCell leadTypeNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getLeadType())));
                leadTypeNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leadTypeNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                leadTypeNameCell.setPaddingRight(4);
                table.addCell(leadTypeNameCell);

                PdfPCell addressNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getAddress())));
                addressNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                addressNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                addressNameCell.setPaddingRight(4);
                table.addCell(addressNameCell);

                PdfPCell designationTypeNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getDesignation())));
                designationTypeNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                designationTypeNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                designationTypeNameCell.setPaddingRight(4);
                table.addCell(designationTypeNameCell);

                PdfPCell companyTypeNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getCompany())));
                companyTypeNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                companyTypeNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                companyTypeNameCell.setPaddingRight(4);
                table.addCell(companyTypeNameCell);

                PdfPCell noteTypeNameCell = new PdfPCell(new Phrase
                        (String.valueOf(lead.getNote())));
                noteTypeNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                noteTypeNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                noteTypeNameCell.setPaddingRight(4);
                table.addCell(noteTypeNameCell);
            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            System.out.println(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}