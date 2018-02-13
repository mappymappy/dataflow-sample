/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataflow_sample.schema;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;
import java.util.ArrayList;
import java.util.List;

public class SampleSchemaFactory {
    public static TableSchema create() {
        List<TableFieldSchema> fields;
        fields = new ArrayList<> ();
        fields.add(new TableFieldSchema().setName("name").setType("STRING"));
        fields.add(new TableFieldSchema().setName("ts").setType("TIMESTAMP"));
        fields.add(new TableFieldSchema().setName("attributes").setType("RECORD")
                .setFields(new ArrayList<TableFieldSchema>() {
                    {
                        add(new TableFieldSchema().setName("attr1").setType("STRING"));                        
                        add(new TableFieldSchema().setName("attr2").setType("RECORD")
                            .setFields(new ArrayList<TableFieldSchema>() {
                                {
                                    add(new TableFieldSchema().setName("prop1").setType("INTEGER"));
                                    add(new TableFieldSchema().setName("prop2").setType("STRING"));
                                }
                            })
                        );
                    }
                })
        );
        TableSchema schema = new TableSchema().setFields(fields);

        return schema;
    }
}
