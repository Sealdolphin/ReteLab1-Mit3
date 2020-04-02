package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockSettings;
import org.mockito.MockitoSession;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.session.MockitoSessionBuilder;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    MockitoSession session;
    TrainUser user;
    TrainController controller;
    TrainSensor sensor;

    @Before
    public void before() {
        user = mock(TrainUser.class);
        controller = mock(TrainController.class);
        session = mockitoSession().startMocking();
        sensor = new TrainSensorImpl(controller);
    }

    @Test
    public void testAlarmStateIdleOperation(){
        //Do nothing to be alarmed about
        Assert.assertFalse(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(60);
        Assert.assertFalse(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(20);
        sensor.overrideSpeedLimit(70);
        Assert.assertFalse(sensor.getAlarmFlag());
    }

    @Test
    public void testAbsoluteMarginAlarm(){
        //Changing speed limit in such a way, that absolute margin is violated
        Assert.assertFalse(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(-10);
        Assert.assertTrue(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(10);
        Assert.assertFalse(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(620);
        Assert.assertTrue(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(40);
        sensor.overrideSpeedLimit(550);
        Assert.assertTrue(sensor.getAlarmFlag());
    }

    @Test
    public void testRelativeMarginAlarm(){
        //Changing speed limit in such a way, that relative margin is violated
        Assert.assertFalse(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(300);
        Assert.assertFalse(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        Assert.assertTrue(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(50);
        sensor.overrideSpeedLimit(20);
        Assert.assertTrue(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(20);
        sensor.overrideSpeedLimit(15);
        Assert.assertFalse(sensor.getAlarmFlag());
    }

    @Test
    public void testAllMarginAlarm(){
        //Testing for both state
        Assert.assertFalse(sensor.getAlarmFlag());
        sensor.overrideSpeedLimit(1200);
        Assert.assertTrue(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(300);
        sensor.overrideSpeedLimit(550);
        Assert.assertTrue(sensor.getAlarmFlag());
        when(controller.getReferenceSpeed()).thenReturn(550);
        Assert.assertTrue(sensor.getAlarmFlag());
    }

    @After
    public void tearDown(){
        session.finishMocking();
    }
}
