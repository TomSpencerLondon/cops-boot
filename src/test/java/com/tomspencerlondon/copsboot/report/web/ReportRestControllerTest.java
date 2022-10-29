package com.tomspencerlondon.copsboot.report.web;

import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.HEADER_AUTHORIZATION;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.bearer;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.obtainAccessToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import com.tomspencerlondon.copsboot.infrastructure.test.CopsbootControllerTest;
import com.tomspencerlondon.copsboot.report.Report;
import com.tomspencerlondon.copsboot.report.ReportId;
import com.tomspencerlondon.copsboot.report.ReportService;
import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserId;
import com.tomspencerlondon.copsboot.user.Users;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@CopsbootControllerTest(ReportRestController.class)
class ReportRestControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ReportService service;

  @Test
  public void officerIsAbleToPostAReport() throws Exception {
    String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
    ZonedDateTime dateTime = ZonedDateTime.parse("2018-04-11T22:59:03.189+02:00");
    String description = "The suspect is wearing a black hat.";
    MockMultipartFile image = createMockImage();
    when(service.createReport(eq(Users.officer().getId()), any(ZonedDateTime.class), eq(description)))
        .thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), dateTime, description));

    postReport(accessToken, dateTime, description, image)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
        .andExpect(jsonPath("dateTime").value("2018-04-11T22:59:03.189+02:00"))
        .andExpect(jsonPath("description").value(description));
  }

  @Test
  void officerIsAbleToGetAReport() throws Exception {
    String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
    UUID uuid = UUID.randomUUID();
    String description = "suspect 1";
    when(service.findReportById(new ReportId(uuid)))
        .thenReturn(Optional.of(new Report(new ReportId(uuid), Users.officer(),
            ZonedDateTime.parse("2018-04-11T22:59:03.189+02:00"), description)));

    String reporterId = Users.officer().getId().asString();
    mvc.perform(get(String.format("/api/reports/%s", uuid))
            .header(HEADER_AUTHORIZATION, "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("reporter").value(reporterId))
        .andExpect(jsonPath("dateTime").value("2018-04-11T22:59:03.189+02:00"))
        .andExpect(jsonPath("description").value(description));
  }


  @NotNull
  private ResultActions postReport(String accessToken, ZonedDateTime dateTime, String description, MockMultipartFile image) throws Exception {
    return mvc.perform(fileUpload("/api/reports")
        .file(image)
        .header(HEADER_AUTHORIZATION, bearer(accessToken))
        .param("dateTime", String.valueOf(dateTime))
        .param("description", description));
  }

  private MockMultipartFile createMockImage() {
    return new MockMultipartFile("image", "picture.png", "image/png", new byte[]{1, 2, 3});
  }
}