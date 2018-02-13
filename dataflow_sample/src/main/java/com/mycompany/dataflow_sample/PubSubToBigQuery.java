/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataflow_sample;


import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.mycompany.dataflow_sample.converter.BigQueryRowConverter;
import com.mycompany.dataflow_sample.partition.DayPartitionDestinations;
import com.mycompany.dataflow_sample.schema.SampleSchemaFactory;
import java.util.ResourceBundle;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;


public class PubSubToBigQuery {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ResourceBundle config = ResourceBundle.getBundle("config");
        PubSubToBigQuery(config);        
    }
        
    private static void PubSubToBigQuery(ResourceBundle config) {
        // create option
        DataflowPipelineOptions options = PipelineOptionsFactory.create()
            .as(DataflowPipelineOptions.class);
        options.setProject(config.getString("targetProject"));
        options.setStagingLocation(config.getString("targetStagingLocation"));
        options.setTempLocation(config.getString("targetTempLocation"));
        options.setRunner(DataflowRunner.class);
        options.setStreaming(true);
        options.setJobName(config.getString("targetJobName"));
        // create pipeline & get inputData
        Pipeline p = Pipeline.create(options);
        PCollection<String> streamData;
        streamData = p.apply(PubsubIO.readStrings()
                .fromSubscription(config.getString("targetSubscription"))
        );
        // apply convertRule (from:string,to:TableRow) 
        PCollection<TableRow> rows = streamData.apply(
            ParDo
            .of(new BigQueryRowConverter())
        );
        // output
        TableSchema schema = SampleSchemaFactory.create();
        rows.apply("WriteToBQ", BigQueryIO.writeTableRows()
            .to(new DayPartitionDestinations(config.getString("targetBigQueryTable")))
            .withSchema(schema)
            .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
            .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));

        p.run();
    }     
}
