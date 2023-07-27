package moh.adp.testapp.adam;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.jscape.inet.sftp.Sftp;
import com.jscape.inet.ssh.util.SshParameters;

import moh.adp.model.config.SftsConfig;
import moh.adp.server.AdpServiceLocator;
import moh.adp.server.config.ConfigSession;
import moh.adp.testapp.util.Properties;

@Singleton
public class SFTService {
	private Map<String, String> defaults;
	private int RETRY_TIMES; 
	@Inject
	private Logger logger;
	
	
	public SFTService(){
		init();
	}
	
	@PostConstruct
	public void init() {
		defaults = new HashMap<>();
		defaults.put("hostname", Properties.get("sftp.hostname"));
		defaults.put("port", Properties.get("sftp.port"));
		defaults.put("username", Properties.get("sftp.username"));
		defaults.put("password", Properties.get("sftp.password"));
		defaults.put("localdir", Properties.get("sftp.local.eclaims.directory"));		
		defaults.put("remotedir", Properties.get("sftp.remote.eclaims.directory"));
		RETRY_TIMES = Integer.parseInt(Properties.get("sftp.retry_times"));
	}
	
	public void doDefaultTransfer() {	
		for (int i=0; i< RETRY_TIMES; i++) {
			try (Sftp channel = tryDefaultTransfer()) {
				break; //break from loop if successful
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}

	private Sftp tryDefaultTransfer() throws Exception {
        Sftp channel = getSession(); 
        channel.connect();
		channel.setLocalDir(getLocalDir());
		channel.setDir(getRemoteDir());
		List<File> files = getFiles();
		transferFiles(channel, files);
		return channel;
	}

	private void transferFiles(Sftp channel, List<File> files) {
		files.forEach(file -> {
			try {
				logger.debug("sftp-ing file " + file.getName());
				channel.upload(file);
			} catch (Exception e) {
				e.printStackTrace(); //TODO: think about whether this should be percolated-up.
			}
		});		
	}

	private List<File> getFiles() throws Exception {
		List<File> files = new ArrayList<>();
		File localDir = Paths.get(defaults.get("localdir")).toFile();
		for (File file : localDir.listFiles()) {
			if (file.isFile()) 
				files.add(file);
		}
		return files;
	}

	private Sftp getSession() throws Exception {
        SftsConfig p = AdpServiceLocator.getBeanReference(ConfigSession.class).getSftsParameter();
		SshParameters params = new SshParameters(p.getHostIp(),
											p.getPortInt(),
											p.getUsername(), 
											p.getPassword());
		return new Sftp(params);	
	}
	
	private File getLocalDir() {
		return Paths.get(defaults.get("localdir")).toFile();
	}
	
	private String getRemoteDir() {
		return defaults.get("remotedir");
	}
	
}
