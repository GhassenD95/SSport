package controllers;

import common.INavigation;
import services.utilities.NavigationService;

public abstract class BaseController implements INavigation {

    protected NavigationService navigationService;
    protected Object data;

    @Override
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }
}
