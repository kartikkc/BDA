package hadoop;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class ProcessUnits {
// Mapper class

    public static class E_EMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
// Map function

        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            String lastToken = null;
            StringTokenizer s = new StringTokenizer(line, "\t");
            String year = s.nextToken();
            while (s.hasMoreTokens()) {
                lastToken = s.nextToken();
            }
            int avgPrice = Integer.parseInt(lastToken);
            output.collect(new Text(year), new IntWritable(avgPrice));
        }
    }
// Main method

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(ProcessUnits.class);
        conf.setJobName("max_electricity_units");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }
}
