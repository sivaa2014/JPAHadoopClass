package com.jpasolutions.db;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by nrelate on 26/4/15.
 */
public class DBReducer extends Reducer<Text, IntWritable, DBOutputWritable, NullWritable>
{
    protected void reduce(Text key, Iterable<IntWritable> values, Context ctx)
    {
        int sum = 0;

        for(IntWritable value : values)
        {
            sum += value.get();
        }

        try
        {
            ctx.write(new DBOutputWritable(key.toString(), sum), NullWritable.get());
        } catch(IOException e)
        {
            e.printStackTrace();
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
