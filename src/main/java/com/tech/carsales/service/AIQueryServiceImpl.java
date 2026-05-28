package com.tech.carsales.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AIQueryServiceImpl implements AIQueryService{

    private final ChatClient chatClient;


    @Override
    public String process(String question) {
        return generateSQL(question);
    }

    private String generateSQL(String question){

        String prompt = """
                You are a SQL generator.
                Columns: id, brand, car_number, city, color, contact_number, customer_name, date_of_purchase, email, engine, fuel_type, mileage, model, payment_mode, price, state, time_of_purchase, warranty_period, year
                
                Rules:
                - Only Select Queries
                - Use Only given columns
                - If not related, return: INVALID
                - Return Only SQL
                
                Question:
                """ + question;

        return Objects.requireNonNull(chatClient.prompt().user(prompt).call().content()).trim();
    }
}
