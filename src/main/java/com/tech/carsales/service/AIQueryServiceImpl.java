package com.tech.carsales.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIQueryServiceImpl implements AIQueryService {

    private final ChatClient chatClient;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public String process(String question) {
        String sql = generateSQL(question);

        log.info("query:{} ", sql);

        if (sql.equalsIgnoreCase("INVALID")) {
            return "Only table related questions allowed";
        }
        if (!isSafe(sql)) {
            return "Unsafe query";
        }

        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            if (result.isEmpty()) {
                return "No Data found";
            }
            System.out.println(result);

            log.info("result:{} ", result);

            //Convert Result: Human Friendly result:
            return toNaturalLanguage(question,result);

        } catch (Exception e) {
            return "Query failed";
        }
    }
    private String toNaturalLanguage(String question ,List<Map<String,Object>>result){
        String prompt = """
                Convert database result into a human readable answer.
                
                User Question:
                """ + question + """
                
                DB Result:
                """ + result.toString() + """
                
                Rules:
                - Answer Clearly (don't write too much)
                - Don't show JSON
                - Don't explain SQL
                """;
        return Objects.requireNonNull(chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content())
                .trim();
    }
    private boolean isSafe(String sql) {
        String lower = sql.toLowerCase();
        return lower.startsWith("select")
                && !lower.startsWith("drop")
                && !lower.startsWith("delete")
                && !lower.startsWith("update")
                && !lower.startsWith("insert");
    }

    private String generateSQL(String question) {

        String prompt = """
                You are an expert SQL query generator.
                
                Database Table:
                car_sales
                
                Column Definitions:
                - id : unique car record id
                - brand : car manufacturer (Honda, Hyundai, Tata, etc.)
                - model : car model name (City, Swift, Creta, Nexon, etc.)
                - year : manufacturing year
                - color : car color
                - fuel_type : Petrol, Diesel, CNG, EV
                - mileage : mileage of car
                - price : car price
                - city : customer city
                - state : customer state
                - customer_name : customer name
                - contact_number : customer contact number
                - email : customer email
                - payment_mode : Cash, Card, UPI, Loan, etc.
                - engine : engine details
                - warranty_period : warranty information
                - date_of_purchase : purchase date
                - time_of_purchase : purchase time
                - car_number : registration number
                
                Important Understanding Rules:
                - "City" may refer to a car model, not a city location.
                - Known car models include examples such as:
                  City, Swift, Creta, Nexon, Baleno, Verna.
                - If user says:
                  "City model ke kitne car hai"
                  OR
                  "model City ke kitne car hai"
                  OR
                  "hamare paas City car kitni hai"
                  then interpret City as model value and use:
                  WHERE model = 'City'
                
                SQL Rules:
                1. Generate ONLY a valid SQL SELECT query.
                2. Do NOT explain anything.
                3. Do NOT use markdown.
                4. Do NOT wrap output in ```sql.
                5. Do NOT add notes or comments.
                6. If question is unrelated to the table, return exactly:
                   INVALID
                7. Output must contain only SQL or INVALID.
                
                User Question:
                %s
                """.formatted(question);

        return Objects.requireNonNull(chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content())
                .trim();
    }
}
