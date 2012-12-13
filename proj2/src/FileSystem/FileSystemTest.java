package FileSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.text.BadLocationException;

import org.junit.Test;


/**
 * JUnit tests for fileSystem package. 
 * 
 * Please note that some methods inevitably requires user interaction to trigger or 
 * manually verify (e.g. move caret on gui). These methods will be tested within 
 * src/gui/guiTest.java, even though they do affect the state of the File System. 
 *
 * Testing Strategy:
 * 
 * (1) Test only one file
 *     (i)   test Basic constructor for MyFile and initialized fields
 *     (ii)  test more complicated for MyFile constructor with given filename and content
 *     
 * (2) Test multiple file handling
 *     (i)   make sure all the essential fields within the File System class have 
 *           preserved invariants
 *     (ii)  make sure docNum count is right, need to add files from the fileSystem
 *     (iii) test "get files" method
 *     (iv)  test whether the "delete document" method correctly update the fileSystem
 *     
 * (3) Test more advanced methods
 * 	   (i)   test whether the change of file name is correctly reflected in the fileSystem
 *     (ii)  test correct behavior of fileSystem when a new GUI is added to it
 *     (iii) adding File from filePackage
 *     
 * (4) Test receiving eventPackge
 *     (i)   test basic insertion
 *     (ii)  test basic removal
 *     (iii) test more complicated insertion 
 *     (iv)  test more complicated removal
 * 	   
 * In each of the test below, the name of the test corresponds to the respective testing 
 * strategy described above. 
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
	@Test
    public void testEditInsert() throws BadLocationException {
		FileSystem fs=new FileSystem();
		MyFile f=new MyFile(fs);
		assertEquals("New Document 0",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==0);
		f.updateDoc(new EventPackage(0, "INSERT", 1, 0, "a", 0));
		int fLenN=f.getDoc().getLength();
		String contentN=f.getDoc().getText(0, fLenN);
		assertEquals(contentN,"a[Start this document]");
    }
	@Test
    public void testEditDelete() throws BadLocationException {
		FileSystem fs=new FileSystem();
		MyFile f=new MyFile(fs);
		assertEquals("New Document 0",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==0);
		f.updateDoc(new EventPackage(0, "REMOVE", 1, 0, "", 0));
		int fLenN=f.getDoc().getLength();
		String contentN=f.getDoc().getText(0, fLenN);
		assertEquals(contentN,"Start this document]");
    }
	@Test
    public void testEditInsertAdvanced() throws BadLocationException {
		FileSystem fs=new FileSystem();
		MyFile f=new MyFile(fs);
		assertEquals("New Document 0",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==0);
		f.updateDoc(new EventPackage(0, "INSERT", 1, 1, "abc", 0));
		int fLenN=f.getDoc().getLength();
		String contentN=f.getDoc().getText(0, fLenN);
		assertEquals(contentN,"[abcStart this document]");
    }
	@Test
    public void testEditDeleteAdvanced() throws BadLocationException {
		FileSystem fs=new FileSystem();
		MyFile f=new MyFile(fs);
		assertEquals("New Document 0",f.docName);
		int fLen=f.getDoc().getLength();
		String content=f.getDoc().getText(0, fLen);
		assertEquals("[Start this document]",content);	
		assertTrue(f.docNum==0);
		f.updateDoc(new EventPackage(0, "REMOVE", 4, 2, "", 0));
		int fLenN=f.getDoc().getLength();
		String contentN=f.getDoc().getText(0, fLenN);
		assertEquals(contentN,"[S this document]");
    }	

	
	
}
