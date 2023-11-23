package com.crm.controller;

import com.crm.entity.Lead;
import com.crm.payload.LeadDto;
import com.crm.service.impl.ExcelHelperServiceImpl;
import com.crm.service.LeadService;
import com.crm.service.impl.PDFHelperServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    public LeadService leadService;


    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {
        LeadDto savedDto = leadService.createLead(leadDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);

    }

    @DeleteMapping("/{lid}")
    public ResponseEntity<String> leadById(@PathVariable("lid") String lid) {
        String s = leadService.deleteByLid(lid);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @GetMapping
    public ResponseEntity<List<LeadDto>> getAllLeads() {
        List<LeadDto> listlead = leadService.getAllLeads();
        return ResponseEntity.status(HttpStatus.OK).body(listlead);
    }

    @GetMapping("/get/{lid}")
    public ResponseEntity<LeadDto> findByLid(@PathVariable("lid") String lid) {
        LeadDto Dto = leadService.findByLid(lid);
        return ResponseEntity.status(HttpStatus.OK).body(Dto);
    }

    //http://localhost:8080/api/leads/excelReports
    @GetMapping("/excelReport")
    public ResponseEntity<Resource> getAllLeadsExcelReports() {
        List<Lead> leads = leadService.getLeadsExcelReports();
        ByteArrayInputStream leadReports = ExcelHelperServiceImpl.leadsToExcel(leads);
        String filename = "leads.xlsx";
        InputStreamResource file = new InputStreamResource(leadReports);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }

    @GetMapping(value = "/leadPDFReports", produces =
            MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport()
            throws IOException {
        List<Lead> leads = (List<Lead>) leadService.getLeadsExcelReports();

        ByteArrayInputStream pdfReport = PDFHelperServiceImpl.employeePDFReport(leads);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=leads.pdf");

        return ResponseEntity.ok().headers(headers).contentType
                        (MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfReport));


    }

}
