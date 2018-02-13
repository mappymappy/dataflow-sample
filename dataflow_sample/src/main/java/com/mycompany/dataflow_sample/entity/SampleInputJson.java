/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataflow_sample.entity;

import com.google.gson.annotations.SerializedName;

public class SampleInputJson {
  @SerializedName("name")
  public String name;
  @SerializedName("ts")
  public double timeStamp;
  @SerializedName("attributes")
  public Attributes attributes; 
  public class Attributes {
      @SerializedName("attr1")
      public String attr1;
      @SerializedName("attr2")
      public Attr2 attr2;
      public class Attr2  {
          @SerializedName("prop1")
          public int prop1;
          @SerializedName("prop2")
          public String prop2;
      }
  }
}
