package com.siva.advancedMR.joins;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sivakumar on 24/4/15.
 */
public class EmpMapperUsingMapsideJoin extends
        Mapper<LongWritable,Text,Text,NullWritable> {
    Properties properties ;
    String cacheFileName = "/cache/emp_salary.txt";
    String cacheFileLocation;

    public void setup(Context context) throws IOException {
        Path paths[] = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        for(Path path:paths){
            if(path.toString().contains(cacheFileName)){
                cacheFileLocation = path.toString();
            }
        }
        properties = new Properties();
        properties.load(new FileInputStream(cacheFileLocation));
    }


    public void map(LongWritable key,Text value,Context context)
            throws IOException, InterruptedException {
        // Split the record delimited by Space,
        String emp[] = value.toString().split(" ");

        // join happens here. lookup using employee name
        String salary = properties.getProperty(emp[1]);

        Text finalData = new Text(value.toString()+" "+salary);

        context.write(finalData, NullWritable.get());
    }
}
