// Created at 2013/02/27 23:25:00, by sonodar.
package jp.sndr.watch;

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
	 * ファイルが作成された際に実行される処理.
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void create(WatchEvent<Path> e);

	/**
	 * ファイルの更新された際に実行される処理.
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void modify(WatchEvent<Path> e);

	/**
	 * ファイルが削除された際に実行される処理.
	 * 
	 * @param e 監視イベントオブジェクト
	 */
	void delete(WatchEvent<Path> e);
}
