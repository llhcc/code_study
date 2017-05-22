package com.whty.cms.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FezoStorageUtils {

	private static final Logger LOGGER = LogManager.getLogger(FezoStorageUtils.class);
	
	/**
	 * @author	xiongxiaofei
	 * @date	2015年9月7日
	 * @desc	上传文件流
	 * @param in
	 * @return
	 */
	public static String uploadFileStream(InputStream in) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.upload.url"));
		InputStreamBody fileBody = new InputStreamBody(in, "application/octet-stream");
		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart(SysConf.getString("fezo.upload.param"), fileBody)
				.build();
		httpPost.setEntity(requestEntity);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			String ret = null;
			if (responseEntity != null) {
				ret = EntityUtils.toString(responseEntity);
			}
			EntityUtils.consume(responseEntity);
			return ret;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				if (response != null) response.close();
				if (httpClient != null) httpClient.close();
				if (in != null) in.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件到云存储
	 * @param filePath 用于上传的文件路径
	 * @return String 如果成功，返回JSON字符串，如果失败，返回NULL
	 */
	public static String uploadFileOld(String filePath) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.upload.url"));
		File file = new File(filePath);
		FileBody fileBody = new FileBody(file);
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart(SysConf.getString("fezo.upload.param"), fileBody)
				.build();
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			String ret = null;
			if (resEntity != null) {
				ret = EntityUtils.toString(resEntity);
			}
			EntityUtils.consume(reqEntity);
			return ret;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}

	public static void downloadFile(String fileId, OutputStream out) {
		downloadFile(fileId, out, 1024 * 4);
	}

	/**
	 * 文件下载
	 * @param fileId 文件id
	 * @param out 输出流
	 * @param bufferSize 下载时所用的缓存大小
	 */
	public static void downloadFile(String fileId, OutputStream out,
			int bufferSize) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.download.url"));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(SysConf.getString("fezo.download.param"), fileId));

		HttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpClient.execute(httpPost);

			BufferedInputStream input = new BufferedInputStream(response.getEntity().getContent());

			byte[] buffer = new byte[bufferSize];
			int len = -1;
			while ((len = input.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			input.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("编码格式不支持", e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
				if (out != null)
					out.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param fileId
	 *            文件ID
	 * @param 结果码
	 */
	public static String deleteFile(String fileId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.delete.url"));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(SysConf.getString("fezo.delete.param"), fileId));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity resEntity = response.getEntity();
			if (resEntity == null) {
				return null;
			} else {
				return EntityUtils.toString(resEntity);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("编码格式不支持", e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				httpClient.close();
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}

	}

	/**
	 * 上传云（支持断点续传）
	 * 
	 * @param file_path
	 * @return
	 */
	public static String uploadFile(String file_path) {
		try {
			File file = new File(file_path);
			String file_md5 = MD5Utils.getFileMD5(file);
			int bufferSize = 4 * 1024; // 设置缓冲区大小
			byte buffer[] = new byte[bufferSize]; // 缓冲区字节数组
			long fileSize = file.length(); // 文件总字节数
			long firstSize = 900 * 1024 * 1024;// 文件大小界限
			long eachFizeSize = 1 * 1024 * 1024; // 分段上传文件最大字节数 (最小为1MB，不能小于1MB)
			String resultCode = null;
			if (fileSize <= firstSize) {// 如果文件在界限内，则直接上传云存储
				resultCode = uploadFileOld(file_path);
			} else { // 如果文件大于界限，则将文件分段上传云存储
				LOGGER.info("file_md5:" + file_md5 + " fileLength:" + firstSize);
				String retDetect = uploadFileFezoByDetect(file_md5, fileSize);
				JSONObject json = JSONObject.fromObject(retDetect);
				String retCode = null;
				long uploadedSize = 0;
				if (json != null) {
					retCode = json.optString("retCode");
					uploadedSize = json.optLong("uploadedSize");
				}
				if ("0".equals(retCode)) {// 说明文件已存在云中
					LOGGER.info("文件已存在云存储");
					return retDetect;
				} else if ("11".equals(retCode)) {
					LOGGER.info("分段上传云存储");
					RandomAccessFile rf = new RandomAccessFile(file_path, "r");
					fileSize = fileSize - uploadedSize;
					long totalPage = fileSize % eachFizeSize == 0 ? fileSize / eachFizeSize : fileSize / eachFizeSize + 1;
					LOGGER.info("分为：" + totalPage + "段");
					for (int i = 0; i < totalPage; i++) {
						rf.seek(uploadedSize + i * eachFizeSize);// 文件指针偏移，接着
						LOGGER.info("第" + (i + 1) + "段，读取偏移量：" + (uploadedSize + i * eachFizeSize));
						ByteArrayOutputStream bos = new ByteArrayOutputStream(1 * 1024 * 1024);
						long haveRead = 0; // 已读取字节数
						int size = 0;
						while ((size = rf.read(buffer)) != -1) {
							bos.write(buffer, 0, size);
							haveRead += size;
							if (haveRead == eachFizeSize) {// 完成读取一个分段的字节数，存入临时文件中
								byte[] byteArray = bos.toByteArray();
								bos.flush();
								bos.close();
								uploadFileFezoByFileBlock(file_md5, uploadedSize + i * eachFizeSize, byteArray);// 上传一个分块文件
								LOGGER.info("已经读取：  " + (i + 1) * eachFizeSize
										+ " Byte 完成" + ((i + 1) * eachFizeSize)
										* 100 / fileSize + "%  单次读取："
										+ haveRead + " Byte");
								break;
							}
						}
						if (size == -1) {// 读取到文件结尾
							byte[] byteArray = bos.toByteArray();
							bos.flush();
							bos.close();
							resultCode = uploadFileFezoByFileBlock(file_md5,
									uploadedSize + i * eachFizeSize, byteArray);// 上传最后一个分块文件
							LOGGER.info("读取完毕：  "
									+ (i * eachFizeSize + haveRead)
									+ " Byte 完成"
									+ (i * eachFizeSize + haveRead) * 100 / fileSize + "%  单次读取：" + haveRead
									+ " Byte");
							break;
						}
					}
					rf.close();
				}
			}
			return resultCode;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 文件上传探测 若文件已存在云中返回{retCode:0,fileId:’xxxxxx’}
	 * 若文件不存在云中返回{retCode:11,uploadedSize:0}，然后进行断点续传
	 * 
	 * @param filePath
	 *            用于上传的文件路径
	 */
	public static String uploadFileFezoByDetect(String file_md5, long size) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.fzDetectYunUrl")); // fzDetectYunUrl
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addTextBody("hash", file_md5).addTextBody("size", size + "")
				.build();
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			String ret = null;
			if (resEntity != null) {
				ret = EntityUtils.toString(resEntity);
			}
			EntityUtils.consume(reqEntity);
			System.out.println(ret);
			return ret;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 2014-11-26 分块上传文件到云存储
	 */
	public static String uploadFileFezoByFileBlock(String file_md5,
			long offset, byte[] byteArray) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(SysConf.getString("fezo.fzFileBlockYunUrl"));// fzFileBlockYunUrl
		ContentBody contentBody = new ByteArrayBody(byteArray, file_md5);
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart("data", contentBody).addTextBody("hash", file_md5)
				.addTextBody("offset", offset + "").build();
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			String ret = null;
			if (resEntity != null) {
				ret = EntityUtils.toString(resEntity);
				System.out.println(ret);
			}
			EntityUtils.consume(reqEntity);
			return ret;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("协议异常", e);
		} catch (IOException e) {
			throw new RuntimeException("执行请求时IO异常", e);
		} finally {
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
