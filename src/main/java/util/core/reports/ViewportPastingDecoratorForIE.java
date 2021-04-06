package util.core.reports;

import org.openqa.selenium.JavascriptExecutor;

import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ViewportPastingDecorator;

public class ViewportPastingDecoratorForIE extends ViewportPastingDecorator{
	public ViewportPastingDecoratorForIE(ShootingStrategy strategy)
	{
		super(strategy);
	}

	protected int getCurrentScrollY(JavascriptExecutor js) {
		return ((Number)js.executeScript("var scrY = window.pageYOffset;if(scrY>0){returnscrY;}else {return 0;}",new Object[0])).intValue();
	}
}
