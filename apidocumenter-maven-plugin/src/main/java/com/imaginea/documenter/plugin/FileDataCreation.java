package com.imaginea.documenter.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.imaginea.documenter.core.documenter.DataCreation;
import com.imaginea.documenter.core.model.ApiInfo;
import com.imaginea.documenter.core.model.ClassInfo;
import com.imaginea.documenter.core.model.ClassResponseEntity;


public class FileDataCreation extends DataCreation {
	private final String MANIFEST_NAME = "apidocs";
	private final String ROOT_FOLDER = "apidocumenter";
	private String docOutDirPath;
	private Gson gson;

	public FileDataCreation(String basePath, String[] classPaths, String docOutDir) {
		super(basePath, classPaths);
		this.docOutDirPath = getFilePath(docOutDir, ROOT_FOLDER);
		this.gson = new Gson();
	}

	@Override
	public void createData() throws ClassNotFoundException, IOException {
		List<ClassResponseEntity> classResList = dlService.extractClassesInfo();
		File outputDir = new File(docOutDirPath);
		deleteIfExists(outputDir);
		dumbManifestFile(outputDir, createManifestObj(classResList));
		dumpClasses(outputDir, classResList);
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

	private void dumpClass(File resourceDir, ClassResponseEntity classRes) throws IOException {
		File resFile = createFile(resourceDir, classRes.getResourcePath());
		FileWriter fw = new FileWriter(resFile);
		fw.write(gson.toJson(classRes, ClassResponseEntity.class));
		fw.flush();
		fw.close();
	}

	private void dumpClasses(File docOutDir, List<ClassResponseEntity> classResList) throws IOException {
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

	private void dumbManifestFile(File outFile, ApiInfo apiInfo) throws IOException {
		File manifFile = new File(outFile, MANIFEST_NAME);
		FileWriter os = null;
		try {
			os = new FileWriter(manifFile);
			os.write(gson.toJson(apiInfo));
			os.flush();
		} finally {
			os.close();
		}
	}

	private String getFilePath(String context, String fileName) {
		context = (context.endsWith(File.separator)) ? context.substring(0, context.length() - 1) : context;
		fileName = (fileName.startsWith(File.separator)) ? fileName.substring(1) : fileName;
		return context + File.separator + fileName;
	}

	private boolean deleteIfExists(File f) {
		boolean deleted = false;
		if (f.exists()) {
			deleted = f.delete();
		} else {
			deleted = true;
		}
		return deleted;
	}

}
