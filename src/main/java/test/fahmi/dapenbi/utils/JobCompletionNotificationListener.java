package test.fahmi.dapenbi.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import test.fahmi.dapenbi.DapenbiApplication;
import test.fahmi.dapenbi.model.User;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
 
  private final JdbcTemplate jdbcTemplate;
 
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
 
  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
    	DapenbiApplication.log.info("!!! JOB FINISHED! Time to verify the results");
 
      List<User> results = jdbcTemplate
          .query("SELECT nip, name, dob, gender, address, id_number, phone FROM user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int row) throws SQLException {
              return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
          });
 
      for (User User : results) {
    	  DapenbiApplication.log.info("Found <" + User + "> in the database.");
      }
 
    }
  }
}
