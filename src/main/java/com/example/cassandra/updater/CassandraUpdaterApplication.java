package com.example.cassandra.updater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

@SpringBootApplication
public class CassandraUpdaterApplication implements CommandLineRunner {

	@Autowired
	private CassandraConnector cassandraConnector;
//	private Session session;
//	private MappingManager manager;
//	private Mapper<ElementState> mapper;

	PreparedStatement ps = null;

	@Value("${cassandra.keyspace}")
	private String keyspaceName;

	public static void main(String[] args) {
		SpringApplication.run(CassandraUpdaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int j = 0; j < 10; j++) {
			long ini = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				Session session = cassandraConnector.getSession();
				String insertUpdateString = null;
				if (j == 0) {
					insertUpdateString = "INSERT INTO elementstate(elementtypeelement,state) values (?,?)";
				} else {
					insertUpdateString = "update elementstate set state=? where elementtypeelement=?";
				}
				if ((i == 0 && j == 0) || (i == 0 && j == 1)) {
					ps = session.prepare(insertUpdateString);
				}
				ElementState es = new ElementState();
				es.setElementTypeElement("prueba" + i);
				es.setState(String.valueOf(j));
				try {
					if (j == 0) {
						session.execute(ps.bind(es.getElementTypeElement(), es.getState()));
					} else {
						session.execute(ps.bind(es.getState(), es.getElementTypeElement()));
					}
				} catch (Exception e) {
					System.out.println("error, volvemos a intentar...");
					i--;
				}
			}
			System.out.println("Vuelta " + j + " " + (System.currentTimeMillis() - ini));
		}

	}

}
