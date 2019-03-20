package fr.fxjavadevblog.xr.commons.gamepads;

import com.badlogic.gdx.Input;
import com.kennycason.gdx.controller.KeyboardController;
import com.kennycason.gdx.controller.LogitechController;
import com.kennycason.gdx.controller.MultiplexedController;
import com.kennycason.gdx.controller.mapping.Axis;
import com.kennycason.gdx.controller.mapping.AxisMapper;
import com.kennycason.gdx.controller.mapping.ButtonMapper;

import fr.fxjavadevblog.xr.commons.utils.GameControls;

public final class ControllerFactory
{

	private ControllerFactory()
	{
		// protection
	}

	public static MultiplexedController<GameControls> buildMultiController()
	{
		return new MultiplexedController<>(buildKeyboard(), buildLogitech());
	}

	public static KeyboardController<GameControls> buildKeyboard()
	{
		final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();
		buttonMapper.map(GameControls.UP, Input.Keys.UP);
		buttonMapper.map(GameControls.DOWN, Input.Keys.DOWN);
		buttonMapper.map(GameControls.LEFT, Input.Keys.LEFT);
		buttonMapper.map(GameControls.RIGHT, Input.Keys.RIGHT);

		buttonMapper.map(GameControls.START, Input.Keys.SPACE);
		buttonMapper.map(GameControls.SELECT, Input.Keys.ENTER);

		buttonMapper.map(GameControls.A, Input.Keys.CONTROL_LEFT);
		buttonMapper.map(GameControls.B, Input.Keys.SHIFT_LEFT);
		buttonMapper.map(GameControls.X, Input.Keys.W);
		buttonMapper.map(GameControls.Y, Input.Keys.X);

		buttonMapper.map(GameControls.L1, Input.Keys.CONTROL_RIGHT);
		buttonMapper.map(GameControls.L2, Input.Keys.ALT_LEFT);
		buttonMapper.map(GameControls.R1, Input.Keys.C);
		buttonMapper.map(GameControls.R2, Input.Keys.D);

		return new KeyboardController<>(buttonMapper);
	}

	public static LogitechController<GameControls> buildLogitech()
	{
		final ButtonMapper<GameControls> buttonMapper = new ButtonMapper<>();
		// dpad buttons are read from axis's, not button codes directly
		// consider allowing axis's to be configured.

		buttonMapper.map(GameControls.START, 9);
		buttonMapper.map(GameControls.SELECT, 8);

		buttonMapper.map(GameControls.A, 1);
		buttonMapper.map(GameControls.B, 2);
		buttonMapper.map(GameControls.X, 0);
		buttonMapper.map(GameControls.Y, 3);

		// left joystick pressed 10
		// right joystick pressed 11

		buttonMapper.map(GameControls.L1, 4);
		buttonMapper.map(GameControls.L2, 6);
		buttonMapper.map(GameControls.R1, 5);
		buttonMapper.map(GameControls.R2, 7);

		// treating the axis / joystick as a typical d-pad
		final AxisMapper<GameControls> axisMapper = new AxisMapper<>();
		axisMapper.map(GameControls.UP, new Axis(1, -0.75f));
		axisMapper.map(GameControls.DOWN, new Axis(1, 0.75f));
		axisMapper.map(GameControls.LEFT, new Axis(0, -0.75f));
		axisMapper.map(GameControls.RIGHT, new Axis(0, 0.75f));

		// hook in joystick for raw usage, i.e you need precise control over the
		// joystick's position.
		axisMapper.map(GameControls.RIGHT_JOYSTICK_VERTICAL, new Axis(3, 0.01f));
		axisMapper.map(GameControls.RIGHT_JOYSTICK_HORIZONTAL, new Axis(2, 0.01f));

		return new LogitechController<>(0, buttonMapper, axisMapper);
	}

}
