package com.springfirst.solutions.gym.repos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springfirst.solutions.gym.bootstrap.Dataloader;
import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JDBCTemplateTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void trainerQuery() {

        List<Trainer> results = jdbcTemplate.query("SELECT id, biography, employee_number,email,name, telnumber FROM trainer t order by email", new RowMapper<Trainer>() {
            @Override
            public Trainer mapRow(ResultSet rs, int rownumber) throws SQLException {
                return Trainer.builder()
                        .id(rs.getLong(1))
                        .biography(rs.getString(2))
                        .employeeID(rs.getString(3))
                        .email(rs.getString(4))
                        .name(rs.getString(5))
                        .telNo(rs.getString(6))
                        .build();
            }
        });

        Set<String> emails = new HashSet<>();

        results.stream().forEach(trainer -> {
            emails.add(trainer.getEmail());
        });

        Set<String> expected = Set.of("kelly@strong.com", "smith@bar.com");

        Assertions.assertAll(
                () -> {
                    Assertions.assertTrue(results.size() == 2);
                },
                () -> {
                    Assertions.assertEquals(expected, emails);
                });


    }

    @Disabled
    @Test
    @DirtiesContext
    public void saveTrainerByPreparedStatement(){

        Trainer trainer = Trainer.builder()
                .employeeID("P3972")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .email("foo1@bar.com")
                .biography("Been a personal trainer for 10 years")
                .id(999L)
                .build();

        final String query="insert into trainer (employee_number,name, telnumber,email, biography, id) values(?,?,?,?,?,?)";
        int rows = jdbcTemplate.update(query, (PreparedStatementCallback<Boolean>) ps -> {

            ps.setString(1,trainer.getEmployeeID());
            ps.setString(2,trainer.getName());
            ps.setString(3,trainer.getTelNo());
            ps.setString(4,trainer.getEmail());
            ps.setString(5,trainer.getBiography());
            ps.setLong(6,trainer.getId());

            return ps.execute();

        });

        Assertions.assertTrue(rows==1);
    }

}