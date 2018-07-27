package com.andromeda.sdm.dmcore;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Observable;

import javax.net.ssl.HttpsURLConnection;

public class Download extends Observable implements Runnable
{
	// Max size of download buffer.
	private static final int MAX_BUFFER_SIZE = 1024;

	// These are the status names.
	public static String STATUSES[] = { "Downloading", "Paused", "Complete", "Cancelled", "Error" };

	private URL url; // download URL
	private int size; // size of file in bytes
	private int downloaded; // number of bytes downloaded
	private int status; // current status of download

	// Statues code.
	public static int DOWNLOADING = 0;
	public static int PAUSED = 0;
	public static int COMPLETE = 0;
	public static int CANCELLED = 0;
	public static int ERROR = 0;

	// Constructor
	public Download(URL url)
	{
		this.url = url;
		this.size = -1;
		this.downloaded = 0;
		this.status = DOWNLOADING;

		// Begin the download.
		download();
	}

	public URL getUrl()
	{
		return url;
	}

	public int getSize()
	{
		return size;
	}

	public int getStatus()
	{
		return status;
	}

	// Get download's progress
	public float getProgress()
	{
		return ((float) downloaded / size) * 100;
	}

	// Pause this download
	public void pause()
	{
		status = PAUSED;
		stateChanged();
	}

	// Resume this download
	public void resume()
	{
		status = DOWNLOADING;
		stateChanged();
		download();
	}

	// Cancel this download
	public void cancel()
	{
		status = CANCELLED;
		stateChanged();
	}

	// Error this download
	public void error()
	{
		status = ERROR;
		stateChanged();
	}

	private void stateChanged()
	{
		setChanged();
		notifyObservers();
	}

	// Get file name portion of URL
	private String getFileName(URL url)
	{
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	private void download()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	// Download file
	@Override
	public void run()
	{
		RandomAccessFile file = null;
		InputStream stream = null;

		try
		{
			// Open connection to URL.
			// For HTTPS connection.
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			/*
			 * For HTTPS connection.
			 * HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			*/
			
			// Specify what portion of file to download.
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			// connect to the server.
			connection.connect();

			// Make sure response code is in the 200 range.
			if (connection.getResponseCode() / 100 != 2)
			{
				error();
			}

			// Check for valid content length.
			int contentLength = connection.getContentLength();
			if (contentLength < 1)
			{
				error();
			}

			/*
			 * Set the size for this download if it hasn't been already set.
			 */
			if (size == -1)
			{
				size = contentLength;
				stateChanged();
			}

			// Open file and seek to the end of it.
			file = new RandomAccessFile("/home/shivam/Desktop/"+getFileName(url), "rw");
			file.seek(downloaded);
			stream = connection.getInputStream();
			while (status == DOWNLOADING)
			{
				/*
				 * Size buffer according to how much of the file is left to download.
				 */
				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE)
				{
					buffer = new byte[MAX_BUFFER_SIZE];
				} else
				{
					buffer = new byte[size - downloaded];
				}
				
				// Read from server into buffer.
				int read = stream.read(buffer);
				if (read == -1)
					break;
				
				// Write buffer to file.
				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}
			
			/*
			 * Change status to complete if this point was reached because downloading has
			 * finished.
			 */
			if (status == DOWNLOADING)
			{
				status = COMPLETE;
				stateChanged();
			}
		} catch (Exception e)
		{
			error();
		} finally
		{
			// Close file.
			if (file != null)
			{
				try
				{
					file.close();
				} catch (Exception e)
				{
				}
			}

			// Close connection to server.
			if (stream != null)
			{
				try
				{
					stream.close();
				} catch (Exception e)
				{
				}
			}
		}
	}

}
