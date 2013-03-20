// Created at 2013/03/21 0:26:02, by sonodar.
package test.sndr.watch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.Iterator;

import jp.sndr.watch.FileSystemWatcher;
import jp.sndr.watch.WatchListener;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO .
 * 
 * @author sonodar
 * @version 0.1 2013/03/21 新規作成 sonodar
 */

@SuppressWarnings({ "static-method", "nls", "javadoc" })
public class FileSystemWatcherTest {

	private static final WatchListener	listener	= new WatchListener() {

		// TODO テスト用のフィールドを実装

		@Override
		public void modify(WatchEvent<Path> e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void delete(WatchEvent<Path> e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void create(WatchEvent<Path> e) {
			// TODO 自動生成されたメソッド・スタブ

		}
	};

	/**
	 * TODO .
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * TODO .
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * TODO .
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * TODO .
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#run()} のためのテスト・メソッド。
	 */
	@Test
	public final void testRun() {
		fail("まだ実装されていません"); // TODO
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#FileSystemWatcher(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public final void testFileSystemWatcherString() {
		FileSystemWatcher watcher = new FileSystemWatcher(".");
		assertEquals(Paths.get("."), watcher.getWatchPath());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#FileSystemWatcher(java.nio.file.Path)} のためのテスト・メソッド。
	 */
	@Test
	public final void testFileSystemWatcherPath() {
		FileSystemWatcher watcher = new FileSystemWatcher(Paths.get("."));
		assertEquals(Paths.get("."), watcher.getWatchPath());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#FileSystemWatcher(java.lang.String, jp.sndr.watch.WatchListener)}
	 * のためのテスト・メソッド。
	 */
	@Test
	public final void testFileSystemWatcherStringWatchListener() {
		FileSystemWatcher watcher = new FileSystemWatcher(".", listener);
		assertEquals(Paths.get("."), watcher.getWatchPath());
		assertEquals(listener, watcher.listeners().next());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#FileSystemWatcher(java.nio.file.Path, jp.sndr.watch.WatchListener)}
	 * のためのテスト・メソッド。
	 */
	@Test
	public final void testFileSystemWatcherPathWatchListener() {
		FileSystemWatcher watcher = new FileSystemWatcher(Paths.get("."), listener);
		assertEquals(listener, watcher.listeners().next());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#addWatchListener(jp.sndr.watch.WatchListener)} のためのテスト・メソッド。
	 */
	@Test
	public final void testAddWatchListener() {
		FileSystemWatcher watcher = new FileSystemWatcher(".");
		watcher.addWatchListener(listener);
		assertEquals(listener, watcher.listeners().next());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#removeWatchListener(jp.sndr.watch.WatchListener)} のためのテスト・メソッド。
	 */
	@Test
	public final void testRemoveWatchListener() {
		FileSystemWatcher watcher = new FileSystemWatcher(".", listener);
		watcher.removeWatchListener(listener);
		assertFalse(watcher.listeners().hasNext());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#setWatchListener(int, jp.sndr.watch.WatchListener)} のためのテスト・メソッド。
	 */
	@Test
	public final void testSetWatchListener() {
		FileSystemWatcher watcher = new FileSystemWatcher(".");
		watcher.addWatchListener(listener);
		WatchListener listener2 = new WatchListener() {

			@Override
			public void modify(WatchEvent<Path> e) {
			}

			@Override
			public void delete(WatchEvent<Path> e) {
			}

			@Override
			public void create(WatchEvent<Path> e) {
			}
		};
		watcher.setWatchListener(0, listener2);
		assertEquals(listener2, watcher.listeners().next());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#listeners()} のためのテスト・メソッド。
	 */
	@Test
	public final void testListeners() {
		FileSystemWatcher watcher = new FileSystemWatcher(".");
		watcher.addWatchListener(listener);
		watcher.addWatchListener(listener);
		for (Iterator<WatchListener> it = watcher.listeners(); it.hasNext();) {
			WatchListener _listener = it.next();
			assertEquals(listener, _listener);
		}
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#getWatchPath()} のためのテスト・メソッド。
	 */
	@Test
	public final void testGetWatchPath() {
		Path path = Paths.get(".");
		FileSystemWatcher watcher = new FileSystemWatcher(path);
		assertEquals(path, watcher.getWatchPath());
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#isRecursive()} のためのテスト・メソッド。
	 */
	@Test
	public final void testIsRecursive() {
		System.out.println("まだ実装されていません"); // TODO
	}

	/**
	 * {@link jp.sndr.watch.FileSystemWatcher#setRecursive(boolean)} のためのテスト・メソッド。
	 */
	@Test
	public final void testSetRecursive() {
		System.out.println("まだ実装されていません"); // TODO
	}

}
