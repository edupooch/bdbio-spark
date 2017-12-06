package bdbio.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import modelo.Proteina;

public class App {
	public static void main(String[] args) {
		// Configura o Spark
		SparkConf conf = new SparkConf().setMaster("local").setAppName("Proteins");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		SQLContext sctx = new SQLContext(ctx);
		
		// Converte texto do nosso txt pra object Proteina
		JavaRDD<String> linhas = ctx.textFile("proteinas.txt");
		JavaRDD<Proteina> proteinas = linhas.map(x -> x.split(",")).map(p -> new Proteina(p[0], p[1], p[2], p[3]));
		
		// Cria o DataFrame
		DataFrame onibusDF = sctx.createDataFrame(proteinas, Proteina.class);
		onibusDF.show();

	}
}
