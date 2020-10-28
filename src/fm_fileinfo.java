import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;


public class fm_fileinfo {
	public String getData(){
		return data;
	}
	public fm_fileinfo(Path p, filemanager m){
		current_path=p;
		mc=m;
		data="<html><table>";
		File f=new File("/");
		String[] files;
		if(current_path==null) {
			files=mc.getRootDirectories();
		}else{
			f=current_path.toFile();
			files=f.list();
		}
		if(f.isFile()){
			Path path = f.toPath();
			BasicFileAttributes bfa;
			try {
				bfa = Files.readAttributes(path, BasicFileAttributes.class);
				data+="<tr><td><b>Attribute</b></td><td>:</td><td>"+f.getName()+"</td></tr>";
				data+="<tr><td><b>Filename</b></td><td>:</td><td>"+f.getName()+"</td></tr>";
				data+="<tr><td><b>Path</b></td><td>:</td><td>"+f.getPath()+"</td></tr>";
				data+="<tr><td><b>Creation Time</b></td><td>:</td><td>" + bfa.creationTime()+"</td></tr>";
				data+="<tr><td><b>Last Access Time</b></td><td>:</td><td>" + bfa.lastAccessTime()+"</td></tr>";
				data+="<tr><td><b>Last Modified Time</b></td><td>:</td><td>" + bfa.lastModifiedTime()+"</td></tr>";
				data+="<tr><td><b>Is Directory</b></td><td>:</td><td>" + bfa.isDirectory()+"</td></tr>";
				data+="<tr><td><b>Is Other</b></td><td>:</td><td>" + bfa.isOther()+"</td></tr>";
				data+="<tr><td><b>Is Regular File</b></td><td>:</td><td>" + bfa.isRegularFile()+"</td></tr>";
				data+="<tr><td><b>Is Symbolic Link</b></td><td>:</td><td>" + bfa.isSymbolicLink()+"</td></tr>";
				data+="<tr><td><b>Size</b></td><td>:</td><td>" + bfa.size()+" "+((bfa.size()<2)?"byte":"bytes")+"</td></tr>";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			if(files!=null){
				if(files.length<1){
					data+="<tr>No such files or directories in this directory.</tr>";
				}else{
					data+="<tr><td>Filename</td><td>Your Access</td><td>Last Modified</td></tr>";
					for(String filename:files){
						current_file=new File(current_path+"/"+filename);
						makeNote();
					}
				}
			}else{
				data+="<tr>No such files or directories in this directory.</tr>";
			}
			data+="</table></html>";
		}
	}
	public void makeNote(){
		int type;
		if(current_file.isDirectory()){
			data+="<tr><td><b><font color=\"blue\">"+current_file.getName()+"</b></td></font>";
			type=0;
		}else if(!current_file.isFile()){
			data+="<tr><td><b><font color=\"blue\">"+current_file.getName()+"</b></td></font>";
			type=1;
		}else{
			data+="<tr><td>"+current_file.getName()+"</td>";
			type=2;
		}
		if(type!=1){
			String color;
			if(current_file.canRead()){
				color="green";
			}else{
				color="red";
			}
			data+=" <td><font color=\""+color+"\">R</font>";
			if(current_file.canWrite()){
				color="green";
			}else{
				color="red";
			}
			data+="-<font color=\""+color+"\">W</font>";
			if(current_file.canExecute()){
				color="green";
			}else{
				color="red";
			}
			data+="-<font color=\""+color+"\">X</font></td>";
			Date dt = new Date(current_file.lastModified());
			data+="<td>"+dt+"</td>";
			data+="</tr>";
		}
	}
	private filemanager mc;
	private Path current_path;
	private File current_file;
	private String data;
}
