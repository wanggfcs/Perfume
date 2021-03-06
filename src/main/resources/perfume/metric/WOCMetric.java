package main.resources.perfume.metric;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class WOCMetric extends AbstractMetricVisitor {
	private HashMap<String, Long> WOCMMap = new HashMap<>();
	private HashMap<String, Long> NOAMMap = null;
	private long totalPublicM = 0;

	public boolean visit(TypeDeclaration node) {
		setPkgClassName(node);
		if (node.isInterface()) {

			WOCMMap.put(getPkgClassName(), -2l);
			return true;
		}
		MethodDeclaration[] mds = node.getMethods();
		for (MethodDeclaration md : mds) {
			int modifiersInt = md.getModifiers();

			if (Modifier.isPublic(modifiersInt)) {
				totalPublicM += 1;
			}
		}
		NOAMMetric nOAMVistor = new NOAMMetric();
		node.accept(nOAMVistor);
		NOAMMap = nOAMVistor.getMetricResult();
		return true;

	}

	@Override
	public void afterMetric() {
		super.afterMetric();

		long val = 0;
		Iterator iter = NOAMMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			val = (long) entry.getValue();

		}

		long a = (long) (val / (double) totalPublicM * 100);
		WOCMMap.put(getPkgClassName(), a);
		totalPublicM = 0;
	}

	@Override
	public HashMap<String, Long> getMetricResult() {
		return WOCMMap;
	}

	@Override
	public String getMetricName() {
		return "WOC";
	}

}
