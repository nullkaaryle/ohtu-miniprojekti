package ohtu.views;

import ohtu.data.ReferenceDAO;
import ohtu.views.MainView.MainViewListener;

public class MainPresenter implements MainViewListener {
	private MainView view;
	private ReferenceDAO dao;

	public MainPresenter(MainView view, ReferenceDAO dao) {
		this.view = view;
		this.dao = dao;

		view.setListener(this);
		updateLists();
	}

	@Override
	public void updateLists() {
		view.setReferences(dao.getAll());
	}
}
