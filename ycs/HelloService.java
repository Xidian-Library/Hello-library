package io.junq.examples.boot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import io.junq.examples.boot.Librarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Librarian> getList(){
        String sql = "SELECT id,password FROM library";
        return (List<Librarian>) jdbcTemplate.query(sql, new RowMapper<Librarian>(){

            @Override
            public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
                Librarian lib = new Librarian();
                lib.setID(rs.getString("id"));
                lib.setPassword(rs.getString("password"));
                return lib;
            }
        });
    }
}
