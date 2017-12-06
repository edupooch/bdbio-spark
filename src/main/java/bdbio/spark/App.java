package bdbio.spark;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import modelo.Proteina;

public class App {
	public static void main(String[] args) {
		PrintStream out;
		try {
			out = new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		// Configura o Spark
		SparkConf conf = new SparkConf().setMaster("local").setAppName("Proteins");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		SQLContext sctx = new SQLContext(ctx);
		
		// Converte texto do nosso txt pra object Proteina
		JavaRDD<String> linhas = ctx.textFile("proteinas.txt");
		JavaRDD<Proteina> proteinas = linhas.map(x -> x.split(",")).map(p -> new Proteina(p[0], p[1], p[2], p[3]));

		// Cria o DataFrame
		DataFrame proteinaDF = sctx.createDataFrame(proteinas, Proteina.class);
		
		System.out.println("Mostra dataset completo");
		proteinaDF.show();

		/**
		 * UTILIZANDO MÉTODOS DE CONSULTA DO SPARK
		 */
		System.out.println("Mostra todos os nomes");
		proteinaDF.select("nome").show();
		
	
		System.out.println("Total de sequências por organismo");
		proteinaDF.groupBy("organismo").count().show();

		System.out.println("Dados ordenados por organismo");
		proteinaDF.orderBy("organismo").show();

		System.out.println("Seleciona sequencia de id P28590.1");
		proteinaDF.filter(proteinaDF.col("id").equalTo("P28590.1")).select("sequencia").show();
		
		/**
		 * UTILIZANDO SQL PARA CONSULTAS
		 */
		 proteinaDF.registerTempTable("proteina");
		 
		 System.out.println("SELECT id, nome, organismo, sequencia FROM proteina");
		 sctx.sql("SELECT id, nome, organismo, sequencia FROM proteina").show();
		 
		 System.out.println("SELECT nome, sequencia FROM proteina WHERE organismo like 'Bos taurus'");
         sctx.sql("SELECT nome, sequencia FROM proteina WHERE organismo like 'Bos taurus'").show();

	}
}
