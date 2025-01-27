// Tags associated with each movie
// Exp-8
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Driver1 {

    public static void main(String[] args) throws Exception {
        Path firstPath = new Path(args[0]);
        Path sencondPath = new Path(args[1]);
        Path outputPath_1 = new Path(args[2]);
        Path outputPath_2 = new Path(args[3]);
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Most Viewed Movies");
        job.setJarByClass(Driver1.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        MultipleInputs.addInputPath(job, firstPath, TextInputFormat.class, movieDataMapper.class);
        MultipleInputs.addInputPath(job, sencondPath, TextInputFormat.class, ratingDataMapper.class);
        job.setReducerClass(dataReducer.class);
        FileOutputFormat.setOutputPath(job, outputPath_1);
        job.waitForCompletion(true);
        Job job1 = Job.getInstance(conf, "Most Viewed Movies2");
        job1.setJarByClass(Driver1.class);
        job1.setMapperClass(topTenMapper.class);
        job1.setReducerClass(topTenReducer.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(LongWritable.class);
        job1.setOutputKeyClass(LongWritable.class);
        job1.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job1, outputPath_1);
        FileOutputFormat.setOutputPath(job1, outputPath_2);
        job1.waitForCompletion(true);
    }
}
