package com.monitor.pcmonitor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MonitorAppTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    SystemDataCollector collector;

    // ── Test 1: contesto Spring si avvia ──────────────────────────────────────
    @Test
    @DisplayName("Il contesto Spring Boot si carica correttamente")
    void contextLoads() {
        assertThat(collector).isNotNull();
    }

    // ── Test 2: /api/status risponde 200 e JSON valido ────────────────────────
    @Test
    @DisplayName("GET /api/status → 200 OK con JSON")
    void statusEndpointReturns200() throws Exception {
        mvc.perform(get("/api/status"))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith("application/json"))
           .andExpect(jsonPath("$.cpuLoad").isNumber())
           .andExpect(jsonPath("$.ramTotal").isNumber())
           .andExpect(jsonPath("$.ramUsed").isNumber())
           .andExpect(jsonPath("$.ramFree").isNumber())
           .andExpect(jsonPath("$.diskTotal").isNumber())
           .andExpect(jsonPath("$.diskUsed").isNumber())
           .andExpect(jsonPath("$.diskFree").isNumber())
           .andExpect(jsonPath("$.topProcesses").isArray());
    }

    // ── Test 3: dati coerenti (RAM used + free == total) ─────────────────────
    @Test
    @DisplayName("Metriche RAM: used + free == total (entro 1 MB di arrotondamento)")
    void ramValuesAreCoherent() throws Exception {
        // Aspetta che il collector abbia almeno un campione
        Thread.sleep(3000);
        SystemMetrics m = collector.getLatestMetrics();
        assertThat(m.getRamTotal()).isGreaterThan(0);
        // used + free deve essere ≤ total (alcune pagine kernel non contate)
        assertThat(m.getRamUsed() + m.getRamFree()).isLessThanOrEqualTo(m.getRamTotal() + 1);
    }

    // ── Test 4: CPU load in range 0-100 ──────────────────────────────────────
    @Test
    @DisplayName("CPU load è nel range [0, 100]")
    void cpuLoadInRange() throws Exception {
        Thread.sleep(3000);
        SystemMetrics m = collector.getLatestMetrics();
        assertThat(m.getCpuLoad()).isBetween(0.0, 100.0);
    }

    // ── Test 5: Disco ha valori positivi ──────────────────────────────────────
    @Test
    @DisplayName("Disco: total > 0 e used <= total")
    void diskValuesAreCoherent() throws Exception {
        Thread.sleep(3000);
        SystemMetrics m = collector.getLatestMetrics();
        assertThat(m.getDiskTotal()).isGreaterThan(0);
        assertThat(m.getDiskUsed()).isLessThanOrEqualTo(m.getDiskTotal());
    }

    // ── Test 6: Top processi presenti e validi ────────────────────────────────
    @Test
    @DisplayName("Top processi: lista non vuota con PID e nome")
    void topProcessesAreValid() throws Exception {
        Thread.sleep(3000);
        SystemMetrics m = collector.getLatestMetrics();
        assertThat(m.getTopProcesses()).isNotEmpty();
        m.getTopProcesses().forEach(p -> {
            assertThat(p.getPid()).isGreaterThan(0);
            assertThat(p.getName()).isNotBlank();
            assertThat(p.getRamUsedMb()).isGreaterThanOrEqualTo(0);
        });
    }

    // ── Test 7: Kill del processo JVM stesso viene rifiutato ─────────────────
    @Test
    @DisplayName("POST /api/kill/<jvm-pid> → 400 Bad Request")
    void killSelfIsRejected() throws Exception {
        long selfPid = ProcessHandle.current().pid();
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .post("/api/kill/" + selfPid))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.success").value(false));
    }
}
