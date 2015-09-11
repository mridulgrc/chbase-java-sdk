package com.microsoft.hsg.thing.oxm.jaxb.thing;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.microsoft.hsg.ApplicationConfig;
import com.microsoft.hsg.Connection;
import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.Request;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;
import com.microsoft.hsg.methods.jaxb.beginputblob.request.BeginPutBlobRequest;
import com.microsoft.hsg.methods.jaxb.beginputblob.response.BeginPutBlobResponse;
import com.microsoft.hsg.thing.oxm.jaxb.types.BlobHashAlgorithmParameters;
import com.microsoft.hsg.thing.oxm.jaxb.types.PersonInfo;

public class Blob 
{	
	private BeginPutBlobResponse beginPutBlobParams = null; 

	public BlobPayloadItem addBlob(String key, InputStream  blob_data, String contentType, Request request) throws Exception
	{
		_InitBlobParams(request);

		int data_length = blob_data.available();
		BlobStreamer streamer = new BlobStreamer(beginPutBlobParams.getBlobRefUrl(), contentType);
		byte[] buffer = null;
		int offset=0, numbytes, i=0;
		boolean isUploadComplete;
		int nchunks =(int)Math.ceil(data_length/ (double)beginPutBlobParams.getBlobChunkSize());
		byte[][] blockhashes = new byte[nchunks][32];
		BlobHasher hasher = new BlobHasher();
		hasher.setBlockSize(beginPutBlobParams.getBlobHashParameters().getBlockSize());
		while(offset<data_length)
		{
			numbytes = Math.min(beginPutBlobParams.getBlobChunkSize(), data_length - offset);
			buffer = new byte[numbytes];
			blob_data.read(buffer, 0, numbytes);
			
			isUploadComplete = (offset+numbytes)>=data_length;
			blockhashes[i++] = hasher.GetHash(buffer);
			streamer.streamBlobToUrl(buffer, offset, numbytes, isUploadComplete);
			offset+= numbytes;
		}

		BlobHashInfo hashInfo = GetHashInfo(blockhashes, beginPutBlobParams.getBlobHashParameters());
		BlobInfo blobInfo = GetBlobInfo(hashInfo,contentType, key);
		return GetBlobPayloadItem(blobInfo,beginPutBlobParams.getBlobRefUrl(), data_length);
	}

	private void _InitBlobParams(Request request) throws Exception 
	{
		beginPutBlobParams = GetBeginPutBlobResponse(request);
	}

	private BeginPutBlobResponse GetBeginPutBlobResponse(Request request) throws Exception 
	{
		Connection con = ConnectionFactory.getConnection();
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(con);
		BeginPutBlobRequest req = new BeginPutBlobRequest();  
		BeginPutBlobResponse  resp = (BeginPutBlobResponse )requestTemplate.makeRequest(request, req);
		return resp;
	}

	private BlobPayloadItem GetBlobPayloadItem(BlobInfo blobInfo, String blobRefUrl, long data_length) 
	{
		BlobPayloadItem item = new BlobPayloadItem();
		item.setBlobInfo(blobInfo);
		item.setBlobRefUrl(blobRefUrl);
		item.setContentLength(data_length);
		return item;
	}

	private BlobInfo GetBlobInfo(BlobHashInfo hashInfo, String contentType, String name) 
	{
		BlobInfo info = new BlobInfo();
		info.setHashInfo(hashInfo);
		info.setContentType(contentType);
		info.setName(name);
		return info;
	}

	private BlobHashInfo GetHashInfo(byte[][] blockhashes, BlobHashAlgorithmParameters params) 
	{

		BlobHasher hasher = new BlobHasher();
		hasher.setBlockSize(params.getBlockSize());
		String hash = hasher.ComputeBlockHashHash(blockhashes);

		BlobHashInfo blobHashInfo = new BlobHashInfo();
		blobHashInfo.setAlgorithm("SHA256Block");
		blobHashInfo.setHash(hash);
		blobHashInfo.setParams(params);
		return blobHashInfo;
	}

}
