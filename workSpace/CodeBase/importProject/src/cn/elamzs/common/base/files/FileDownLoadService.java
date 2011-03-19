package cn.elamzs.common.base.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 文件下载
 * 
 * @author Ethan.Lam 2011-3-19
 * 
 */
public class FileDownLoadService {

	/**
	 * 
	 * @param response
	 * @param fileAlias
	 * @param fileLocation
	 * @throws IOException
	 */
	public void downloadFile(HttpServletResponse response,
			CHART_ENCODEING encoding, String fileAlias, String fileLocation)
			throws IOException {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			File target = new File(fileLocation);
			if (!target.exists()||target.isDirectory()) {
				StringBuffer html = new StringBuffer();
				html.append("<script>");
				html.append("alert('找不到文件，下载失败！')");
				html.append("</script>");
                response.getWriter().write(html.toString());
                html = null;
                return;
			}
			response.setContentType("application/octet-stream");
			String fileName = fileAlias.replace("\\", "").replace("/", "");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(fileName
							.getBytes(encoding == null ? CHART_ENCODEING.GB2312
									.type() : encoding.type()), "iso8859-1")
					+ "\"");
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileLocation);
				byte[] buf = new byte[4096];
				int bytesRead;
				while ((bytesRead = fis.read(buf)) != -1) {
					out.write(buf, 0, bytesRead);
				}
			} finally {
				if (fis != null)
					fis.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

	public enum CHART_ENCODEING {
		GB2312("gb2312");

		private String type;

		public String type() {
			return this.type;
		}

		CHART_ENCODEING(String type) {
			this.type = type;
		}

	}

}
