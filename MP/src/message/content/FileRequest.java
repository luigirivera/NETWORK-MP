package message.content;

public class FileRequest {
	private String filename;

	public FileRequest(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
