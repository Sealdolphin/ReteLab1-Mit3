package hu.bme.mit.train.system;

import javax.swing.*;

public class TrainSimulation extends JFrame {

    public TrainSimulation(TrainSystem system) {
        //Initiating layout and default JFrame parameters
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Train Simulation");
        //Setting initial speed limit
        system.getSensor().overrideSpeedLimit(50);
        //Creating visual components
        JLabel lbSpeed = new JLabel("0");
        JLabel lbSpeedText = new JLabel("Speed:");
        JLabel lbLimit = new JLabel("Speed Limit");
        JLabel lbJoystick = new JLabel("Joystick");
        JSpinner spLimit = new JSpinner(new SpinnerNumberModel(50,50,120,1));
        JSlider sliderJoystick = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        //Creating timer
        /*
        The timer is set to 1 second delay.
        Every second it triggers the followSpeed event of the controller
        It also updates the speed value in the Speed label
         */
        Timer timer = new Timer(1000, actionEvent -> {
            system.getController().followSpeed();
            lbSpeed.setText(Integer.toString(system.getController().getReferenceSpeed()));
        });
        //Setting up speed limit spinner
        spLimit.addChangeListener(event ->
                system.getSensor().overrideSpeedLimit((int) spLimit.getValue()));
        //Creating joytick interface
        sliderJoystick.setMajorTickSpacing(5);
        sliderJoystick.setMinorTickSpacing(1);
        sliderJoystick.setPaintLabels(true);
        //Adding joystick change event -> use sensor's override function
        sliderJoystick.addChangeListener(event ->
                system.getUser().overrideJoystickPosition(sliderJoystick.getValue()));


        //Constructing horizontal layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(lbSpeedText).addGap(10).addComponent(lbSpeed)
                ).addGroup(
                        layout.createSequentialGroup()
                                .addComponent(lbLimit).addGap(10).addComponent(spLimit)
                ).addComponent(lbJoystick).addComponent(sliderJoystick)
        );
        //Constructing vertical layout
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lbSpeedText).addComponent(lbSpeed)
                ).addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbLimit).addComponent(spLimit)
                ).addGap(5).addComponent(lbJoystick).addComponent(sliderJoystick)
        );

        //Finalizing size and start timer
        pack();
        timer.start();
    }

}
