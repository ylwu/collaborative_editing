package FileSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.text.BadLocationException;

import org.junit.Test;


/*
 * This is junit test for fileSystem package. Please note that some methods inevitably requires
 * user interaction to trigger or check, e.g. move caret on gui, these method can not be tested here
 *
 * test strategy:
 * (1) Test only one file
 *     (i)   test Basic constructor for MyFile and initialized fields
 *     (ii)  test more complicated for MyFile constructor with filename and content given
 * (2) Test more than one file
 *     (i)   make sure all the Basic fields works
 *     (ii)  make sure docNum count is right, need to add files from the fileSystem
 *     (iii) testing get files
 *     (iv)  testing delete documents
 * (3) Test more advanced methods
 * 	   (i)   testing change file name
 *     (ii)  testing add gui to fileSystem
 *     (iii) adding File from filePackage
 *  
 *
 * 	   
 *
 */

public class FileSystemTest {
	@Test
    public void testMyFileBasicConstructor() throws BadLocationException {
		FileSystem fs=new FileSystem();
		MyFile f=new MyFile(fs);
		assertEquals("New Document 0",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==0);
    }
	
	@Test
    public void testMyFileComplicatedConstructor() throws BadLocationException {
		FileSystem fs=new FileSystem();
		String initialContent="initial test content";
		File filename=new File("new file");
		MyFile f=new MyFile(fs,filename,initialContent);
		assertEquals("new file",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals(initialContent,content);	
		assertTrue(f.docNum==0);
    }
	@Test
    public void testCreatingFiles() throws BadLocationException {
		FileSystem fs=new FileSystem();
		String initialContent="initial test content";
		File filename=new File("new file");
		MyFile f=new MyFile(fs,filename,initialContent);
		assertEquals("new file",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals(initialContent,content);	
		assertTrue(f.docNum==0);
		String initialContent2="initial test content2";
		File filename2=new File("new file2");
		MyFile f2=new MyFile(fs,filename2,initialContent2);
		assertEquals("new file2",f2.docName);
		int fLen2=f2.getDoc().getLength();
		String content2=f2.getDoc().getText(0, fLen2);
		assertEquals(initialContent2,content2);			
    }
	@Test
    public void testCreatingFiles2(){
		FileSystem fs=new FileSystem();
		fs.addEmptyFile();
		fs.addEmptyFile();
		assertTrue(fs.getCurDocNum()==2);
	}
	@Test
    public void testGetFiles() throws BadLocationException{
		FileSystem fs=new FileSystem();
		fs.addEmptyFile();
		fs.addEmptyFile();
		MyFile f=fs.getFile().get(1);
		assertEquals("New Document 1",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==1);
	}
	@Test
    public void deleteFile() throws BadLocationException{
		FileSystem fs=new FileSystem();
		fs.addEmptyFile();
		fs.addEmptyFile();
		fs.deleteDoc(0);
		MyFile f=fs.getFile().get(1);
		assertEquals("New Document 1",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==1);
	}	
	@Test
    public void changeFileName() throws BadLocationException{
		FileSystem fs=new FileSystem();
		fs.addEmptyFile();
		fs.addEmptyFile();
		fs.deleteDoc(0);
		fs.changeFileName(1, "newFileName");
		MyFile f=fs.getFile().get(1);
		assertEquals("newFileName",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==1);
	}	
	@Test
    public void addview(){
		FileSystem fs=new FileSystem();
		fs.addEmptyFile();
		fs.addEmptyFile();
		fs.addView(null);
	}	
	@Test
    public void addFileFromFilePackage() throws BadLocationException{
		FileSystem fs=new FileSystem();
		String initialContent="initial test content";
		File filename=new File("new file");
		fs.addFile(new FilePackage(filename,initialContent));
		assertTrue(fs.getCurDocNum()==1);
		MyFile f=fs.getFile().get(1);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals(initialContent,content);
	}	
	
	
	
	

	
	
}
