// Created at 2013/02/27 23:37:16, by sonodar.
package jp.sndr.watch;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO .
 * 
 * @author sonodar
 * @version 0.1 2013/02/27 新規作成 sonodar
 */
public class FileSystemWatcher extends Thread {

	/** このインスタンスに設定されている監視イベントリスナのリスト */
	private List<WatchListener>	listeners;

	/** 監視対象パス */
	private Path				watchPath;

	/**
	 * 監視対象パスを指定してインスタンスを生成する.
	 * 
	 * @param watchPath 監視対象パス
	 */
	public FileSystemWatcher(Path watchPath) {
		this.watchPath = watchPath;
	}

	/**
	 * 監視対象パスと最初に実行されるイベントリスナを指定してインスタンスを生成する.
	 * 
	 * @param watchPath 監視対象パス
	 * @param listener 最初に実行されるイベントリスナ
	 */
	public FileSystemWatcher(Path watchPath, WatchListener listener) {
		this.watchPath = watchPath;
		this.addWatchListener(listener);
	}

	/**
	 * 監視イベントリスナを登録する.
	 * 
	 * @param listener 登録する監視イベントリスナ
	 */
	public void addWatchListener(WatchListener listener) {
		if (this.listeners == null) {
			this.listeners = new ArrayList<>();
		}
		this.listeners.add(listener);
	}

	/**
	 * 監視イベントリスナの登録を解除する.
	 * 
	 * @param listener 登録解除する監視イベントリスナ
	 */
	public void removeWatchListener(WatchListener listener) {
		if (this.listeners == null) {
			return;
		}
		this.listeners.remove(listener);
	}

	/**
	 * 監視イベントリスナを設定する.
	 * 
	 * @param order 0から始まるリスナの実施順序<br>
	 *            指定した値が設定されているリスナの数+1だった場合は末尾に追加される.
	 * @param listener 設定する監視イベントリスナ
	 * @throw {@link ArrayIndexOutOfBoundsException} 設定しているリスナ数+1より大きい場合
	 */
	public void setWatchListener(int order, WatchListener listener) {

		if (this.listeners == null) {
			this.listeners = new ArrayList<>();
		}

		// 格納されているリスナ数より1大きければ追加
		if (this.listeners.size() == order) {
			this.addWatchListener(listener);
			return;
		}

		// 格納されているリスナ数より2大きければ例外をスロー
		if (this.listeners.size() < order) {
			throw new ArrayIndexOutOfBoundsException(order);
		}

		// 上記以外は指定された位置に設定
		this.listeners.set(order, listener);
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// FIXME 監視処理開始
		try {
			Path dirPath = this.watchPath;

			// ディレクトリが属するファイルシステムを得る
			FileSystem fs = dirPath.getFileSystem();

			// ファイルシステムに対応する監視サービスを構築する.
			// (一つのサービスで複数の監視が可能)
			try (WatchService watcher = fs.newWatchService()) {
				// ディレクトリに対して監視サービスを登録する.
				WatchKey watchKey = dirPath.register(watcher, new Kind[] {
								StandardWatchEventKinds.ENTRY_CREATE, // 作成
								StandardWatchEventKinds.ENTRY_MODIFY, // 変更
								StandardWatchEventKinds.ENTRY_DELETE, // 削除
								StandardWatchEventKinds.OVERFLOW }, // 特定不能時
								new Modifier[] {}); // オプションの修飾子、不要ならば空配列

				// 監視が有効であるかぎり、ループする.
				// (監視がcancelされるか、監視サービスが停止した場合はfalseとなる)
				while (watchKey.isValid()) {
					try {
						// スレッドの割り込み = 終了要求を判定する.
						if (Thread.currentThread().isInterrupted()) {
							throw new InterruptedException();
						}

						// ファイル変更イベントが発生するまで待機する.
						WatchKey detecedtWatchKey = watcher.poll(500, TimeUnit.MILLISECONDS);
						if (detecedtWatchKey == null) {
							// タイムアウト
							System.out.print(".");
							continue;
						}
						System.out.println();

						// イベント発生元を判定する
						if (detecedtWatchKey.equals(watchKey)) {
							// 発生したイベント内容をプリントする.
							for (WatchEvent event: detecedtWatchKey.pollEvents()) {
								// 追加・変更・削除対象のファイルを取得する.
								// (ただし、overflow時などはnullとなることに注意)
								Path file = (Path)event.context();
								System.out.println(event.kind() + ": count=" + event.count()
												+ ": path=" + file);
							}
						}

						// イベントの受付を再開する.
						detecedtWatchKey.reset();

					}
					catch (InterruptedException ex) {
						// スレッドの割り込み = 終了要求なので監視をキャンセルしループを終了する.
						System.out.println("監視のキャンセル");
						watchKey.cancel();
					}
				}
			}
		}
		catch (RuntimeException | IOException ex) {
			this.interrupt();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		// FIXME 監視処理中断
		super.interrupt();
	}

	/**
	 * このインスタンスに登録されたWatchListenerのItaratorを返す.
	 * 
	 * @return listeners WatchListnerのIterator
	 */
	public Iterator<WatchListener> listeners() {
		if (this.listeners == null) {
			return null;
		}
		return this.listeners.iterator();
	}

	/**
	 * 監視対象パスを返す.
	 * 
	 * @return watchPath 監視対象パス
	 */
	public Path getWatchPath() {
		return this.watchPath;
	}

}
