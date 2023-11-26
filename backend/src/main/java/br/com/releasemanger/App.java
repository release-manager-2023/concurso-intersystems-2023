package br.com.releasemanger;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class App {

	public static void main(String... args) {
//		System.out.println("Running main method");
//		IRISDatabase database = (IRISDatabase) DatabaseFactory.getInstance().openDatabase(url, null, null, null, null);
//		Liquibase liquibase = new Liquibase("liquibase/ext/changelog.generic.test.xml",
//				new ClassLoaderResourceAccessor(), database);
//		liquibase.update("");
		Quarkus.run(args);
	}
}
