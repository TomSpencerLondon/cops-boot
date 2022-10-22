package com.tomspencerlondon.copsboot.report.web;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportParameters {
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private ZonedDateTime dateTime;

  @ValidReportDescription
  private String description;

  @NotNull
  private MultipartFile image;
}