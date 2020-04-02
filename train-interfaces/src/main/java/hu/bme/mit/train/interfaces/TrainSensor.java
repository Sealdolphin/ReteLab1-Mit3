package hu.bme.mit.train.interfaces;

public interface TrainSensor {

	int getSpeedLimit();

	boolean getAlarmFlag();

	void setAlarmFlag(boolean alarmed);

	void overrideSpeedLimit(int speedLimit);

}
