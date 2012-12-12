package FileSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.text.BadLocationException;

import org.junit.Test;

/*
 * test strategy:
 * (1) Test only one file
 *     (i)  test Basic constructor for MyFile and initialized fields
 *     (ii) test more complicated for MyFile constructor with filename and content given
 * (2) Test more than one file
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
		System.out.println(f2.docNum);
		assertTrue(f2.docNum==1);		
		
    }
}
