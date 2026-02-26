package com.example.agent.web;

import com.example.agent.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentControllerIT {

  @LocalServerPort
  int port;

  WebTestClient web;

  @MockitoBean
  AgentService agentService;

  @BeforeEach
  void setUp() {
    web = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  void should_call_endpoint() {
    when(agentService.run(anyString()))
        .thenReturn("Issue created successfully: {number=1, html_url=https://github.com/o/r/issues/1}");

    web.post().uri("/api/agent/run")
        .header("Content-Type", "application/json")
        .bodyValue(Map.of("prompt", "Create a task to add OpenTelemetry"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .isEqualTo("Issue created successfully: {number=1, html_url=https://github.com/o/r/issues/1}");
  }
}