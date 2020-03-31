package hu.bme.mit.train.system;

import javax.swing.*;

public class TrainSimulation extends JFrame {

    public TrainSimulation(TrainSystem system) {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        system.getSensor().overrideSpeedLimit(50);

        JLabel lbSpeed = new JLabel("0");

        Timer timer = new Timer(1000, actionEvent -> {
            system.getController().followSpeed();
            lbSpeed.setText(Integer.toString(system.getController().getReferenceSpeed()));
        });
        timer.start();

        JSpinner spLimit = new JSpinner(new SpinnerNumberModel(50,50,70,1));
        spLimit.addChangeListener(event ->
                system.getSensor().overrideSpeedLimit((int) spLimit.getValue()));

        JLabel lbSpeedText = new JLabel("Speed:");
        JSlider sliderJoystick = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        sliderJoystick.setMajorTickSpacing(5);
        sliderJoystick.setMinorTickSpacing(1);
        sliderJoystick.setPaintLabels(true);

        sliderJoystick.addChangeListener(event ->
                system.getUser().overrideJoystickPosition(sliderJoystick.getValue()));

        JLabel lbLimit = new JLabel("Speed Limit");
        JLabel lbJoystick = new JLabel("Joystick");


        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(lbSpeedText).addGap(10).addComponent(lbSpeed)
                ).addComponent(lbLimit).addComponent(spLimit).addComponent(lbJoystick).addComponent(sliderJoystick)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbSpeedText).addComponent(lbSpeed)
                )
                        .addComponent(lbLimit)
                        .addGap(5)
                        .addComponent(spLimit)
                        .addGap(5)
                        .addComponent(lbJoystick)
                        .addComponent(sliderJoystick)
        );
        pack();
    }

}
