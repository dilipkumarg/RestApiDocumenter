package com.imaginea.documenter.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imaginea.documenter.core.documenter.DataCreation;
import com.imaginea.documenter.core.model.ApiInfo;
import com.imaginea.documenter.core.model.ClassInfo;
import com.imaginea.documenter.core.model.ClassResponseEntity;
import com.imaginea.documenter.plugin.docgen.ApiDocumenter;

public class FileDataCreation extends DataCreation {
	private static final Logger LOGGER = Logger
			.getLogger(FileDataCreation.class);
	private final String MANIFEST_NAME = "apidocs";
	private final String HTML_FILE_NAME = "apidocs.html";
	private String docOutDirPath;
	private Gson gson;

	public FileDataCreation(String basePath, String[] classPaths,
			String docOutDir) {
		super(basePath, classPaths);
		this.docOutDirPath = docOutDir;
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	public void createData() throws ClassNotFoundException, IOException {
		List<ClassResponseEntity> classResList = dlService.extractClassesInfo();
		File outputDir = new File(docOutDirPath);
		cleanAndCreateDir(outputDir);
		dumpManifestFile(outputDir, createManifestObj(classResList));
		dumpClasses(outputDir, classResList);
		dumpHtmlFile(outputDir,
				new ApiDocumenter(classResList, this.basePath).toMarkDown());
	}

	private void dumpHtmlFile(File baseDir, String html) throws IOException {
		dumpToFile(baseDir, HTML_FILE_NAME, html);
	}

	private File createFile(File resourceDir, String resource) {
		String[] resParts = resource.split("/");
		File curResourceDir = resourceDir;
		if (resParts.length > 1) {
			String middlePath = "";
			for (int i = 0; i < (resParts.length - 1); i++) {
				middlePath = middlePath + File.separator + resParts[i];
			}
			File tmpResDir = new File(resourceDir, middlePath);
			tmpResDir.mkdirs();
			curResourceDir = tmpResDir;
		}
		return new File(curResourceDir, resParts[resParts.length - 1]);
	}

	private void dumpToFile(File baseDir, String fileName, String data)
			throws IOException {
		File newFile = new File(baseDir, fileName);
		newFile.createNewFile();
		FileWriter writer = null;

		try {
			writer = new FileWriter(newFile);
			writer.write(data);
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		LOGGER.info("File created at " + newFile.getAbsolutePath());
	}

	private void dumpClass(File resourceDir, ClassResponseEntity classRes)
			throws IOException {
		File resFile = createFile(resourceDir, classRes.getResourcePath()
				+ ".json");
		LOGGER.debug("Json file creation started for  "
				+ resFile.getAbsolutePath());
		FileWriter fw = null;
		try {
			fw = new FileWriter(resFile);
			fw.write(gson.toJson(classRes, ClassResponseEntity.class));
			fw.flush();
		} finally {
			if (fw != null) {
				fw.close();
			}
		}
		LOGGER.debug("Json file sucessfully created for file "
				+ resFile.getAbsolutePath());

	}

	private void dumpClasses(File docOutDir,
			List<ClassResponseEntity> classResList) throws IOException {
		File resourceDir = createResourceDirectory(docOutDir);
		for (ClassResponseEntity classRes : classResList) {
			dumpClass(resourceDir, classRes);
		}
	}

	private File createResourceDirectory(File docOutDir) {
		File resourceDir = new File(docOutDir, MANIFEST_NAME);
		resourceDir.mkdir();
		return resourceDir;
	}

	private ApiInfo createManifestObj(List<ClassResponseEntity> classResList) {
		ApiInfo apisInfo = new ApiInfo();
		List<ClassInfo> apis = new ArrayList<ClassInfo>();
		for (ClassResponseEntity classRes : classResList) {
			ClassInfo classDesc = new ClassInfo();
			classDesc.setPath(classRes.getResourcePath());
			apis.add(classDesc);
		}
		apisInfo.setApis(apis);
		return apisInfo;
	}

	private void dumpManifestFile(File outFile, ApiInfo apiInfo)
			throws IOException {
		dumpToFile(outFile, MANIFEST_NAME + ".json", gson.toJson(apiInfo));

	}

	private void deleteRecursively(File root) {
		if (root.isDirectory()) {
			for (File f : root.listFiles()) {
				deleteRecursively(f);
			}
		}
		// deletes file and directory after recursively deleting files.
		root.delete();
	}

	private boolean cleanAndCreateDir(File f) {
		if (f.exists()) {
			deleteRecursively(f);
		}
		return f.mkdirs();
	}

}
