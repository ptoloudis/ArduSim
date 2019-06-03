package muscop.logic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import api.pojo.AtomicDoubleArray;
import api.pojo.location.Location2DGeo;
import api.pojo.location.Location3DUTM;
import main.api.formations.FlightFormation.Formation;

/** 
 * Parameters needed by the protocol.
 * <p>Developed by: Francisco Jos&eacute; Fabra Collado, from GRC research group in Universitat Polit&egrave;cnica de Val&egrave;ncia (Valencia, Spain).</p> */

public class MUSCOPParam {
	
	// General parameters
	public static final int MASTER_POSITION = 0; // Position of master UAV into array of UAVs
	public static final long SIMULATION_MASTER_ID = 0;
	public static final String[] MAC = new String[] { "b8:27:eb:57:4c:0e", "b8:27:eb:02:19:5b" };// MACs of master (Hexacopter) with standard format
	public static final long[] MAC_ID = new long[] { 202481591602190L, 202481586018651L };// MACs of master with long format
	
	public static volatile double formationHeading; // (rad) Master UAV Heading (first mission segment orientation in simulation)

	// Timeouts
	public static final int RECEIVING_TIMEOUT = 50;			// (ms) The port is unlocked after this time when receiving messages
	public static final long SENDING_TIMEOUT = 200;			// (ms) Time between packets sent
	public static final long TAKE_OFF_CHECK_TIMEOUT = 250;	// (ms) Between checks if the target altitude has been reached
	public static final long TAKE_OFF_LOG_TIMEOUT = 1000;	// (ms) Between checks to show the current altitude during takeoff
	public static final long MOVE_CHECK_TIMEOUT = 200;		// (ms) Between checks if the target location has been reached
	public static final long LAND_CHECK_TIMEOUT = 250;		// (ms) Between checks if the UAV has landed
	public static final long STATE_CHANGE_TIMEOUT = 250; 	// (ms) Waiting time in sending messages or reading threads
	public static final long HOVERING_TIMEOUT = 500;		// (ms) Waiting time after takeoff before moving to first waypoint
	// Wait between ACK
	
	// Data beacon info
	public static AtomicBoolean[] iAmCenter;				// Whether the UAV is in the center of the flying formation or not
	public static AtomicLongArray idPrev;					// id of the previous UAV to takeoff
	public static AtomicLongArray idNext;					// id of the next UAV to takeoff
	public static AtomicIntegerArray numUAVs;				// Number of UAVs detected by the master (master included)
	public static AtomicReferenceArray<Formation> flyingFormation;	// Formation used while flying
	public static AtomicIntegerArray flyingFormationPosition;		// Position of the UAV in the formation
	public static AtomicDoubleArray flyingFormationHeading;			// (rad) Heading of the formation while flying
	public static AtomicDoubleArray takeoffAltitude;		// (m) Altitude for the takeoff
	public static AtomicReference<byte[][]> data;			// Master: array containing the data sent to the slaves
	public static AtomicReferenceArray<Location3DUTM[]> uavMissionReceivedUTM; // Mission for each UAV in UTM coordinates
	public static AtomicReferenceArray<Location2DGeo[]> uavMissionReceivedGeo; // Matrix with individual missions of each Drone GEO
	// Maximum number of waypoints
	public static final int MAX_WAYPOINTS = 58;	// api.Tools.DATAGRAM_MAX_LENGTH - 2 - 8x4 - 4 - 2 - 4 - 8 - 8 - 4 - 3x8xn >= 0
	
	// Thread and protocol coordination
	public static AtomicIntegerArray state;
	public static AtomicIntegerArray moveSemaphore;
	public static AtomicIntegerArray wpReachedSemaphore;
	
	// (ms) Timeout to assert that the configuration step has finished
	public static final long SETUP_TIMEOUT = 2000;
	// (ms) Timeout to assert that the takeoff has finished
	public static final long TAKEOFF_TIMEOUT = 2000;

}
