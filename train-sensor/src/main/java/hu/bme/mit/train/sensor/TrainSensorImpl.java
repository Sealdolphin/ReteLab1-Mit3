package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private int speedLimit = 5;
	private boolean alarmed = false;

	public TrainSensorImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmFlag() {
		return alarmed;
	}

	@Override
	public void setAlarmFlag(boolean alarmed) {
		this.alarmed = alarmed;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		//Analyze speed limit
		//Absolute margin: 0 - 500
		boolean absolute = speedLimit < 0 || speedLimit > 500;
		//Relative margin: 50%
		boolean relative = controller.getReferenceSpeed() * 0.5 > speedLimit;

		setAlarmFlag(absolute || relative);

		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

}
