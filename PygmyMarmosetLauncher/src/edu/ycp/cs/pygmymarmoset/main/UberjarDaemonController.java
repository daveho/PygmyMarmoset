package edu.ycp.cs.pygmymarmoset.main;

import org.cloudcoder.daemon.DaemonController;
import org.cloudcoder.daemon.IDaemon;

public class UberjarDaemonController extends DaemonController {
	@Override
	public String getDefaultInstanceName() {
		return "instance";
	}

	@Override
	public Class<? extends IDaemon> getDaemonClass() {
		return UberjarDaemon.class;
	}
}
