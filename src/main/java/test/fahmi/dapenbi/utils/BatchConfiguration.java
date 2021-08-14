package test.fahmi.dapenbi.utils;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;

import test.fahmi.dapenbi.model.User;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
 
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  @Autowired
  private DataSource dataSource;
 
 
  // tag::readerwriterprocessor[]
  @Bean
  public FlatFileItemReader<User> reader() {
    FlatFileItemReader<User> reader = new FlatFileItemReader<>();
    reader.setResource(new PathResource("dataRandom.csv"));
    reader.setLineMapper(new DefaultLineMapper<User>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{"nip", "name", "dob", "gender", "address", "idNumber", "phone"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
        setTargetType(User.class);
      }});
    }});
    return reader;
  }
 
  @Bean
  public UserItemProcessor processor() {
    return new UserItemProcessor();
  }
 
  @Bean
  public JdbcBatchItemWriter<User> writer() {
    JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<>();
    writer.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<User>());
    writer.setSql("INSERT INTO user (nip, name, dob, gender, address, id_number, phone) VALUES (:nip, :name, :dob, :gender, :address , :idNumber, :phone)");
    writer.setDataSource(dataSource);
    return writer;
  }
  // end::readerwriterprocessor[]
 
 
  // tag::jobstep[]
  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener) {
    return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer())
        .listener(listener).flow(step1()).end().build();
  }
 
  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(reader())
        .processor(processor()).writer(writer()).build();
  }
  // end::jobstep[]
}
