package com.mindcare.mindcareapi.client;

import java.util.List;

public class AiResponse {
    private String risk;
    private String reason;
    private List<String> recommendations;
    private List<String> referral;

    // getters / setters
    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    public List<String> getReferral() { return referral; }
    public void setReferral(List<String> referral) { this.referral = referral; }
}
