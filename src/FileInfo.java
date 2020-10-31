import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;


public class FileInfo {
	public String getData(){
		return data;
	}
	public FileInfo(Path path, Filemanager filemanager){
		this.filemanager = filemanager;
		this.path = path;

		data="<html><table>";
		File file = new File("/");

		HashMap<File, String> map;

		if(path == null) {
			if (filemanager.unix) {
				map = new HashMap<>();
				File[] files = file.listFiles();
				for (File fileInRoot : files)
				{
					map.put(fileInRoot, fileInRoot.getName());
				}
			} else {
				map = filemanager.getRootsOrInRoot();
			}
		}else{
			file = path.toFile();

			map = new HashMap<>();
			File[] files = file.listFiles();
			for (File fileInRoot : files)
			{
				map.put(fileInRoot, fileInRoot.getName());
			}
		}
		if(file.isFile()){
			BasicFileAttributes basicFileAttributes;
			try {
				basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
				data+="<tr><td><b>Attribute</b></td><td>:</td><td>"+file.getName()+"</td></tr>";
				data+="<tr><td><b>Filename</b></td><td>:</td><td>"+file.getName()+"</td></tr>";
				data+="<tr><td><b>Path</b></td><td>:</td><td>"+file.getPath()+"</td></tr>";
				data+="<tr><td><b>Creation Time</b></td><td>:</td><td>" + basicFileAttributes.creationTime()+"</td></tr>";
				data+="<tr><td><b>Last Access Time</b></td><td>:</td><td>" + basicFileAttributes.lastAccessTime()+"</td></tr>";
				data+="<tr><td><b>Last Modified Time</b></td><td>:</td><td>" + basicFileAttributes.lastModifiedTime()+"</td></tr>";
				data+="<tr><td><b>Is Directory</b></td><td>:</td><td>" + basicFileAttributes.isDirectory()+"</td></tr>";
				data+="<tr><td><b>Is Other</b></td><td>:</td><td>" + basicFileAttributes.isOther()+"</td></tr>";
				data+="<tr><td><b>Is Regular File</b></td><td>:</td><td>" + basicFileAttributes.isRegularFile()+"</td></tr>";
				data+="<tr><td><b>Is Symbolic Link</b></td><td>:</td><td>" + basicFileAttributes.isSymbolicLink()+"</td></tr>";
				data+="<tr><td><b>Size</b></td><td>:</td><td>" + basicFileAttributes.size()+" "+((basicFileAttributes.size()<2)?"byte":"bytes")+"</td></tr>";
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}else{
			if(map!=null){
				if(map.size()<1){
					data = data + "<tr>No such files or directories in this directory.</tr>";
				}else{
					data = data + "<tr><td>Filename</td><td>Your Access</td><td>Last Modified</td></tr>";
					for (HashMap.Entry<File, String> entry : map.entrySet()) {
						makeNote(entry.getKey(), entry.getValue());
					}
				}
			}else{
				data = data + "<tr>No such files or directories in this directory.</tr>";
			}
			data = data + "</table></html>";
		}
	}

	enum FileType {
		UNKNOWN_FILE_TYPE,
		REGULAR_FILE,
		DIRECTORY,
		ROOT
	}

	public void makeNote(File file, String filename){
		FileType type;

		if (file.getName().equals("")) {
			data = data + ("<tr><td><b><font color=\"blue\">" + filename + "</b></td></font>");
			type = FileType.ROOT;
		}else if(file.isDirectory()){
			data = data + ("<tr><td><b><font color=\"blue\">" + file.getName() + "</b></td></font>");
			type = FileType.DIRECTORY;
		}else if(!file.isFile()){
			data = data + ("<tr><td><b><font color=\"blue\">" + file.getName() + "</b></td></font>");
			type = FileType.UNKNOWN_FILE_TYPE;
		}else{
			data = data + ("<tr><td>" + file.getName() + "</td>");
			type = FileType.REGULAR_FILE;
		}
		if(type != FileType.UNKNOWN_FILE_TYPE){
			String color;
			if(file.canRead()){
				color = "green";
			}else{
				color = "red";
			}
			data = data + (" <td><font color=\"" + color + "\">R</font>");
			if(file.canWrite()){
				color = "green";
			}else{
				color = "red";
			}
			data = data + ("-<font color=\"" + color + "\">W</font>");
			if(file.canExecute()){
				color = "green";
			}else{
				color = "red";
			}
			data = data + ("-<font color=\"" + color + "\">X</font></td>");
			Date date = new Date(file.lastModified());
			data = data + ("<td>" + date + "</td>");
			data = data + "</tr>";
		}
	}
	private Filemanager filemanager;
	private String data;
	private Path path;
}
