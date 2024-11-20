
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Max_temp {
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        // Mapper
        Text k = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            while (tokenizer.hasMoreTokens()) {
                String year = tokenizer.nextToken();
                k.set(year);
                String temp = tokenizer.nextToken().trim();
                int v = Integer.parseInt(temp);
                context.write(k, newIntWritable(v));
            }
        }
    }

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int maxtemp = 0;
        for (IntWritable it : values) {
            int temperature = it.get();
            if (maxtemp < temperature) {
                maxtemp = temperature;
            }
        }
        context.write(key, new IntWritable(maxtemp));
    }

}

    // Driver
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Max_temp");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        Path outputPath = new Path(args[1]);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        outputPath.getFileSystem(conf).delete(outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
