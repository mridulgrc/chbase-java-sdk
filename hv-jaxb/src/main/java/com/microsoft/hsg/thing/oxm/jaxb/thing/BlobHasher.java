package com.microsoft.hsg.thing.oxm.jaxb.thing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import com.microsoft.hsg.ApplicationConfig;

public class BlobHasher {

	private byte[] data;
	private String algorithm = "SHA-256";
	private int blockSize = ApplicationConfig.File_Block_Size;
	private int hashSizeBytes = 32;

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setHashSizeBytes(int hashsize) {
		this.hashSizeBytes = hashsize;
	}

	public long getBlockSize() {
		return this.blockSize;
	}

	public void setBlockSize(BigInteger blockSize) {
		this.blockSize = blockSize.intValue();
	}

	public BlobHasher() {
	}

	public String ComputeBlockHashHash(byte[][] blockHashes) {
		int nblocks = blockHashes.length;
		byte[] buffer = new byte[nblocks * this.hashSizeBytes];
		int offset = 0;
		for (int i = 0; i < nblocks; i++) {
			System.arraycopy(blockHashes[i], 0, buffer, offset, this.hashSizeBytes);
			offset += this.hashSizeBytes;
		}

		byte[] hash = null;
		try {
			hash = GetHash(buffer);
			return new String(Base64.encodeBase64(hash));
		} catch (Exception e) {

		}
		return null;
	}

	public byte[] GetHash(byte[] chunk) throws Exception {
		MessageDigest md = MessageDigest.getInstance(this.algorithm);
		md.update(chunk);
		return md.digest();
	}

}