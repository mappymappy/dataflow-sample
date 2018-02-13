# dataflow Sample

## Introduction

SampleCode For[GoogleCloudDataflow](https://cloud.google.com/dataflow/)
This Sample inserts data from PubSubMessage To BigQuery.

## Configuration

Please switch profiles with maven.
default profile is dev.

* see pom.xml and write your gcp configuration to here.

```
            <properties>
                <target.project>YOUR_PROJECT_NAME</target.project>
                <target.subscription>YOUR_SUBSCRIPTION_PATH</target.subscription>
                <target.bigquery.table>PROJECT_NAME:DATASET_NAME.TABLE_NAME</target.bigquery.table>
                <target.templocation>YOUR_GCS_LOCATION FOR TEMP(example: gs://hoge/tmp) </target.templocation>
                <target.jobname>YOUR_DATAFLOW_JOBNAME</target.jobname>
                <target.staginglocation>YOUR_GCS_LOCATION FOR STAGING (example: gs://hoge/staging)</target.staginglocation>
            </properties>
```

## Author
[marnie_ms4](https://github.com/mappymappy?tab=repositories)
