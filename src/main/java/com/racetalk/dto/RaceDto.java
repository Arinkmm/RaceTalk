package com.racetalk.dto;

public class RaceDto {
    private int session_key;
    private String meeting_name;
    private String circuit_short_name;
    private String session_start_utc;

    public int getSession_key() { return session_key; }
    public void setSession_key(int session_key) { this.session_key = session_key; }

    public String getMeeting_name() { return meeting_name; }
    public void setMeeting_name(String meeting_name) { this.meeting_name = meeting_name; }

    public String getCircuit_short_name() { return circuit_short_name; }
    public void setCircuit_short_name(String circuit_short_name) { this.circuit_short_name = circuit_short_name; }

    public String getSession_start_utc() { return session_start_utc; }
    public void setSession_start_utc(String session_start_utc) { this.session_start_utc = session_start_utc; }
}
