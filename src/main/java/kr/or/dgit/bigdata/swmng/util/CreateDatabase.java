package kr.or.dgit.bigdata.swmng.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Member;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.MemberService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;

public class CreateDatabase {
	private Statement stmt;
	private static final CreateDatabase instance = new CreateDatabase();
	private static SqlSessionFactory sqlSessionForReset;
	private String q = "";

	public static CreateDatabase getInstance() {
		return instance;
	}

	public static SqlSession openSession() {
		return getSqlSessionForReset().openSession();
	}

	// 데이터베이스 초기화
	public void resetDB() {
		// 초기화시 데이터베이스에 첨부파일 필요할시 추가
		// 사용시 apache-commons-io.jar 라이브러리 필요
		try {
			copyImgToUploadsDir();

			SqlSession sql = openSession();

			// UTF-8로 저장된 txt파일 읽어와서 리스트에 저장
			// src/main/resources/ 경로 기준
			List<String> list = Files.readAllLines(Paths.get((getClass().getResource("/txt/reset.txt").toURI())),
					StandardCharsets.UTF_8);
			for (String s : list) {
				// txt쿼리에서 주석 및 불필요한 문장 삭제
				q += s.replaceAll(".$?--.*", "").replaceAll("(?m)^.*--.*$(\r?\n|\r)?", "").replaceAll("\\s+", " ");
				// ("(?m)^.*--.*$(\r?\n|\r)?", ""); .replaceAll("\\s+", "
				// ");.replaceAll("'", "\"")
			}
			String[] query = q.split(";");
			// ; 기준으로 스플릿후 쿼리 실행
			stmt = sql.getConnection().createStatement();
			for (String s : query) {
				if (s.trim().startsWith("insert") || s.trim().startsWith("update") || s.trim().startsWith("delete")) {
					stmt.executeUpdate(s);
				} else {
					stmt.execute(s);
				}
			}
			sql.commit();
			stmt.close();
			sql.close();

			// 그림파일 업로드후 다시 삭제

			deleteDirFromUploads("/sw");
		} catch (IOException | URISyntaxException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "데이터베이스 초기화가 완료되었습니다.");
	}

	// 소스폴더 이미지 파일 mysql폴더로 복사
	private void copyImgToUploadsDir() throws IOException, URISyntaxException {
		makeDirInUploads("");
		// 복사할 이미지 파일 경로 src/main/resources 기준

		FileUtils.copyDirectoryToDirectory(new File(getClass().getResource("/sw").toURI()),
				new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads"));

	}

	// root계정으로 데이터베이스 접속
	public static SqlSessionFactory getSqlSessionForReset() {
		// 초기화시 필요한 데이터베이스 root계정 정보
		String user = "root";
		String password = "rootroot";
		String databasenameURL = "jdbc:mysql://localhost:3306";
		String dbDriver = "com.mysql.jdbc.Driver";

		if (sqlSessionForReset == null) {
			DataSource dataSource = new PooledDataSource(dbDriver, databasenameURL, user, password);
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Configuration configuration = new Configuration(
					new Environment("development", transactionFactory, dataSource));
			sqlSessionForReset = new SqlSessionFactoryBuilder().build(configuration);
		}
		return sqlSessionForReset;
	}

	// 데이터베이스에서 자료 백업 1/2 단계
	public void backupFromDB() {
		try {
			makeDirInUploads("/backUpFolder");
			String[] query = { "USE swmng",
					"SELECT * FROM company INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/company.txt' CHARACTER SET utf8 FIELDS TERMINATED BY ',' ENCLOSED BY '`' LINES TERMINATED BY '\n'",
					"SELECT no,category,title,supprice,sellprice,coname FROM software INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/software.txt' CHARACTER SET utf8 FIELDS TERMINATED BY ',' ENCLOSED BY '`' LINES TERMINATED BY '\n'",
					"SELECT no,shopname,address,tel FROM buyer  INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/buyer.txt' CHARACTER SET utf8 FIELDS TERMINATED BY ',' ENCLOSED BY '`' LINES TERMINATED BY '\n'",
					"SELECT * FROM sale INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/sale.txt' CHARACTER SET utf8 FIELDS TERMINATED BY ',' ENCLOSED BY '`' LINES TERMINATED BY '\n'",
					"SELECT id,password,email FROM member INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/member.txt' CHARACTER SET utf8 FIELDS TERMINATED BY ',' ENCLOSED BY '`' LINES TERMINATED BY '\n'" };
			SqlSession sql = openSession();

			stmt = sql.getConnection().createStatement();

			for (int i = 0; i < query.length; i++) {
				stmt.execute(query[i]);
			}
			stmt.close();
			sql.close();

			backupImgFromDB();

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	// 데이터베이스에서 자료 백업 2/2 단계
	public void backupImgFromDB() throws SQLException, IOException {
		makeDirInUploads("/backUpFolder/swImg");
		makeDirInUploads("/backUpFolder/buyerImg");
		makeDirInUploads("/backUpFolder/memberImg");
		List<Software> Swlist = SoftwareService.getInstance().selectAll();
		List<Buyer> buyerList = BuyerService.getInstance().selectAll();
		List<Member> memberList = MemberService.getInstance().selectAllId();
		SqlSession sql = openSession();
		stmt = sql.getConnection().createStatement();
		for (Software s : Swlist) {
			stmt.execute("select picpath from software where no='" + s.getNo()
					+ "'order by no into dumpfile 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/swImg/"
					+ s.getNo() + ".gif'");
		}
		for (Buyer b : buyerList) {
			stmt.execute("select picpath from buyer where no='" + b.getNo()
					+ "'order by no into dumpfile 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/buyerImg/"
					+ b.getNo() + ".gif'");
		}
		for (Member m : memberList) {
			stmt.execute("select pic from member where id='" + m.getId()
					+ "'order by id into dumpfile 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/memberImg/"
					+ m.getId() + ".gif'");
		}
		stmt.close();
		sql.close();

		copyDirFromUploadsToSrc("/backUpFolder");
		deleteDirFromUploads("/backUpFolder");

		JOptionPane.showMessageDialog(null, "src/backUpFolder에 백업하였습니다");
	}

	// mysql 업로드 폴더 소스내 폴더로 복사
	private void copyDirFromUploadsToSrc(String dir) throws IOException {
		if (new File("src" + dir).exists()) {
			FileUtils.deleteDirectory(new File("src" + dir));
		}
		FileUtils.copyDirectoryToDirectory(new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir),
				new File("src"));
	}

	// mysql 업로드 폴더 체크후 중복시 삭제후 새폴더 생성
	private void makeDirInUploads(String dir) {
		try {
			if (new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir).exists()) {
				FileUtils.deleteDirectory(new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir));
				FileUtils.forceMkdir(new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir));
			} else {
				FileUtils.forceMkdir(new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// mysql 업로드 폴더내의 폴더 삭제
	private void deleteDirFromUploads(String dir) throws IOException {
		// 복사된 파일이 삭제될 경로
		FileUtils.deleteDirectory(new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads" + dir));
	}

	// 소스내의 백업폴더 확인후 백업 진행 복원 1/2단계
	public void restoreBackupToDB() {
		try {
			dropTheTables();

			if (!Files.exists(new File("src/backUpFolder").toPath())) {
				if (JOptionPane.showConfirmDialog(null, "백업파일이 없습니다. 초기화 하시겠습니까?") == 0) {
					resetDB();
				}
			} else if (Files.exists(new File("src/backUpFolder").toPath())) {
				FileUtils.copyDirectoryToDirectory(new File("src/backUpFolder"),
						new File("C:/ProgramData/MySQL/MySQL Server 5.6/Uploads"));
				loadTxtInDB();
				loadImgInDB();
				deleteDirFromUploads("/backUpFolder");
				JOptionPane.showMessageDialog(null, "데이터베이스 복원이 완료되었습니다");
			} else {
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 이미지 파일 데이터베이스로 insert 복원 2/2단계
	private void loadImgInDB() {
		try {
			List<Software> swList = SoftwareService.getInstance().selectAll();
			List<Buyer> buyerList = BuyerService.getInstance().selectAll();
			List<Member> memberList = MemberService.getInstance().selectAllId();
			SqlSession sql = openSession();
			stmt = sql.getConnection().createStatement();

			for (Software s : swList) {
				stmt.executeUpdate(
						"UPDATE software set picPath =  LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/swImg/"
								+ s.getNo() + ".gif') WHERE no =" + s.getNo());
			}
			for (Buyer b : buyerList) {
				stmt.executeUpdate(
						"UPDATE buyer set picPath = LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/buyerImg/"
								+ b.getNo() + ".gif') WHERE no =" + b.getNo());
			}
			for (Member m : memberList) {
				stmt.executeUpdate(
						"UPDATE member set pic = LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/backUpFolder/memberImg/"
								+ m.getId() + ".gif') WHERE id ='" + m.getId() + "'");
			}

			sql.commit();
			stmt.close();
			sql.close();
		} catch (Exception e) {
		}

	}

	// mysql폴더의 txt파일 데이터베이스로 로드
	private void loadTxtInDB() {
		try {
			SqlSession sql = openSession();
			List<String> list = Files.readAllLines(Paths.get((getClass().getResource("/txt/load.txt").toURI())),
					StandardCharsets.UTF_8);
			for (String s : list) {
				q += s;
			}
			String[] query = q.split(";");
			stmt = sql.getConnection().createStatement();
			for (String s : query) {
				stmt.execute(s);
			}
			stmt.close();
			sql.close();
		} catch (IOException | URISyntaxException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	// 복원시 기존 데이터베이스 테이블 자료 삭제
	private void dropTheTables() {
		String[] query = { "USE swmng", "SET foreign_key_checks = 0",
				"delete m.*,s.*,b.*,sw.*,c.* from member m, sale s,buyer b,software sw, company c",
				"SET foreign_key_checks = 1" };
		try {
			SqlSession sql = openSession();
			stmt = sql.getConnection().createStatement();
			for (String s : query) {
				stmt.execute(s);
			}
			sql.commit();
			stmt.close();
			sql.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
