/*
 * Copyright (c) 2012-2019 Snowflake Computing Inc. All right reserved.
 */
package net.snowflake.client.jdbc.cloud.storage;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.util.Iterator;
import java.util.List;


/**
 * Provides and iterator over storage object summaries
 * from all supported cloud storage providers
 *
 * @author lgiakoumakis
 */
public class StorageObjectSummaryCollection implements Iterable<StorageObjectSummary>
{

  private enum storageType
  {
    S3,
    AZURE
  }

  ;
  private final storageType sType;
  private List<S3ObjectSummary> s3ObjSummariesList = null;
  private Iterable<ListBlobItem> azCLoudBlobIterable = null;

  // Constructs platform-agnostic collection of object summaries from S3 object summaries
  public StorageObjectSummaryCollection(List<S3ObjectSummary> s3ObjectSummaries)
  {
    this.s3ObjSummariesList = s3ObjectSummaries;
    sType = storageType.S3;
  }

  // Constructs platform-agnostic collection of object summaries from an Azure CloudBlobDirectory object
  public StorageObjectSummaryCollection(Iterable<ListBlobItem> azCLoudBlobIterable)
  {
    this.azCLoudBlobIterable = azCLoudBlobIterable;
    sType = storageType.AZURE;
  }


  public Iterator<StorageObjectSummary> iterator()
  {
    if (sType == storageType.S3)
    {
      return new S3ObjectSummariesIterator(s3ObjSummariesList);
    }
    else if (sType == storageType.AZURE)
    {
      return new AzureObjectSummariesIterator(azCLoudBlobIterable);
    }
    else
    {
      throw new IllegalArgumentException("Unspecified storage provider");
    }

  }
}

