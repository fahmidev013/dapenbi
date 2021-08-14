package test.fahmi.dapenbi.utils;

import java.time.Instant;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import test.fahmi.dapenbi.model.User;

@Configuration

@EnableBatchProcessing


public class BatchConfiguration {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public DataSource dataSource;
	
	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		// Configure database settings here
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/dapenbi?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		return dataSource;
	
	}
	
	@Bean
	public FlatFileItemReader<User> reader() {
		
		FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
		
		// Name of source csv file . Relative path is src/main/resources
		reader.setResource(new ClassPathResource("user.csv"));
		
		//Setting token names for the values extracted from csv
		reader.setLineMapper(new DefaultLineMapper<User>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"nip","name","dob","address"});
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
		JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
		
		// Update MySQL database using insert statements and object fields (starting with :)
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
		writer.setSql("Insert Into User (nip,name,dob,address) "
				+ "VALUES (:nip,:name,:dob,:address)");
		writer.setDataSource(dataSource);
		
		return writer;
	}
	
	
	// First step reads data from CSV and inserts it into MySQL database
	@Bean
	public Step step() {
		
		return stepBuilderFactory.get("step").<User,User> chunk(3).reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	@Bean
	public BulkDataProcessor processor2() {
		return new BulkDataProcessor();
	}
	
	@Bean
	public FlatFileItemWriter<User> writer2() {
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
		
		// Assign Header Information for the Flat File
		String header = "Nip,Name,DOB,Address";
		Header headerWriter = new Header(header);
		writer.setHeaderCallback(headerWriter);
		
		// Retrieve Timestamp in epoch notation and assign file name
		Instant instant = Instant.now();
		long ts = instant.toEpochMilli();
		String csv_res = "bulk-data-"+ String.valueOf(ts)+".csv";
		
		//Relative Path to Project
		String path = "src/main/resources/csv/";
		writer.setResource(new FileSystemResource(path + csv_res));
		
		DelimitedLineAggregator<User> delimitedLineAggregator = new DelimitedLineAggregator<User>();
		delimitedLineAggregator.setDelimiter(",");
		
		// Extracts values from User class object fields
		BeanWrapperFieldExtractor<User> fieldExtractor = new BeanWrapperFieldExtractor<User>();
		fieldExtractor.setNames(new String[] {"nip","name","dob","address"});
		delimitedLineAggregator.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delimitedLineAggregator);
		
		return writer;
	}
	
	
	// Second step reads data from CSV and filters out incorrect information to another CSV file
	@Bean
	public Step step2() {
		
		return stepBuilderFactory.get("step2").<User,User> chunk(3).reader(reader())
				.processor(processor2())
				.writer(writer2())
				.build();
	}
	
		
	@Bean
	public Job importUserJob() { 
		
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("flow");
		Flow flow = flowBuilder
					.start(step())
					.next(step2())
					.end();
		
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.start(flow)
				.end()
				.build();
	}

}
