package dev.daryz.translateT.translation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcTranslationRepository implements TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Translation translation) {
        String sql = "INSERT INTO translations (ip_address, local_date_time, source_lang, target_lang, query, response) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                translation.getIpAddress(),
                translation.getLocalDateTime(),
                translation.getSourceLang(),
                translation.getTargetLang(),
                translation.getQuery(),
                translation.getResponse());
    }

    @Override
    public Translation findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM translations WHERE id = ?",
                new Object[]{id},
                new TranslationRowMapper()
        );
    }

    @Override
    public List<Translation> findAll() {
        return jdbcTemplate.query("SELECT * FROM translations", new TranslationRowMapper());
    }

    @Override
    public List<Translation> findByIpAddress(String ipAddress) {
        return jdbcTemplate.query(
                "SELECT * FROM translations WHERE ip_address = ?",
                new Object[]{ipAddress},
                new TranslationRowMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM translations WHERE id = ?", id);
    }

    private static class TranslationRowMapper implements RowMapper<Translation> {
        @Override
        public Translation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Translation translation = new Translation();
            translation.setIpAddress(rs.getString("ip_address"));
            translation.setLocalDateTime(rs.getTimestamp("local_date_time").toLocalDateTime());
            translation.setSourceLang(rs.getString("source_lang"));
            translation.setTargetLang(rs.getString("target_lang"));
            translation.setQuery(rs.getString("query"));
            translation.setResponse(rs.getString("response"));
            return translation;
        }
    }
}
