package com.mycompany.dataflow_sample.converter;

import com.google.api.services.bigquery.model.TableRow;
import com.google.gson.Gson;
import com.mycompany.dataflow_sample.entity.SampleInputJson;
import org.apache.beam.sdk.transforms.DoFn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class BigQueryRowConverter extends DoFn<String,TableRow> {

    @ProcessElement
    public void processElement(ProcessContext dofn) throws Exception {
      String json = dofn.element();
      Gson gson = new Gson();
      SampleInputJson jsonObj = gson.fromJson(json,SampleInputJson.class);
      TableRow output = new TableRow();
      TableRow attributesOutput = new TableRow();
      TableRow attr2Output = new TableRow();      
            
      attributesOutput.set("attr1", jsonObj.attributes.attr1);      
      attributesOutput.set("attr2", jsonObj.attributes.attr2);
      attr2Output.set("attr2_prop1",jsonObj.attributes.attr2.prop1);
      attr2Output.set("attr2_prop2",jsonObj.attributes.attr2.prop2);
      
      attributesOutput .set("attr2",attr2Output);
      output.set("attributes", attributesOutput );
      output.set("name", jsonObj.name);
      output.set("ts", jsonObj.timeStamp/1000);
      
      dofn.output(output);
    }  
}
