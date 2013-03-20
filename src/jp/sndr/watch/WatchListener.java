// Created at 2013/02/27 23:25:00, by sonodar.
package jp.sndr.watch;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * ディレクトリ監視のイベントリスナ.<br>
 * このインタフェースを実装したクラスは、ディレクトリ内のファイル・ディレクトリに変更があった際のイベント処理を実装する.<br>
 * 
 * @author sonodar
 * @version 0.1 2013/02/27 新規作成 sonodar
 */
public interface WatchListener {

	/**
	 * ファイルが最初に作成された際に実行される処理.<br>
	 * このイベントはファイルを作成したプロセスなどによりファイルへの書き込みが完了していなくても実行される.<br>
	 * イベントの発生順序は {@link WatchListener#creating(WatchEvent)} => {@link WatchListener#created(WatchEvent)}
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void creating(WatchEvent<Path> e);

	/**
	 * ファイルが作成された際に実行される処理.<br>
	 * このイベントはファイルを作成したプロセスなどによりファイルへの書き込みが完了した時点で実行される.<br>
	 * 実際の動きとしてはファイルの排他ロック({@link FileChannel#tryLock()})を試み、ロック({@link FileLock})を取得できなければこのイベントの実行は待機される.<br>
	 * イベントの発生順序は {@link WatchListener#creating(WatchEvent)} => {@link WatchListener#created(WatchEvent)}
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void created(WatchEvent<Path> e);

	/**
	 * ファイルの更新が開始された際に実行される処理.<br>
	 * このイベントはファイルを更新しているプロセスなどによりファイルへの書き込みが完了していなくても実行される.<br>
	 * イベントの発生順序は {@link WatchListener#modifing(WatchEvent)} => {@link WatchListener#modified(WatchEvent)}
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void modifing(WatchEvent<Path> e);

	/**
	 * ファイルの更新が完了した際に実行される処理.<br>
	 * このイベントはファイルを更新しているプロセスなどによりファイルへの書き込みが完了した時点で実行される.<br>
	 * 実際の動きとしてはファイルの排他ロック({@link FileChannel#tryLock()})を試み、ロック({@link FileLock})を取得できなければこのイベントの実行は待機される.<br>
	 * イベントの発生順序は {@link WatchListener#modifing(WatchEvent)} => {@link WatchListener#modified(WatchEvent)}
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void modified(WatchEvent<Path> e);

	/**
	 * ファイルが削除された際に実行される処理.
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void deleted(WatchEvent<Path> e);
}
