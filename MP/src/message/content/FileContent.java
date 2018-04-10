package message.content;

import java.io.Serializable;

public class FileContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private String path;
	private byte[] bytes;

	public FileContent(String path, byte[] bytes) {
		this.path = path;
		this.bytes = bytes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
