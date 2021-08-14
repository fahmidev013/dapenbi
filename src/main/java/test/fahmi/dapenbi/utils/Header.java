package test.fahmi.dapenbi.utils;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class Header implements FlatFileHeaderCallback {

	private final String header_csv;
	
	public Header(String header) {
		super();
		this.header_csv = header;
	}
	
	@Override
	public void writeHeader(Writer writer) throws IOException {
		// TODO Auto-generated method stub
		writer.write(header_csv);
		
	}
	
	

}
