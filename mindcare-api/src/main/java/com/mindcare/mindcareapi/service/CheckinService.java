package com.mindcare.mindcareapi.service;

import com.mindcare.mindcareapi.client.AiClient;
import com.mindcare.mindcareapi.model.Checkin;
import com.mindcare.mindcareapi.model.User;
import com.mindcare.mindcareapi.repository.CheckinRepository;
import com.mindcare.mindcareapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final UserRepository userRepository;
    private final AiClient aiClient;

    public CheckinService(CheckinRepository checkinRepository, UserRepository userRepository, AiClient aiClient) {
        this.checkinRepository = checkinRepository;
        this.userRepository = userRepository;
        this.aiClient = aiClient;
    }

    @Transactional
    public Checkin processCheckin(Long userId, String text) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Checkin c = new Checkin();
        c.setUser(user);
        c.setRawText(text);

        String aiJson = aiClient.analyzeText(text);
        c.setAiResponseJson(aiJson);

        if (aiJson.contains("\"HIGH\"")) c.setRiskLevel("HIGH");
        else if (aiJson.contains("\"MODERATE\"")) c.setRiskLevel("MODERATE");
        else c.setRiskLevel("LOW");

        c.setRecommendations("Descansar | Procurar água");

        return checkinRepository.save(c);
    }

    public List<Checkin> findByUser(Long userId) {
        return checkinRepository.findByUserId(userId);
    }

    public Checkin findById(Long id) {
        return checkinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Check-in não encontrado"));
    }

    public Map<String,Object> getStats() {
        List<Checkin> list = checkinRepository.findAll();

        long low = list.stream().filter(c -> "LOW".equals(c.getRiskLevel())).count();
        long moderate = list.stream().filter(c -> "MODERATE".equals(c.getRiskLevel())).count();
        long high = list.stream().filter(c -> "HIGH".equals(c.getRiskLevel())).count();

        Map<String,Object> m = new HashMap<>();
        m.put("total", list.size());
        m.put("low", low);
        m.put("moderate", moderate);
        m.put("high", high);
        return m;
    }
}
