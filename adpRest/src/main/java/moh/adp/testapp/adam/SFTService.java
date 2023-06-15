package moh.adp.testapp.adam;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.sftp.SftpClientFactory;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;


import moh.adp.testapp.util.Properties;


public class SFTService {
	private Map<String, String> defaults;
	private int RETRY_TIMES; 
	
	public SFTService(){
		init();
	}
	
	private void init() {
		defaults = new HashMap<>();
		defaults.put("hostname", Properties.get("sftp.hostname"));
		defaults.put("port", Properties.get("sftp.port"));
		defaults.put("username", Properties.get("sftp.username"));
		defaults.put("password", Properties.get("sftp.password"));
		RETRY_TIMES = Integer.parseInt(Properties.get("sftp.retry_times"));
	}
	
	public void doDefaultTransfer() {	
		for (int i=0; i< RETRY_TIMES; i++) {
			try {
				tryDefaultTransfer();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}	
		}
	}

	private void tryDefaultTransfer() throws Exception {
		Session session = getSession();
		session.connect();
		ChannelSftp channel = (ChannelSftp)session.openChannel("sftp");
		channel.lcd(defaults.get("sftp.local.eclaims.directory"));
		channel.cd(defaults.get("sftp.remote.eclaims.directory"));
		List<String> fileNames = getFileNames();
		transferFiles(channel, fileNames);
	}

	private void transferFiles(ChannelSftp channel, List<String> fileNames) {
		System.out.println("about to sftp");
		fileNames.forEach(fn -> {
			try {
				channel.put(fn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});		
	}

	private List<String> getFileNames() {
		List<String> fileNames = new ArrayList<>();
		Path localDir = Paths.get(defaults.get("local.eclaims.directory"));
		localDir.forEach(p -> {
			File f = p.toFile();
			if (f.isFile()) {
				System.out.println("adding file to sftp " + f.getName());
				fileNames.add(f.getName());
			}
		});
		return fileNames;
	}

	private Session getSession() throws Exception {
		return SftpClientFactory.createConnection(defaults.get("hostname"), 
				Integer.parseInt(defaults.get("port")), 
				defaults.get("username").toCharArray(), 
				defaults.get("password").toCharArray(), 
				null); //getFSOptions())
	}

	@SuppressWarnings("unused")
	private FileSystemOptions getFSOptions() {
		return SftpFileSystemConfigBuilder.getInstance().getProxyOptions(null);
	}
	
}
