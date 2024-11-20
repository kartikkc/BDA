
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Uber1 {
    // Mapper class
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM/dd/yyyy");
        String[] days = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        private Text basement = new Text();
        Date date = null;
        private int trips;

        // Map function
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] splits = line.split(",");
            basement.set(splits[0]); 
            basement = first column (presumably a location or similar) try {
            date = format.parse(splits[1]); // Parsing the date (second column)
        }catch(
        ParseException e)
        { e.printStackTrace();
}trips=Integer.parseInt(splits[3]); // Parsing trips (fourth column)
        // Creating a composite key from basement and day of the week String keys =
        basement.toString() + " " + days[date.getDay()];
        context.write(new Text(keys),new IntWritable(trips)); // Writing the output
    }
}

// Reducer class
public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    // Reduce function
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        // Summing up the values (total trips for the given key)
         for (IntWritable val :values) {
        sum += val.get();
    }result.set(sum);context.write(key,result); // Writing the result

}}

    // Main method
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Uber1");
        // Setting the job's main class and Mapper/Reducer classes
        job.setJarByClass(Uber1.class); job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        // Setting output key/value types job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // Setting input and output paths FileInputFormat.addInputPath(job, new
        Path(args[0])); FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // Exiting based on job completion status
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
