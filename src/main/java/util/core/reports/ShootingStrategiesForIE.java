package util.core.reports;

import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;

public final class ShootingStrategiesForIE {
	private ShootingStrategiesForIE() {
		throw new UnsupportedOperationException();
		
	}
	
	public static ShootingStrategy simple() {
		
		return new SimpleShootingStrategy();
	}

	public static ShootingStrategy viewportPasting(int scrollTimeout) {
		return (new ViewportPastingDecoratorForIE(simple())).withScrollTimeout(scrollTimeout);
	}
}
