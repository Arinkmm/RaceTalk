package com.racetalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceDto {
    private int session_key;
    private String circuit_short_name;
    private String date_start;

    public int getSession_key() { return session_key; }
    public void setSession_key(int session_key) { this.session_key = session_key; }

    public String getCircuit_short_name() { return circuit_short_name; }
    public void setCircuit_short_name(String circuit_short_name) { this.circuit_short_name = circuit_short_name; }

    public String getDate_start() { return date_start; }
    public void setDate_start(String date_start) { this.date_start = date_start; }
}
