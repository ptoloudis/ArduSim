package com.api.communications;

import com.api.API;
import com.setup.Param;
import com.api.ArduSim;
import com.setup.sim.logic.SimParam;
import com.setup.sim.logic.SimTools;
import com.uavController.UAVParam;

/** 
 * Thread used to periodically check if two UAVs can communicate.
 * <p>Developed by: Francisco Jos&eacute; Fabra Collado, from GRC research group in Universitat Polit&egrave;cnica de Val&egrave;ncia (Valencia, Spain).</p> */

public class RangeCalculusThread extends Thread {
	
	private final ArduSim ardusim;
	public static final long RANGE_CHECK_PERIOD = 1000; // (ms) Time between UAVs range check
	
	public RangeCalculusThread() {
		this.ardusim = API.getArduSim();
	}
	
	@Override
	public void run() {
		long checkTime = System.currentTimeMillis();
		long waitingTime;
		while (Param.simStatus == Param.SimulatorState.STARTING_UAVS
				|| Param.simStatus == Param.SimulatorState.UAVS_CONFIGURED
				|| Param.simStatus == Param.SimulatorState.SETUP_IN_PROGRESS
				|| Param.simStatus == Param.SimulatorState.READY_FOR_TEST
				|| Param.simStatus == Param.SimulatorState.TEST_IN_PROGRESS) {
			boolean isInRange;
			if (UAVParam.distanceCalculusIsOnline) {
				SimParam.communicationsOnline = true;
				for (int i = 0; i < Param.numUAVs - 1; i++) {
					for (int j = i + 1; j < Param.numUAVs; j++) {
						isInRange = SimTools.isInRange(UAVParam.distances[i][j].get());
						CommLinkObject.isInRange[i][j].set(isInRange);
						CommLinkObject.isInRange[j][i].set(isInRange);
					}
				}
			}
			checkTime = checkTime + RANGE_CHECK_PERIOD;
			waitingTime = checkTime - System.currentTimeMillis();
			if (waitingTime > 0) {
				ardusim.sleep(waitingTime);
			}
		}
	}

}
